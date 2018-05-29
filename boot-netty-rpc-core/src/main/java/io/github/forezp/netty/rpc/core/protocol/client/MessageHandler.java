package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;

/**
 * Created by forezp on 2018/5/26.
 */
public interface MessageHandler {

    void beforeHandle(NettyRpcMessage nettyRpcMessage);

    void afterHandle(NettyRpcMessage nettyRpcMessage);
}
