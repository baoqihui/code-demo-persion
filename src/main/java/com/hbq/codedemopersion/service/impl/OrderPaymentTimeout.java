package com.hbq.codedemopersion.service.impl;

import com.hbq.codedemopersion.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付超时处理类
 * Created by LPB on 2021/04/20.
 */
@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<String> {
	@Override
	public void execute(String map) {
		log.info("(收到订单支付超时延迟消息) {}", map);
		// TODO 订单支付超时，自动取消订单处理业务...
	}
}