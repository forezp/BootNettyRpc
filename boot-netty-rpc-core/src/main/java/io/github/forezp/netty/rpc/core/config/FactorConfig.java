package io.github.forezp.netty.rpc.core.config;

import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public class FactorConfig {

    public void setThreadPoolFactory(CommonProperties commonProperties) {
        ThreadPoolFactory.setCommonProperties( commonProperties );
    }
}
