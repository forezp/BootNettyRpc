package io.github.forezp.netty.rpc.core.common.enums;

import io.github.forezp.netty.rpc.core.common.constant.ConfigConstants;


/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public enum LoadBalancerType {

    RANDOM(ConfigConstants.LB_RANDOM),ROUND_ROBIN( ConfigConstants.LB_ROUNDROBIN);

    private String type;

    LoadBalancerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
