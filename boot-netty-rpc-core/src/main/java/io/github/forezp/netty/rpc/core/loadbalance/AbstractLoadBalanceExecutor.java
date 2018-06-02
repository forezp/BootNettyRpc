package io.github.forezp.netty.rpc.core.loadbalance;

import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;
import io.github.forezp.netty.rpc.core.common.entity.NettyClient;
import io.github.forezp.netty.rpc.core.common.entity.RpcClientEntity;
import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public abstract class AbstractLoadBalanceExecutor extends NettyRpcDelegateImpl implements LoadBalanceExcutor {

    private Logger LOG = LoggerFactory.getLogger( AbstractLoadBalanceExecutor.class );

    @Override
    public RpcClientEntity loadBalance(RpcClientEntity rpcClientEntity, String interfaceName) {
        List<NettyClient> clients = null;
        if (excutorContainer.getClientDiscovery() != null) {
            clients = excutorContainer.getClientDiscovery().getNettyClietMap().get( rpcClientEntity.getName() );
        }
        if (clients == null) {
            clients = nettyRpcProperties.getClients();
        }
        List<NettyClient> candidates = null;
        if (clients != null && clients.size() > 0) {
            candidates = findCandidateClients( rpcClientEntity.getName(), clients );
        }
        if (candidates == null) {
            LOG.error( "netty client host or port is null" );
            throw new CommonException( "netty client host or port is null" );
        }
        return loadBalance( rpcClientEntity, interfaceName, candidates );
    }

    //TODO 这一步可以做缓存，减少遍历，暂缓
    private List<NettyClient> findCandidateClients(String name, List<NettyClient> list) {

        List<NettyClient> candidates = new ArrayList<>();
        for (NettyClient client : list) {
            if (name.equals( client.getName() )) {
                candidates.add( client );
            }
        }
        return candidates;

    }

    protected abstract RpcClientEntity loadBalance(RpcClientEntity rpcClientEntity, String interfaceName, List<NettyClient> candidates);
}
