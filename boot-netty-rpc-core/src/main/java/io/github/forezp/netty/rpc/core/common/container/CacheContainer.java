package io.github.forezp.netty.rpc.core.common.container;

import io.github.forezp.netty.rpc.core.common.entity.AppEntity;
import io.github.forezp.netty.rpc.core.common.entity.ConnectionEntity;
import io.github.forezp.netty.rpc.core.common.entity.ResponseSyncEntity;
import io.github.forezp.netty.rpc.core.registar.RpcClientEntity;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-25
 **/
public class CacheContainer {

    private AppEntity appEntity;

    /**
     * key applicationName
     * value 连接实体
     */
    private Map<String, List<ConnectionEntity>> connectionMap = Maps.newConcurrentMap();
    /**
     * 同步调用的暂存实体
     */
    private Map<String, ResponseSyncEntity> syncEntityMap = Maps.newConcurrentMap();

    private Map<String, RpcClientEntity> rpcEntityMap = Maps.newHashMap();

    public boolean containsConnection(String name) {
        if (connectionMap.get( name ) == null || connectionMap.get( name ).size() == 0) {
            return false;
        } else {
            return true;
        }
    }


    //fixme
    public void putConnection(String name, ConnectionEntity connectionEntity) {
        if (connectionMap.containsKey( name )) {
            List<ConnectionEntity> connectionEntities = connectionMap.get( name );
            for (ConnectionEntity entity : connectionEntities) {
                if (entity.getAppEntity().equals( connectionEntity.getAppEntity() )) {
                    connectionEntities.remove( entity );//删除旧的
                }
            }
            connectionEntities.add( connectionEntity );
        } else {
            List<ConnectionEntity> connectionEntities = new CopyOnWriteArrayList<>();
            connectionEntities.add( connectionEntity );
            connectionMap.put( name, connectionEntities );
        }
    }

    public void removeConnection(AppEntity appEntity) {
        List<ConnectionEntity> connectionEntities = connectionMap.get( appEntity.getName() );
        Iterator<ConnectionEntity> it = connectionEntities.iterator();
        while (it.hasNext()) {
            ConnectionEntity connectionEntity = it.next();
            if (connectionEntity.getAppEntity().equals( appEntity )) {
                connectionEntities.remove( connectionEntity );
            }
        }
    }

    public List<ConnectionEntity> getConnection(String name) {
        return connectionMap.get( name );
    }


    public AppEntity getAppEntity() {
        return appEntity;
    }

    public void setAppEntity(AppEntity appEntity) {
        this.appEntity = appEntity;
    }

    public Map<String, List<ConnectionEntity>> getConnectionMap() {
        return connectionMap;
    }

    public void setConnectionMap(Map<String, List<ConnectionEntity>> connectionMap) {
        this.connectionMap = connectionMap;
    }

    public Map<String, ResponseSyncEntity> getSyncEntityMap() {
        return syncEntityMap;
    }

    public void setSyncEntityMap(Map<String, ResponseSyncEntity> syncEntityMap) {
        this.syncEntityMap = syncEntityMap;
    }

    public Map<String, RpcClientEntity> getRpcEntityMap() {
        return rpcEntityMap;
    }

    public void setRpcEntityMap(Map<String, RpcClientEntity> rpcEntityMap) {
        this.rpcEntityMap = rpcEntityMap;
    }
}
