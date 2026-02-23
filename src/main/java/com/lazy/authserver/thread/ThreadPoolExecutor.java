package com.lazy.authserver.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author UTSAB
 * @for ulci-backend
 */
public class ThreadPoolExecutor {
    private static final ThreadPoolExecutor INSTANCE = new ThreadPoolExecutor();

    private ThreadPoolExecutor() {
    }

    public ExecutorService getExecutorService(int size) {
        return Executors.newFixedThreadPool(size);
    }

    /**
     * Singleton thread pool
     *
     * @return
     */
    public static ThreadPoolExecutor getInstance() {
        return INSTANCE;
    }
}
