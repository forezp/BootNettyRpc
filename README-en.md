# BootNettyRpc

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/forezp/BootNettyRpc/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.forezp/boot-netty-rpc-core.svg?label=maven%20central)](http://mvnrepository.com/artifact/io.github.forezp/boot-netty-rpc-core)

README: [English](https://github.com/forezp/BootNettyRpc/blob/master/README-en.md) | [中文](https://github.com/forezp/BootNettyRpc/blob/master/README.md)
## What's BootNettyRpc?


BootNettyRpc  is a open resource of rpc with netty and spring boot !

## How to use?


### The server side

1.Add the dependency to your maven pom file.

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.3</version>
 </dependency>

```

2.Add @EnableNettyRpc annotation config to your project ,for example:

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
3. config the netty server port in your  springboot config file,like application.yml:


```
server:
  port: 7001

netty.server.name: server
netty.server.port: 6001

```

4. As a service provider ,expose the service to other client

```

@RpcClient(name = "server", rpcClz = "com.forezp.examplerpcserver.api.Greeting")
public interface IGreeting {

    String sayHello(String name);
}


```

Implement the service:

```

@Service
public class Greeting implements IGreeting {
    @Override
    public String sayHello(String name) {
        return "hi "+name;
    }
}

```

### The Client Side


1.Add the dependency to your maven pom file.

```
 <dependency>
       <groupId>io.github.forezp</groupId>
        <artifactId>boot-netty-rpc-core</artifactId>
        <version>1.0.3</version>
 </dependency>

```

2.Add @EnableNettyRpc annotation config to your project ,for example:

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

3. config the netty server port in your  springboot config file,like application.yml:


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

4.comsume the provider service:

```
 IGreeting invoker = (IGreeting) Invoker.invoke( IGreeting.class );
 Object result = invoker.sayHello( "sww" );
```

## Contact Me

If there is any problem, let me know. My email: miles02@163.com
