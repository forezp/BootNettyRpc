package com.forezp.localrpclient.client;

import io.github.forezp.netty.rpc.core.annotation.RpcClient;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-08
 **/
@RpcClient(name = "server", rpcClz = "com.forezp.localrpcserver.api.Greeting")
public interface IGreeting {

    String sayHello(String name);

}
