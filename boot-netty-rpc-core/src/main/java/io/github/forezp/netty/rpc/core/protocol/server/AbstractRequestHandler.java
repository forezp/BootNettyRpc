package io.github.forezp.netty.rpc.core.protocol.server;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;
import io.github.forezp.netty.rpc.core.protocol.client.MessageHandler;
import io.github.forezp.netty.rpc.core.protocol.client.MessageHandler;

/**
 * Created by forezp on 2018/5/26.
 */
public abstract class AbstractRequestHandler extends NettyRpcDelegateImpl implements MessageHandler {


    public abstract void handle(NettyRpcRequest request, NettyRpcResponse response);

    @Override
    public void beforeHandle(NettyRpcMessage nettyRpcMessage) {

        if (nettyRpcMessage instanceof NettyRpcResponse) {
            NettyRpcResponse response = (NettyRpcResponse) nettyRpcMessage;
            response.setStartHandleTime(System.currentTimeMillis());
        }
    }

    @Override
    public void afterHandle(NettyRpcMessage nettyRpcMessage) {

        if (nettyRpcMessage instanceof NettyRpcResponse) {
            NettyRpcResponse response = (NettyRpcResponse) nettyRpcMessage;
            response.setEndHandleTime(System.currentTimeMillis());
        }

    }
}
