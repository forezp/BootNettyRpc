package io.github.forezp.netty.rpc.core.protocol.loadbalancer;

import io.github.forezp.netty.rpc.core.common.entity.NettyClient;
import io.github.forezp.netty.rpc.core.common.entity.RpcClientEntity;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public class RoundRobinLoadBalaceExecutor extends AbstractLoadBalanceExecutor {

    private Map<String, AtomicLong> indexMap = Maps.newConcurrentMap();

    @Override
    protected RpcClientEntity loadBalance(RpcClientEntity rpcClientEntity, String interfaceName, List<NettyClient> candidates) {
        AtomicLong atomicLong = indexMap.get( interfaceName );
        if (atomicLong == null) {
            atomicLong = new AtomicLong( 0 );
            indexMap.put( interfaceName, atomicLong );
        }
        int index = (int) (atomicLong.getAndAdd( 1 ) % candidates.size());
        NettyClient nettyClient = candidates.get( Math.abs( index ) );
        RpcClientEntity entity = new RpcClientEntity();
        BeanUtils.copyProperties( nettyClient, entity );
        entity.setIsSyn( rpcClientEntity.isSyn() );
        entity.setRpcClz( rpcClientEntity.getRpcClz() );
        return entity;
    }
}
