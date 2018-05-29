package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.netty.channel.ChannelFuture;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-22
 **/
public interface IRequsetInterceptor {

    // 异步调用
    void invokeAsync(ChannelFuture channelFuture, NettyRpcRequest request) throws Exception;

    // 同步调用
    Object invokeSync(ChannelFuture channelFuture, NettyRpcRequest request) throws Exception;

}
