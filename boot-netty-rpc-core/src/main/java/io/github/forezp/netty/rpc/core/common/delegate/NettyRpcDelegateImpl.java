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
public class NettyRpcDelegateImpl implements NettyRpcDelegate {

    protected NettyRpcProperties nettyRpcProperties;
    protected CacheContainer cacheContainer;
    protected ExcutorContainer excutorContainer;

    @Override
    public NettyRpcProperties getNettyRpcProperties() {
        return nettyRpcProperties;
    }

    @Override
    public void setNettyRpcProperties(NettyRpcProperties nettyRpcProperties) {
        this.nettyRpcProperties = nettyRpcProperties;
    }

    @Override
    public CacheContainer getCacheContainer() {
        return cacheContainer;
    }

    @Override
    public void setCacheContainer(CacheContainer cacheContainer) {
        this.cacheContainer = cacheContainer;
    }

    @Override
    public ExcutorContainer getExcutorContainer() {
        return excutorContainer;
    }

    @Override
    public void setExcutorContainer(ExcutorContainer excutorContainer) {
        this.excutorContainer = excutorContainer;
    }
}
