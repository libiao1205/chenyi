package com.cn.sundeinfo.main.modular.elasticsearch.controller;

import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;


/**
 * es 6.0版本之前使用
 * es 7.0版本限定单机版使用
 * 该控制器暂时弃用
 * 请使用RestHighLevelClientDemoController.class连接7.x版本es 进行逻辑实现
 *
 *  * @author menshikai
 *  * @create 2021/5/26
 *  * @since 1.0.0
 */
@RestController
@RequestMapping("/elasticsearch2")
public class TransportClientDemoController {

//    @Autowired
//    ElasticExecutor elasticExecutor;

//    @Autowired
//    private TransportClient transportClient;

    //暂时弃用使用请打开上面的transportClient自动注入
    private TransportClient transportClient=null;


    //条件测试 and not or
    @RequestMapping("/conditionsTest")
    public ResponseData conditionsTest() throws Exception {

        //创建源检索器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(1);
        sourceBuilder.size(10);

        //创建判断检索器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // must 完全匹配 and操作
        // must_not  相当于not  
        // should 至少有一个匹配 相当于or
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "Kill");;
        boolQueryBuilder.must(termQueryBuilder);
//        TermQueryBuilder termQueryBuilder2 = QueryBuilders.termQuery("title", "The Assassination of Jesse James by the Coward Robert Ford");;
//        boolQueryBuilder.should(termQueryBuilder2);
//        TermQueryBuilder termQueryBuilder3 = QueryBuilders.termQuery("title", "Kill Bill: Vol. 1");;
//        boolQueryBuilder.should(termQueryBuilder3);
//        TermQueryBuilder termQueryBuilder4 = QueryBuilders.termQuery("title", "The Assassination of Jesse James by the Coward Robert Ford");;
//        boolQueryBuilder.mustNot(termQueryBuilder4);

        //排序
        sourceBuilder.sort("createDate", SortOrder.ASC);
        //添加查询对象
        sourceBuilder.query(boolQueryBuilder);
        //连接客户端进行查询
        ActionFuture<SearchResponse> search = transportClient.search(new SearchRequest().source(sourceBuilder));
        try {
            SearchResponse searchResponse = search.get();
            out.println(searchResponse.status());
            for (SearchHit searchHit : searchResponse.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                sourceAsMap.entrySet().forEach(
                        entry-> out.println(entry.getKey() + ":" + entry.getValue())
                );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new SuccessResponseData();
    }


    @RequestMapping("test2")
    public ResponseData test2(){

        //创建源检索器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // limit 0 10
        sourceBuilder.from(1);
        sourceBuilder.size(10);



        //like检索
        StringBuilder matchValue = new StringBuilder();
        matchValue.append("*").append("Kill").append("*");
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("title",matchValue.toString());

        //处理范围查询 builder
        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("score");
        //// 小于等于 <=
        //rangeQueryBuilder.lte("10");
        //// 小于 <
        //rangeQueryBuilder.lt("10");
        ////大于 >
        //rangeQueryBuilder.gt("10");
        //// 大于等于 >=
        //rangeQueryBuilder.gte("10");
        //
        //// between 10 and 100
        //rangeQueryBuilder.from("10");
        //rangeQueryBuilder.to("100");


//        TermQueryBuilder termQuery = QueryBuilders.termQuery("level", "A");

        //创建检索器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(wildcardQueryBuilder); // title like '%Kill%' and
//        boolQueryBuilder.must(rangeQuery); // score > 90
//        boolQueryBuilder.should(termQuery);// or level = 'A'

        //排序
        sourceBuilder.sort("createDate", SortOrder.ASC);
        //添加查询对象
        sourceBuilder.query(boolQueryBuilder);
        //连接客户端进行查询
        ActionFuture<SearchResponse> search = transportClient.search(new SearchRequest().source(sourceBuilder));
        try {
            SearchResponse searchResponse = search.get();
            out.println(searchResponse.status());
            for (SearchHit searchHit : searchResponse.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                sourceAsMap.entrySet().forEach(
                        entry-> out.println(entry.getKey() + ":" + entry.getValue())
                );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new SuccessResponseData();
    }

    @RequestMapping("test3")
    public ResponseData test3(){
        //创建源检索器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // limit 0 10
        sourceBuilder.from(1);
        sourceBuilder.size(10);

        //like检索
        StringBuilder matchValue = new StringBuilder();
        matchValue.append("*").append("Kill").append("*");
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("title",matchValue.toString());

        //处理范围查询 builder
        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("score");
        //// 小于等于 <=
        //rangeQueryBuilder.lte("10");
        //// 小于 <
        //rangeQueryBuilder.lt("10");
        ////大于 >
        //rangeQueryBuilder.gt("10");
        //// 大于等于 >=
        //rangeQueryBuilder.gte("10");
        //
        //// between 10 and 100
        //rangeQueryBuilder.from("10");
        //rangeQueryBuilder.to("100");

//        TermQueryBuilder termQuery = QueryBuilders.termQuery("level", "A");

        //创建检索器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(wildcardQueryBuilder); // title like '%Kill%' and
//        boolQueryBuilder.must(rangeQuery); // score > 90
//        boolQueryBuilder.should(termQuery);// or level = 'A'
        String[] includes = {"title"};

        //过滤器
        sourceBuilder.fetchSource(includes, null);
        //排序
        sourceBuilder.sort("createDate", SortOrder.ASC);
        //添加查询对象
        sourceBuilder.query(boolQueryBuilder);
        //连接客户端进行查询
        ActionFuture<SearchResponse> search = transportClient.search(new SearchRequest().source(sourceBuilder));
        try {
            SearchResponse searchResponse = search.get();
            out.println(searchResponse.status());
            for (SearchHit searchHit : searchResponse.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                sourceAsMap.entrySet().forEach(
                        entry-> out.println(entry.getKey() + ":" + entry.getValue())
                );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return new SuccessResponseData();
    }

}
