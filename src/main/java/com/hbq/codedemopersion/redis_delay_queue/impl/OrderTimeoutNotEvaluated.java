package com.hbq.codedemopersion.redis_delay_queue.impl;

import com.hbq.codedemopersion.redis_delay_queue.RedisDelayQueueHandle;
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
		try {
			log.info("(收到订单超时未评价延迟消息) {}", map);
		}catch (Exception e){
			log.error("订单超时未评价延迟消息异常,{}",e.getMessage());
		}
	}
}