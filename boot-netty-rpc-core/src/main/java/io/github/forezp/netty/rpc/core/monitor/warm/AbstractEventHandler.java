package io.github.forezp.netty.rpc.core.monitor.warm;

import com.google.common.eventbus.Subscribe;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public abstract class AbstractEventHandler implements EventHandler {


    @Subscribe
    @Override
    public void listen(Event event) {
        onEvent();
    }

    public abstract void onEvent();
}
