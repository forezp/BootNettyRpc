package io.github.forezp.netty.rpc.core.protocol.http;


import io.github.forezp.netty.rpc.core.config.CommonProperties;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by forezp on 2018/6/2.
 */


public class ApacheSyncClientExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(ApacheSyncClientExecutor.class);

    private CloseableHttpClient httpSyncClient;

    public void initialize(NettyRpcProperties properties) throws Exception {
        initialize(properties, false);
    }

    public void initialize(NettyRpcProperties properties, boolean https) throws Exception {
        CommonProperties cp = properties.getCommonProperties();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(cp.getHttpConnectTimeout()))
                .setConnectionRequestTimeout(Integer.parseInt(cp.getHttpConnectRequestTimeout()))
                .setSocketTimeout(Integer.parseInt(cp.getHttpSocketTimeout()))
                .build();

        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setDefaultRequestConfig(requestConfig);

        if (https) {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }

            }).build();
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
        }

        httpSyncClient = clientBuilder.build();

        LOG.info("Create apache sync client with {} successfully", https ? "https mode" : "http mode");
    }

    public CloseableHttpClient getClient() {
        return httpSyncClient;
    }
}