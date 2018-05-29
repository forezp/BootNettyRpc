package io.github.forezp.netty.rpc.core.protocol.loadbalancer;

import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegate;
import io.github.forezp.netty.rpc.core.common.entity.RpcClientEntity;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public interface LoadBalanceExcutor extends NettyRpcDelegate {

    RpcClientEntity loadBalance(RpcClientEntity rpcClientEntity, String interfaceName);

}
