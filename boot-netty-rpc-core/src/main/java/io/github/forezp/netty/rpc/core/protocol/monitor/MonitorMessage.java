package io.github.forezp.netty.rpc.core.protocol.monitor;

import java.io.Serializable;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-01
 **/
public class MonitorMessage implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;
    private String messageId;
    private Long startTime;
    private String traceId;
    private boolean isHeatBeat;
    private Long startHandleTime;
    private Long endHandleTime;
    private Long endTime;
    private String method;
    private String interfaceClassName;

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

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public void setInterfaceClassName(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
    }
}
