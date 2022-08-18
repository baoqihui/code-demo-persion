package com.hbq.codedemopersion.redis_queue;

import com.hbq.codedemopersion.common.model.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: huibq
 * @Date: 2022/5/11 13:58
 * @Description: 消息消费者
 */
@Slf4j
@Component
public class MessageConsumer implements BaseQueueConsumer {

    @Override
    public String getQueueName() {
        return RedisKey.REDIS_MESSAGE_LIST_KEY;
    }

    @Override
    public void consumer(String message) {
        log.info("消息消费：{}", message);
    }
}
