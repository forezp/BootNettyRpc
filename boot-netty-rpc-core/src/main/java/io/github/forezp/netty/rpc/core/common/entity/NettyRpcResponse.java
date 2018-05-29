package io.github.forezp.netty.rpc.core.common.entity;


import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-18
 **/

public class NettyRpcResponse<T> extends NettyRpcMessage implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;


    private Long startHandleTime;
    private Long endHandleTime;
    private Long endTime;
    private int code;
    private String msg;
    private T result;

    private Class interfaceClz;

    private boolean isSyn;

    public NettyRpcResponse() {

    }

    public NettyRpcResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public NettyRpcResponse(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    public boolean isSyn() {
        return isSyn;
    }

    public void setSyn(boolean syn) {
        isSyn = syn;
    }


    public Class getInterfaceClz() {
        return interfaceClz;
    }

    public void setInterfaceClz(Class interfaceClz) {
        this.interfaceClz = interfaceClz;
    }

    @Override
    public String toString() {
        return "NettyRpcResponse{" +
                "messageId='" + messageId + '\'' +
                ", startTime=" + startTime +
                ", startHandleTime=" + startHandleTime +
                ", endHandleTime=" + endHandleTime +
                ", endTime=" + endTime +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                ", isSyn=" + isSyn +
                '}';
    }
}
