package io.github.forezp.netty.rpc.core.common.entity;


import io.github.forezp.netty.rpc.core.common.entity.NettyRpcMessage;
import io.github.forezp.netty.rpc.core.util.SnowflakeIdWorker;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-18
 **/

public class NettyRpcRequest extends NettyRpcMessage implements Serializable {

    private static final long serialVersionUID = 6395330487058794296L;


    String rpcClz;
    private boolean isSyn;

    private String method;
    private Class[] paramTypes;
    private Object[] params;

    private Class interfaceClass;

    private String fromUrl;

    public NettyRpcRequest() {
        messageId = "" + SnowflakeIdWorker.getInstance().nextId();
    }


    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getRpcClz() {
        return rpcClz;
    }

    public void setRpcClz(String rpcClz) {
        this.rpcClz = rpcClz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }




    public boolean isSyn() {
        return isSyn;
    }

    public void setSyn(boolean syn) {
        isSyn = syn;
    }


    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public String toString() {
        return "NettyRpcRequest{" +
                "messageId='" + messageId + '\'' +
                ", startTime=" + startTime +
                ", rpcClz='" + rpcClz + '\'' +
                ", isSyn=" + isSyn +
                ", method='" + method + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                ", interfaceClass=" + interfaceClass +
                ", fromUrl='" + fromUrl + '\'' +
                '}';
    }
}
