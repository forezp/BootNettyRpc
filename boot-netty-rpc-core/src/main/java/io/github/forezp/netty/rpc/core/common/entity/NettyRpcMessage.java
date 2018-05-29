package io.github.forezp.netty.rpc.core.common.entity;

import java.io.Serializable;

/**
 * Created by forezp on 2018/5/26.
 */
public class NettyRpcMessage implements Serializable{

    private static final long serialVersionUID = 6395330487058794296L;

    protected String messageId;

    protected Long startTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
