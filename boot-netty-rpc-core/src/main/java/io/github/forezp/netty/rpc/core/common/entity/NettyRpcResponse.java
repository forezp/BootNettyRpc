package io.github.forezp.netty.rpc.core.common.entity;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-18
 **/

public class NettyRpcResponse<T> extends NettyRpcMessage implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;
    private int code;
    private String msg;
    private T result;


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
