package io.github.forezp.netty.rpc.core.protocol.server;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectDecoder;
import io.github.forezp.netty.rpc.core.protocol.serializer.NettyObjectEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/

public class NettyServerExcutor extends AbstractServerExecutor {
    Logger LOG = LoggerFactory.getLogger(NettyServerExcutor.class);

    private AtomicBoolean start = new AtomicBoolean(false);

    @Override
    public void start(AppEntity appEntity) throws Exception {
        boolean started = started(appEntity);
        if (started) {
            return;
        }
        final String host = appEntity.getHost();
        final int port = appEntity.getPort();


        Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
            @Override
            public Object call() {
                EventLoopGroup group = new NioEventLoopGroup(1);
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(group)
                            .channel(NioServerSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .localAddress(new InetSocketAddress(port))
                            .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline()
                                            //.addLast( new LineBasedFrameDecoder( 2048 ) )
                                            //  .addLast( new StringDecoder() )
                                            .addLast(new NettyObjectDecoder(1024))
                                            .addLast(new NettyObjectEncoder())
                                            .addLast(new JdkZlibDecoder())
                                            .addLast(new JdkZlibEncoder())
                                            .addLast(new NettyServerHandler(getExcutorContainer()));
                                }
                            });

                    ChannelFuture f = b.bind().sync();
                    start.set(true);

                    LOG.info("netty server started successfully", host, port);
                    System.out.println("netty server started and listening for connections on " + f.channel().localAddress());
                    return f;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
                return null;
            }
        }).get();


    }

    @Override
    public boolean started(AppEntity appEntity) {
        return start.get();
    }
}
