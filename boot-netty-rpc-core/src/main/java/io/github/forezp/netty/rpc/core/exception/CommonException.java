package io.github.forezp.netty.rpc.core.exception;

/**
 *
 * @author fangzhipeng
 * create 2018-05-18
 **/
public class CommonException extends RuntimeException {

    public CommonException() {
    }

    public CommonException(String msg) {
        super( msg );
    }
}
