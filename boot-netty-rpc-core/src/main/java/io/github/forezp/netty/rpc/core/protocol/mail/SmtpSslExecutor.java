package io.github.forezp.netty.rpc.core.protocol.mail;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Properties;

public class SmtpSslExecutor extends SmtpExecutor {

    public SmtpSslExecutor(String host, String user, String password) {
        super(host, user, password);
    }

    @Override
    protected Properties createProperties() {
        Properties properties = super.createProperties();
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        MailSSLSocketFactory sslSocketFactory = null;
        try {
            sslSocketFactory = new MailSSLSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sslSocketFactory.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.socketFactory", sslSocketFactory);

        return properties;
    }
}