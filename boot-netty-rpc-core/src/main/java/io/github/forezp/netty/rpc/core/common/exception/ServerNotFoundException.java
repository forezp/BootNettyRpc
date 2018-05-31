package io.github.forezp.netty.rpc.core.common.exception;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-16
 **/
public class ServerNotFoundException extends RuntimeException {

    public ServerNotFoundException() {

    }

    public ServerNotFoundException(String msg) {
        super( msg );
    }

}
