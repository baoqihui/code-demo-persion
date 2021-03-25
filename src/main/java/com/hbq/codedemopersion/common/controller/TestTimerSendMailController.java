package com.hbq.codedemopersion.common.controller;

import cn.hutool.core.collection.CollUtil;
import com.hbq.codedemopersion.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "测试定时发送邮件")
@RequestMapping("ums")
public class TestTimerSendMailController {
    /**
     * 示例
     *每隔5秒执行一次：*\/5****?
     *每隔1分钟执行一次：0*\/1***?
     *每天23点执行一次：0 0 23**?
     *每天凌晨1点执行一次：0 0 1**?
     *每月1号凌晨1点执行一次：0 0 1 1*?
     *每月最后一天23点执行一次：0 0 23 L *?
     *每周星期六凌晨1点实行一次：0 0 1?*L
     *在26分、29 分、33 分执行一次：0 26,29,33***?
     *每天的0点、13 点、18 点、21 点都执行一次：0 0 0,13,18,21**?
     *
     * */
    @Scheduled(cron ="0,30,59 20 16 * * *")
    @ApiOperation(value = "发送邮件测试")
    @PostMapping("/mail/send")
    public Result send( ){
        ArrayList<String> tos = CollUtil.newArrayList(
                "idse666666@163.com",
                "1697253894@qq.com",
                "1315751089@qq.com",
                "2442069168@qq.com");
        String oneS = getOneS();
        System.out.println(oneS);
        //MailUtil.send(tos, "彩虹屁测试", oneS, false);
        return Result.succeed("保存成功");
    }

    public static   String getOneS(){
        try {
            //创建客户端对象
            HttpClient client = HttpClients.createDefault();
            /*创建地址 https://du.shadiao.app/api.php*/
            HttpGet get = new HttpGet("https://chp.shadiao.app/api.php");
            //发起请求，接收响应对象
            HttpResponse response = client.execute(get);
            //获取响应体，响应数据是一种基于HTTP协议标准字符串的对象
            //响应体和响应头，都是封装HTTP协议数据。直接使用可能出现乱码或解析错误
            HttpEntity entity = response.getEntity();
            //通过HTTP实体工具类，转换响应体数据
            String responseString = EntityUtils.toString(entity, "utf-8");
            return responseString;
        } catch (IOException e) {
            throw  new RuntimeException("网站获取句子失败");
        }
    }
}
