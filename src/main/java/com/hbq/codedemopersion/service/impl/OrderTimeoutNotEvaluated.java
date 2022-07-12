package com.hbq.codedemopersion.service.impl;

import com.hbq.codedemopersion.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单超时未评价处理类
 * Created by LPB on 2021/04/20.
 */
@Component
@Slf4j
public class OrderTimeoutNotEvaluated implements RedisDelayQueueHandle<String> {
	@Override
	public void execute(String map) {
		log.info("(收到订单超时未评价延迟消息) {}", map);
		// TODO 订单超时未评价，系统默认好评处理业务...
 
	}
}