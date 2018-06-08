package com.forezp.examplerpcclient;

import com.forezp.examplerpclib.lib.IGreeting;
import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;

import io.github.forezp.netty.rpc.core.common.dto.RespDTO;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableNettyRpc(basePackages = "com.forezp")
@EnableEurekaClient
@EnableDiscoveryClient
public class ExampleRpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcClientApplication.class, args );
    }

    @Autowired
    NettyRpcProperties properties;

    @Autowired
    IGreeting greeting;

    @GetMapping("/test")
    public RespDTO test() throws Exception {
        // IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
        //   Object result = invoker.sayHello( "sww" );
        String resuult = greeting.sayHello( "miya" );
        return RespDTO.success( resuult );
    }


}
