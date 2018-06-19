package io.github.forezp.netty.rpc.core.monitor.warm;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public class Event<T> {

    private T msg;

    public Event(T msg) {
        this.msg = msg;
    }
}
