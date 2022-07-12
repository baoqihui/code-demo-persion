package com.hbq.codedemopersion.common.controller.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlPath;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.hbq.codedemopersion.common.model.DingDingBot;
import com.hbq.codedemopersion.common.model.RedisKey;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.util.AssetUtil;
import com.hbq.codedemopersion.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @Author: hbq
 * @Description: 定时发送邮件测试
 * @Date: 2021/3/26 15:13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "测试定时发送邮件")
@RequestMapping("test")
@AllArgsConstructor
public class ScheduledController {
    private RedisUtils redisUtils;

    /**
     * 示例
     * 每隔5秒执行一次：*\/5****?
     * 每隔1分钟执行一次：0*\/1***?
     * 每天23点执行一次：0 0 23**?
     * 每天凌晨1点执行一次：0 0 1**?
     * 每月1号凌晨1点执行一次：0 0 1 1*?
     * 每月最后一天23点执行一次：0 0 23 L *?
     * 每周星期六凌晨1点实行一次：0 0 1?*L
     * 在26分、29 分、33 分执行一次：0 26,29,33***?
     * 每天的0点、13 点、18 点、21 点都执行一次：0 0 0,13,18,21**?
     * [秒] [分] [小时] [日] [月] [周] [年]
     * 序号 	说明 	必填 	允许填写的值 	    允许的通配符
     * 1 	秒 	    是 	    0-59 	        , - * /
     * 2 	分 	    是 	    0-59 	        , - * /
     * 3 	时 	    是 	    0-23 	        , - * /
     * 4 	日 	    是 	    1-31 	        , - * ? / L W
     * 5 	月 	    是 	    1-12/JAN-DEC 	, - * /
     * 6 	周 	    是 	    1-7 or SUN-SAT 	, - * ? / L #
     * 7 	年 	    否 	    1970-2099 	    , - * /
     */
/*    @Scheduled(cron = "0 * * * *")
    @ApiOperation(value = "发送邮件测试")
    @PostMapping("/mail/send")
    public Result send() {
        ArrayList<String> tos = CollUtil.newArrayList(
                "3022183257@qq.com",
                "1318168098@qq.com"
        );
        String oneS = HttpUtil.get("https://chp.shadiao.app/api.php");
        System.out.println(oneS);
        MailUtil.send(tos, "亲爱的雪！", oneS, true);
        return Result.succeed("保存成功");
    }*/

    /**
     * 定时推送钉钉消息
     * 在每小时的第 0 分钟, 每隔 2 小时, 在 10:00 和 18:59 之间, 星期一至星期五
     * 最终请求：
     * curl --location --request POST 'https://oapi.dingtalk.com/robot/send?access_token=aab19ac6aad2244f54059904f86092f6a11c069784ab80496d564bbcd30ce829&timestamp=1657604878962&sign=kZaUjj8VK%2F7A2NV7BzOtiTQavy293CvYGqK4%2FXpfN1Q%3D' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     *     "msgtype": "text",
     *     "text": {
     *         "content": "消息消息消息"
     *     }
     * }'
     * @return
     */
    @Scheduled(cron = "0 0 10-18/2 * * 1-5")
    @ApiOperation(value = "发送发送钉钉消息")
    @PostMapping("/ding/send")
    public Result dingSend() {
        //钉钉签名
        DingDingBot dingDingBot = AssetUtil.dingDingSign();
        //彩虹屁
        String caiHongPiBody = HttpUtil.get("https://api.shadiao.app/chp");
        String caiHongPi = new JSONObject(new JSONObject(caiHongPiBody).get("data")).getStr("text");
        //随机风景图
        String imgBody = HttpUtil.get(" https://api.ixiaowai.cn/gqapi/gqapi.php?return=json");
        String imgUrl = new JSONObject(imgBody).getStr("imgurl");
        //喝水的次数
        String drink = String.format(RedisKey.DRINK_COUNT_KEY, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN));
        String drinkCount = redisUtils.get(drink);
        redisUtils.incr(drink, 1);
        drinkCount = ObjectUtil.defaultIfNull(drinkCount, "1");
        //恋爱的天数
        long day = DateUtil.betweenDay(DateUtil.parse("2021-10-01"), DateUtil.date(), false);
        String url = UrlBuilder
                .create()
                .setScheme("https")
                .setHost("oapi.dingtalk.com")
                .setPath(UrlPath.of("/robot/send", StandardCharsets.UTF_8))
                .addQuery("access_token", "aab19ac6aad2244f54059904f86092f6a11c069784ab80496d564bbcd30ce829")
                .addQuery("timestamp", dingDingBot.getTimestamp())
                .addQuery("sign", dingDingBot.getSign())
                .build();
        JSONObject body = new JSONObject()
                .set("msgtype", "markdown")
                .set("markdown", new JSONObject()
                        .set("title", "宝宝，要喝水喽！")
                        .set("text", String.format("## 宝宝，今天第%s次喝水喽！\n" +
                                        "![](%s)\n" +
                                        "- %s\n" +
                                        "- 今日喝水**%s**次喽，多喝水，多休息！\n" +
                                        "- 我们已经恋爱**%s**天啦\n",
                                drinkCount,
                                imgUrl,
                                caiHongPi,
                                drinkCount,
                                day)))
                .set("at", new JSONObject().set("isAtAll", true));
        String result = HttpUtil.post(url, body.toString());
        return Result.succeed(result);
    }
}
