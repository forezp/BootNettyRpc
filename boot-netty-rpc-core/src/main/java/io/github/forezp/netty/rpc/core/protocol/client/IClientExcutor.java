package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.netty.channel.ChannelFuture;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public interface IClientExcutor {

    // 客户端启动连接
    void start(AppEntity appEntity) throws Exception;

    // 客户端是否启动
    boolean started(AppEntity appEntity) throws Exception;

    // 客户端上线，更新缓存
    ChannelFuture online(AppEntity appEntity, ChannelFuture connnectionHandler) throws Exception;

    // 客户端下线，更新缓存
    void offline(AppEntity appEntity) throws Exception;
}
