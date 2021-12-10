package com.hbq.codedemopersion.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 微信消息发送模板
 * @Description:
 * @Date: 2021/10/29 13:52
 */
public enum WechatTemplateEnum {
    ACCOUNT_CHANGE("奖励到账通知","xpNegMVvwaRCSHGFOB7QUv9c1ak_B2NXBGlouOZClo0","pages/index/index", new HashMap(8){{
        put("param1","thing1");
        put("param2","amount2");
        put("param3","time3");
        put("param4","thing4");
    }}),
    ACTIVITY_SCHEDULE("活动进度提醒","sOgInDYlc3aZlOQwLPFeAz0HauCZ1paw02FMvbdXqB8","pages/index/index", new HashMap(8){{
        put("param1","thing1");
        put("param2","thing4");
        put("param3","thing5");
        put("param4","thing6");
    }}),
    ACTIVE_NOTE("活动通知","QEZ8Tj79Jrh9B_LiltMGsr8pDs4mtyM0aUoShefElRQ","pages/index/index", new HashMap(8){{
        put("param1","thing4");
        put("param2","amount9");
        put("param3","thing11");
        put("param4","thing3");
    }});
    private String title;
    private String templateId;
    private String page;
    private Map map;

    WechatTemplateEnum(String title, String templateId, String page, Map map) {
        this.title = title;
        this.templateId = templateId;
        this.page = page;
        this.map = map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
