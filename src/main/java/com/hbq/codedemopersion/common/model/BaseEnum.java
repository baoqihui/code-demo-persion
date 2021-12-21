package com.hbq.codedemopersion.common.model;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

/**
 * @Author: huibq
 * @Description: 标准枚举
 * @Date: 2021/12/10 14:37
 */
@Slf4j
public enum BaseEnum {
    /**
     * 回
     */
    HUI_ENUM(1, "HUI"),
    /**
     * 雪
     */
    XUE_ENUM(2, "XUE");

    private final Integer id;
    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    BaseEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 通过id获取当前枚举
     *
     * @param id 序号
     * @return 返回对应枚举
     */
    public static BaseEnum getBaseEnumById(Integer id) {
        return Arrays.stream(values())
                .filter(it -> it.getId().equals(id))
                .findFirst().orElseThrow();
    }
    /**
     * 使用switch进行测试
     *
     * @param args
     */
    public static void switchEnum(Integer id) {
        BaseEnum testEnum = getBaseEnumById(id);
        switch (testEnum) {
            case HUI_ENUM:
                log.info(HUI_ENUM.getName());
                break;
            case XUE_ENUM:
                log.info(XUE_ENUM.getName());
                break;
            default:
                log.info("not find");
        }
    }

    public static void main(String[] args) {
        switchEnum(2);
    }
}
