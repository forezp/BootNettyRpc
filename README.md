# BootNettyRpc

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/forezp/BootNettyRpc/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/thunder.svg?label=maven%20central)](http://mvnrepository.com/search?q=io.github.forezp)


README: [English](https://github.com/forezp/BootNettyRpc/blob/master/README-en.md) | [中文](https://github.com/forezp/BootNettyRpc/blob/master/README.md)

## 什么是 BootNettyRpc?

BootNettyRpc 是一个采用Netty实现的Rpc框架，适用于Spring Boot项目，**未来会支持Spring Cloud**。


## 怎么使用？


BootNettyRpc 包括Server端和Client端。

### Server端

在pom文件中加上依赖：

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.1</version>
 </dependency>
 
```

在Spring Boot启动工程加上注解@EnableNettyRpc，需要开启包扫描，不填也可以，会全局扫描，有一点影响启动速度，比如例子中的ExampleRpcServerApplication：

```
@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
@RestController
public class ExampleRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcServerApplication.class, args );
    }
}

```

在配置文件配置Netty Server的端口和Netty Server的name,该name会作client端的调用的name。

```
server:
  port: 7001

netty.server.name: server
netty.server.port: 6001

```

作为一个服务提供者，需要对外暴露二方包,并提供接口，比如在本案例中，将所有的对外包括的接口放在二方包example-rpc-lib中，服务调用者
，只需要提供引用这个包就具有服务的调用能力。比如对外暴露一个服务，需要在接口上写@RpcClient注解,name必填为服务提供者名，rpcClz可不填。

```
@RpcClient(name = "server", rpcClz = "com.forezp.examplerpcserver.api.Greeting")
public interface IGreeting {

    String sayHello(String name);
}
```

### Client端

在工程的pom 文件加上一下依赖：

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.1</version>
</dependency>

```


2.在SpringBoot的启动类加上@EnableNettyRpc注解，如下:

```
@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
@RestController
public class ExampleRpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleRpcClientApplication.class, args );
    }
}

```

在Spring Boot配置文件 application.yml，加上以下的配置，其中name为Server端的name,同一个name可以配置多个服务实例，默认使用了轮询的负载均衡。

```
netty:
  clients:
    - name: server
      host: localhost
      port: 6001
    - name: server
      host: localhost
      port: 6001

```

4.消费服务：


```

 IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
 Object result = invoker.sayHello( "sww" );

```

## 联系我

如果有任何问题和建议，请联系我，我的邮箱miles02@163.com

## 已经实现的功能

- rpc
- 负载均衡
- 接口线程池隔离

## 未来计划

- 优化rpc性能
- 接入eureka、consule
- 接入链路追踪
- 接入监控