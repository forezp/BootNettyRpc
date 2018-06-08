package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;
import io.github.forezp.netty.rpc.core.common.entity.ResponseSyncEntity;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-22
 **/
public class RequestInterceptor extends NettyRpcDelegateImpl implements IRequsetInterceptor {

    Logger LOG = LoggerFactory.getLogger( RequestInterceptor.class );

    AtomicInteger integer = new AtomicInteger(0);

    @Override
    public void invokeAsync(ChannelFuture channelFuture, NettyRpcRequest request) throws Exception {
        channelFuture.channel().writeAndFlush( request );
    }

    @Override
    public Object invokeSync(ChannelFuture channelFuture, NettyRpcRequest request) throws Exception {
        String messageId = request.getMessageId();
        request.setSyn( true );
        ResponseSyncEntity responseSyncEntity = new ResponseSyncEntity();
        CyclicBarrier barrier = new CyclicBarrier( 2 );
        responseSyncEntity.setBarrier( barrier );
        cacheContainer.getSyncEntityMap().put( messageId, responseSyncEntity );
        try {
            invokeAsync( channelFuture, request );
            barrier.await( 5000, TimeUnit.MILLISECONDS );//TODO 5s 超时没有处理
            return responseSyncEntity.getResult();
        } catch (Exception e) {
            LOG.info( "Exception  number:" + integer.getAndAdd( 1 ) );
            e.printStackTrace();
        } finally {
            cacheContainer.getSyncEntityMap().remove( messageId );
        }
        return null;

    }
}
