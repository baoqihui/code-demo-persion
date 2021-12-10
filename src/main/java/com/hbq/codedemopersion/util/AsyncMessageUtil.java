package com.hbq.codedemopersion.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.hbq.codedemopersion.model.SendMsgReq;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: huibq
 * @Description: 异步发送微信消息，因Async注解限制需要单独类
 * @Date: 2021/11/22 10:10
 */
@Slf4j
@Service
@AllArgsConstructor
public class AsyncMessageUtil {
    private RedisUtils redisUtils;
    /**
     * 发送微信消息
     */
    @Async("taskExecutor")
    public void sendWechatMessage(SendMsgReq sendMsgReq) {
        log.info("Send wechat message start, sendMsgReq:{}", sendMsgReq);
        String accessToken= redisUtils.get("externalapi:wechat:token");
        String result = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken,sendMsgReq.covertMsgReqToJsonStr());
        log.info("Send wechat message success, sendMsgReq:{}", sendMsgReq);
        try {} catch (Exception e) {
            log.error("except-error-monitor, Send wechat message failed，cause：{}，sendMsgReq:{}",e.getMessage(),sendMsgReq);
        }
    }

}
