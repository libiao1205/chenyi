package com.cn.sundeinfo.main.modular.elasticsearch.service.impl;

import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.ConditionalSearch;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.QueryCriteria;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.SearchCitions;
import com.cn.sundeinfo.main.modular.elasticsearch.service.EleasticSearchProcessor;
import com.cn.sundeinfo.main.modular.elasticsearch.service.ResourcePermissionHandlingService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.*;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * 〈es核心处理器〉
 *
 * @author shikai_men
 * @create 2021/5/29
 */
@Service
public class EleasticSearchProcessorImpl implements EleasticSearchProcessor {

    private final int DEFAULT_TIME_VALUE=1000;
    private final int DEFAULT_GROUP_SIZE=100;

    @Qualifier("restClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Resource
    ResourcePermissionHandlingService resourcePermissionHandlingService;


    /**
     * 查询方法-默认
     * version 1.0
     * @param request
     * @param citions
     * @return
     */
    public ResponseData search(HttpServletRequest request, SearchCitions citions, String _index){
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(citions.getPages().getCurrentPage(), citions.getPages().getPageSize());
        ResponseData dataFilteringArray = resourcePermissionHandlingService.getDataFilteringArray(request);

        return dataFilteringArray.getSuccess()?
                new SuccessResponseData(
                        searchDataProcessor(searchSourceBuilder,citions,(String[])dataFilteringArray.getData(),_index)
                    ):
                dataFilteringArray;

    }

    /**
     * 获取SearchSourceBuilder对象 初始化
     * @param from
     * @param size
     * @return
     */
    @Override
    public SearchSourceBuilder getSearchSourceBuilder(int from,int size) {
        return new SearchSourceBuilder().
                from(disposeByFrom(from)).
                size(disposeBySize(size)).
                timeout(new TimeValue(DEFAULT_TIME_VALUE, TimeUnit.SECONDS));
    }

    /**
     * 获取SearchSourceBuilder对象 重载方法
     * @param from
     * @param size
     * @param timeOut
     * @return
     */
    @Override
    public SearchSourceBuilder getSearchSourceBuilder(int from,int size,int timeOut) {
        return new SearchSourceBuilder().
                from(disposeByFrom(from)).
                size(disposeBySize(size)).
                timeout(new TimeValue(timeOut, TimeUnit.SECONDS));
    }


    /**
     * 检索数据核心处理方法
     * @param searchSourceBuilder
     * @return
     */
    @Override
    public SearchResponse searchDataProcessor(SearchSourceBuilder searchSourceBuilder,SearchCitions citions,String[] includes,String _index){

        //起止时间
        long start = System.currentTimeMillis();


        //添加过滤器
        searchSourceBuilder.fetchSource(includes ,null);
//        searchSourceBuilder.fetchSource(new String[]{"summary","searchYears","keyword","author"} ,null);

        //创建选择器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //获取基本参数
        Map<String, Object> where = citions.getConditionsMap();

        /**
         * 其他参数组合检索 包括 年份+类型两种判断
         * 如传递key值为年份选择器相关：
         *          年份使用时间选择器 请确保传递类型为Integer
         *          暂未使用Date类型进行格式化存储
         *
         *  如传递key值为类型选择器相关：
         *          类型使用精确选择器 请确保传递类型为Integer类型数组
         */
        if (where != null && !where.isEmpty() && where.size()>0) {
            where.forEach((k, v) -> {
                if (v!=null){
                    if (v instanceof Map) {
                        //范围选择map  暂定时间
                        Map<String, Integer> mapRrange = (Map<String, Integer>) v;
                        if (!mapRrange.isEmpty()) {
                            boolQueryBuilder.must(
                                    QueryBuilders.rangeQuery(k).
                                            gte(mapRrange.get("startDate")).
                                            lte(mapRrange.get("endDate")));
                        }
                    } else if(v instanceof List){
                        List<String> type_arr = (List) v;
                        if (type_arr.size() > 0) {
                            boolQueryBuilder.must(QueryBuilders.termsQuery(k, type_arr));
                            out.println(boolQueryBuilder.toString());
                        }
                    }
                }
            });
        }


        //添加高亮显示
//        List<String> fieldList = citions.getItems().stream().map(ConditionalSearch::getField).collect(Collectors.toList());
//        HighlightBuilder highlightBuilder = new HighlightBuilder()
//                .highlighterType("unified")
//                .preTags("<span class=\"highLight\">")
//                .postTags("</span>");
//        for (String field : fieldList){
//            highlightBuilder.field(field);
//        }


        /**
         * 拼接组合条件检索信息
         */
        List<ConditionalSearch> andList = citions.getItems()
                .stream()
                .filter(conditionalSearch ->
                        conditionalSearch
                                .getSearchCriteria().getKey() == 0 && conditionalSearch.getValue()!=null && !"".equals(conditionalSearch.getValue()))
                .collect(Collectors.toList());

        List<ConditionalSearch> orList = citions.getItems()
                .stream()
                .filter(conditionalSearch ->
                        conditionalSearch
                                .getSearchCriteria().getKey() == 1 && conditionalSearch.getValue()!=null && !"".equals(conditionalSearch.getValue()))
                .collect(Collectors.toList());

        List<ConditionalSearch> notList = citions.getItems()
                .stream()
                .filter(conditionalSearch ->
                        conditionalSearch
                                .getSearchCriteria().getKey() == 2 && conditionalSearch.getValue()!=null && !"".equals(conditionalSearch.getValue()))
                .collect(Collectors.toList());

        //not取出来直接处理
        for (ConditionalSearch conditionalSearch : notList){
            if (conditionalSearch.getSearchOper().getKey()==0){
                boolQueryBuilder.mustNot(QueryBuilders.termQuery(conditionalSearch.getField(), conditionalSearch.getValue()));
            }else{
//                boolQueryBuilder.mustNot(QueryBuilders.wildcardQuery(conditionalSearch.getField(), "*"+conditionalSearch.getValue()+"*"));
                boolQueryBuilder.mustNot(QueryBuilders.matchQuery(conditionalSearch.getField(), conditionalSearch.getValue()).analyzer("ik_smart"));
            }
        }

        //or :field3 and:field2,4 not:field1
//        year=xxx and type=xxx and field1!=xxx and ((field2=xxx or field3=xxx) and (field4=xxx or field3=xxx))
        BoolQueryBuilder boolQueryBuilderInternal = QueryBuilders.boolQuery();

        //拼接 or/and 判断连接
        for (ConditionalSearch andCondit : andList){
            BoolQueryBuilder boolQueryBuilderTemp = QueryBuilders.boolQuery();
            if (andCondit.getSearchOper().getKey()==0){
                if (orList.size()==0){
                    boolQueryBuilderTemp.must(
                            QueryBuilders.termQuery(
                                    andCondit.getField(), andCondit.getValue()));
                }else{
                    boolQueryBuilderTemp.should(
                            QueryBuilders.termQuery(
                                    andCondit.getField(), andCondit.getValue()));
                }
            }else{
                if (orList.size()==0){
//                    boolQueryBuilderTemp.must(QueryBuilders.wildcardQuery(andCondit.getField(), "*"+andCondit.getValue()+"*"));
                    boolQueryBuilderTemp.must(
                            QueryBuilders.matchQuery(
                                    andCondit.getField(), andCondit.getValue()).analyzer("ik_smart"));
                }else{
//                    boolQueryBuilderTemp.should(QueryBuilders.wildcardQuery(andCondit.getField(), "*"+andCondit.getValue()+"*"));
                    boolQueryBuilderTemp.should(
                            QueryBuilders.matchQuery(
                                    andCondit.getField(), andCondit.getValue()).analyzer("ik_smart"));
                }
            }

            for (ConditionalSearch orCondit : orList){
                if (orCondit.getSearchOper().getKey()==0){
                    boolQueryBuilderTemp.should(
                            QueryBuilders.termQuery(
                                    orCondit.getField(), orCondit.getValue()));
                }else{
//                    boolQueryBuilderTemp.should(QueryBuilders.wildcardQuery(orCondit.getField(), "*"+orCondit.getValue()+"*"));
                    boolQueryBuilderTemp.should(
                            QueryBuilders.matchQuery(
                                    orCondit.getField(), orCondit.getValue()).analyzer("ik_smart"));
                }
            }
            boolQueryBuilderInternal.must(boolQueryBuilderTemp);
        }
        boolQueryBuilder.must(boolQueryBuilderInternal);


        //拼接分组信息 提供接口暂时只提供了count计数功能
        String[] group_field_arr = citions.getGroup_field_arr();
        for (String field : group_field_arr){
            searchSourceBuilder.aggregation(AggregationBuilders.
                    terms("tag_"+field).field(field).                   //桶分组
                    subAggregation(AggregationBuilders.count("count_"+field).field(field)).size(DEFAULT_GROUP_SIZE));
        }


        //高亮录入
//        searchSourceBuilder.highlighter(highlightBuilder);
        //封装最终数据源
        searchSourceBuilder.query(boolQueryBuilder).trackTotalHits(true);;

        out.println("打印查询条件");
        out.println("searchSourceBuilder:----------------------------------------");
        out.println(searchSourceBuilder.toString());

        if (citions.getItems() != null && citions.getItems().size() == 0){
            if ("chenyi".equals(_index)){
                searchSourceBuilder.sort("groupYear", SortOrder.ASC);
            }else if ("incident".equals(_index)){
                searchSourceBuilder.sort("searchStartDate", SortOrder.ASC);
            }
        }

        //发送请求获取源信息
        SearchRequest source = new SearchRequest(_index).source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(source, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        out.println("消耗时间="+(end-start));


        //打印信息
//        SearchDataPrint(search);
        //打印分组信息
//        SearchDataPrintByGroup(search,group_field_arr);
//        search = searchDataHandleByHighLight(search);
//        searchDataPrint(search);
        return search;
    }



    /**
     * 检索值打印信息
     * @param searchResponse
     */
    @Override
    public void searchDataPrint(SearchResponse searchResponse) {
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }
    }

    /**
     * 打印来自于分组的字段信息 通过grouptitle以及分桶字段信息
     * @param searchResponse
     * @param group_field_arr
     */
    @Override
    public void searchDataPrintByGroup(SearchResponse searchResponse, String[] group_field_arr) {
        for (String field : group_field_arr){
            ParsedTerms parsedTerms = searchResponse.getAggregations().get("tag_" + field);
            List<? extends Terms.Bucket> buckets = parsedTerms.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                //key的数据
                String key = bucket.getKey().toString();
                long docCount = bucket.getDocCount();
                ParsedValueCount count_field =  bucket.getAggregations().get("count_"+field);
                out.println("分组字段："+field);
                out.println(key + ":" + docCount +  "-  存在数量:" + count_field.getValue());
            }
        }
    }

