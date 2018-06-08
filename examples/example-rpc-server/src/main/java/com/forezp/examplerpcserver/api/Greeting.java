package com.forezp.examplerpcserver.api;


import com.forezp.examplerpclib.lib.IGreeting;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * @create 2018-05-21
 **/
@Service
@Primary
public class Greeting implements IGreeting {
    @Override
    public String sayHello(String name) {
        return "hi " + name;
    }
}
