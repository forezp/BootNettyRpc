package io.github.forezp.netty.rpc.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-24
 **/
@Component
public class NettyRpcApplication implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        NettyRpcApplication.context = applicationContext;

    }

    public static Object getBean(String name) {
        return context.getBean( name );
    }

    public static Object getBean(Class clz) {

        return context.getBean( clz );

    }

    public static Map<String, Object> getBeansOfType(Class clz) {

        return context.getBeansOfType( clz );
    }

    public static boolean containsBean(String name) {
        return context.containsBean( name );
    }


}
