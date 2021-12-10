package com.hbq.codedemopersion.util.manyTaskUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: huibq
 * @Description: 异步执行任务，Async注解限制单独类
 * @Date: 2021/11/22 14:33
 */
@Slf4j
@Component
public class AsyncUtil{
    /**
     * 使用自定义线程池异步执行任务
     * @param i
     */
    @Async("taskExecutor")
    public void execAsync(Integer i) {
        try {
            log.info("处理值{}，线程名{}",i,Thread.currentThread().getName());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
