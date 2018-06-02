package io.github.forezp.netty.rpc.core.registry;

import com.google.common.collect.Maps;
import io.github.forezp.netty.rpc.core.common.constant.NettyRpcConstants;
import io.github.forezp.netty.rpc.core.common.entity.NettyClient;
import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fangzhipeng
 * create 2018-05-31
 **/
public class ClientDiscoveryImpl extends AbstractClientDiscovery {

    Logger LOG = LoggerFactory.getLogger( ClientDiscoveryImpl.class );

    private DiscoveryClient discoveryClient;
    ScheduledExecutorService scheduledExecutorService;

    private Map<String, List<NettyClient>> nettyClietMap = Maps.newConcurrentMap();

    public ClientDiscoveryImpl(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public void init() {
        refresh();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //刚启动每10s查询一次，之后每30s查询一次。
        scheduledExecutorService.schedule( new DiscoveryClientTask(), 10, TimeUnit.SECONDS );
        scheduledExecutorService.schedule( new DiscoveryClientTask(), 20, TimeUnit.SECONDS );
        scheduledExecutorService.scheduleAtFixedRate( new DiscoveryClientTask(), 30,
                Integer.parseInt( nettyRpcProperties.getCommonProperties().getNettyClientRenewInterval() ), TimeUnit.SECONDS );

    }

    @Override
    public void refresh() {
        List<String> services = discoveryClient.getServices();
        if (!CollectionUtils.isEmpty( services )) {
            for (String serviceId : services) {
                List<ServiceInstance> serviceInstances = discoveryClient.getInstances( serviceId );
                List<NettyClient> nettyClients = convertNettyClients( serviceInstances );
                if (CollectionUtils.isEmpty( nettyClients )) {
                    continue;
                }
                List<NettyClient> newCopyOnWriteList = new CopyOnWriteArrayList<>( nettyClients );
                if (!nettyClietMap.containsKey( serviceId )) {
                    nettyClietMap.putIfAbsent( serviceId, newCopyOnWriteList );
                    continue;
                }
                List<NettyClient> copyOnWriteList = nettyClietMap.get( serviceId );
                if (isNeedUpdate( copyOnWriteList, newCopyOnWriteList )) {
                    LOG.info( "{}: need to update serverList", serviceId );
                    copyOnWriteList = newCopyOnWriteList;
                    nettyClietMap.putIfAbsent( serviceId, copyOnWriteList );
                }
            }
        }
    }


    private List<NettyClient> convertNettyClients(List<ServiceInstance> serviceInstances) {
        if (CollectionUtils.isEmpty( serviceInstances )) {
            return null;
        }
        List<NettyClient> nettyClients = new CopyOnWriteArrayList<>();
        for (ServiceInstance serviceInstance : serviceInstances) {
            NettyClient nettyClient = new NettyClient();
            Map<String, String> metadata = serviceInstance.getMetadata();
            String host = metadata.get( NettyRpcConstants.NETTY_SERVER_HOST );
            if (StringUtils.isEmpty( host )) {
                host = serviceInstance.getHost();
            }
            nettyClient.setHost( host );

            String name = metadata.get( NettyRpcConstants.NETTY_SERVER_NAME );
            if (StringUtils.isEmpty( name )) {
                name = serviceInstance.getServiceId();
            }
            nettyClient.setName( name );

            String port = metadata.get( NettyRpcConstants.NETTY_SERVER_PORT );
            if (StringUtils.isEmpty( port )) {
                throw new CommonException( "netty server " + name + " 's port connot be null" );
            }
            nettyClient.setPort( Integer.valueOf( port ) );
            nettyClients.add( nettyClient );
        }
        return nettyClients;


    }


    private boolean isNeedUpdate(List<NettyClient> copyOnWriteList, List<NettyClient> newCopyOnWriteList) {

        if (copyOnWriteList.size() == newCopyOnWriteList.size()) {

            for (NettyClient serviceInstance : copyOnWriteList) {
                boolean isSame = false;
                for (NettyClient newServiceInstance : newCopyOnWriteList) {
                    if (newServiceInstance.getHost().equals( serviceInstance.getHost() ) && newServiceInstance.getPort() == serviceInstance.getPort()) {
                        isSame = true;
                        break;
                    }
                }
                if (!isSame) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public void destroy() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdownNow();
        }
    }

    class DiscoveryClientTask implements Runnable {

        @Override
        public void run() {
            refresh();
        }
    }

    @Override
    public Map<String, List<NettyClient>> getNettyClietMap() {
        return nettyClietMap;
    }
}
