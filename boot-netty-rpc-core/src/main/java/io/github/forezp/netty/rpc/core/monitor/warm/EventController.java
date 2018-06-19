package io.github.forezp.netty.rpc.core.monitor.warm;

import com.google.common.eventbus.EventBus;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public interface EventController {

    void register(Object object);

    void unregister(Object object);

    void post(Event event);

}
