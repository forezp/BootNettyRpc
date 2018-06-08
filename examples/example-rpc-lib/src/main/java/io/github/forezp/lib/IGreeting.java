package com.forezp.examplerpclib.lib;

import io.github.forezp.netty.rpc.core.annotation.RpcClient;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * @create 2018-05-18
 **/
@RpcClient(name = "server")
public interface IGreeting {

    String sayHello(String name);
}
