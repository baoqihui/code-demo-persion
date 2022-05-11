package com.hbq.codedemopersion.listener;

import com.hbq.codedemopersion.listener.config.RedisQueueConsumerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huibq
 * @Date: 2022/5/11 13:20
 * @Description: redis消费者/生产者模式配置
 */
@Slf4j
@Configuration
public class QueueConsumerConfig {

    /**
     * 初始化完毕后调取 init
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public RedisQueueConsumerContainer redisQueueConsumerContainer() {
        log.info("redis队列开始加载");
        RedisQueueConsumerContainer redisQueueConsumerContainer = new RedisQueueConsumerContainer();
        // 添加消费者到消费者容器
        redisQueueConsumerContainer.addConsumer(new MessageConsumer());
        log.info("redis队列开始加载成功");
        return redisQueueConsumerContainer;
    }
}
