package io.github.forezp.netty.rpc.core.monitor.warm;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public class EventControllerFactory {

    private static final String SHARED_ANSY_CONTROLLER = "sharedAnsyController";

    public static ConcurrentHashMap<String, EventController> SYN_EVENT_CONTROLLER = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, EventController> ASYNC_EVENT_CONTROLLER = new ConcurrentHashMap<>();


    public static EventController getAsyncController() {
        return get( SHARED_ANSY_CONTROLLER, EventControllerType.ASYN );
    }

    public static EventController get(String identifier, EventControllerType type) {
        switch (type) {
            case SYN:
                EventController synController = SYN_EVENT_CONTROLLER.get( identifier );
                if (synController == null) {
                    synController = new EventControllerImpl( new EventBus( identifier ) );
                    SYN_EVENT_CONTROLLER.putIfAbsent( identifier, synController );
                }
                return synController;

            case ASYN:
                EventController asynController = ASYNC_EVENT_CONTROLLER.get( identifier );
                if (asynController == null) {
                    asynController = new EventControllerImpl( new AsyncEventBus( identifier, ThreadPoolFactory.createThreadPoolDefaultExecutor() ) );
                    ASYNC_EVENT_CONTROLLER.putIfAbsent( identifier, asynController );
                }
                return asynController;
        }
        return null;
    }
}
