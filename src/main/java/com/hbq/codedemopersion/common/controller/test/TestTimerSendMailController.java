package com.hbq.codedemopersion.common.controller.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import com.hbq.codedemopersion.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
public class TestTimerSendMailController {
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
    @Scheduled(cron = "0 * * * *")
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
    }
}
