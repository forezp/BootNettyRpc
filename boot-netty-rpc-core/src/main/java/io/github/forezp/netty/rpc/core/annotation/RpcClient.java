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

    /**
     * traceId的在方法参数中的位置，默认为0，只能一个类全局配置，不支持单个方法配置。
     * traceId 用于分布式链路追踪，需要手动传入，如果没有这个需求可不做任何配置即可
     * @return
     */
    int traceIdIndex() default 0;


}
