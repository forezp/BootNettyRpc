package io.github.forezp.netty.rpc.core.config;

import com.netflix.appinfo.EurekaInstanceConfig;
import io.github.forezp.netty.rpc.core.config.condition.NettyServerCondition;
import io.github.forezp.netty.rpc.core.common.constant.NettyRpcConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *
 * @author fangzhipeng
 * create 2018-05-31
 **/
@Configuration
@EnableConfigurationProperties
@ConditionalOnBean(EurekaInstanceConfig.class)
@Conditional(NettyServerCondition.class)
public class NettyMeteEurekaConfig {

    @Autowired
    private EurekaInstanceConfig instance;

    @Autowired
    private NettyRpcProperties nettyRpcProperties;

    @PostConstruct
    public void init() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (address != null) {
            this.instance.getMetadataMap().put( NettyRpcConstants.NETTY_SERVER_HOST, address.getHostAddress() );
        }
        this.instance.getMetadataMap().put( NettyRpcConstants.NETTY_SERVER_NAME, String.valueOf( nettyRpcProperties.getServer().getName() ) );

        this.instance.getMetadataMap().put( NettyRpcConstants.NETTY_SERVER_PORT, String.valueOf( nettyRpcProperties.getServer().getPort() ) );
    }
}