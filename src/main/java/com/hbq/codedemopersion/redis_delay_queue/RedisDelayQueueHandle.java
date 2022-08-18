package com.hbq.codedemopersion.redis_delay_queue;

/**
 * @Author: huibq
 * @Date: 2022/7/1 11:46
 * @Description: 延迟队列执行器
 */
public interface RedisDelayQueueHandle<T> {

    /**
     * 执行延迟队列
     * @param t 延迟队列参数
     */
	void execute(T t);

}
