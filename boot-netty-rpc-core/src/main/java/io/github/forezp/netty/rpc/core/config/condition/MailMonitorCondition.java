package io.github.forezp.netty.rpc.core.config.condition;

import static io.github.forezp.netty.rpc.core.common.constant.ConfigConstants.MONITOR_MAIL_ENABLE;
import static io.github.forezp.netty.rpc.core.common.constant.ConfigConstants.MONITOR_MAIL_ENABLE_TRUE;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-22
 **/
public class MailMonitorCondition extends PropertyEqualsCondition {

    public MailMonitorCondition() {
        super( MONITOR_MAIL_ENABLE, MONITOR_MAIL_ENABLE_TRUE );
    }
}
