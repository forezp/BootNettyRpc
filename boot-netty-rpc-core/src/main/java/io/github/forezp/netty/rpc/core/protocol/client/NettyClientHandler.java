package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;

import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger LOG = LoggerFactory.getLogger(NettyClientHandler.class);
    private ExcutorContainer excutorContainer;

    public NettyClientHandler(ExcutorContainer excutorContainer) {
        this.excutorContainer = excutorContainer;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final NettyRpcResponse response) throws Exception {

        ThreadPoolFactory.createClientPoolExecutor( response.getInterfaceClz().getName()).submit( new Callable<NettyRpcResponse>() {

            @Override
            public NettyRpcResponse call() throws Exception {

                ResponseHandler responseHandler = excutorContainer.getResponseHandler();
                responseHandler.handle(response);
                ReferenceCountUtil.release(response);
                return null;
            }
        });

    }
}
