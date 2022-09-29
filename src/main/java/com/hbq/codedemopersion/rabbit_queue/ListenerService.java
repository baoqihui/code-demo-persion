package com.hbq.codedemopersion.rabbit_queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: huibq
 * @Date: 2022/9/29 11:29
 * @Description: 监听等待消息
 */
@Slf4j
@Component
public class ListenerService {

    @RabbitListener(queues = RabbitMqDefaultConstants.DEFAULT_ROUTING_KEY_1)
    public void defaultListener1(String message) {
        log.info("默认消费者1接收到的消息为：{}", message);
    }

    @RabbitListener(queues = RabbitMqDefaultConstants.DEFAULT_ROUTING_KEY_2)
    public void defaultListener2(String message) {
        log.info("默认消费者2接收到的消息为：{}", message);
    }
    int count = 1;
    @RabbitListener(queues = RabbitMqDelayConstants.DELAY_ROUTING_KEY_1)
    public void delayListener1(String message) {
        log.error("重试次数：{}",count++);
        int i=1/0;
        log.info("延时消费者1接收到的消息为：{}", message);
    }

    @RabbitListener(queues = RabbitMqDelayConstants.DELAY_ROUTING_KEY_2)
    public void delayListener2(String message) {
        log.info("延时消费者2接收到的消息为：{}", message);
    }
}
