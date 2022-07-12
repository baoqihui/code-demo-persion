package com.hbq.codedemopersion.common.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.hbq.codedemopersion.common.model.RedisDelayQueueEnum;
import com.hbq.codedemopersion.service.RedisDelayQueueHandle;
import com.hbq.codedemopersion.util.RedisDelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动延迟队列
 * Created by LPB on 2021/04/20.
 */
@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {
 
	@Autowired
	private RedisDelayQueueUtil redisDelayQueueUtil;
 
    @Override
	public void run(String... args) {
		RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
		for (RedisDelayQueueEnum queueEnum : queueEnums) {
			new Thread(() -> {
				try {
					while (true) {
						Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
						RedisDelayQueueHandle redisDelayQueueHandle = SpringUtil.getBean(queueEnum.getBeanId());
						redisDelayQueueHandle.execute(value);
					}
				} catch (InterruptedException e) {
					log.error("(Redis延迟队列异常中断) {}", e.getMessage());
				}
			}).start();
		}
		log.info("(Redis延迟队列启动成功)");
	}
}