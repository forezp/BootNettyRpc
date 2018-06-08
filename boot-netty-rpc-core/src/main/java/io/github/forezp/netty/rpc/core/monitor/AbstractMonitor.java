package io.github.forezp.netty.rpc.core.monitor;

import io.github.forezp.netty.rpc.core.common.delegate.NettyRpcDelegateImpl;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-01
 **/
public abstract class AbstractMonitor extends NettyRpcDelegateImpl implements Monitor {

    @Override
    public MonitorMessage create(NettyRpcMessage message) {
        if (StringUtils.isEmpty( message.getTraceId() )) {
            return null;
        }
        MonitorMessage monitorMessage = new MonitorMessage();
        BeanUtils.copyProperties( message, monitorMessage );
        monitorMessage.setInterfaceClassName( message.getInterfaze() );
        return monitorMessage;
    }

}
