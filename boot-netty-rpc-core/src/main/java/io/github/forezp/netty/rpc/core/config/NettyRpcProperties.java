package io.github.forezp.netty.rpc.core.config;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.common.entity.NettyClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-25
 **/

@ConfigurationProperties(prefix = "netty")
public class NettyRpcProperties {

    AppEntity server;
    List<NettyClient> clients = new ArrayList<>();
    CommonProperties commonProperties;

    public List<NettyClient> getClients() {
        return clients;
    }

    public void setClients(List<NettyClient> clients) {
        this.clients = clients;
    }

    public AppEntity getServer() {
        return server;
    }

    public void setServer(AppEntity server) {
        this.server = server;
    }

    public CommonProperties getCommonProperties() {
        return commonProperties;
    }

    public void setCommonProperties(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }
}

