package com.hbq.codedemopersion.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlPath;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.hbq.codedemopersion.common.model.DingDingBot;
import com.hbq.codedemopersion.common.model.RedisKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@AllArgsConstructor
public class DingUtil {
    private RedisUtils redisUtils;
    /**
     * 恋爱时间
     */
    private static DateTime loveDay = DateUtil.parse("2021-10-01");

    /**
     * 图片
     */
    public String imgUrl() {
        //随机风景图
        return HttpUtil.get(" https://common.service.cf/file/random/love");
    }

    /**
     * 获取钉钉url
     */
    public String dingUrl() {
        //钉钉签名
        DingDingBot dingDingBot = AssetUtil.dingDingSign();
        return UrlBuilder
                .create()
                .setScheme("https")
                .setHost("oapi.dingtalk.com")
                .setPath(UrlPath.of("/robot/send", StandardCharsets.UTF_8))
                .addQuery("access_token", "aab19ac6aad2244f54059904f86092f6a11c069784ab80496d564bbcd30ce829")
                .addQuery("timestamp", dingDingBot.getTimestamp())
                .addQuery("sign", dingDingBot.getSign())
                .build();
    }

    /**
     * 获取彩虹屁
     */
    public String caiHongPi() {
        String caiHongPiBody = HttpUtil.get("https://api.shadiao.app/chp");
        return new JSONObject(new JSONObject(caiHongPiBody).get("data")).getStr("text");
    }

    /**
     * 恋爱天数
     */
    public long loveDay() {
        return DateUtil.betweenDay(loveDay, DateUtil.date(), false);
    }

    /**
     * 恋爱年数
     * @return
     */
    public long loveYear() {
        return DateUtil.betweenYear(loveDay, DateUtil.date(), false);
    }
    /**
     * 喝水次数
     */
    public long drinkCount() {
        return redisUtils.incr(String.format(RedisKey.DRINK_COUNT_KEY, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN)), 1);
    }

    /**
     * 发送请求
     */
    public String send(JSONObject body) {
        String result = HttpUtil.post(dingUrl(), body.toString());
        return new JSONObject(result).getStr("errmsg");
    }

    /**
     * 是否周年
     */
    public Boolean isAnniversary() {
        boolean isSameMonth = DateUtil.month(loveDay) == DateUtil.month(DateUtil.date());
        boolean isSameDay = DateUtil.dayOfMonth(loveDay) == DateUtil.dayOfMonth(DateUtil.date());
        return isSameMonth && isSameDay;
    }
}
