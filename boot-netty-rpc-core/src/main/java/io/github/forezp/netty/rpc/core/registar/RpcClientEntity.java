package io.github.forezp.netty.rpc.core.registar;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-23
 **/
public class RpcClientEntity extends RegistrarFactoryBean implements Serializable {

    private static final long serialVersionUID = 5149083798031489971L;
    private String name;
    private boolean isSyn;
    private String host;
    private int port;
    private String rpcClz;
    private int traceIdIndex;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSyn() {
        return isSyn;
    }

    public void setIsSyn(boolean syn) {
        isSyn = syn;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRpcClz() {
        return rpcClz;
    }

    public void setRpcClz(String rpcClz) {
        this.rpcClz = rpcClz;
    }


    public void setSyn(boolean syn) {
        isSyn = syn;
    }

    public int getTraceIdIndex() {
        return traceIdIndex;
    }

    public void setTraceIdIndex(int traceIdIndex) {
        this.traceIdIndex = traceIdIndex;
    }

    @Override
    public String toString() {
        return "RpcClientEntity{" +
                "name='" + name + '\'' +
                ", isSyn=" + isSyn +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", rpcClz='" + rpcClz + '\'' +
                ", traceIdIndex=" + traceIdIndex +
                '}';
    }
}
