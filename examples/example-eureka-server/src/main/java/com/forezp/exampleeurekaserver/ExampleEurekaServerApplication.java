package com.forezp.exampleeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ExampleEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleEurekaServerApplication.class, args );
    }
}
