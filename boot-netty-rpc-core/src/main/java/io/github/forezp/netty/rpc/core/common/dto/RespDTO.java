package io.github.forezp.netty.rpc.core.common.dto;


import io.github.forezp.netty.rpc.core.exception.ErrorCode;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-22
 **/
public class RespDTO<T> implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;

    private int code;
    private String msg;
    private T result;

    public RespDTO() {

    }

    public RespDTO(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static RespDTO fail(ErrorCode errorCode) {
        return new RespDTO( errorCode.getCode(), errorCode.getMsg(), null );
    }

    public static <T> RespDTO success(T result) {
        return new RespDTO( 1, "success", result );
    }

    public static RespDTO success() {
        return new RespDTO( 1, "success", null );
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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
}
