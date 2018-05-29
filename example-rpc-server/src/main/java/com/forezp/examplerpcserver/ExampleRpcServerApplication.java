package com.forezp.examplerpcserver;

import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import io.github.forezp.netty.rpc.core.common.entity.RpcClientEntity;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import io.github.forezp.netty.rpc.core.util.NettyRpcApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
@RestController
public class ExampleRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcServerApplication.class, args );
    }

    @Autowired
    NettyRpcProperties serversConfig;



    @GetMapping("/test")
    public void test() {
        RpcClientEntity rpcEntity = (RpcClientEntity) NettyRpcApplication.getBean( "com.forezp.atlasrpcframework.api.IGreeting" );
        if (rpcEntity != null) {

        }

    }
}
