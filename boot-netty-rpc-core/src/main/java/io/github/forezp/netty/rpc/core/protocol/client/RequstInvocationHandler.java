package io.github.forezp.netty.rpc.core.protocol.client;


import io.github.forezp.netty.rpc.core.common.container.CacheContainer;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.common.entity.ConnectionEntity;
import io.github.forezp.netty.rpc.core.common.entity.RpcClientEntity;
import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import io.github.forezp.netty.rpc.core.loadbalance.LoadBalanceExcutor;
import io.github.forezp.netty.rpc.core.util.NettyRpcApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 参考了 https://www.cnblogs.com/clonen/p/6735011.html
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class RequstInvocationHandler implements InvocationHandler {

    private Logger LOG = LoggerFactory.getLogger( RequstInvocationHandler.class );

    public Class interfaceClz;
    //private CacheContainer cacheContainer;
    //  private ExcutorContainer excutorContainer;

    public RequstInvocationHandler(Class interfaceClz) {
        this.interfaceClz = interfaceClz;
        //  cacheContainer = (CacheContainer) NettyRpcApplication.getBean( "cacheContainer" );
        //  excutorContainer = (ExcutorContainer) NettyRpcApplication.getBean( "excutorContainer" );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals( method.getDeclaringClass() )) {
            try {
                return method.invoke( this, args );
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            return run( method, args );
        }
        return null;
    }

    /**
     * 实现接口的核心方法
     *
     * @param method
     * @param args
     * @return
     */
    public Object run(Method method, Object[] args) throws Exception {

        RpcClientEntity entity = getRpcClient( interfaceClz );
        NettyRpcRequest request = buildRequest( entity, method, args );
        ConnectionEntity connectionEntity = findCandidateConnection( entity );
        if (connectionEntity != null) {
            LOG.info( "run connectionEntity:" + connectionEntity.toString() );
            RequestInterceptor interceptor = (RequestInterceptor) NettyRpcApplication.getBean( "requestInterceptor" );
            if (request.isSyn()) {
                Object object = interceptor.invokeSync( connectionEntity.getChannelFuture(), request );
                if (object instanceof NettyRpcResponse) {
                    NettyRpcResponse response = (NettyRpcResponse) object;

                    return response.getResult();
                }
            } else {
                interceptor.invokeAsync( connectionEntity.getChannelFuture(), request );
            }
            return null;
        } else {
            AppEntity appEntity = new AppEntity();
            BeanUtils.copyProperties( entity, appEntity );
            ExcutorContainer excutorContainer = (ExcutorContainer) NettyRpcApplication.getBean( "excutorContainer" );
            NettyClientExcutor excutor = (NettyClientExcutor) excutorContainer.getClientExcutor();
            try {
                excutor.start( appEntity );
                while (!excutor.started( appEntity )) {
                }
                return run( method, args );
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private ConnectionEntity findCandidateConnection(RpcClientEntity entity) {
        CacheContainer cacheContainer = (CacheContainer) NettyRpcApplication.getBean( "cacheContainer" );
        List<ConnectionEntity> connectionEntities = cacheContainer.getConnection( entity.getName() );
        if (connectionEntities != null && connectionEntities.size() > 0) {
            AppEntity appEntity = new AppEntity();
            BeanUtils.copyProperties( entity, appEntity );
            ConnectionEntity returnConnection = null;
            for (ConnectionEntity connectionEntity : connectionEntities) {
                if (appEntity.equals( connectionEntity.getAppEntity() )) {
                    returnConnection = connectionEntity;
                    break;
                }
            }
            return returnConnection;
        }
        return null;
    }

    private RpcClientEntity getRpcClient(Class interfaceClz) {
        if (!NettyRpcApplication.containsBean( interfaceClz.getName() )) {
            throw new CommonException( "cannot find bean named" + interfaceClz.getName() );
        }
        RpcClientEntity entity = (RpcClientEntity) NettyRpcApplication.getBean( interfaceClz.getName() );
        if (entity == null) {
            throw new CommonException( "cannot find bean named" + interfaceClz.getName() );
        }
        if (!StringUtils.isEmpty( entity.getHost() ) && entity.getPort() > 0) {
            //次数注解上写明了ip，port，信息齐全，就不从配置或者注册中心读，就直接返回了，也没有负载的功能
            return entity;
        }
        //从负载中获取配
        ExcutorContainer excutorContainer = (ExcutorContainer) NettyRpcApplication.getBean( "excutorContainer" );
        LoadBalanceExcutor loadBalanceExcutor = excutorContainer.getLoadBalanceExcutor();
        entity = loadBalanceExcutor.loadBalance( entity, interfaceClz.getName() );
        return entity;
    }

    private NettyRpcRequest buildRequest(RpcClientEntity entity, Method method, Object[] args) {

        NettyRpcRequest request = new NettyRpcRequest();
        request.setInterfaceClass( interfaceClz );
        request.setMethod( entity.getName() );
        request.setSyn( entity.isSyn() );
        String fromUrl = entity.getName() + "//" + entity.getHost() + entity.getPort();
        request.setFromUrl( fromUrl );
        request.setStartTime( System.currentTimeMillis() );
        request.setMethod( method.getName() );
        request.setParamTypes( method.getParameterTypes() );
        request.setParams( args );
        if (request.getParams().length > entity.getTraceIdIndex()) {
            request.setTraceId( request.getParams()[0].toString() );
        }
        return request;
    }

}
