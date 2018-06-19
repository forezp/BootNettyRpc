package io.github.forezp.netty.rpc.core.protocol.mail;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-13
 **/
public class SmtpExecutor {



    protected String host;
    protected String user;
    protected String password;

    protected Session session;

    public SmtpExecutor(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;

        this.session = createSession();
    }

    protected Properties createProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.socketFactory.port", "25");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        return properties;
    }

    protected Authenticator createAuthenticator() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
    }

    protected Session createSession() {
        Properties properties = createProperties();
        Authenticator authenticator = createAuthenticator();

        Session session = Session.getDefaultInstance(properties, authenticator);

        return session;
    }



    public void send(String from, String to, String cc, String bcc, String subject, String text, boolean htmlStyle, String encoding) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setSentDate(new Date());
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        if ( cc != null && cc.length() != 0) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        }
        if (bcc != null && bcc.length() != 0) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
        }
        message.setSubject(subject);
        if (htmlStyle) {
            message.setContent(text, "text/html;charset=" + encoding);
        } else {
            message.setText(text);
        }

        Transport.send(message);
    }


//    public static void main(String[] args) throws Exception {
//        SmtpExecutor smtpExecutor=new SmtpExecutor("smtp.163.com","miles02",""  );
//        smtpExecutor.send( "miles02@163.com","124746406@qq.com",null,null,"sesee","weeeeeeee",false,"utf8" );
//
//    }
}
