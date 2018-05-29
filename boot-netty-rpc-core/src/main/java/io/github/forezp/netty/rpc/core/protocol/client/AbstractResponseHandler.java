package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;

/**
 * Created by forezp on 2018/5/26.
 */
public abstract class AbstractResponseHandler extends NettyRpcDelegateImpl implements MessageHandler {


    public abstract void handle(NettyRpcResponse response);

    @Override
    public void beforeHandle(NettyRpcMessage nettyRpcMessage) {

    }

    @Override
    public void afterHandle(NettyRpcMessage nettyRpcMessage) {

    }
}
