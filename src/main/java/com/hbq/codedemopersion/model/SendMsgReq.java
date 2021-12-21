package com.hbq.codedemopersion.model;

import com.hbq.codedemopersion.common.model.WechatTemplateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送微信消息对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
public abstract class SendMsgReq {
    /**
     * 接收人openid
     */
    private String receiver;

    public SendMsgReq(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 微信小程序订阅消息模板枚举配置
     *
     * @return 模板信息
     */
    public abstract WechatTemplateEnum templateEnum();


    /**
     * 微信小程序订阅消息请求【定制化参数】组装
     */
    public abstract Map<String, Object> assembleCustomTemplateParams();

    /**
     * 微信小程序订阅消息请求【通用参数】组装
     */
    private Map<String, Object> assembleCommonTemplateParams() {
        Map<String, Object> commonParams = new HashMap<>(4);
        commonParams.put("touser", this.receiver);
        commonParams.put("template_id", templateEnum().getTemplateId());
        commonParams.put("page", templateEnum().getPage());
        return commonParams;
    }

    /**
     * 将系统消息体转换为微信所需map参数
     */
    public Map<String, Object> covertMsgReqToMap() {
        Map<String, Object> requestParams = new HashMap<>();
        Map<String, Object> templateParams = assembleCommonTemplateParams();
        Map<String, Object> customParams = assembleCustomTemplateParams();
        templateParams.put("data", customParams);
        requestParams.put("body", templateParams);
        return requestParams;
    }
}
