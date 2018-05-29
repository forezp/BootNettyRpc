package io.github.forezp.netty.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-16
 **/
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcClient {

    /**
     * 对应的服务名
     *
     * @return
     */
    String name();

    /**
     * 需要调用的类
     *
     * @return
     */
    String rpcClz() default "";

    /**
     * 是否同步操作
     *
     * @return
     */
    boolean isSyn() default true;

    /**
     * 服务提供者的服务host
     *
     * @return
     */
    String host() default "localhost";

    /**
     * 服务提供者的端口
     *
     * @return
     */
    int port() default -1;


}
