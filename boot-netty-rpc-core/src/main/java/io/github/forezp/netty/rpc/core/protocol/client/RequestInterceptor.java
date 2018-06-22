package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;
import io.github.forezp.netty.rpc.core.common.entity.ResponseSyncEntity;
import io.github.forezp.netty.rpc.core.monitor.warm.EventControllerFactory;
import io.github.forezp.netty.rpc.core.monitor.warm.TimeoutEvent;
import io.github.forezp.netty.rpc.core.util.ExceptionUtil;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.forezp.netty.rpc.core.common.constant.ConfigConstants.MONITOR_MAIL_ENABLE_TRUE;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-22
 **/
public class RequestInterceptor extends NettyRpcDelegateImpl implements IRequsetInterceptor {

    Logger LOG = LoggerFactory.getLogger( RequestInterceptor.class );

    AtomicInteger timeoutNum = new AtomicInteger( 99 );

    @Override
    public void invokeAsync(ChannelFuture channelFuture, NettyRpcRequest request) {
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
            LOG.info( "Exception  number:" + timeoutNum.getAndAdd( 1 ) );
            e.printStackTrace();
            handleException( messageId, e );
        } finally {
            cacheContainer.getSyncEntityMap().remove( messageId );
        }
        return null;

    }

    private void handleException(String messageId, Exception e) {
        String mailEnable = nettyRpcProperties.getCommonProperties().getMailEnable();
        if (StringUtils.isEmpty( mailEnable ) || !mailEnable.equals( MONITOR_MAIL_ENABLE_TRUE )) {
            return;
        }
        if (timeoutNum.get() % 100 == 0) {//发送报警邮箱
            String eMsg = ExceptionUtil.toExceptionString( e );
            TimeoutEvent event = new TimeoutEvent();
            event.setMessageId( messageId );
            event.setMsg( eMsg );
            EventControllerFactory.getAsyncController().post( event );
        }
    }
}
