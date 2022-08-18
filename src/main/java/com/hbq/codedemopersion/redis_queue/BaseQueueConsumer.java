package com.hbq.codedemopersion.redis_queue;

/**
 * @Author: huibq
 * @Date: 2022/5/11 13:45
 * @Description: redis队列消费者接口
 */
public interface BaseQueueConsumer {
    /**
     * 获取队列名称
     * @return 队列名称
     */
    String getQueueName();

    /**
     * 对队列进行消费
     * @param message 消费的消息
     */
    void consumer(String message);
}
