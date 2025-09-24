package com.typ.business.config;

import cn.hutool.core.thread.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 文件读写线程池配置
 * @author: typ
 * @date: 2025/9/9
 * @version: 1.0
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static int coreSize = Runtime.getRuntime().availableProcessors();

    private static final int corePoolSize = 5;

    //文件处理线程池
    @Bean(name = "fileThreadPoolExecutor")
    public ThreadPoolExecutor fileThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, coreSize, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
                new NamedThreadFactory("file-thread-pool-",false ),
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.prestartAllCoreThreads();
        return executor;
    }

}
