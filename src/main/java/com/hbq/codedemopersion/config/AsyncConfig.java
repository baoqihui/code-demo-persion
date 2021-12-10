package com.hbq.codedemopersion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    /**最大线程数*/
    private static final int MAX_POOL_SIZE = 5;
    /**核心线程数*/
    private static final int CORE_POOL_SIZE = 3;
    /**队列大小*/
    private static final int QUEUE_SIZE = 50000;
    /**线程名称前缀*/
    private static final String PREFIX = "async-task-thread-pool";

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_SIZE);
        taskExecutor.setThreadNamePrefix(PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }
}