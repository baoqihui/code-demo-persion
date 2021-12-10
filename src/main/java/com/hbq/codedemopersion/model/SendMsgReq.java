package com.hbq.codedemopersion.model;

import cn.hutool.json.JSONObject;
import com.google.gson.JsonObject;
import com.hbq.codedemopersion.common.model.WechatTemplateEnum;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送微信消息对象
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
public class SendMsgReq {
    /**
     * 微信模板的枚举
     */
    private WechatTemplateEnum templateEnum;

    /**
     * 接收人openid
     */
    private String receiver;

    public SendMsgReq(WechatTemplateEnum templateEnum, String receiver) {
        this.templateEnum = templateEnum;
        this.receiver = receiver;
    }
    public Map<String, Object> covertMsgReqToMap(){
        return new HashMap<>();
    }

    public String covertMsgReqToJsonStr(){
        return new JSONObject(covertMsgReqToMap()).toString();
    }
}
