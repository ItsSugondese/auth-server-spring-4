package com.lazy.authserver.config.mail;

import com.lazy.authserver.entity.mail.MailPropertiesModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Santosh Paudel
 */

@Configuration
public class EmailConfig {

    public HtmlEmail simpleJavaMailSender(MailProperties mailProperties) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(mailProperties.getHost());
        email.setSmtpPort(mailProperties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(mailProperties.getUsername(), mailProperties.getPassword()));
        email.setSSLOnConnect(true);
        email.setFrom(mailProperties.getUsername());
        email.setDebug(true);
        email.setStartTLSEnabled(true);
        return email;
    }

    public MultiPartEmail emailWithAttachment(MailProperties mailProperties) throws Exception {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(mailProperties.getHost());
        email.setSmtpPort(mailProperties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(mailProperties.getUsername(), mailProperties.getPassword()));
        email.setSSLOnConnect(true);
        email.setFrom(mailProperties.getUsername());
        email.setDebug(true);
        email.setStartTLSEnabled(true);
        return email;
    }

    public HtmlEmail htmlJavaMailSender(MailPropertiesModel mailProperties) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(mailProperties.getHost());
        email.setSmtpPort(mailProperties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(mailProperties.getUsername(), mailProperties.getPassword()));
        email.setSSLOnConnect(true);
        email.setFrom(mailProperties.getUsername());
        email.setDebug(true);
        email.setStartTLSEnabled(true);
        return email;
    }
}
//source: https://commons.apache.org/proper/commons-email/userguide.html