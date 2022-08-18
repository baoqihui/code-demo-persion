package com.hbq.codedemopersion.redis_delay_queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 延迟队列业务枚举
 * Created by LPB on 2021/04/20.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RedisDelayQueueEnum {

    ORDER_PAYMENT_TIMEOUT("orderPaymentTimeout", "订单支付超时，自动取消订单"),
    ORDER_TIMEOUT_NOT_EVALUATED("orderTimeoutNotEvaluated", "订单超时未评价，系统默认好评");

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

}