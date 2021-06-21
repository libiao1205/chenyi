

package com.cn.sundeinfo.main.modular.elasticsearch.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.dataGather.enums.GatherExceptionEnum;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.ConditionalSearch;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.PageBean;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.SearchCitions;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchCriteria;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchOper;
import com.cn.sundeinfo.main.modular.elasticsearch.service.ResourcePermissionHandlingService;
import com.cn.sundeinfo.main.modular.elasticsearch.service.impl.EleasticSearchProcessorImpl;
import com.cn.sundeinfo.sys.core.fedora.Fedora;
import com.cn.sundeinfo.sys.modular.file.param.SysFileInfoParam;
import com.cn.sundeinfo.sys.modular.file.result.SysFileInfoResult;
import com.cn.sundeinfo.sys.modular.file.util.DownloadUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


/**
 *  RestHighLevelClient对象使用Demo
 *
 *  *  * @author menshikai
 *  *  * @create 2021/5/28
 *  *  * @since 1.0.0
 */
@RestController
@RequestMapping("/elasticsearch3")
public class RestHighLevelClientDemoController {


    @Qualifier("restClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ResourcePermissionHandlingService resourcePermissionHandlingService;

    @Autowired
    private EleasticSearchProcessorImpl eleasticSearchProcessor;

    @Autowired
    Fedora fedora;


    /**
     * RestHighLevelClient 基本用法
     */
    public void BasicProgram() throws Exception{
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest rq = new SearchRequest();
        //索引
        rq.indices("index");
        //各种组合条件
        rq.source(sourceBuilder);
        //请求
        out.println(rq.source().toString());
        SearchResponse rp = restHighLevelClient.search(rq, RequestOptions.DEFAULT);
    }


    /**
     * RestHighLevelClient 基础过滤方法
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fetchSource")
    public ResponseData fetchSource(HttpServletRequest request) throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //其实位置
        sourceBuilder.from(0);
        //每页数量
        sourceBuilder.size(10);


        //检索条件添加
        QueryBuilder boolQueryBuilder= QueryBuilders.matchAllQuery();
        //添加过滤器
        String[] includes = (String[]) resourcePermissionHandlingService.getDataFilteringArray(request).getData();
        String [] includes2 = new String[]{"name","age","interests"};//demo测试
        sourceBuilder.fetchSource(includes2 ,null);
        sourceBuilder.query(boolQueryBuilder);

        SearchRequest source = new SearchRequest().indices("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }
        return new SuccessResponseData();
    }

    /**
     * RestHighLevelClient 基础模糊检索方法
     * @param request
     * @return
     * @throws Exception
     *must 相当于 与 & = ；must not 相当于 非 ~ ！=；should 相当于 或 | or ；filter 过滤
     *
     * QueryBuilders.termQuery("字段.keyword", obj) 完全匹配
     * QueryBuilders.termsQuery("key.keyword", obj1, obj2..)   一次匹配多个值
     * QueryBuilders.matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * QueryBuilders.multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     * QueryBuilders.matchAllQuery();         匹配所有文件
     *
     */
    @RequestMapping("/findLikeByPersons")
    public ResponseData findLikeByPersons(HttpServletRequest request) throws Exception {

        //核心查询器初始化
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.should(QueryBuilders.termQuery("name.keyword", "是啊俊"));
//        boolQueryBuilder.should(QueryBuilders.termQuery("name.keyword", "JetWu"));//精确查询

        //检索条件添加
//        boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*俊"));
        sourceBuilder.query(boolQueryBuilder);

        //发送请求获取源信息
        SearchRequest source = new SearchRequest("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //解析返回
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //返回值状态和查询信息打印
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //测试完成
        return new SuccessResponseData();
    }

    /**
     * 排序-demo测试
     * @return
     */
    @RequestMapping("/searchSort")
    public ResponseData searchSort() throws Exception {

        //核心查询器初始化
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        //默认查询所有
        QueryBuilder boolQueryBuilder= QueryBuilders.matchAllQuery();
        //排序 根据某字段进行排休可添加多个
        Boolean bool = true;
        sourceBuilder.sort(new FieldSortBuilder("age").order(bool ? SortOrder.ASC : SortOrder.DESC));
        sourceBuilder.query(boolQueryBuilder);

        //发送请求获取源信息
        SearchRequest source = new SearchRequest().indices("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //解析返回
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //返回值状态和查询信息打印
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //测试完成
        return new SuccessResponseData();
    }


    /**
     * 分桶-分组demo测试
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchGroup")
    public ResponseData searchGroup() throws Exception {
        //核心查询器初始化
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));


        //根据age字段进行分组 统计总和和平均值
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.
                terms("tag_tr").field("age").                   //桶分组
                subAggregation(AggregationBuilders.sum("sum_age").field("age")).  //求和
                subAggregation(AggregationBuilders.avg("avg_age").field("age")); //求平均值

        TermsAggregationBuilder aggregationBuilder2 = AggregationBuilders.
                terms("tag_tr2").field("age").
                subAggregation(AggregationBuilders.count("count_age").field("age")). //计数
                subAggregation(AggregationBuilders.min("min_age").field("age")). //最小值
                subAggregation(AggregationBuilders.max("max_age").field("age")); //最大值
        sourceBuilder.aggregation(aggregationBuilder);
        sourceBuilder.aggregation(aggregationBuilder2);

        out.println(sourceBuilder.toString());

        //发送请求获取源信息
        SearchRequest source = new SearchRequest().indices("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //解析返回
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //结果集查询
        ParsedLongTerms parsedLongTerms = searchResponse.getAggregations().get("tag_tr");
        List<? extends Terms.Bucket> buckets = parsedLongTerms.getBuckets();
        buckets.forEach(bucket -> {
            ParsedSum sum_id = bucket.getAggregations().get("sum_age");
            ParsedAvg avg_id = bucket.getAggregations().get("avg_age");
            out.println(bucket.getKey()+
                    ":sum_age-"+sum_id.getValue()+
                    " avg_age-"+avg_id.getValue());
        });
        out.println("-------------------------------------");
        ParsedLongTerms parsedLongTerms2 = searchResponse.getAggregations().get("tag_tr2");
        List<? extends Terms.Bucket> buckets2 = parsedLongTerms2.getBuckets();
        for (Terms.Bucket bucket : buckets2) {
            //key的数据
            String key = bucket.getKey().toString();
            long docCount = bucket.getDocCount();
            //获取数据
            Aggregations bucketAggregations = bucket.getAggregations();
            ParsedValueCount count_age = bucketAggregations.get("count_age");
            ParsedMin min_age = bucketAggregations.get("min_age");
            ParsedMax max_age = bucketAggregations.get("max_age");
            out.println(key + ":" + docCount +  "-" + count_age.getValue() + "-" + min_age.getValue() + "-" + max_age.getValue());
        }

        //返回值状态和查询信息打印
//        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
//            sourceAsMap.entrySet().forEach(
//                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
//            );
//        }
        //测试完成
        return new SuccessResponseData();
    }


    /**
     * 高亮处理方法 Demo 和分词器结合使用暂未研究
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchHighlight")
    public ResponseData searchHighlight() throws Exception {

        //核心查询器初始化
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));


        //默认查询所有
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.should(QueryBuilders.matchPhraseQuery("year", "1949").analyzer("ik_smart"));
        boolBuilder.should(QueryBuilders.matchPhraseQuery("category", "体报").analyzer("ik_smart"));

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("category")
                .field("year")
                .highlighterType("unified")
                .preTags("<span style=\"color:red\">")
                .postTags("</span>");

        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.query(boolBuilder);

        //发送请求获取源信息
        SearchRequest source = new SearchRequest().indices("chenyi").source(sourceBuilder);
        SearchResponse searchResponse = null;
        searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //解析返回
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        SearchHits hits = searchResponse.getHits();
        //高亮处理
        for(SearchHit hit : hits.getHits()){
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            //处理高亮片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for(Map.Entry<String,Object> highEntry:sourceMap.entrySet()){
                out.println(highEntry.getKey());
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

        //返回值状态和查询信息打印
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //测试完成
        return new SuccessResponseData();
    }




    /**
     * RestHighLevelClient 应用级demo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchIndex")
    public ResponseData searchIndex(HttpServletRequest request) throws Exception {

        Map<String, Object> where = new HashMap<>();//查询参数
        where.put("persons","*庆*");
        Map<String, Boolean> sortFieldsToAsc = new HashMap<>();//排序集合
        String index="";//索引
        int from=0,size=15,timeOut=10000;

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

       /*
            from = from <= -1 ? 0 : from;
            size = size >= 1000 ? 1000 : size;
            size = size <= 0 ? 15 : size;
        */
        //起始位置
        sourceBuilder.from(from);
        //每页数量
        sourceBuilder.size(size);


        if (where != null && !where.isEmpty()) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            where.forEach((k, v) -> {
                if (v instanceof Map) {
                    //范围选择map  暂定时间
                    Map<String, Date> mapRrange = (Map<String, Date>) v;
                    if (mapRrange != null) {
                        boolQueryBuilder.must(
                                QueryBuilders.rangeQuery(k).
                                        gte(mapRrange.get("start")).
                                        lt(mapRrange.get("end")));
                    }
                } else {
                    boolQueryBuilder.must(QueryBuilders.wildcardQuery(k, v.toString()));
                }
            });
            sourceBuilder.query(boolQueryBuilder);
        }

        //超时
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        //排序
        if (sortFieldsToAsc  != null && !sortFieldsToAsc .isEmpty()) {
            sortFieldsToAsc.forEach((k, v) -> {
                sourceBuilder.sort(new FieldSortBuilder(k).order(v ? SortOrder.ASC : SortOrder.DESC));
            });
        } else {
            sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        }

        //添加过滤器
        String [] includes2 = new String[]{"groupPersonsText","title","personsText","persons","version"};//demo测试
        String [] excludeFields = new String[]{};
        sourceBuilder.fetchSource(includes2 ,null);
        //返回和排除列
        if (!CollectionUtils.isEmpty(includes2) || !CollectionUtils.isEmpty(excludeFields)) {
            sourceBuilder.fetchSource(includes2, excludeFields);
        }


        //索引
//        SearchRequest source = new SearchRequest().indices("xxx").source(sourceBuilder);
        SearchRequest source = new SearchRequest().source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //解析返回
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //返回值状态和查询信息打印
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //测试完成
        return new SuccessResponseData();
    }


    /*
           模拟数据检索 es使用Demo
     */
    @RequestMapping("/testDemo")
    public void testDemo(HttpServletRequest request){

        List<Map<String,Object>> listDemo = new ArrayList<Map<String,Object>>();
        Map<String,Object> mapDemo = new HashMap<String,Object>();
        mapDemo.put("title","陈毅发起成立西山文社（北京市）");
        mapDemo.put("coordinate","116.403613,39.916016");
        listDemo.add(mapDemo);
        Map<String,Object> mapDemo2 = new HashMap<String,Object>();
        mapDemo2.put("title","陈毅发起成立西山文社（北京市）2");
        listDemo.add(mapDemo2);

        for (Map<String,Object> mValue : listDemo) {
            Set<Map.Entry<String, Object>> entries = mValue.entrySet();
            for (Map.Entry<String,Object> entry : mValue.entrySet())
                if (entry.getKey().equals("coordinate")){
                    if (entry.getValue() != null && entry.getValue().toString().length()>0){
                        mValue.put(entry.getKey(),entry.getValue().toString().split(","));
                        out.println(entry.getValue().toString().split(","));
                    }
                }
        }

        String a = JSONArray.toJSON(listDemo).toString();
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        Map<String, Integer> yearMap = new HashMap<String, Integer>();
//        String[] arr = new String[]{"媒体报道","研究文献"};
//
//        yearMap.put("startDate",1800);
//        yearMap.put("endDate",2020);
//        map.put("category",arr);
////        Integer[] type_arr1= new Integer[2];
////        map.put("type_arr1",type_arr1);
//        citions.setConditionsMap(map);
//
//        ///author  keyword  summary
//       ConditionalSearch conditionalSearch = new ConditionalSearch();
//        conditionalSearch.setField("summaryText");
//        conditionalSearch.setSearchCriteria(SearchCriteria.MUST);
//        conditionalSearch.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch.setValue("孙中山");
//        citions.getItems().add(conditionalSearch);
//
//        ConditionalSearch conditionalSearch1 = new ConditionalSearch();
//        conditionalSearch1.setField("summaryText");
//        conditionalSearch1.setSearchCriteria(SearchCriteria.MUST);
//        conditionalSearch1.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch1.setValue("蒋介石");
//        citions.getItems().add(conditionalSearch1);
//
//        ConditionalSearch conditionalSearch2 = new ConditionalSearch();
//        conditionalSearch2.setField("summaryText");
//        conditionalSearch2.setSearchCriteria(SearchCriteria.SHOULD);
//        conditionalSearch2.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch2.setValue("宋");
//        citions.getItems().add(conditionalSearch2);
//
// /*       ConditionalSearch conditionalSearch3 = new ConditionalSearch();
//        conditionalSearch3.setField("summary");
//        conditionalSearch3.setSearchCriteria(SearchCriteria.MUSTNOT);
//        conditionalSearch3.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch3.setValue("宋");
//        citions.getItems().add(conditionalSearch3);*/
//
//       /* String[] group_field_Arr = new String[]{"searchYears","author"};
//        citions.setGroup_field_arr(group_field_Arr);*/
//        ResponseData search = eleasticSearchProcessor.search(request, citions, "chenyi");
//
//         String a = JSONArray.toJSON(citions).toString();
//        System.out.println(a);
//        if (search.getSuccess()){
//            eleasticSearchProcessor.SearchDataPrint((SearchResponse) search.getData());//输出信息
//        }else {
//            //失败则将信息返回给前台
//        }
    }


    /**
     * 测试上传接口
     * @param file
     * @return
     */
    @PostMapping("/add")
    @BusinessLog(title = "模拟上传测试-fedora", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData add(@RequestPart("file") MultipartFile file) {
        String path = fedora.addFile(file);
        if(ObjectUtil.isEmpty(path)){
            throw new RuntimeException("文件保存失败");
        }
        //存数据库返回id给前台/直接返回对象页面进行详情页展示
        return new SuccessResponseData();
    }


    /**
     * 测试下载接口
     * @param url
     * @param response
     */
    @GetMapping("/download")
    @BusinessLog(title = "模拟下载测试-fedora", opType = LogAnnotionOpTypeEnum.OTHER)
    public void download(@RequestParam("url")String url, HttpServletResponse response) {
        JSONObject file = fedora.getFile(url);
        File file1 = (File) file.get("file");
        DownloadUtil.download(file1, response);
    }

}
