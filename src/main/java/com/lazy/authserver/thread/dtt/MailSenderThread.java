package com.lazy.authserver.thread.dtt;

import com.lazy.authserver.config.mail.EmailConfig;
import com.lazy.authserver.config.mail.MailProperties;
import com.lazy.authserver.utils.mail.EmailSenderRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author:Prabin-Bhandari
 * @Version:1.0
 * @Date:2023-10-20
 * @For-Investment-Provider
 */
public class MailSenderThread implements Runnable {
    private final EmailSenderRequest request;
    private final Configuration mailConfig;
    private final MailProperties mailProperties;

    public MailSenderThread(EmailSenderRequest request, Configuration mailConfig, MailProperties mailProperties) {
        this.request = request;
        this.mailConfig = mailConfig;
        this.mailProperties = mailProperties;
    }

    @Override
    public void run() {
        try {
            if (request.getTemplateName() == null) {
                this.sendEmail(request);
                return;
            }
            Template template = mailConfig.getTemplate(request.getTemplateName());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, request.getModel());
            request.setContent(html);
            if (request.getAttachmentsUrl() == null) this.sendEmail(request);
            else this.sendEmailWithAttachmentsForTemplate(request);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendEmail(EmailSenderRequest request) {
        try {
            request.getToEmail().forEach(to -> {
                HtmlEmail email;
                try {
                    email = new EmailConfig().simpleJavaMailSender(mailProperties);
                    email.setSubject(request.getSubject());
                    email.setHtmlMsg(request.getContent() == null ? "" : request.getContent());
                    email.addTo(to);
                    if (request.getCcEmail() != null && !request.getCcEmail().isEmpty())
                        email.setCc(parseEmail(request.getCcEmail()));
                    email.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendEmailWithAttachmentsForTemplate(EmailSenderRequest request) {
        try {
            request.getToEmail().forEach(to -> {
                HtmlEmail email = null;
                try {
                    email = new EmailConfig().simpleJavaMailSender(mailProperties);
                    email.setSubject(request.getSubject());
                    email.setHtmlMsg(request.getContent() == null ? "" : request.getContent());
                    email.addTo(to);
                    for (String s : request.getAttachmentsUrl()) {
                        EmailAttachment attachment = new EmailAttachment();
                        attachment.setPath(s);
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);


                        attachment.setName(fileNameFromPathExtractor(s));
                        try {
                            email.attach(attachment);
                        } catch (EmailException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (request.getCcEmail() != null && !request.getCcEmail().isEmpty())
                        email.setCc(parseEmail(request.getCcEmail()));
                    email.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String fileNameFromPathExtractor(String path) {
        String sep = File.separator;
        int lastSlashIndex = path.lastIndexOf(sep);
        return path.substring(lastSlashIndex + 1);
    }

    private Collection<InternetAddress> parseEmail(List<String> emailIds) {
        Collection<InternetAddress> internetAddressList = new ArrayList<>();
        emailIds.forEach(s -> {
            try {
                InternetAddress internetAddress = new InternetAddress(s);
                internetAddressList.add(internetAddress);
            } catch (AddressException e) {
                e.printStackTrace();
            }
        });
        return internetAddressList;
    }
}
