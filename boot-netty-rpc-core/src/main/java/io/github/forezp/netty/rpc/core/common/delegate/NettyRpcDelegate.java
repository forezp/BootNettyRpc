package io.github.forezp.netty.rpc.core.common.delegate;

import io.github.forezp.netty.rpc.core.common.container.CacheContainer;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;


/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-25
 **/
public interface NettyRpcDelegate {

    NettyRpcProperties getNettyRpcProperties();

    void setNettyRpcProperties(NettyRpcProperties nettyRpcProperties);

    CacheContainer getCacheContainer();

    void setCacheContainer(CacheContainer cacheContainer);

    ExcutorContainer getExcutorContainer();


    void setExcutorContainer(ExcutorContainer excutorContainer);

}
