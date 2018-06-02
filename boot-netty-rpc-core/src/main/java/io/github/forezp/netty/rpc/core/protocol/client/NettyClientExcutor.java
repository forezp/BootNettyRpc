package io.github.forezp.netty.rpc.core.protocol.client;


import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectDecoder;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectEncoder;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectDecoder;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class NettyClientExcutor extends AbstractClientExcutor {

    Logger LOG = LoggerFactory.getLogger(NettyClientExcutor.class);

    @Override
    public void start(AppEntity appEntity) throws Exception {

        CyclicBarrier barrier = new CyclicBarrier(2);
        connect(appEntity, barrier);
        // 主线程等待连接成功后，第一个await执行，唤醒当前连接线程和主线程
        // 两倍于Netty的连接超时事件，这么做防止服务端Netty连接还不完成，客户端就开始调用服务
        barrier.await(10000 * 2, TimeUnit.MILLISECONDS);
    }


    private void connect(final AppEntity appEntity, final CyclicBarrier barrier) throws Exception {
        final String host = appEntity.getHost();
        final int port = appEntity.getPort();
        LOG.info("Attempt to connect to {}:{}", host, port);

        final EventLoopGroup group = new NioEventLoopGroup(5);
        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    Bootstrap client = new Bootstrap();
                    client.group(group).channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .option(ChannelOption.SO_KEEPALIVE, true)

                            .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)

                            .handler(new LoggingHandler(LogLevel.INFO))
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel channel) throws Exception {
                                    channel.pipeline()
                                            .addLast(new IdleStateHandler(
                                                    90000,
                                                    60000,
                                                   45000, TimeUnit.MILLISECONDS))
                                            .addLast(new NettyObjectDecoder(1024))
                                            .addLast(new NettyObjectEncoder())
                                            .addLast(new JdkZlibDecoder())
                                            .addLast(new JdkZlibEncoder())
                                            .addLast(new WriteTimeoutHandler(15000))
                                            .addLast(new ReadTimeoutHandler(15000))
                                            .addLast(new NettyClientHandler(excutorContainer));
                                }
                            });

                    ChannelFuture channelFuture = null;
                    try {
                        channelFuture = client.connect(host, port).sync();
                    } catch (Exception e) {
                        LOG.info("Connect failed", e);

                        // 如果连不上，那么让本地缓存中对应的连接信息失效
                        offline(appEntity);

                        // 失效后，马上唤醒线程
                        if (barrier != null) {
                            barrier.await();
                        }

                        return null;
                    }

                    online(appEntity, channelFuture);

                    LOG.info("Connect to {}:{} successfully", host, port);

                    // 连接成功后唤醒当前连接线程和主线程，但要等待另外一个await
                    if (barrier != null) {
                        barrier.await();
                    }

                    // 作用：做阻塞线程用，一直等待服务端Channel关闭，然后结束线程
                    channelFuture.channel().closeFuture().sync();

                    offline(appEntity);
                } catch (Exception e) {
                    LOG.error("Client executor exception", e);
                } finally {
                    group.shutdownGracefully().sync();
                    TimeUnit.MILLISECONDS.sleep(20000);

                    // 避免开启多个相同的通道
                    boolean started = started(appEntity);
                    if (!started) {
                        LOG.info("Channel is closed, remote address={}:{}, try to reconnect...", host, port);
                        connect(appEntity, null);
                    }
                }
                return null;
            }
        });
    }


}
