package com.hbq.codedemopersion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 8;
    /**
     * 队列大小
     */
    private static final int QUEUE_SIZE = 200;
    /**
     * 线程名称前缀
     */
    private static final String PREFIX = "TaskExecutor-";

    @Bean("taskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_SIZE);
        taskExecutor.setThreadNamePrefix(PREFIX);
        taskExecutor.setAwaitTerminationSeconds(10);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return taskExecutor;
    }
}