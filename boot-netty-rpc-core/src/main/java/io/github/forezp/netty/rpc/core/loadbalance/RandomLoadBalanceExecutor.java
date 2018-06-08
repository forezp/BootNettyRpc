package io.github.forezp.netty.rpc.core.loadbalance;

import io.github.forezp.netty.rpc.core.common.entity.NettyClient;
import io.github.forezp.netty.rpc.core.registar.RpcClientEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public class RandomLoadBalanceExecutor extends AbstractLoadBalanceExecutor {

    @Override
    protected RpcClientEntity loadBalance(RpcClientEntity rpcClientEntity , String interfaceName, List<NettyClient> candidates) {
        if (candidates == null || candidates.size() == 0) {
            return null;
        }
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int index = threadLocalRandom.nextInt( candidates.size() );
        NettyClient nettyClient=candidates.get( index );
        RpcClientEntity entity=new RpcClientEntity();
        BeanUtils.copyProperties( nettyClient,entity );
        entity.setIsSyn( rpcClientEntity.isSyn() );
        entity.setRpcClz( rpcClientEntity.getRpcClz() );
        return entity;
    }
}
