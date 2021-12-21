package com.hbq.codedemopersion.common.model;


/**
 * @Author: 微信消息发送模板
 * @Description:
 * @Date: 2021/10/29 13:52
 */
public enum WechatTemplateEnum {
    ACCOUNT_CHANGE("奖励到账通知", "accountChange", "xpNegMVvwaRCSHGFOB7QUv9c1ak_B2NXBGlouOZClo0", "pages/index/index"),
    ACTIVITY_SCHEDULE("活动进度提醒", "activitySchedule", "sOgInDYlc3aZlOQwLPFeAz0HauCZ1paw02FMvbdXqB8", "pages/index/index"),
    ACTIVE_NOTE("活动通知", "activityNote", "QEZ8Tj79Jrh9B_LiltMGsr8pDs4mtyM0aUoShefElRQ", "pages/index/index");

    private String title;
    private String key;
    private String templateId;
    private String page;


    WechatTemplateEnum(String title, String key, String templateId, String page) {
        this.title = title;
        this.key = key;
        this.templateId = templateId;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
