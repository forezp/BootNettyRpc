package io.github.forezp.netty.rpc.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 *  create 2018-05-18
 **/
public class ReflectUtils {

    public static Object invoke(String clzName, String method, Object... args) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clz = Class.forName( clzName );
        Method method1 = clz.getMethod( method );
        return method1.invoke( clz.newInstance(), args );
    }
}