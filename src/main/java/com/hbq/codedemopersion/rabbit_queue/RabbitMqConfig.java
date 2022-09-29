package com.hbq.codedemopersion.rabbit_queue;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: huibq
 * @Date: 2022/9/29 15:33
 * @Description: 配置rabbitmq
 */
@Configuration
public class RabbitMqConfig {
    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * 创建初始化RabbitAdmin对象
     *
     * @param connectionFactory connectionFactory
     * @return rabbitAdmin
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 默认交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange defaultExchange() {
        // 参数意义: name: 名称、durable: true、autoDelete: 自动删除
        return new DirectExchange(RabbitMqDefaultConstants.DIRECT_DEFAULT, true, false);
    }

    /**
     * 延时交换机
     *
     * @return CustomExchange
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMqDelayConstants.DIRECT_DELAYED, "x-delayed-message", true, false, args);
    }

    /**
     * 读取创建交换机和对列
     */
    @PostConstruct
    public void declareQueueByConfig() {
        //延时
        Arrays.stream(RabbitMqDelayConstants.QueueEnum.values()).forEach(
                queueEnum -> {
                    Queue queue = new Queue(queueEnum.getCode(), true);
                    rabbitAdmin.declareQueue(queue);
                    rabbitAdmin.declareBinding(
                            BindingBuilder.bind(queue)
                                    .to(delayedExchange())
                                    .with(queueEnum.getCode())
                                    .noargs()
                    );
                }
        );
        //默认
        Arrays.stream(RabbitMqDefaultConstants.QueueEnum.values()).forEach(
                queueEnum -> {
                    Queue queue = new Queue(queueEnum.getCode(), true);
                    rabbitAdmin.declareQueue(queue);
                    rabbitAdmin.declareBinding(
                            BindingBuilder.bind(queue)
                                    .to(defaultExchange())
                                    .with(queueEnum.getCode())
                    );
                }
        );
    }
}