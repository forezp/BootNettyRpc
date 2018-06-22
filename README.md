# BootNettyRpc

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/forezp/BootNettyRpc/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.forezp/boot-netty-rpc-core.svg?label=maven%20central)](http://mvnrepository.com/artifact/io.github.forezp/boot-netty-rpc-core)


README: [English](https://github.com/forezp/BootNettyRpc/blob/master/README-en.md) | [中文](https://github.com/forezp/BootNettyRpc/blob/master/README.md)

## 什么是 BootNettyRpc?

BootNettyRpc 是一个采用Netty实现的Rpc框架，适用于Spring Boot项目，支持Spring Cloud。
目前支持的版本为Spring Boot 1.5.x，Spring Cloud版本为D和E版本。


## 怎么使用？

分为本地启动和结合Spring Cloud启动。具体见example 案例，现在以本地案例来说明，Spring Cloud案例省略。

BootNettyRpc 包括Server端和Client端。

### Server端

在pom文件中加上依赖：

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.5</version>
 </dependency>
 
```

在Spring Boot启动工程加上注解@EnableNettyRpc，需要开启包扫描，不填也可以，会全局扫描，有一点影响启动速度，比如例子中的ExampleRpcServerApplication：

```
@SpringBootApplication
@EnableNettyRpc(basePackages = "com.forezp")
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

写一个服务，接口如下：

```

public interface IGreeting {

    String sayHello(String name);
}
```

实现类如下：

```
@Service
public class Greeting implements IGreeting {
    @Override
    public String sayHello(String name) {
        return "hi "+name;
    }
}

```

### Client端

在工程的pom 文件加上一下依赖：

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.5</version>
</dependency>

```


在SpringBoot的启动类加上@EnableNettyRpc注解，如下:

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


服务调用者需要需要写一个接口，在接口上写@RpcClient注解,name必填为服务提供者名，rpcClz必填，为服务提供者的类。

```
@RpcClient(name = "server", rpcClz = "com.forezp.localrpcserver.api.Greeting")
public interface IGreeting {

    String sayHello(String name);
}
```


```
@Autowired
IGreeting greeting;

Object object = greeting.sayHello( "hi" );
 
```

## 联系我

如果有任何问题和建议，请联系我，我的邮箱miles02@163.com

## 已经实现的功能

- rpc(实现同步、异步调用)
- 负载均衡
- 接口线程池隔离
- 接入Eureka
- 接入链路追踪
- 接入监控
- 接入报警邮箱
- 优化rpc性能 (需持续优化)

## 未来计划

- 接入多种序列化，做到可配置
- 接入consule
