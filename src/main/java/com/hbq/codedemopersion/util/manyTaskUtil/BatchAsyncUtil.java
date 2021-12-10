package com.hbq.codedemopersion.util.manyTaskUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: huibq
 * @Description: 批量异步执行，Async注解限制需要单独类
 * @Date: 2021/11/22 14:34
 */
@Slf4j
@Component
@AllArgsConstructor
public class BatchAsyncUtil{
    AsyncUtil asyncUtil;

    /**
     * 使用自定义线程池批量异步执行任务
     * @param list
     */
    @Async("taskExecutor")
    public void batchExecAsync(List<Integer> list) {
        long startTime = System.currentTimeMillis();
        list.forEach(i->asyncUtil.execAsync(i));
        long endTime = System.currentTimeMillis();
        log.info("批量程序执行线程{}，耗时{}ms",Thread.currentThread().getName(),(endTime-startTime));
    }
}
