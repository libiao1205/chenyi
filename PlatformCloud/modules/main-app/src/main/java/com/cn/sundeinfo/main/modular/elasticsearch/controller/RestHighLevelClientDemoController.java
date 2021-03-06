

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
 *  RestHighLevelClient????????????Demo
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
     * RestHighLevelClient ????????????
     */
    public void BasicProgram() throws Exception{
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest rq = new SearchRequest();
        //??????
        rq.indices("index");
        //??????????????????
        rq.source(sourceBuilder);
        //??????
        out.println(rq.source().toString());
        SearchResponse rp = restHighLevelClient.search(rq, RequestOptions.DEFAULT);
    }


    /**
     * RestHighLevelClient ??????????????????
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fetchSource")
    public ResponseData fetchSource(HttpServletRequest request) throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //????????????
        sourceBuilder.from(0);
        //????????????
        sourceBuilder.size(10);


        //??????????????????
        QueryBuilder boolQueryBuilder= QueryBuilders.matchAllQuery();
        //???????????????
        String[] includes = (String[]) resourcePermissionHandlingService.getDataFilteringArray(request).getData();
        String [] includes2 = new String[]{"name","age","interests"};//demo??????
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
     * RestHighLevelClient ????????????????????????
     * @param request
     * @return
     * @throws Exception
     *must ????????? ??? & = ???must not ????????? ??? ~ ???=???should ????????? ??? | or ???filter ??????
     *
     * QueryBuilders.termQuery("??????.keyword", obj) ????????????
     * QueryBuilders.termsQuery("key.keyword", obj1, obj2..)   ?????????????????????
     * QueryBuilders.matchQuery("key", Obj) ????????????, field??????????????????, ?????????????????????
     * QueryBuilders.multiMatchQuery("text", "field1", "field2"..);  ??????????????????, field??????????????????
     * QueryBuilders.matchAllQuery();         ??????????????????
     *
     */
    @RequestMapping("/findLikeByPersons")
    public ResponseData findLikeByPersons(HttpServletRequest request) throws Exception {

        //????????????????????????
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.should(QueryBuilders.termQuery("name.keyword", "?????????"));
//        boolQueryBuilder.should(QueryBuilders.termQuery("name.keyword", "JetWu"));//????????????

        //??????????????????
//        boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", "*???"));
        sourceBuilder.query(boolQueryBuilder);

        //???????????????????????????
        SearchRequest source = new SearchRequest("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //????????????
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //????????????????????????????????????
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //????????????
        return new SuccessResponseData();
    }

    /**
     * ??????-demo??????
     * @return
     */
    @RequestMapping("/searchSort")
    public ResponseData searchSort() throws Exception {

        //????????????????????????
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        //??????????????????
        QueryBuilder boolQueryBuilder= QueryBuilders.matchAllQuery();
        //?????? ??????????????????????????????????????????
        Boolean bool = true;
        sourceBuilder.sort(new FieldSortBuilder("age").order(bool ? SortOrder.ASC : SortOrder.DESC));
        sourceBuilder.query(boolQueryBuilder);

        //???????????????????????????
        SearchRequest source = new SearchRequest().indices("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //????????????
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //????????????????????????????????????
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //????????????
        return new SuccessResponseData();
    }


    /**
     * ??????-??????demo??????
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchGroup")
    public ResponseData searchGroup() throws Exception {
        //????????????????????????
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));


        //??????age?????????????????? ????????????????????????
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.
                terms("tag_tr").field("age").                   //?????????
                subAggregation(AggregationBuilders.sum("sum_age").field("age")).  //??????
                subAggregation(AggregationBuilders.avg("avg_age").field("age")); //????????????

        TermsAggregationBuilder aggregationBuilder2 = AggregationBuilders.
                terms("tag_tr2").field("age").
                subAggregation(AggregationBuilders.count("count_age").field("age")). //??????
                subAggregation(AggregationBuilders.min("min_age").field("age")). //?????????
                subAggregation(AggregationBuilders.max("max_age").field("age")); //?????????
        sourceBuilder.aggregation(aggregationBuilder);
        sourceBuilder.aggregation(aggregationBuilder2);

        out.println(sourceBuilder.toString());

        //???????????????????????????
        SearchRequest source = new SearchRequest().indices("school").source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //????????????
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //???????????????
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
            //key?????????
            String key = bucket.getKey().toString();
            long docCount = bucket.getDocCount();
            //????????????
            Aggregations bucketAggregations = bucket.getAggregations();
            ParsedValueCount count_age = bucketAggregations.get("count_age");
            ParsedMin min_age = bucketAggregations.get("min_age");
            ParsedMax max_age = bucketAggregations.get("max_age");
            out.println(key + ":" + docCount +  "-" + count_age.getValue() + "-" + min_age.getValue() + "-" + max_age.getValue());
        }

        //????????????????????????????????????
//        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
//            sourceAsMap.entrySet().forEach(
//                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
//            );
//        }
        //????????????
        return new SuccessResponseData();
    }


    /**
     * ?????????????????? Demo ????????????????????????????????????
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchHighlight")
    public ResponseData searchHighlight() throws Exception {

        //????????????????????????
        int from=0,size=15,timeOut=10000;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));


        //??????????????????
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.should(QueryBuilders.matchPhraseQuery("year", "1949").analyzer("ik_smart"));
        boolBuilder.should(QueryBuilders.matchPhraseQuery("category", "??????").analyzer("ik_smart"));

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("category")
                .field("year")
                .highlighterType("unified")
                .preTags("<span style=\"color:red\">")
                .postTags("</span>");

        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.query(boolBuilder);

        //???????????????????????????
        SearchRequest source = new SearchRequest().indices("chenyi").source(sourceBuilder);
        SearchResponse searchResponse = null;
        searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //????????????
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        SearchHits hits = searchResponse.getHits();
        //????????????
        for(SearchHit hit : hits.getHits()){
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            //??????????????????
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
                    //????????????????????????????????????
                    sourceMap.put(highEntry.getKey(), nameTmp.toString());
                }
            }
        }

        //????????????????????????????????????
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //????????????
        return new SuccessResponseData();
    }




    /**
     * RestHighLevelClient ?????????demo
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchIndex")
    public ResponseData searchIndex(HttpServletRequest request) throws Exception {

        Map<String, Object> where = new HashMap<>();//????????????
        where.put("persons","*???*");
        Map<String, Boolean> sortFieldsToAsc = new HashMap<>();//????????????
        String index="";//??????
        int from=0,size=15,timeOut=10000;

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

       /*
            from = from <= -1 ? 0 : from;
            size = size >= 1000 ? 1000 : size;
            size = size <= 0 ? 15 : size;
        */
        //????????????
        sourceBuilder.from(from);
        //????????????
        sourceBuilder.size(size);


        if (where != null && !where.isEmpty()) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            where.forEach((k, v) -> {
                if (v instanceof Map) {
                    //????????????map  ????????????
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

        //??????
        sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

        //??????
        if (sortFieldsToAsc  != null && !sortFieldsToAsc .isEmpty()) {
            sortFieldsToAsc.forEach((k, v) -> {
                sourceBuilder.sort(new FieldSortBuilder(k).order(v ? SortOrder.ASC : SortOrder.DESC));
            });
        } else {
            sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        }

        //???????????????
        String [] includes2 = new String[]{"groupPersonsText","title","personsText","persons","version"};//demo??????
        String [] excludeFields = new String[]{};
        sourceBuilder.fetchSource(includes2 ,null);
        //??????????????????
        if (!CollectionUtils.isEmpty(includes2) || !CollectionUtils.isEmpty(excludeFields)) {
            sourceBuilder.fetchSource(includes2, excludeFields);
        }


        //??????
//        SearchRequest source = new SearchRequest().indices("xxx").source(sourceBuilder);
        SearchRequest source = new SearchRequest().source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(source, RequestOptions.DEFAULT);

        //????????????
        if (searchResponse.status() != RestStatus.OK || searchResponse.getHits().getTotalHits().value <= 0) {
            return new SuccessResponseData(Collections.emptyList());
        }

        //????????????????????????????????????
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.entrySet().forEach(
                    entry-> out.println(entry.getKey() + ":" + entry.getValue())
            );
        }

        //????????????
        return new SuccessResponseData();
    }


    /*
           ?????????????????? es??????Demo
     */
    @RequestMapping("/testDemo")
    public void testDemo(HttpServletRequest request){

        List<Map<String,Object>> listDemo = new ArrayList<Map<String,Object>>();
        Map<String,Object> mapDemo = new HashMap<String,Object>();
        mapDemo.put("title","?????????????????????????????????????????????");
        mapDemo.put("coordinate","116.403613,39.916016");
        listDemo.add(mapDemo);
        Map<String,Object> mapDemo2 = new HashMap<String,Object>();
        mapDemo2.put("title","?????????????????????????????????????????????2");
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
//        String[] arr = new String[]{"????????????","????????????"};
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
//        conditionalSearch.setValue("?????????");
//        citions.getItems().add(conditionalSearch);
//
//        ConditionalSearch conditionalSearch1 = new ConditionalSearch();
//        conditionalSearch1.setField("summaryText");
//        conditionalSearch1.setSearchCriteria(SearchCriteria.MUST);
//        conditionalSearch1.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch1.setValue("?????????");
//        citions.getItems().add(conditionalSearch1);
//
//        ConditionalSearch conditionalSearch2 = new ConditionalSearch();
//        conditionalSearch2.setField("summaryText");
//        conditionalSearch2.setSearchCriteria(SearchCriteria.SHOULD);
//        conditionalSearch2.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch2.setValue("???");
//        citions.getItems().add(conditionalSearch2);
//
// /*       ConditionalSearch conditionalSearch3 = new ConditionalSearch();
//        conditionalSearch3.setField("summary");
//        conditionalSearch3.setSearchCriteria(SearchCriteria.MUSTNOT);
//        conditionalSearch3.setSearchOper(SearchOper.SHOULD);
//        conditionalSearch3.setValue("???");
//        citions.getItems().add(conditionalSearch3);*/
//
//       /* String[] group_field_Arr = new String[]{"searchYears","author"};
//        citions.setGroup_field_arr(group_field_Arr);*/
//        ResponseData search = eleasticSearchProcessor.search(request, citions, "chenyi");
//
//         String a = JSONArray.toJSON(citions).toString();
//        System.out.println(a);
//        if (search.getSuccess()){
//            eleasticSearchProcessor.SearchDataPrint((SearchResponse) search.getData());//????????????
//        }else {
//            //?????????????????????????????????
//        }
    }


    /**
     * ??????????????????
     * @param file
     * @return
     */
    @PostMapping("/add")
    @BusinessLog(title = "??????????????????-fedora", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData add(@RequestPart("file") MultipartFile file) {
        String path = fedora.addFile(file);
        if(ObjectUtil.isEmpty(path)){
            throw new RuntimeException("??????????????????");
        }
        //??????????????????id?????????/?????????????????????????????????????????????
        return new SuccessResponseData();
    }


    /**
     * ??????????????????
     * @param url
     * @param response
     */
    @GetMapping("/download")
    @BusinessLog(title = "??????????????????-fedora", opType = LogAnnotionOpTypeEnum.OTHER)
    public void download(@RequestParam("url")String url, HttpServletResponse response) {
        JSONObject file = fedora.getFile(url);
        File file1 = (File) file.get("file");
        DownloadUtil.download(file1, response);
    }

}
