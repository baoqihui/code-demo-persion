package com.hbq.codedemopersion.common.controller.test;

import com.hbq.codedemopersion.util.manyTaskUtil.BatchAsyncUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: huibq
 * @Description: ElasticSearch测试类
 * @Date: 2021/11/22 14:40
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("test")
@AllArgsConstructor
public class CommonTest {
    private RestHighLevelClient restHighLevelClient;
    private BatchAsyncUtil asyncTest;

    /**
     * 测试异步请求
     */
    @PostMapping("/async")
    public void testAsync() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        asyncTest.batchExecAsync(list);
        long endTime = System.currentTimeMillis();
        log.info("主程序执行线程{}，耗时{}ms", Thread.currentThread().getName(), (endTime - startTime));
    }


    /**
     * 获取es中token
     *
     * @return token
     */
    @PostMapping("/getWxTokenFormES")
    public String getTokenFormElasticSearch() {
        SearchRequest searchRequest = new SearchRequest("prod-tinggeili-scheduletask-*");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.simpleQueryStringQuery("WxPlatformToken"));
        sourceBuilder.from(0);
        sourceBuilder.size(1);
        sourceBuilder.sort("@timestamp", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        Map<String, Object> sourceAsMap = hits1[0].getSourceAsMap();
        Object message = sourceAsMap.get("message");
        log.info("从es中获取到：{}", sourceAsMap.get("message").toString());
        return message.toString();
    }

    /**
     * 检索、分页
     *
     * @param indexName    索引名称
     * @param mpParams     查询参数
     * @param from         起始页
     * @param size         每页数量
     * @param fieldArray   返回列数组
     * @param preciseQuery 1:精确查询 2:模糊查询
     * @return
     */
    @PostMapping("/searchDocument")
    public List<Map<String, Object>> searchDocument(String indexName, Map<String, Object> mpParams,
                                                    int from, int size, String[] fieldArray, Integer preciseQuery) {
        SearchRequest searchRequest = new SearchRequest(indexName);
        // 大多数搜索参数添加到searchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            // 组合字段查询
            BoolQueryBuilder boolQueryBuilder = this.getBoolQueryBuilder(mpParams, preciseQuery);
            searchSourceBuilder.query(boolQueryBuilder);
            // 自定义返回列
            if (fieldArray != null && fieldArray.length > 0) {
                searchSourceBuilder.fetchSource(fieldArray, null);
            }
            // 按照Id倒序
            searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
            // 分页
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(size);
            // 允许搜索的超时时长
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 返回结果
            SearchHits searchHitArray = searchResponse.getHits();
            for (SearchHit searchHit : searchHitArray.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                mapList.add(sourceAsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapList;
    }

    /**
     * 组合字段查询条件
     *
     * @param mpParams
     * @param preciseQuery 1:精确查询 2:模糊查询
     * @return
     */
    public BoolQueryBuilder getBoolQueryBuilder(Map<String, Object> mpParams, Integer preciseQuery) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        try {
            if (mpParams != null) {
                for (Map.Entry<String, Object> entry : mpParams.entrySet()) {
                    if (preciseQuery != null && preciseQuery == 1) {
                        // 精确匹配
                        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(entry.getKey() + ".keyword", entry.getValue());
                        boolQueryBuilder = boolQueryBuilder.must(termQueryBuilder);
                    } else {
                        // 模糊匹配
                        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
                        boolQueryBuilder = boolQueryBuilder.must(matchQueryBuilder);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boolQueryBuilder;
    }
}
