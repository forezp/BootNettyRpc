package com.forezp.localrpclient;

import com.forezp.localrpclient.client.IGreeting;
import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import io.github.forezp.netty.rpc.core.common.dto.RespDTO;
import io.github.forezp.netty.rpc.core.monitor.trace.MonitorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableNettyRpc(basePackages = "com.forezp")
public class LocalRpcLientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalRpcLientApplication.class, args);
    }

    @Autowired
    IGreeting greeting;

    @GetMapping("/test")
    public RespDTO test() throws Exception {
//        IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
//        Object result = invoker.sayHello( "sww" );

        //调用服务端sayHello()方法
        Object object = greeting.sayHello("zhangsan");
        return RespDTO.success(object);
    }

    @PostMapping("/postmsg")
    public void post(@RequestBody MonitorMessage message) {
        //System.out.println(JSON.toJSONString(message));
    }

}