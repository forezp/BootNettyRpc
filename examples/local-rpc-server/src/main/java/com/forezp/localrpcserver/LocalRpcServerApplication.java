package com.forezp.localrpcserver;


import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
@RestController

public class LocalRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( LocalRpcServerApplication.class, args );
    }

    @GetMapping("/test")
    public String test() {
        return "server : test";
    }
}
