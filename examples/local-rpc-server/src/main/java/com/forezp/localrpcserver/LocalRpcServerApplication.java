package com.forezp.localrpcserver;

import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
public class LocalRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( LocalRpcServerApplication.class, args );
    }
}
