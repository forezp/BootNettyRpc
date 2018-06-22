package io.github.forezp.netty.rpc.core.monitor.warm;

import com.google.common.eventbus.Subscribe;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public abstract class AbstractEventHandler {


    public AbstractEventHandler() {
        EventControllerFactory.getAsyncController().register( this );
    }

    @Subscribe
    public void listen(Event event) {
        onEvent( event );
    }

    public abstract void onEvent(Event event);
}
