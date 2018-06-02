package io.github.forezp.netty.rpc.core.protocol.http;


import io.github.forezp.netty.rpc.core.config.CommonProperties;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

/**
 * Created by forezp on 2018/6/2.
 */

public class ApacheAsyncClientExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(ApacheAsyncClientExecutor.class);

    private CloseableHttpAsyncClient httpAsyncClient;

    public void initialize(final NettyRpcProperties properties) throws Exception {
        final CyclicBarrier barrier = new CyclicBarrier(2);
        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {



                    IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                            .setIoThreadCount(CommonProperties.CPUS*2)
                            .setConnectTimeout(5000)
                            .setSoTimeout(5000)
                            .setSndBufSize(64 * 1024)
                            .setRcvBufSize(64 * 1024)
                            .setBacklogSize(128)
                            .setTcpNoDelay(true)
                            .setSoReuseAddress(true)
                            .setSoKeepAlive(true)
                            .build();
                    ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
                    PoolingNHttpClientConnectionManager httpManager = new PoolingNHttpClientConnectionManager(ioReactor);
                    httpManager.setMaxTotal(32);

                    httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(httpManager).build();
                    httpAsyncClient.start();

                    LOG.info("Create apache async client successfully");

                    barrier.await();
                } catch (IOReactorException e) {
                    LOG.error("Create apache async client failed", e);
                }

                return null;
            }
        });

        barrier.await();
    }

    public CloseableHttpAsyncClient getClient() {
        return httpAsyncClient;
    }
}