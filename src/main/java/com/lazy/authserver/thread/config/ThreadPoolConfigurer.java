package com.lazy.authserver.thread.config;



import com.lazy.authserver.thread.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;

/**
 * @Author:Prabin-Bhandari
 * @Version:1.0
 * @Date:2023-10-20
 */
public class ThreadPoolConfigurer {
     public static final ExecutorService mailSenderThread = ThreadPoolExecutor.getInstance().getExecutorService(5);
     public static final ExecutorService fallbackAuthenticationRequest = ThreadPoolExecutor.getInstance().getExecutorService(3);

}
