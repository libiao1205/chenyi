package com.cn.sundeinfo.main.modular.elasticsearch.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.elasticsearch.ElasticExecutor;
import com.cn.sundeinfo.core.elasticsearch.model.*;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.dataSource.importESData.SDStringUtils;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.*;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchCriteria;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchOper;
import com.cn.sundeinfo.main.modular.elasticsearch.mapper.GiveTheTumbsUpMapper;
import com.cn.sundeinfo.main.modular.elasticsearch.service.DataProcessing;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GetClassAttribute;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GiveTheTubsUpLogService;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GiveTheTubsUpService;
import com.cn.sundeinfo.main.modular.elasticsearch.service.impl.EleasticSearchProcessorImpl;
import org.apache.lucene.search.TotalHits;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;


@RestController
@RequestMapping("/elasticsearch")
public class ElasticsearchController {

    @Autowired
    ElasticExecutor elasticExecutor;

    @Qualifier("restClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private EleasticSearchProcessorImpl eleasticSearchProcessor;

    //?????????????????????
    @Autowired
    private DataProcessing dataProcessing;

    @Autowired
    private GiveTheTubsUpService giveTheTubsUpService;

    @Autowired
    private GiveTheTubsUpLogService giveTheTubsUpLogService;

    /**
     * ??????????????????
     *
     * @throws Exception
     */
    @RequestMapping("/query")
    public ResponseData query(@RequestBody SearchCitions citions, HttpServletRequest httpRequest) throws Exception {
        //???????????????ALL ?????????????????????
        List itemList = new ArrayList();
        String kw = "";
        for (ConditionalSearch conditionalSearch : citions.getItems()) {
            kw += SDStringUtils.convertZHCNForCharacter(conditionalSearch.getValue());
            kw += SDStringUtils.convertTWCNForCharacter(conditionalSearch.getValue());
            if (conditionalSearch.getField() != null && conditionalSearch.getField().equals("all")) {
                Map<Integer, String> testMap = new HashMap<>();
                testMap.put(1, "titleText");
                testMap.put(2, "authorText");
                testMap.put(3, "abstractText");
                testMap.put(4, "keywordsText");
                testMap.put(5, "sourceText");

                for (Map.Entry<Integer, String> entry : testMap.entrySet()) {
                    //?????????????????????????????????set???????????????????????????
                    ConditionalSearch conditionalSearch1 = new ConditionalSearch();
                    conditionalSearch1.setField(entry.getValue());
                    conditionalSearch1.setValue(conditionalSearch.getValue());
                    conditionalSearch1.setSearchOper(conditionalSearch.getSearchOper() != null ?
                            conditionalSearch.getSearchOper() :
                            SearchOper.SHOULD);
                    if(entry.getKey() == 1){
                        conditionalSearch1.setSearchCriteria(conditionalSearch.getSearchCriteria() != null ?
                                conditionalSearch.getSearchCriteria() :
                                SearchCriteria.MUST);
                    }else{
                        conditionalSearch1.setSearchCriteria(conditionalSearch.getSearchCriteria() != null ?
                                conditionalSearch.getSearchCriteria() :
                                SearchCriteria.SHOULD);
                    }
                    itemList.add(conditionalSearch1);
                }
            } else {
                conditionalSearch.setSearchCriteria(conditionalSearch.getSearchCriteria()==null?
                        SearchCriteria.MUST:
                        conditionalSearch.getSearchCriteria());
                conditionalSearch.setSearchOper(conditionalSearch.getSearchOper()==null?
                        SearchOper.SHOULD:
                        conditionalSearch.getSearchOper());
                itemList.add(conditionalSearch);
            }
        }
        citions.setItems(itemList);
        ResponseData search = eleasticSearchProcessor.search2(httpRequest, citions, "chenyi");
        SearchResponse searchResponse = (SearchResponse) search.getData();

        //???????????????map
        Map<String, Object> sourceAsMap = new HashMap<>();
        List<Map<String, Object>> sourceAsMap2 = new ArrayList<>();

        //for????????????
        for (SearchHit searchHit : searchResponse.getHits()) {
            sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.put("id", searchHit.getId());
            sourceAsMap2.add(sourceAsMap);
        }
        //????????????????????????
        PageBean value = dataProcessing.PageValue(citions, search, sourceAsMap2);
        value.setSearchWord(kw);
        SuccessResponseData successResponseData = new SuccessResponseData(value);
        return successResponseData;
    }

    /**
     * ??????????????????
     *
     * @return
     * @throws Exception
     */

    //List<ConditionalSearch> conditionalSearchList
    @RequestMapping("/advQuery")
    public ResponseData advQuery(@RequestBody SearchCitions citions, HttpServletRequest httpRequest) throws Exception {

        //???????????????ALL ?????????????????????
        List<ConditionalSearch> itemList = new ArrayList();
        String kw = "";
        for (ConditionalSearch conditionalSearch : citions.getItems()) {
            kw += SDStringUtils.convertZHCNForCharacter(conditionalSearch.getValue());
            kw += SDStringUtils.convertTWCNForCharacter(conditionalSearch.getValue());
            if (conditionalSearch.getField() != null && conditionalSearch.getField().equals("all")) {
                Map<Integer, String> testMap = new HashMap<>();
                testMap.put(1, "titleText");
                testMap.put(2, "authorText");
                testMap.put(3, "abstractText");
                testMap.put(4, "keywordsText");
                testMap.put(5, "sourceText");

                for (Map.Entry<Integer, String> entry : testMap.entrySet()) {
                    //?????????????????????????????????set???????????????????????????
                    ConditionalSearch conditionalSearch1 = new ConditionalSearch();
                    conditionalSearch1.setField(entry.getValue());
                    conditionalSearch1.setValue(conditionalSearch.getValue());
                    conditionalSearch1.setSearchOper(conditionalSearch.getSearchOper() != null ?
                            conditionalSearch.getSearchOper() :
                            SearchOper.SHOULD);
                    if(entry.getKey() == 1){
                        conditionalSearch1.setSearchCriteria(conditionalSearch.getSearchCriteria() != null ?
                                conditionalSearch.getSearchCriteria() :
                                SearchCriteria.MUST);
                    }else{
                        conditionalSearch1.setSearchCriteria(conditionalSearch.getSearchCriteria() != null ?
                                conditionalSearch.getSearchCriteria() :
                                SearchCriteria.SHOULD);
                    }
                    itemList.add(conditionalSearch1);
                }
            } else {
                conditionalSearch.setSearchCriteria(conditionalSearch.getSearchCriteria()==null?
                        SearchCriteria.MUST:
                        conditionalSearch.getSearchCriteria());
                itemList.add(conditionalSearch);
            }
        }
        citions.setItems(itemList);


        ResponseData search = eleasticSearchProcessor.search2(httpRequest, citions, "chenyi");
        //??????????????????????????????
        //  if (search.getSuccess()==true){
        SearchResponse searchResponse = (SearchResponse) search.getData();
        //???????????????map
        Map<String, Object> sourceAsMap = new HashMap<>();
        List<Map<String, Object>> sourceAsMap2 = new ArrayList<>();
        //for????????????
        for (SearchHit searchHit : searchResponse.getHits()) {
            sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.put("id", searchHit.getId());
            sourceAsMap2.add(sourceAsMap);
        }
        PageBean value = dataProcessing.PageValue(citions, search, sourceAsMap2);
        value.setSearchWord(kw);
        SuccessResponseData successResponseData = new SuccessResponseData(value);
        return successResponseData;
    }

    /**
     * ??????????????????
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail")
    public ResponseData detail(@RequestBody HashMap<String, String> map, HttpServletRequest httpRequest) throws Exception {
        SearchCitions citions = new SearchCitions();

        String Id = map.get("id");
        ConditionalSearch conditionalSearch = new ConditionalSearch();
        conditionalSearch.setField("_id");

        conditionalSearch.setValue(Id);
        conditionalSearch.setSearchCriteria(SearchCriteria.MUST);
        conditionalSearch.setSearchOper(SearchOper.SHOULD);
        List<ConditionalSearch> list = new ArrayList<>();
        list.add(conditionalSearch);
        citions.setItems(list);
        ResponseData search = eleasticSearchProcessor.search2(httpRequest, citions, "chenyi");
        SearchResponse searchResponse = (SearchResponse) search.getData();
        //???????????????map
       // List<Map<String, Object>> sourceAsMap2 = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        //????????????
        GiveTheTumbsUp giveTumbs = giveTheTubsUpService.getOne(Id);
        //for????????????
        for (SearchHit searchHit : searchResponse.getHits()) {
            map1 = searchHit.getSourceAsMap();
            if(giveTumbs != null){
                map1.put("tumbsupcount",giveTumbs.getCount());
            }else {
                map1.put("tumbsupcount",0);
            }
          //  sourceAsMap2.add(map1);
        }

        SuccessResponseData successResponseData = new SuccessResponseData(map1);
        return successResponseData;
    }


    /**
     * ????????????????????????????????????
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/SpaceTreeMap")
    public ResponseData SpaceTreeMap(HttpServletRequest httpRequest) throws Exception {

        SearchCitions citions = new SearchCitions();
        //?????????
        GetClassAttribute getClassAttribute = new GetClassAttribute();

        //???????????????????????????
        String[] searchConditions = getClassAttribute.GetClassAttribute(new Incident());

        //??????????????????????????????
        ResponseData DataNum = eleasticSearchProcessor.search2(httpRequest, citions, "incident");
        SearchResponse searchResponse = (SearchResponse) DataNum.getData();

        //??????????????????
        int pageNum = (int) searchResponse.getHits().getTotalHits().value;

        //?????????????????????????????????size???
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(pageNum);

        // TODO ?????????????????????
        SearchResponse search = eleasticSearchProcessor.searchDataProcessor(sourceBuilder, citions, searchConditions, "incident");

        List<Map<String,Object>> sourceAsMap = new ArrayList<>();
        //???????????????map
        Map<String, Object> sourceAsMap2 = new HashMap<>();
        for (SearchHit searchHit : search.getHits()) {
            sourceAsMap2 = searchHit.getSourceAsMap();
            sourceAsMap2.put("id", searchHit.getId());
            sourceAsMap.add(sourceAsMap2);
        }

        //????????????
        DataProcessing dataProcessing = new DataProcessing();
        sourceAsMap = dataProcessing.MapProcessing(sourceAsMap);

        SuccessResponseData successResponseData2 = new SuccessResponseData(sourceAsMap);
        return successResponseData2;


    }


    /**
     * ??????????????????????????????
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/SpacePointMap")
    public ResponseData SpacePointMap(HttpServletRequest httpRequest) throws Exception {

        SearchCitions citions = new SearchCitions();
        //?????????
        GetClassAttribute getClassAttribute = new GetClassAttribute();

        //???????????????????????????
        String[] searchConditions = getClassAttribute.GetClassAttribute(new MapSearchConditions());

        //??????????????????????????????
        ResponseData DataNum = eleasticSearchProcessor.search2(httpRequest, citions, "incident");
        SearchResponse searchResponse = (SearchResponse) DataNum.getData();

        //??????????????????
        int pageNum = (int) searchResponse.getHits().getTotalHits().value;

        //?????????????????????????????????size???
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(pageNum);

        // TODO ?????????????????????
        SearchResponse search = eleasticSearchProcessor.searchDataProcessor(sourceBuilder, citions, searchConditions, "incident");


        List<Map<String, Object>> sourceAsMap = new ArrayList<>();

        for (SearchHit searchHit : search.getHits()) {
            Map<String, Object> sourceAsMap1 = searchHit.getSourceAsMap();
            sourceAsMap1.put("id", searchHit.getId());
            sourceAsMap.add(sourceAsMap1);
        }
        sourceAsMap = dataProcessing.SpacePointMap(sourceAsMap);
        SuccessResponseData successResponseData2 = new SuccessResponseData(sourceAsMap);
        return successResponseData2;

    }




    /**
     * ????????????????????????
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/SpaceQueryMap")
    public ResponseData SpaceQueryMap(@RequestBody QueryCriteria citions, HttpServletRequest httpRequest) throws Exception {


        //?????????
        GetClassAttribute getClassAttribute = new GetClassAttribute();

        citions.setPages(new PageBean(0,10));

        //???????????????????????????
        String[] searchConditions = getClassAttribute.GetClassAttribute(new MapSearchConditions());

        //??????????????????????????????
        ResponseData DataNum = eleasticSearchProcessor.searchMap(citions,"incident");

        SearchResponse searchResponse = (SearchResponse) DataNum.getData();

        //??????????????????
        int pageNum = (int) searchResponse.getHits().getTotalHits().value;

        //?????????????????????????????????size???
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(pageNum);


        // TODO ?????????????????????
        SearchResponse search = eleasticSearchProcessor.searchDataMap(sourceBuilder,searchConditions, citions,"incident");


        List<Map<String, Object>> sourceAsMap = new ArrayList<>();
        List<Map<String, Object>> sourceAsMap2 = new ArrayList<>();
        List<Map<String, Object>> sourceAsMap3 = new ArrayList<>();
        for (SearchHit searchHit : search.getHits()) {
            Map<String, Object> sourceAsMap1 = searchHit.getSourceAsMap();
            sourceAsMap1.put("id", searchHit.getId());
            sourceAsMap.add(sourceAsMap1);
        }
        sourceAsMap3 = dataProcessing.SpacePointMap(sourceAsMap);

        out.println(sourceAsMap3.size());


        SuccessResponseData successResponseData2 = new SuccessResponseData(sourceAsMap3);
        return successResponseData2;


    }



    /**
     * ????????????????????????
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("GiveTheThumbsUp")
    public ResponseData GiveTheThumbsUp(@RequestBody GiveTheTumbsUp giveTheTumbsUp,HttpServletRequest httpServletRequest){
        GiveTheTumbsUp list = giveTheTubsUpService.getOne(giveTheTumbsUp.getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //?????????????????? ????????????????????????????????????????????????ID???????????????????????????????????????????????????????????????

        //????????????
        GiveTheTumbsUpLog giveTheTumbsUpLog = new GiveTheTumbsUpLog();
        giveTheTumbsUpLog.setPid(giveTheTumbsUp.getId());
        giveTheTumbsUpLog.setUid(giveTheTumbsUp.getUid());
        giveTheTumbsUpLog.setTime(df.format(new Date()).toString());
        giveTheTubsUpLogService.insert(giveTheTumbsUpLog);




        if(list!=null){
            giveTheTubsUpService.update(list);
            return new SuccessResponseData();
        }else{
            giveTheTumbsUp.setCount("0");
            giveTheTubsUpService.insert(giveTheTumbsUp);
            GiveTheTumbsUp list2 = giveTheTubsUpService.getOne(giveTheTumbsUp.getId());
            return new SuccessResponseData(list2);
        }

    }
}
