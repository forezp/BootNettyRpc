package com.forezp.localrpcserver.api;

import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * @create 2018-05-21
 **/
@Service
public class Greeting implements IGreeting {

    @Override
    public String sayHello(String name) {
        System.out.println("server: Client message received {"+name+"}");
        return "hi " + name;
    }

}
