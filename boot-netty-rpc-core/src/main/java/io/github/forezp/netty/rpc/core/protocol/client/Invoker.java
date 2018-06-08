//package io.github.forezp.netty.rpc.core.protocol.client;
//
//import java.lang.reflect.Proxy;
//
///**
// * ${DESCRIPTION}
// *
// * @author fangzhipeng
// * create 2018-05-21
// **/
//public class Invoker {
//
//    public static Object invoke(Class<?> cls) {
//        RequstInvocationHandler invocationHandler = new RequstInvocationHandler( cls );
//        Object newProxyInstance = Proxy.newProxyInstance(
//                cls.getClassLoader(),
//                new Class[]{cls},
//                invocationHandler );
//        return newProxyInstance;
//    }
//}
