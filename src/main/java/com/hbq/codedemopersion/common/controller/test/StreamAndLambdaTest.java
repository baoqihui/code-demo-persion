package com.hbq.codedemopersion.common.controller.test;

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
    }
}
