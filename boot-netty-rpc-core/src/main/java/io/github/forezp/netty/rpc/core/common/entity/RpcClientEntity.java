package io.github.forezp.netty.rpc.core.common.entity;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-23
 **/
public class RpcClientEntity implements Serializable {

    private static final long serialVersionUID = 5149083798031489971L;


    private String name;
    boolean isSyn;
    String host;
    int port;
    String rpcClz;


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

    @Override
    public String toString() {
        return "RpcEntity{" +
                "name='" + name + '\'' +
                ", isSyn=" + isSyn +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", rpcClz='" + rpcClz + '\'' +
                '}';
    }
}
