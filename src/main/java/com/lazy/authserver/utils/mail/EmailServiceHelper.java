package com.lazy.authserver.utils.mail;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.enums.PasswordSetType;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Author:Prabin-Bhandari
 * @Version:1.0
 * @Date:2023-10-20
 * @For-Investment-Provider
 */

@Component
@RequiredArgsConstructor
public class EmailServiceHelper {
    private final CustomMessageSource customMessageSource;
    private final MailSenderService mailSenderService;
//    @Value("${base.admin-fe}")
//    private String ADMIN_FRONTEND_URI;
//
//    @Value("${base.member-fe}")
//    private String MEMBER_FRONTEND_URI;

    public void sendSimpleEmail(List<String> to, List<String> cc, String subject, String text) {
        CompletableFuture.supplyAsync(() -> {
            try {
                mailSenderService.sendEmail(EmailSenderRequest.builder().toEmail(to).content(text == null ? "" : text).ccEmail(cc).subject(subject).build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }


    public void sendResetPasswordEmail(ResetPasswordDetailRequestPojo requestPojo) {
//        String baseUrl = (requestPojo.getBaseUrl() == null ? (requestPojo.getUserType() == UserType.MEMBER ? MEMBER_FRONTEND_URI :  ADMIN_FRONTEND_URI ):
//                requestPojo.getBaseUrl()) + "/reset-password/";

        String baseUrl = "/reset-password";
        String link = baseUrl + requestPojo.getResetToken();

        Map<String, Object> model = new HashMap<>();
        model.put("fullName", requestPojo.getFullName());
        model.put("passwordResetLink", link);
        model.put("expireTime", requestPojo.getPasswordSetType().equals(PasswordSetType.SET) ? "24 hours" : "10 minutes");
        mailSenderService.sendEmailWithTemplate(EmailSenderRequest.builder().toEmail(Collections.singletonList(requestPojo.getUserEmail())).subject("Password Reset").templateName("password-reset-mail.ftl").model(model).build());
    }

    public void sendResetPasswordSuccessEmail(ResetPasswordDetailRequestPojo requestPojo) {
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", requestPojo.getFullName());
        mailSenderService.sendEmailWithTemplate(EmailSenderRequest.builder().toEmail(Collections.singletonList(requestPojo.getUserEmail())).subject("Password Reset").templateName("password-reset-success-mail.ftl").model(model).build());
    }

//    public void sendNewUserPasswordSetMail(ResetPasswordDetailRequestPojo requestPojo) {
//        String baseUrl = requestPojo.getBaseUrl() == null ? customMessageSource.get(MessageConstants.PASSWORD_SET_LINK) : requestPojo.getBaseUrl() + "/reset-password/";
//        mailSenderService.sendEmail(EmailSenderRequest.builder().toEmail(Collections.singletonList(requestPojo.getUserEmail())).content(customMessageSource.get(MessageConstants.SET_PASSWORD_MAIL_CONTENT, requestPojo.getFullName(), baseUrl)).subject(customMessageSource.get(MessageConstants.SET_PASSWORD_MAIL)).build());
//    }
//
//    public void sendEmailWithAttachments(EmailSenderRequest emailSenderRequest) {
//        mailSenderService.sendEmailWithTemplateAndAttachments(emailSenderRequest);
//    }
//
//
//    public void sendTokenEmail(String token, String email) {
//        Map<String, Object> model = new HashMap<>();
//        model.put("token", token);
//        model.put("expireTime", "10 minutes");
//        model.put("url", customMessageSource.get("verify.user.send.token.url"));
//        mailSenderService.sendEmailWithTemplate(EmailSenderRequest.builder().toEmail(Collections.singletonList(email)).subject("User Authentication Token").templateName("user-authentication-token-mail.ftl").model(model).build());
//    }

}

//source: https://commons.apache.org/proper/commons-email/userguide.html