package io.github.forezp.netty.rpc.core.protocol.client;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.entity.ResponseSyncEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;

/**
 * Created by forezp on 2018/5/26.
 */
public class ResponseHandler extends AbstractResponseHandler {

    Logger LOG = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public void handle(NettyRpcResponse response) {
        response.setEndTime(System.currentTimeMillis());
        LOG.info("Client received: " + response.toString());
        LOG.info("耗时: " + (response.getEndTime() - response.getStartTime()) + "ms");
        //处理response
        if (response.isSyn()) {
            String messageId = response.getMessageId();
            ResponseSyncEntity entity = cacheContainer.getSyncEntityMap().get(messageId);
            if (entity != null) {
                try {
                    entity.setResult(response);
                    entity.getBarrier().await();
                }catch (Exception e){
                    e.printStackTrace();
                    //TODO 异常
                }finally {
                    cacheContainer.getSyncEntityMap().remove( messageId );
                }
            }

        }

    }
}
