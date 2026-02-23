package com.lazy.authserver.thread.executor;

import com.lazy.authserver.config.mail.MailProperties;
import com.lazy.authserver.thread.config.ThreadPoolConfigurer;
import com.lazy.authserver.thread.dtt.MailSenderThread;
import com.lazy.authserver.utils.mail.EmailSenderRequest;
import freemarker.template.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * @Author:Prabin-Bhandari
 * @Version:1.0
 * @Date:2023-10-20
 * @For-Investment-Provider
 */
public class MailSenderThreadExecutor {
    private static final ExecutorService executorService = ThreadPoolConfigurer.mailSenderThread;

    public static ExecutorService getDataExecutorService(EmailSenderRequest request, Configuration mailConfig, MailProperties mailProperties) {
        executorService.submit(new MailSenderThread(request,mailConfig,mailProperties));
        return executorService;
    }
}
