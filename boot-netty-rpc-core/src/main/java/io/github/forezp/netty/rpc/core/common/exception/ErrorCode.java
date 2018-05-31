package io.github.forezp.netty.rpc.core.common.exception;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-18
 **/
public enum ErrorCode {

    REQUEST_PARSE_ERRPR( 11000, "json解析错误" ),
    REQUEST_MISS_ARGS( 11001, "参数不齐" );

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

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
}
