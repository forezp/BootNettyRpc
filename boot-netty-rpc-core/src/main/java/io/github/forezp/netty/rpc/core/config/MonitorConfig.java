package io.github.forezp.netty.rpc.core.config;

import io.github.forezp.netty.rpc.core.annotation.condition.HttpMonitorConditon;
import io.github.forezp.netty.rpc.core.annotation.condition.RedisMonitorCondition;
import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import io.github.forezp.netty.rpc.core.monitor.HttpMonitor;
import io.github.forezp.netty.rpc.core.monitor.RedisMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * Created by forezp on 2018/6/2.
 */

@Configuration
@EnableConfigurationProperties
public class MonitorConfig {


    @Configuration
    @Conditional(HttpMonitorConditon.class)
    protected static class HttpMonitorConfig {
        @Autowired
        NettyRpcProperties nettyRpcProperties;

        @Autowired
        ExcutorContainer excutorContainer;

        @Bean
        public HttpMonitor monitor() {
            HttpMonitor monitor = new HttpMonitor();
            if (StringUtils.isEmpty(nettyRpcProperties.commonProperties.getMonitorUrl())) {
                throw new CommonException("monitor.url connot be null");
            }
            monitor.setNettyRpcProperties(nettyRpcProperties);
            excutorContainer.setMonitor(monitor);
            return monitor;

        }

    }


    @Configuration
    @ConditionalOnClass(StringRedisTemplate.class)
    @Conditional(RedisMonitorCondition.class)
    protected static class RedisMonitorConfig {

        @Autowired
        NettyRpcProperties nettyRpcProperties;

        @Autowired
        ExcutorContainer excutorContainer;


        @Bean
        public RedisMonitor redisMonitor(StringRedisTemplate stringRedisTemplate) {

            RedisMonitor redisMonitor = new RedisMonitor(stringRedisTemplate);
            redisMonitor.setNettyRpcProperties(nettyRpcProperties);
            excutorContainer.setMonitor(redisMonitor);
            return redisMonitor;
        }
    }

}
