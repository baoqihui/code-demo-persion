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
import cn.hutool.core.util.StrUtil;
import com.hbq.codedemopersion.util.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HutoolTest {
    public static void main(String[] args) {

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

        log.info("-------------HTTP------------");
/*        String s2 = HttpUtil.get("https://www.baidu.com");
        log.info("HTTP GET Result:{}",s2);
        JSONObject body=new JSONObject();
        body.set("token","ognFF5W5JQiXx40TVPIKegfy8JLY");
        String s3 = HttpUtil.post("https://www.baidu.com", body);
        log.info("HTTP POST Result:{}",s3);*/

        Map<String, Object> params = new HashMap<>(8) {{
            put("username", 1);
            put("plate_no", 1);
            put("plate_type", 1);
        }};
        System.out.println(BeanUtil.toBeanIgnoreCase(params, ZhiCheAuto.class, true));


        int increase = 10;
        int allMonth = 80 * 12;
        int allMoney = 0;
        for (int i = 1; i <= allMonth; i++) {
            allMoney=allMoney+increase*i;
        }
        System.out.println(allMoney);

    }
}
