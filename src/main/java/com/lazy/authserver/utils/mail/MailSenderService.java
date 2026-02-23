package com.lazy.authserver.utils.mail;


import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.lazy.authserver.config.mail.MailProperties;
import com.lazy.authserver.thread.executor.MailSenderThreadExecutor;

/**
 * @Author:Prabin-Bhandari
 * @Version:1.0
 * @Date:2023-10-20
 * @For-Investment-Provider
 */
@Component
public class MailSenderService {


    private final MailProperties mailProperties;
    private final Configuration mailConfig;

    public MailSenderService(MailProperties mailProperties, @Qualifier("emailConfigBean") Configuration mailConfig) {
        this.mailProperties = mailProperties;
        this.mailConfig = mailConfig;
    }


    public void sendEmailWithTemplate(EmailSenderRequest request) {
        MailSenderThreadExecutor.getDataExecutorService(request, mailConfig, mailProperties);
    }

    public void sendEmailWithTemplateAndAttachments(EmailSenderRequest request) {
        MailSenderThreadExecutor.getDataExecutorService(request, mailConfig, mailProperties);
    }


    public void sendEmail(EmailSenderRequest request) {
        MailSenderThreadExecutor.getDataExecutorService(request, mailConfig, mailProperties);
    }
}

//source: https://commons.apache.org/proper/commons-email/userguide.html