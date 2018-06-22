package io.github.forezp.netty.rpc.core.monitor.warm;

import io.github.forezp.netty.rpc.core.config.CommonProperties;
import io.github.forezp.netty.rpc.core.config.NettyRpcProperties;
import io.github.forezp.netty.rpc.core.protocol.mail.SmtpExecutor;
import io.github.forezp.netty.rpc.core.protocol.mail.SmtpSslExecutor;
import org.apache.commons.lang3.StringUtils;

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
        this.properties = properties;
        CommonProperties cp = properties.getCommonProperties();
        String host = cp.getMailHost();
        String user = cp.getMailUserName();
        String password = cp.getMailUserPassword();
        String enableSsl = cp.getIsMailEnableSsl();
        boolean ssl = false;
        if (!StringUtils.isEmpty( enableSsl )) {
            ssl = Boolean.valueOf( enableSsl );
        }
        if (ssl) {
            smtpExecutor = new SmtpSslExecutor( host, user, password );
        } else {
            smtpExecutor = new SmtpExecutor( host, user, password );
        }
    }

    @Override
    public void onEvent(Event event) {

        sendMail( event );
    }

    private void sendMail(Event event) {
        CommonProperties cp = properties.getCommonProperties();
        String from = cp.getMailFrom();
        String to = cp.getMailTo();
        String cc = cp.getMailCc();
        String bcc = cp.getMailBcc();
        String subject="warm!warm!";
        String text=event.getMsg();
        try {
            smtpExecutor.send( from,to, cc,bcc ,subject,text,false,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
