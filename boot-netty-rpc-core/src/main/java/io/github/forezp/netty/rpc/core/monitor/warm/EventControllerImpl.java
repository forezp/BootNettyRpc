package io.github.forezp.netty.rpc.core.monitor.warm;

import com.google.common.eventbus.EventBus;


/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public class EventControllerImpl implements EventController {
    private EventBus eventBus;

    public EventControllerImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object object) {
        eventBus.register(object);
    }

    @Override
    public void unregister(Object object) {
        eventBus.unregister(object);
    }

    @Override
    public void post(Event event) {
        eventBus.post(event);
    }


}
