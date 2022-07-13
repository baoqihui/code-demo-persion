package com.hbq.codedemopersion.common.controller.test;

import cn.hutool.json.JSONObject;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.util.DingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final DingUtil dingUtil;
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
     * "msgtype": "text",
     * "text": {
     * "content": "消息消息消息"
     * }
     * }'
     *
     * @return
     */
    @Scheduled(cron = "0 0 10,12,14,16,18 * * 1-5")
    @ApiOperation(value = "宝宝，要喝水喽！")
    @GetMapping("/ding/drink")
    public Result drink() {
        long drinkConut = dingUtil.drinkCount();
        JSONObject body = new JSONObject()
                .set("msgtype", "markdown")
                .set("markdown", new JSONObject()
                        .set("title", "宝宝，要喝水喽！")
                        .set("text", String.format(
                                "## 宝宝，今天第%s次喝水喽！\n" +
                                "![](%s)\n" +
                                "- %s\n" +
                                "- 今日喝水**%s**次喽，多喝水，多休息！\n",
                                drinkConut,
                                dingUtil.imgUrl(),
                                dingUtil.caiHongPi(),
                                drinkConut)))
                .set("at", new JSONObject().set("isAtAll", true));
        String msg = dingUtil.send(body);
        log.info("[宝宝，要喝水喽!]消息发送结果：{}", msg);
        return Result.succeed(msg);
    }

    /**
     * 下班提醒
     * 周一-周五下午5:20提醒
     *
     * @return
     */
    @Scheduled(cron = "0 20 17,18 * * 1-5")
    @ApiOperation(value = "宝宝，要下班喽！")
    @GetMapping("/ding/offDuty")
    public Result offDuty() {
        JSONObject body = new JSONObject()
                .set("msgtype", "markdown")
                .set("markdown", new JSONObject()
                        .set("title", "宝宝，要下班喽！")
                        .set("text", String.format(
                                "## 宝宝，要下班喽！\n" +
                                "![](%s)\n" +
                                "- 下班啦！下班啦！不要忘了提交日志哦！\n" +
                                "- 回家路上要慢点，到家了及时报备哦，不然人家会担心的！\n", dingUtil.imgUrl())))
                .set("at", new JSONObject().set("isAtAll", true));
        String msg = dingUtil.send(body);
        log.info("[宝宝，要下班喽！]消息发送结果：{}", msg);
        return Result.succeed(msg);
    }

    /**
     * 恋爱天数提醒
     * 每天8:30
     */
    @Scheduled(cron = "0 30 8 * * *")
    @ApiOperation(value = "宝宝，恋爱n天喽！")
    @GetMapping("/ding/love")
    public Result love() {
        JSONObject body = new JSONObject()
                .set("msgtype", "markdown")
                .set("at", new JSONObject().set("isAtAll", true));
        //周年纪念提醒，连发三次
        if (dingUtil.isAnniversary()) {
            body = new JSONObject()
                    .set("markdown", new JSONObject()
                            .set("title", "宝宝，恋爱n天喽！")
                            .set("text", String.format(
                                    "## 宝宝，%s周年纪念哦！\n" +
                                    "![](%s)\n" +
                                    "- %s\n" +
                                    "- 恋爱**%s**周年，有了你，这世间疾苦烟消云散！\n",
                                    dingUtil.loveYear(),
                                    dingUtil.imgUrl(),
                                    dingUtil.caiHongPi(),
                                    dingUtil.loveYear()
                            )));
            dingUtil.send(body);
            dingUtil.send(body);
        } else if (dingUtil.loveDay() % 100 == 0) {
            //百天纪念提醒，连发三次
            body = new JSONObject()
                    .set("markdown", new JSONObject()
                            .set("title", "宝宝，恋爱n天喽！")
                            .set("text", String.format(
                                    "## 宝宝，%s天纪念哦！\n" +
                                    "![](%s)\n" +
                                    "- %s\n" +
                                    "- 恋爱**%s**天，人生很长，感谢有你相伴！\n",
                                    dingUtil.loveDay(),
                                    dingUtil.imgUrl(),
                                    dingUtil.caiHongPi(),
                                    dingUtil.loveDay()
                            )));
            dingUtil.send(body);
            dingUtil.send(body);
        } else {
            //日常恋爱提醒
            body = body.set("markdown", new JSONObject()
                    .set("title", "宝宝，恋爱n天喽！")
                    .set("text", String.format(
                            "## 宝宝，我们在一起%s天喽！\n" +
                            "![](%s)\n" +
                            "- %s\n" +
                            "- 恋爱**%s**天，恋爱进度条又增加了呢！\n",
                            dingUtil.loveDay(),
                            dingUtil.imgUrl(),
                            dingUtil.caiHongPi(),
                            dingUtil.loveDay()
                    )));
        }
        String msg = dingUtil.send(body);
        log.info("[宝宝，恋爱n天喽！]消息发送结果：{}", msg);
        return Result.succeed(msg);
    }
}
