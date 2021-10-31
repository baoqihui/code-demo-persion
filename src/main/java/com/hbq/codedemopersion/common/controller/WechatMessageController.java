package com.hbq.codedemopersion.common.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
/**
 * @Author: huibq
 * @Description: 微信小程序发送订阅消息
 * @Date: 2021/10/31 17:53
 */
@RestController
@RequestMapping("/msg")
public class WechatMessageController {

    public String getAccessToken() {
        String appId = "wx9d399a911e0e7989";
        String appSecret = "6eec332357787298bcbdb2628c4abb39";
        String result = cn.hutool.http.HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("access_token");
    }

    @RequestMapping("/send")
    public String send(){
        JSONObject body=new JSONObject();
        body.set("touser","ognFF5W5JQiXx40TVPIKegfy8JLY");
        body.set("template_id","W2JaxAVfBP9CAXxkla2-vroqwSxn0MM6K6eZemSmCek");
        JSONObject json=new JSONObject();
        json.set("amount1",new JSONObject().set("value",5));
        json.set("time2",new JSONObject().set("value", LocalDateTime.now()));
        json.set("thing3",new JSONObject().set("value","测试提现"));
        body.set("data",json);
        //发送
        String accessToken= getAccessToken();
        System.out.println(accessToken);
        String post =  cn.hutool.http.HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken, body.toString());
        return "ok";
    }
}

