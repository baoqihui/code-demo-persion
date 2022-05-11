package com.hbq.codedemopersion.listener.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: huibq
 * @Date: 2022/5/11 13:22
 * @Description: redis线程池
 */
@Slf4j
public class MyRedisThreadPool {
    private ThreadPoolExecutor executor;

    private static MyRedisThreadPool instance;

    public MyRedisThreadPool(int coreNum) {
        // 用单例模式创建线程池，保留2个核心线程
        executor = new ThreadPoolExecutor(coreNum == 0 ? 2 : coreNum,
            coreNum,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    }

    public static MyRedisThreadPool getInstance(int coreNum) {
        if (instance == null) {
            instance = new MyRedisThreadPool(coreNum);
            log.info("MyRedisThreadPool线程池已经开启");
        }
        return instance;
    }

    public void executor(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        executor.execute(runnable);
    }

    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
