package com.forezp.examplerpcclient;

import com.forezp.examplerpclib.lib.IGreeting;
import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;

import io.github.forezp.netty.rpc.core.common.dto.RespDTO;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import io.github.forezp.netty.rpc.core.protocol.client.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableNettyRpc(basePackages = "com.forezp")
public class ExampleRpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcClientApplication.class, args );
    }

    @Autowired
    NettyRpcProperties properties;

    @GetMapping("/test")
    public RespDTO test() throws Exception {

//        RpcEntity entity = CacheUtils.getRpcEntityMap().get( IGreeting.class.getName());
        IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
        Object result = invoker.sayHello( "sww" );
        return RespDTO.success( result );
    }


}
