package io.github.forezp.netty.rpc.core.protocol.server;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Callable;
/**
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<NettyRpcRequest> {


    private ExcutorContainer excutorContainer;

    public NettyServerHandler(ExcutorContainer excutorContainer) {
        this.excutorContainer = excutorContainer;
    }

    private Logger LOG = LoggerFactory.getLogger( NettyServerHandler.class );


    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final NettyRpcRequest request) {

        ThreadPoolFactory.createServerPoolExecutor( request.getInterfaceClass().getName() ).submit( new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                LOG.info( "Server received: " + request.toString() );
                NettyRpcResponse response = new NettyRpcResponse( 1, "sucess", null );
                excutorContainer.getServerRequestHandler().handle( request, response );
                ctx.writeAndFlush( response );
                ReferenceCountUtil.release( request );
                return null;
            }
        } );

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
