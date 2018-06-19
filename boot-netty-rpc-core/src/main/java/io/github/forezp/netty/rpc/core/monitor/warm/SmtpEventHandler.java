package io.github.forezp.netty.rpc.core.monitor.warm;

import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import io.github.forezp.netty.rpc.core.protocol.mail.SmtpExecutor;
import io.github.forezp.netty.rpc.core.protocol.mail.SmtpSslExecutor;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-19
 **/
public class SmtpEventHandler extends AbstractEventHandler {

    private SmtpExecutor smtpExecutor;
    private NettyRpcProperties properties;

    public SmtpEventHandler(NettyRpcProperties properties) {
        //TODO  从properties读取配置信息
        String host = null;
        String user = null;
        String password = null;

        boolean ssl = false;
        if (ssl) {
            smtpExecutor = new SmtpSslExecutor( host, user, password );
        } else {
            smtpExecutor = new SmtpExecutor( host, user, password );
        }
    }

    @Override
    public void onEvent() {

        sendMail();

    }

    private void sendMail() {
        // smtpExecutor.send(  );
    }
}
