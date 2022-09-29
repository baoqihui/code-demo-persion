package com.hbq.codedemopersion.common.controller;

import cn.hutool.core.date.DateUtil;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.rabbit_queue.RabbitMqDefaultConstants;
import com.hbq.codedemopersion.rabbit_queue.RabbitMqDelayConstants;
import com.hbq.codedemopersion.rabbit_queue.RabbitMqUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: huibq
 * @Date: 2022/9/29 15:42
 * @Description: RabbitMqController
 */
@Slf4j
@RestController
@RequestMapping("test/rabbit")
@AllArgsConstructor
public class RabbitMqController {
    private RabbitMqUtil rabbitMqUtil;

    @ApiOperation(value = "发送信息到默认队列1")
    @GetMapping(value = "toQueue1")
    public Result toQueue1(String message) {
        rabbitMqUtil.sendDefaultMessage(RabbitMqDefaultConstants.DEFAULT_ROUTING_KEY_1,message);
        return Result.succeed("发送成功");
    }

    @ApiOperation(value = "发送信息到默认队列2")
    @GetMapping(value = "toQueue2")
    public Result toQueue2(String message) {
        rabbitMqUtil.sendDefaultMessage(RabbitMqDefaultConstants.DEFAULT_ROUTING_KEY_2,message);
        return Result.succeed("发送成功");
    }

    @ApiOperation(value = "延时发送：延时10s发送信息到延时队列1")
    @GetMapping(value = "toDelayQueue1")
    public Result toDelayQueue1(String message) {
        rabbitMqUtil.addDelayQueue(RabbitMqDelayConstants.DELAY_ROUTING_KEY_1, message, 10L, TimeUnit.SECONDS);
        return Result.succeed("发送成功");
    }

    @ApiOperation(value = "定时发送：20s后推送消息到延时队列2")
    @GetMapping(value = "toDelayQueue2")
    public Result toDelayQueue2(String message) {
        rabbitMqUtil.addDelayQueue(RabbitMqDelayConstants.DELAY_ROUTING_KEY_2, message, DateUtil.offsetSecond(DateUtil.date(),20));
        return Result.succeed("发送成功");
    }
}
