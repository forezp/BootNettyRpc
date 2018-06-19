package io.github.forezp.netty.rpc.core.monitor.trace;

import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import io.github.forezp.netty.rpc.core.protocol.http.ApacheAsyncClientExecutor;
import io.github.forezp.netty.rpc.core.protocol.serializer.SerializerExecutor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by forezp on 2018/6/2.
 */
public class HttpMonitor extends AbstractMonitor {

    private static final Logger LOG = LoggerFactory.getLogger(HttpMonitor.class);

    private CloseableHttpAsyncClient httpAsyncClient;

    @Override
    public void execute(MonitorMessage message) {

        if (message == null) {
            return;
        }
        String url = nettyRpcProperties.getCommonProperties().getMonitorUrl();
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        String value = SerializerExecutor.toJson(message);

        HttpEntity entity = new StringEntity(value, "utf-8");

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("content-type", "application/json;charset=utf-8");
        httpPost.setEntity(entity);

        HttpAsyncCallback httpAsyncCallback = new HttpAsyncCallback();
        httpAsyncCallback.setHttpPost(httpPost);

        httpAsyncClient.execute(httpPost, httpAsyncCallback);

    }

    @Override
    public void setNettyRpcProperties(NettyRpcProperties properties) {
        super.setNettyRpcProperties(properties);

        try {
            ApacheAsyncClientExecutor clientExecutor = new ApacheAsyncClientExecutor();
            clientExecutor.initialize(properties);
            httpAsyncClient = clientExecutor.getClient();
        } catch (Exception e) {
            LOG.error("Get htty async client failed", e);
        }
    }

    public class HttpAsyncCallback implements FutureCallback<HttpResponse> {
        private HttpPost httpPost;

        public void setHttpPost(HttpPost httpPost) {
            this.httpPost = httpPost;
        }

        @Override
        public void completed(HttpResponse httpResponse) {
            httpPost.reset();
        }

        @Override
        public void failed(Exception e) {
            httpPost.reset();

            LOG.error("Monitor web service invoke failed, url={}", httpPost.getURI(), e);
        }

        @Override
        public void cancelled() {
            httpPost.reset();
        }
    }

}
