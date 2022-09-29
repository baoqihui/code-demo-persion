package com.hbq.codedemopersion.rabbit_queue;

/**
 * @Author: huibq
 * @Date: 2022/9/29 15:26
 * @Description: 默认队列常量
 */
public class RabbitMqDefaultConstants {

    /**
     * 交换器
     */
    public static final String DIRECT_DEFAULT = "direct.default";

    /**
     * 队列的key
     */
    public static final String DEFAULT_ROUTING_KEY_1 = "default.no1";
    public static final String DEFAULT_ROUTING_KEY_2 = "default.no2";

    /**
     * 队列key枚举，用于创建队列；
     * 将上方的队列key加入这里，队列才会被创建
     */
    public enum QueueEnum {
        DELAY1(DEFAULT_ROUTING_KEY_1, "默认消息1"),
        DELAY2(DEFAULT_ROUTING_KEY_2, "默认消息2");
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
