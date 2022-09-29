package com.hbq.codedemopersion.rabbit_queue;

/**
 * @Author: huibq
 * @Date: 2022/9/29 15:26
 * @Description: 延时队列常量
 */
public class RabbitMqDelayConstants {

    /**
     * 交换器
     */
    public static final String DIRECT_DELAYED = "direct.delayed";
    /**
     * 队列key
     */
    public static final String DELAY_ROUTING_KEY_1 = "delay.no1";
    public static final String DELAY_ROUTING_KEY_2 = "delay.no2";

    /**
     * 队列key枚举，用于创建队列；
     * 将上方的队列key加入这里，队列才会被创建
     */
    public enum QueueEnum {
        DELAY1(DELAY_ROUTING_KEY_1, "延时消息1"),
        DELAY2(DELAY_ROUTING_KEY_2, "延时消息2");
        public final String code;
        private final String name;
        QueueEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
    }
}
