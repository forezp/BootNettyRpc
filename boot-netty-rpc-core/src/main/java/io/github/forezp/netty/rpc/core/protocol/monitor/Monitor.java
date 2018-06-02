package io.github.forezp.netty.rpc.core.protocol.monitor;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-01
 **/
public interface Monitor {

    MonitorMessage create(NettyRpcMessage message);

    void execute(MonitorMessage message);
}
