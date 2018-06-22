package com.forezp.examplerpcserver;

import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
public class ExampleRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcServerApplication.class, args );
    }


    @GetMapping("/test")
    public void test() {


    }
}
