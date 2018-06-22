package io.github.forezp.netty.rpc.core.monitor.warm;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-22
 **/
public class TimeoutEvent extends Event {

    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
