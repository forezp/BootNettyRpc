package io.github.forezp.netty.rpc.core.monitor;

import io.github.forezp.netty.rpc.core.common.thread.ThreadPoolFactory;
import io.github.forezp.netty.rpc.core.config.CommonProperties;
import io.github.forezp.netty.rpc.core.protocol.serializer.SerializerExecutor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.concurrent.TimeUnit;

/**
 * Created by forezp on 2018/6/2.
 */
public class RedisMonitor extends AbstractMonitor {


    private StringRedisTemplate stringRedisTemplate;


    public RedisMonitor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void execute(MonitorMessage message) {

        ThreadPoolFactory.createThreadPoolDefaultExecutor().execute(new Runnable() {
            @Override
            public void run() {

                CommonProperties cp = nettyRpcProperties.getCommonProperties();
                ListOperations<String, String> ops = stringRedisTemplate.opsForList();
                ops.leftPush(message.getTraceId(), SerializerExecutor.toJson(message));
                stringRedisTemplate.expire(message.getTraceId(), Long.parseLong(cp.getMonitorRedisExpire()),
                        getTimeUnit(cp.getMonitorRedisExpireTimeUnit()));
            }
        });


    }

    private TimeUnit getTimeUnit(String timeTpye) {
        switch (timeTpye) {
            case "second":
                return TimeUnit.SECONDS;

            case "munite":
                return TimeUnit.MINUTES;

            case "hour":
                return TimeUnit.HOURS;

            case "day":
                return TimeUnit.DAYS;

            default:
                return TimeUnit.HOURS;

        }


    }
}
