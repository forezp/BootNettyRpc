package com.forezp.localrpclient;

import com.forezp.examplerpclib.lib.IGreeting;
import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import io.github.forezp.netty.rpc.core.common.dto.RespDTO;
import io.github.forezp.netty.rpc.core.protocol.client.Invoker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableNettyRpc(basePackages = "com.forezp")
public class LocalRpcLientApplication {

    public static void main(String[] args) {
        SpringApplication.run( LocalRpcLientApplication.class, args );
    }

    @GetMapping("/test")
    public RespDTO test() throws Exception {

        IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
        Object result = invoker.sayHello( "sww" );
        return RespDTO.success( result );
    }


}