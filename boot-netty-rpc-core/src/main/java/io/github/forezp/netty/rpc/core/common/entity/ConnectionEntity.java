package io.github.forezp.netty.rpc.core.common.entity;

import io.netty.channel.ChannelFuture;

import java.util.Objects;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class ConnectionEntity {

    private AppEntity appEntity;
    private ChannelFuture channelFuture;

    public AppEntity getAppEntity() {
        return appEntity;
    }

    public void setAppEntity(AppEntity appEntity) {
        this.appEntity = appEntity;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionEntity)) return false;
        ConnectionEntity entity = (ConnectionEntity) o;
        return Objects.equals( getAppEntity(), entity.getAppEntity() );
    }

    @Override
    public int hashCode() {

        return Objects.hash( getAppEntity() );
    }

    @Override
    public String toString() {
        return "ConnectionEntity{" +
                "appEntity=" + appEntity +
                ", channelFuture=" + channelFuture +
                '}';
    }
}
