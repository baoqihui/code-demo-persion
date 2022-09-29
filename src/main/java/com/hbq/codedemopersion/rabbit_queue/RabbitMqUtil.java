package com.hbq.codedemopersion.rabbit_queue;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.hbq.codedemopersion.rabbit_queue.RabbitMqDelayConstants.DIRECT_DELAYED;

/**
 * @Author: huibq
 * @Date: 2022/9/28 17:42
 * @Description: RabbitMqUtil
 */
@Slf4j
@Service
@AllArgsConstructor
public class RabbitMqUtil {
    private RabbitTemplate rabbitTemplate;

    /**
     * 向默认队列推送消息
     *
     * @param queueCode 队列编码
     * @param value     消息内容
     */
    public <T> void sendDefaultMessage(String queueCode, T value) {
        log.info("(添加默认队列成功) 队列键：{}，队列值：{}", queueCode, value);
        try {
            rabbitTemplate.convertAndSend(RabbitMqDefaultConstants.DIRECT_DEFAULT, queueCode, value);
        } catch (Exception e) {
            log.error("(添加默认队列失败)", e);
        }
    }

    /**
     * 发送延时消息到默认交换机
     *
     * @param queueCode 队列编码
     * @param value     消息内容
     * @param delayTime 延时时间
     * @param timeUnit  时间单位
     */
    public <T> void addDelayQueue(String queueCode, T value, Long delayTime, TimeUnit timeUnit) {
        log.info("(添加延时队列成功) 队列键：{}，队列值：{}，延迟时间：{}秒", queueCode, value, timeUnit.toSeconds(delayTime));
        try {
            rabbitTemplate.convertAndSend(DIRECT_DELAYED, queueCode, value,
                    message -> {
                        message.getMessageProperties().setHeader("x-delay", timeUnit.toMillis(delayTime));
                        return message;
                    });
        } catch (Exception e) {
            log.error("(添加延时队列失败) ", e);
        }
    }

    /**
     * 定时推送消息
     * @param queueCode 队列编码
     * @param value     消息内容
     * @param sendTime  发送时间
     */
    public <T> void addDelayQueue(String queueCode, T value, Date sendTime) {
        log.info("(添加定时推送队列成功) 队列键：{}，队列值：{}，延迟时间：{}秒，实际发送时间为：{}", queueCode, value, DateUtil.between(new Date(), sendTime, DateUnit.SECOND), sendTime);
        addDelayQueue(queueCode, value, DateUtil.between(new Date(), sendTime, DateUnit.MS, false), TimeUnit.MILLISECONDS);
    }
}
