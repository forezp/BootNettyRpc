package io.github.forezp.netty.rpc.core.config;


import io.github.forezp.netty.rpc.core.common.container.ExcutorContainer;
import io.github.forezp.netty.rpc.core.protocol.registry.ClientDiscoveryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author fangzhipeng
 * create 2018-05-31
 **/
@Configuration
@EnableConfigurationProperties
public class DiscoveryClientConfig {


    @Configuration
    @ConditionalOnBean(DiscoveryClient.class)
    protected static class DiscoveryClientAutoConfiguration {

        @Autowired
        ExcutorContainer excutorContainer;

        @Autowired
        NettyRpcProperties nettyRpcProperties;

        @Bean(initMethod = "init", destroyMethod = "destroy")
        @ConditionalOnMissingBean
        public ClientDiscoveryImpl clientDiscoveryFactory(DiscoveryClient discoveryClient) {

            ClientDiscoveryImpl clientDiscoveryFactory = new ClientDiscoveryImpl( discoveryClient );
            clientDiscoveryFactory.setNettyRpcProperties( nettyRpcProperties );
            excutorContainer.setClientDiscovery( clientDiscoveryFactory );
            return clientDiscoveryFactory;
        }
    }


}