    /**
     * 高亮数据格式处理器
     * @param search
     */
    @Override
    public SearchResponse searchDataHandleByHighLight(SearchResponse search){
        SearchHits hits = search.getHits();
        for(SearchHit hit : hits.getHits()){
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            //处理高亮片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for(Map.Entry<String,Object> highEntry:sourceMap.entrySet()){
                if(highEntry.getKey()!=null && highlightFields.get(highEntry.getKey())!=null){
                    HighlightField nameField = highlightFields.get(highEntry.getKey());
                    Text[] fragments = nameField.fragments();
                    StringBuilder nameTmp = new StringBuilder();
                    for(Text text:fragments){
                        nameTmp.append(text);
                    }
                    //将高亮片段组装到结果中去
                    sourceMap.put(highEntry.getKey(), nameTmp.toString());
                }
            }
        }
        return search;
    }

    /**
     * form字段处理器 后期汇总通用处理器方法
     * @param from
     * @return
     */
    public int disposeByFrom(int from){
        return from = from <= -1 ? 0 : from;
    }


    /**
     * size字段处理器 后期汇总通用处理器方法
     * @param size
     * @return
     */
    public int disposeBySize(int size){
        size = size >= 1000 ? 1000 : size;
        size = size <= 0 ? 10 : size;
        return size;
    }



    /**
     * 前台数据处理方法-挪移后端处理 过滤时空地图经纬度信息 返回数组
     * @param searchResponse String[]
     * @return
     */
    @Override
    public SearchResponse searchPointHandle(SearchResponse searchResponse) {
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> mValue = searchHit.getSourceAsMap();
            for (Map.Entry<String,Object> entry : mValue.entrySet())
                if (entry.getKey().equals("coordinate")){
                    if (entry.getValue() != null && entry.getValue().toString().length()>0){
                        mValue.put(entry.getKey(),entry.getValue().toString().split(","));
                    }
                }
        }
        return searchResponse;
    }

    //无权限判断的方法 后期可删除
    public ResponseData search2(HttpServletRequest request, SearchCitions citions, String _index) {
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder((citions.getPages().getCurrentPage() - 1) * citions.getPages().getPageSize(), citions.getPages().getPageSize());

        return new SuccessResponseData(
                searchDataProcessor(searchSourceBuilder, citions, new String[]{}, _index)
        );
    }

    //时空地图查询接口
    public ResponseData searchMap(QueryCriteria citions, String _index) {
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(citions.getPages().getCurrentPage(), citions.getPages().getPageSize());

        return new SuccessResponseData(
                searchDataMap(searchSourceBuilder,new String[]{},citions, _index)
        );
    }


    //时空地图查询方法
    public SearchResponse searchDataMap(SearchSourceBuilder searchSourceBuilder,String[] includes, QueryCriteria citions, String _index) {



        //创建选择器 改下起名规则
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


        //记得后续改名 不要这么拼接 1~6
        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilder6 = QueryBuilders.boolQuery();

        //函数处理 待封装
        if(citions.getId()!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("_id",citions.getId()));
        }
        if(citions.getPlace()!=null){
            BoolQueryBuilder boolQueryBuilderSplicing = QueryBuilders.boolQuery();
            boolQueryBuilderSplicing.should(QueryBuilders.matchQuery("place","*"+citions.getPlace()+"*"));
            boolQueryBuilderSplicing.should(QueryBuilders.matchQuery("category","*"+citions.getPlace()+"*"));
            boolQueryBuilder1.must(boolQueryBuilderSplicing);
        }
        if(citions.getStartYear()!=null){//结束年份未做校验 看单拼接双拼接 年份支持 yyyy|dd-MM-yyyy
            boolQueryBuilder2.must(QueryBuilders.rangeQuery("startDate").
                    gte(citions.getStartYear()).lte(citions.getEndYear()).format("dd-MM-yyyy||yyyy"));
        }
        if(citions.getType()!=null && citions.getType().size()>0){
            BoolQueryBuilder boolQueryBuilderTypeSplicing = QueryBuilders.boolQuery();
            if (citions.getType().size() > 0) {
                for (String cition : citions.getType())
                boolQueryBuilderTypeSplicing.should(QueryBuilders.matchQuery("type", cition));
            }
            boolQueryBuilder6.must(boolQueryBuilderTypeSplicing);
        }


        //添加检索器
        boolQueryBuilder.
                must(boolQueryBuilder2).
                must(boolQueryBuilder1).
                must(boolQueryBuilder6);

        //查询
        searchSourceBuilder.fetchSource(includes ,null);
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest source = new SearchRequest(_index).source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(source, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("打印查询条件");
        out.println("searchSourceBuilder:----------------------------------------");
        out.println(searchSourceBuilder.toString());
        return search;

    }

}
