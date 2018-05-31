package io.github.forezp.netty.rpc.core.protocol.registry;

import io.github.forezp.netty.rpc.core.common.entity.NettyClient;

import java.util.List;
import java.util.Map;

/**
 * @author fangzhipeng
 * create 2018-05-31
 **/
public interface ClientDiscovery {
    Map<String, List<NettyClient>> getNettyClietMap();

    void refresh();
}
