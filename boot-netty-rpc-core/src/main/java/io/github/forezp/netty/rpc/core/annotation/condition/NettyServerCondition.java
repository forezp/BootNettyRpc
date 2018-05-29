package io.github.forezp.netty.rpc.core.annotation.condition;

import io.github.forezp.netty.rpc.core.common.constant.ConfigConstants;

/**
 * @author fangzhipeng
 * create 2018-05-27
 **/
public class NettyServerCondition extends PropertyContainsCondition {

    public NettyServerCondition() {
        super( ConfigConstants.NETTY_SERVSER_PORT );
    }
}
