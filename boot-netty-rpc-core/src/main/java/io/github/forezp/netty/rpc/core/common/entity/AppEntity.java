package io.github.forezp.netty.rpc.core.common.entity;


import io.netty.util.internal.StringUtil;

import java.util.Objects;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/


public class AppEntity {

    /**
     * 属于什么集群
     */
    private String name;

    private String host;
    private int port;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppEntity)) return false;
        AppEntity that = (AppEntity) o;
        return getPort() == that.getPort() &&
                Objects.equals( getHost(), that.getHost() ) &&
                Objects.equals( getName(), that.getName() );
    }

    @Override
    public int hashCode() {

        return Objects.hash( getHost(), getPort(), getName() );
    }

    public boolean validate() {
        if (StringUtil.isNullOrEmpty( name ) || StringUtil.isNullOrEmpty( host ) || port < 0) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "AppEntity{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
