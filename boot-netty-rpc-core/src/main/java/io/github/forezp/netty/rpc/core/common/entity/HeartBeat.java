package io.github.forezp.netty.rpc.core.common.entity;

import java.io.Serializable;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-01
 **/

public class HeartBeat implements Serializable {
    private static final long serialVersionUID = 7616648596751123394L;

    public String beat() {
        return "beat";
    }
}