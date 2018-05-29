package io.github.forezp.netty.rpc.core.common.entity;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-25
 **/
public class NettyClient {

    private String name;
    private String host;
    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
