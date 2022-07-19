package com.hbq.codedemopersion.common.controller.test;

import cn.hutool.core.collection.CollUtil;
import com.hbq.codedemopersion.common.model.AutoModel;
import com.hbq.codedemopersion.common.model.F;
import com.hbq.codedemopersion.common.model.S;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: huibq
 * @Description: 流和Lambda表达式测试
 * @Date: 2021/12/10 13:57
 */
@Slf4j
public class StreamAndLambdaTest {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3};
        List<AutoModel> list = List.of(
                new AutoModel(1, "HUI"),
                new AutoModel(2, "XUE"));
        Map<Integer, String> map = new HashMap<>(8) {{
            put(1, "HUI");
            put(2, "XUE");
        }};
        log.info("-------------数组流------------");
        int first = Arrays.stream(array)
                .findFirst()
                .orElseThrow(); //取值失败报错
        log.info("[取第一项] array:{} -> first:{}", array, first);
        log.info("");

        log.info("-------------集合流------------");
        log.info("↓↓↓↓↓原集合:{}↓↓↓↓↓", list);
        list.forEach(e -> log.info("[遍历]{} {}", e.getId(), e));

        AutoModel maxModel = list.stream()
                .max(Comparator.comparing(AutoModel::getId))
                .orElseThrow();
        log.info("[取最大项] maxModel:{}", maxModel);

        List<String> nameList = list.stream()
                .map(AutoModel::getName)
                .collect(Collectors.toList());
        log.info("[取值转换] nameList:{}", nameList);

        String listString = list.stream()
                .map(AutoModel::getName)
                .collect(Collectors.joining(",", "[", "]"));
        log.info("[转换后数据处理] listString:{}", listString);

        List<AutoModel> filterList = list.stream()
                .filter(e -> e.getId() != 2)
                .collect(Collectors.toList());
        log.info("[过滤] filterList:{}", filterList);

        Map<String, List<AutoModel>> groupMap = list.stream()
                .collect(Collectors.groupingBy(AutoModel::getName));
        log.info("[分组] groupMap:{}", groupMap);
        log.info("");

        log.info("-------------map流------------");
        Map<Integer, String> newMap = map.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey() + 1, e -> e.getValue() + 1));
        log.info("map:{} -> newMap:{}", map, newMap);

/*                        .filter(e -> e < 100)   //过滤
                .map(e -> e + 1)    //转换
                .sorted()       //排序
                .distinct()     //去重
                .limit(2)       //限制数量*/
        log.info("复杂");
        map.entrySet().stream()
                .map(e -> new AutoModel(e.getKey(), e.getValue()))         //转换为实体类型
                .filter(e -> e.getId() < 100)               //过滤id小于100
                .limit(2)                               //过滤
                .distinct()                             //去重
                .sorted(Comparator.comparing(AutoModel::getId))         //按照id排序
                .max(Comparator.comparing(AutoModel::getId))            //找最大
                .stream().map(AutoModel::getName)
                .collect(Collectors.joining(",", "[", "]"))
        ;
        HashSet<S> s1 = new HashSet<>();
        s1.add(S.builder().name("s1").build());
        s1.add(S.builder().name("s2").build());

        HashSet<S> s2 = new HashSet<>();
        s2.add(S.builder().name("s2").build());
        s2.add(S.builder().name("s3").build());
        HashSet<S> s3 = new HashSet<>();
        s3.add(S.builder().name("s2").build());
        s3.add(S.builder().name("s3").build());
        F f1 = F.builder().name("f1").password("p1").age(500).s(s1).build();
        F f2 = F.builder().name("f1").password("p1").age(600).s(s2).build();
        F f3 = F.builder().name("f2").age(500).s(s3).build();
        List<F> orgList = List.of(f1, f2, f3);
        log.info("原集合:{}", orgList);
        List<F> nowList = new ArrayList<>(orgList.stream().collect(
                Collectors.toMap(F::fetchGroupKey, a -> a, (o1, o2) -> {
                    // 2.将重复的 o1与o2, Value属性求和, 赋值给o1，最后返回o1
                    o1.setAge(o1.getAge() + o2.getAge());
                    //合并set
                    CollUtil.addAll(o1.getS(), o2.getS());
                    return o1;
                })).values())
                .stream()
                .peek(a-> a.setAge2(getString(a.getAge())))
                .collect(Collectors.toList());
        log.info("现集合:{}", nowList);
    }

    static String getString(Integer i) {
        if (i < 1000) {
            return "1000以下";
        }
        int y = i / 1000;
        return y * 1000 + "-" + (y + 1) * 1000;
    }
}
