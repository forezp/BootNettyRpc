package io.github.forezp.netty.rpc.core.registar;

import io.github.forezp.netty.rpc.core.common.container.CacheContainer;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.common.entity.ConnectionEntity;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.loadbalance.LoadBalanceExcutor;
import io.github.forezp.netty.rpc.core.protocol.client.NettyClientExcutor;
import io.github.forezp.netty.rpc.core.protocol.client.RequestInterceptor;
import io.github.forezp.netty.rpc.core.util.NettyRpcApplication;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-08
 **/
public class NettyRpcInterceptor implements MethodInterceptor {

    MutablePropertyValues annotationValues;

    public NettyRpcInterceptor(MutablePropertyValues annotationValues) {
        this.annotationValues = annotationValues;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        Object[] args = methodInvocation.getArguments();

        RpcClientEntity entity = getRpcClient( (Class) annotationValues.get( "interfaze" ) );
        NettyRpcRequest request = buildRequest( entity, method, args );
        ConnectionEntity connectionEntity = findCandidateConnection( entity );
        if (connectionEntity != null) {
            //  LOG.info( "run connectionEntity:" + connectionEntity.toString() );
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
            synchronized (NettyRpcInterceptor.class) {
                try {
                    if (excutor.started( appEntity )) {
                        return invoke( methodInvocation );
                    }
                    excutor.start( appEntity );
                    while (!excutor.started( appEntity )) {
                    }
                    return invoke( methodInvocation );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
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
        RpcClientEntity rpcClientEntity = new RpcClientEntity();
        Object name = annotationValues.get( "name" );
        if (name != null) {
            rpcClientEntity.setName( (String) name );
        }
        Object isSyn = annotationValues.get( "isSyn" );
        if (isSyn != null) {
            rpcClientEntity.setIsSyn( (Boolean) isSyn );
        }

        Object host = annotationValues.get( "host" );
        if (host != null) {
            rpcClientEntity.setHost( (String) host );
        }

        Object port = annotationValues.get( "port" );
        if (port != null) {
            rpcClientEntity.setPort( (Integer) port );
        }

        Object rpcClz = annotationValues.get( "rpcClz" );
        if (rpcClz != null) {
            rpcClientEntity.setRpcClz( (String) rpcClz );
        }

        Object traceIdIndex = annotationValues.get( "traceIdIndex" );
        if (traceIdIndex != null) {
            rpcClientEntity.setTraceIdIndex( (Integer) traceIdIndex );
        }

        if (!StringUtils.isEmpty( rpcClientEntity.getHost() ) && rpcClientEntity.getPort() > 0) {
            //次数注解上写明了ip，port，信息齐全，就不从配置或者注册中心读，就直接返回了，也没有负载的功能
            return rpcClientEntity;
        }
        //从负载中获取配
        ExcutorContainer excutorContainer = (ExcutorContainer) NettyRpcApplication.getBean( "excutorContainer" );
        LoadBalanceExcutor loadBalanceExcutor = excutorContainer.getLoadBalanceExcutor();
        rpcClientEntity = loadBalanceExcutor.loadBalance( rpcClientEntity, interfaceClz.getName() );
        return rpcClientEntity;
    }

    private NettyRpcRequest buildRequest(RpcClientEntity entity, Method method, Object[] args) {


        NettyRpcRequest request = new NettyRpcRequest();
        request.setInterfaze( ((Class) (annotationValues.get( "interfaze" ))).getName() );
        request.setMethod( entity.getName() );
        request.setSyn( entity.isSyn() );
        String fromUrl = entity.getName() + "//" + entity.getHost() + entity.getPort();
        request.setFromUrl( fromUrl );
        request.setStartTime( System.currentTimeMillis() );
        request.setMethod( method.getName() );
        request.setParamTypes( method.getParameterTypes() );
        request.setParams( args );
        request.setRpcClz( entity.getRpcClz() );

        if (request.getParams().length > entity.getTraceIdIndex()) {
            request.setTraceId( request.getParams()[0].toString() );
        }
        return request;
    }
}
