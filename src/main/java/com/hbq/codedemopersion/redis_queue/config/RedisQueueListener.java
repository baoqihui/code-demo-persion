package com.hbq.codedemopersion.redis_queue.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hbq.codedemopersion.redis_queue.BaseQueueConsumer;
import com.hbq.codedemopersion.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: huibq
 * @Date: 2022/5/11 13:26
 * @Description: 消息队列的监听器
 */
@Slf4j
public class RedisQueueListener implements Runnable {
    private BaseQueueConsumer baseQueueConsumer;

    public RedisQueueListener(BaseQueueConsumer baseQueueConsumer) {
        this.baseQueueConsumer = baseQueueConsumer;
    }

    @Override
    public void run() {
        RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
        String queueName = baseQueueConsumer.getQueueName();
        log.info("redis监听器开始监听:{}", queueName);
        while (RedisQueueConsumerContainer.isRun) {
            try {
                String message = redisUtils.brpop(queueName,300L, TimeUnit.MILLISECONDS);
                if (ObjectUtil.isNotEmpty(message)) {
                    log.info("received message [{}] from queue [{}] ", message, queueName);
                    baseQueueConsumer.consumer(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("redis监听器错误：{}", queueName);
            }
        }
    }
}
