package io.github.forezp.netty.rpc.core.config.condition;

import io.github.forezp.netty.rpc.core.config.condition.PropertyEqualsCondition;

import static io.github.forezp.netty.rpc.core.common.constant.ConfigConstants.MONITOR_TYPE;
import static io.github.forezp.netty.rpc.core.common.constant.ConfigConstants.MONITOR_TYPE_REDIS;

/**
 * Created by forezp on 2018/6/2.
 */
public class RedisMonitorCondition extends PropertyEqualsCondition {

    public RedisMonitorCondition() {
        super(MONITOR_TYPE, MONITOR_TYPE_REDIS);
    }
}
