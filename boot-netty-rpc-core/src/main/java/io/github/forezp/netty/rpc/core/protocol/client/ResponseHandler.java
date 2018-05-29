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
            if (entity == null) {
                //设置异常
            }
            entity.setResult(response);
            try {
                entity.getBarrier().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }
}
