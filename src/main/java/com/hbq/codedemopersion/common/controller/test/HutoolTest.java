package com.hbq.codedemopersion.common.controller.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hbq.codedemopersion.util.User;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class HutoolTest {
    public static void main(String[] args) {
        String s2 = JSONUtil.toJsonStr(new User("aaa", "bbb", null));
        System.out.println(s2);

        List<String> betweenDates = getBetweenDates(null,DateUtil.parse("2022-04-01 12:59:59", DatePattern.NORM_DATE_PATTERN), new Date(), new ArrayList<>(10));
        log.info("-------------新建------------");
        List<Integer> l1 = ListUtil.of(1, 2);
        log.info("新建list并赋值:{}", l1);
        log.info("");

        log.info("-------------判空------------");
        String s = null;
        List list = null;
        Map map = null;
        Integer code = null;

        boolean b1 = StrUtil.isEmpty(s);
        log.info("字符串判空:{}", b1);
        boolean b2 = CollectionUtil.isEmpty(list);
        log.info("集合判空:{}", b2);
        boolean b3 = ObjectUtil.isEmpty(map);
        log.info("对象判空:{}", b3);
        Integer i1 = ObjectUtil.defaultIfNull(code, 2);
        log.info("如果为空替换为默认值:{}", i1);
        log.info("");

        log.info("-------------复制------------");
        User user = User.builder().name("HUI").gender("24").build();
        User newUser = new User();
        BeanUtil.copyProperties(user, newUser);
        log.info("user->newUser:{}", newUser);
        //集合中对象复制
        List<User> users = ListUtil.of(user);
        List<User> newUsers = BeanUtil.copyToList(users, User.class);
        log.info("user->newUsers:{}", newUsers);
        log.info("");

        log.info("-------------时间------------");
        DateTime t = DateUtil.date();
        String s1 = DateUtil.format(t, DatePattern.NORM_DATE_PATTERN);
        log.info("DateTime->String:{}", s1);
        DateTime t1 = DateUtil.parse("2021-11-10 12:59:59", DatePattern.NORM_DATE_PATTERN);
        log.info("String->DateTime:{}", t1);
        int c1 = DateUtil.compare(t, t1, DatePattern.NORM_DATE_PATTERN);
        log.info("按照指定格式对比两个时间t-t1：{}", c1);
        DateTime t2 = DateUtil.beginOfDay(t);
        DateTime t3 = DateUtil.endOfDay(t).offset(DateField.MILLISECOND, -999);
        log.info("每天开始时间：{}，每天结束时间（注意存入数据库应左偏以免毫秒大于500进位）：{}", t2, t3);
        boolean b = DateUtil.isSameDay(DateUtil.date(), DateUtil.endOfMonth(DateUtil.date()));
        log.info("今天是否为本月最后一天：{}", b);
        log.info("s");

        log.info("-------------转换------------");
        Long l = Convert.toLong(1);
        log.info("Object->Long：{}", l);

        boolean m1 = ReUtil.isMatch("^142000.*", "142000123456");
        log.info("正则校验筛142000123456是否是142000开头{}", m1);

        Map<String, Object> params = new HashMap<>(8) {{
            put("username", 1);
            put("plate_type", 1);
        }};
        log.info("map转实体{}", BeanUtil.toBeanIgnoreCase(params, AutoModel.class, true));
        log.info("实体转json参数：{}", HttpUtil.toParams(params));
        log.info("实体转map参数：{}", Convert.toMap(String.class, String.class, params));
        log.info("-------------HTTP------------");
        /*
        String s2 = HttpUtil.get("https://www.baidu.com");
        log.info("HTTP GET Result:{}",s2);
        JSONObject body=new JSONObject();
        body.set("token","ognFF5W5JQiXx40TVPIKegfy8JLY");
        String s3 = HttpUtil.post("https://www.baidu.com", body);
        log.info("HTTP POST Result:{}",s3);
        log.info("-------------file------------");
        String fileUrl = "https://cheche-dev.obs.cn-north-1.myhuaweicloud.com:443/sdas/itg/ac_01/60e8d13b-2229-46b0-a62d-fad017d798f4/default/8f4c2621-011e-4145-8374-04d4db5a223a.zip?AccessKeyId=LP558ZIZWMCVWNQR53PW&Expires=1646378117&Signature=aERZyJ2mzIxIbYV2Yl1P33XVWRc%3D";
        File file1 = HttpUtil.downloadFileFromUrl(fileUrl, FileUtil.mkdir(IdWorker.getId() + File.separator), 10000);
        String mimeType = FileUtil.getMimeType(file1.getName());
        log.info("从url下载文件，filename：{}", file1.getName());
        File file2 = HttpUtil.downloadFileFromUrl("http://img.huijia21.com/blog/1626493168032.png", FileUtil.file("./"), 10000);
        File zip = ZipUtil.zip(FileUtil.file("./test.zip"), false, List.of(file1, file2).toArray(File[]::new));
        log.info("压缩文件，filename：{}", zip.getName());
        //FileUtil.del(zip);
        */
        //String host = UrlBuilder.ofHttp(request.getRequestURL().toString()).getHost();
/*        String callBackUrl = UrlBuilder.create()
                .setScheme("https")
                .setHost(host)
                .setPath(UrlPath.of("/api/v2.0/wechatApp/subscribeMessage/callback", StandardCharsets.UTF_8))
                .build();
        log.info("callBackUrl:{}", callBackUrl);
        log.info("callBackUrl:{}", URLEncoder.createPathSegment().encode(callBackUrl, StandardCharsets.UTF_8));
        log.info("callBackUrl:{}", org.apache.catalina.util.URLEncoder.DEFAULT.encode(callBackUrl,StandardCharsets.UTF_8));*/
    }

    private static List<String> getBetweenDates(Date nowDate, Date startDate, Date endDate, List<String> dateStrList) {
        nowDate = ObjectUtil.defaultIfNull(nowDate, startDate);
        if (DateUtil.isIn(nowDate, startDate, endDate)) {
            String format = DateUtil.format(nowDate, DatePattern.NORM_DATE_PATTERN);
            dateStrList.add(format);
            return getBetweenDates(DateUtil.offsetDay(nowDate, 1), startDate, endDate, dateStrList);
        } else {
            return dateStrList;
        }
    }
}
