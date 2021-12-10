package com.hbq.codedemopersion.common.controller.test;

import com.hbq.codedemopersion.model.AccountChangeMsgReq;
import com.hbq.codedemopersion.util.AsyncMessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hbq.codedemopersion.common.model.WechatTemplateEnum.ACCOUNT_CHANGE;

/**
 * @Author: huibq
 * @Description: 微信小程序发送订阅消息
 * @Date: 2021/10/31 17:53
 */
@RestController
@RequestMapping("test")
@AllArgsConstructor
public class WechatMessageController {
    private AsyncMessageUtil asyncMessageUtil;
    /**
     * 发送消息到用户
     * @return
     */
    @RequestMapping("/send")
    public String send(){
        AccountChangeMsgReq msgReq = new AccountChangeMsgReq(ACCOUNT_CHANGE, "ognFF5W5JQiXx40TVPIKegfy8JLY", 10, 10, 20L);
        asyncMessageUtil.sendWechatMessage(msgReq);
        return "ok";
    }
}

