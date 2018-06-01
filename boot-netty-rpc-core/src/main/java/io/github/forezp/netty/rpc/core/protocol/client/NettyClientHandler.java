package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.HeaaBeat;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;

import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<NettyRpcResponse> {
    private Logger LOG = LoggerFactory.getLogger( NettyClientHandler.class );
    private ExcutorContainer excutorContainer;
    private boolean logHeartBeat = true;

    public NettyClientHandler(ExcutorContainer excutorContainer) {
        this.excutorContainer = excutorContainer;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final NettyRpcResponse response) throws Exception {


        ThreadPoolFactory.createClientPoolExecutor( response.getInterfaceClass().getName() ).submit( new Callable<NettyRpcResponse>() {

            @Override
            public NettyRpcResponse call() throws Exception {

                ResponseHandler responseHandler = excutorContainer.getResponseHandler();
                responseHandler.handle( response );
                ReferenceCountUtil.release( response );
                return null;
            }
        } );

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {

        if (cause instanceof ChannelException) {
            LOG.error( "Channel will be closed for {}", cause.getClass().getName() );

            context.close();
        }
        //TODO 发布异常处理事件
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object o) throws Exception {
        // 读写空闲时,发送心跳信息
        if (o instanceof IdleStateEvent) {
            /*IdleStateEvent event = (IdleStateEvent) e;
            IdleState state = event.state();
            if (state == IdleState.WRITER_IDLE) {*/
            NettyRpcRequest request = new NettyRpcRequest();
            request.setHeatBeat( true );
            request.setInterfaceClass( HeaaBeat.class );
            request.setMethod( "beat" );
            request.setSyn( false );

            if (logHeartBeat) {
                LOG.info( "Send heart beat request..." );
            }

            context.writeAndFlush( request );
        }

    }

    public SocketAddress getLocalAddress(ChannelHandlerContext context) {
        return context.channel().localAddress();
    }

    public SocketAddress getRemoteAddress(ChannelHandlerContext context) {
        return context.channel().remoteAddress();
    }
}
