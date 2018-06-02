package io.github.forezp.netty.rpc.core.protocol.monitor;

import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;
import io.github.forezp.netty.rpc.core.protocol.serializer.SerializerExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.concurrent.TimeUnit;

/**
 * Created by forezp on 2018/6/2.
 */
public class RedisMonitor extends AbstractMonitor {

    Logger LOG = LoggerFactory.getLogger(RedisMonitor.class);

    private StringRedisTemplate stringRedisTemplate;

    public RedisMonitor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void execute(MonitorMessage message) {

        ThreadPoolFactory.createThreadPoolDefaultExecutor().execute(new Runnable() {
            @Override
            public void run() {

               // LOG.info("redis insert traceid :{}" , message.getTraceId());
                ListOperations<String, String> ops = stringRedisTemplate.opsForList();
                ops.leftPush(message.getTraceId(), SerializerExecutor.toJson(message));
                stringRedisTemplate.expire(message.getTraceId(), 24, TimeUnit.HOURS);
            }
        });


    }
}
