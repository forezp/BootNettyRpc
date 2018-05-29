package io.github.forezp.netty.rpc.core.protocol.serializer;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public interface ServerExcutor {

    /**
     * 启动netty server
     */
    void start(AppEntity appEntity) throws Exception;

    /**
     * 是否启动
     *
     * @return
     */
    boolean started(AppEntity appEntity) throws Exception;
}
