package io.github.forezp.netty.rpc.core.common.entity;

import java.io.Serializable;

/**
 * Created by forezp on 2018/5/26.
 */
public class NettyRpcMessage implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;

    protected String messageId;

    protected Long startTime;

    protected String traceId;

    protected boolean isHeatBeat;

    protected Long startHandleTime;
    protected Long endHandleTime;
    protected Long endTime;

    protected String method;


    protected String interfaze;

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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public boolean isHeatBeat() {
        return isHeatBeat;
    }

    public void setHeatBeat(boolean heatBeat) {
        isHeatBeat = heatBeat;
    }

    public Long getStartHandleTime() {
        return startHandleTime;
    }

    public void setStartHandleTime(Long startHandleTime) {
        this.startHandleTime = startHandleTime;
    }

    public Long getEndHandleTime() {
        return endHandleTime;
    }

    public void setEndHandleTime(Long endHandleTime) {
        this.endHandleTime = endHandleTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public void setInterfaze(String interfaze) {
        this.interfaze = interfaze;
    }
}
