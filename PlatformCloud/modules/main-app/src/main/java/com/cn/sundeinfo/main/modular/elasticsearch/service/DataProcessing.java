package com.cn.sundeinfo.main.modular.elasticsearch.service;


import com.alibaba.fastjson.JSONArray;
import com.anji.captcha.util.StringUtils;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.ConditionalSearch;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.PageBean;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.SearchCitions;
import org.apache.lucene.search.TotalHits;
import org.checkerframework.checker.units.qual.K;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.springframework.stereotype.Component;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * @Author:KangZD
 * @Date:2021/6/8
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service.impl
 * @version:1.2
 * 数据处理
 */
@Component
public class DataProcessing {


    /**
     * 拼接前台所需格式 该方法为处理器 负责对es检索信息过滤成指定格式数据 时空地图-左侧事件树信息
     *
     * @param sourceAsMap
     * @return
     * @Author Shikai
     */
    public List<Map<String, Object>> MapProcessing(List<Map<String, Object>> sourceAsMap) {

        //获取一级类目树
        List<Map<String, Object>> level1List = sourceAsMap
                .stream()
                .filter(hashMap ->
                        hashMap.get("pid") == null)
                .collect(Collectors.toList());
        //获取二级类目树
        List<Map<String, Object>> level2List = sourceAsMap
                .stream()
                .filter(hashMap ->
                        hashMap.get("pid") != null)
                .collect(Collectors.toList());
        //对有时间信息汇总
        List<Map<String, Object>> orderList = level1List
                .stream()
                .filter(hashMap ->
                        hashMap.get("searchStartDate") != null && !hashMap.get("searchStartDate").equals(""))
                .collect(Collectors.toList());
        //对无时间信息追加到结尾
        List<Map<String, Object>> joinList = level1List
                .stream()
                .filter(hashMap ->
                        hashMap.get("searchStartDate") == null || hashMap.get("searchStartDate").equals(""))
                .collect(Collectors.toList());
        //排序
        Collections.sort(orderList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String year1 = (String) o1.get("searchStartDate");//name1是从你list里面拿出来的一个
                String year2 = (String) o2.get("searchStartDate"); //name1是从你list里面拿出来的第二个name
                return year1.compareTo(year2);
            }
        });
        //追加
        orderList.addAll(joinList);
        //拼接json串
        List<Map<String, Object>> returnLevel1List = new LinkedList<>();
        for (Map<String, Object> level1Map : orderList) {
            Map<String, Object> returnLevel1Map = new HashMap<>();
            returnLevel1Map.put("title", level1Map.get("title"));
            returnLevel1Map.put("key", level1Map.get("id"));
            returnLevel1Map.put("incidentId", level1Map.get("incidentId"));
            returnLevel1Map.put("year", level1Map.get("searchStartDate") != null ? level1Map.get("searchStartDate") : null);
            List<Map<String, Object>> returnLevel2List = new LinkedList<>();
            for (Map<String, Object> level2Map : level2List) {
                if (level2Map.get("pid").equals(level1Map.get("incidentId"))) {
                    Map<String, Object> returnLeve21Map = new HashMap<>();
                    returnLeve21Map.put("title", level2Map.get("title"));
                    returnLeve21Map.put("incidentId", level2Map.get("incidentId"));
                    returnLeve21Map.put("key", level2Map.get("id"));
                    returnLevel2List.add(returnLeve21Map);
                }
            }
            if (returnLevel2List.size() > 0) {
                returnLevel1Map.put("children", returnLevel2List);
            }
            returnLevel1List.add(returnLevel1Map);
        }
        //
        out.println(JSONArray.toJSON(returnLevel1List).toString());
        return returnLevel1List;
    }


    /**
     * 页码页数处理以及分组数据封装
     */
    public PageBean PageValue(SearchCitions searchCitions, ResponseData responseData, List<Map<String, Object>> mapList) {
        //获取总条数
        final TotalHits totalPage = ((SearchResponse) responseData.getData()).getHits().getTotalHits();
        SearchResponse searchResponse = (SearchResponse) responseData.getData();

        //封装分页对象
        PageBean pageBean = new PageBean(0, 10);
        pageBean.setTotalCount((int) totalPage.value);
        pageBean.setPageSize(searchCitions.getPages().getPageSize());
        pageBean.setCurrentPage(searchCitions.getPages().getCurrentPage());
        pageBean.setBean(mapList);
        pageBean.setTotalPage(pageBean.getTotalPage());

        //获取分组信息
        String group_field_arr[] = searchCitions.getGroup_field_arr();

        //菜单
        List<Map<String, Object>> treeMenu = new ArrayList<>();
        for (String field : group_field_arr) {
            ParsedTerms parsedTerms = searchResponse.getAggregations().get("tag_" + field);
            List<? extends Terms.Bucket> buckets = parsedTerms.getBuckets();

            Map<String, Object> stringLongMap = new HashMap<>();
            //一级菜单
            List<Map<String, Object>> firstLevelMenu = new ArrayList<>();
            stringLongMap.put("name", field);
            for (Terms.Bucket bucket : buckets) {

                //二级菜单
                Map secondLevelMenu = new HashMap<>();
                //key的数据
                String key = bucket.getKey().toString();
                //计数
                ParsedValueCount count_field = bucket.getAggregations().get("count_" + field);
                secondLevelMenu.put("name", key);
                secondLevelMenu.put("count", bucket.getDocCount() + "");
                secondLevelMenu = sortByValue(secondLevelMenu);

                //存入到一级菜单下
                firstLevelMenu.add(secondLevelMenu);
            }

            //封装
            stringLongMap.put("value", firstLevelMenu);
            //菜单封装
            treeMenu.add(stringLongMap);

        }

        pageBean.setGroup_field_arr(treeMenu);
        return pageBean;
    }


    /**
     * map根据value排序工具类 封装方法1/6 未使用lambda版本
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     * @Author Shikai
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 拼接前台所需格式 该方法为处理器 负责对es检索信息过滤成指定格式数据 时空地图-右部节点信息
     *
     * @param sourceAsMap
     * @return
     * @Author Shikai
     */
    public List<Map<String, Object>> SpacePointMap(List<Map<String, Object>> sourceAsMap) {




        List<Map<String, Object>> returnLevel1List = new LinkedList<>();
        String codr[] = new String[]{};
        int i = 0;
        for (Map<String, Object> level1Map : sourceAsMap) {
            Map<String, Object> returnLevel1Map = new HashMap<>();
            Map<String, Object> returnLevel2Map = new HashMap<>();
            returnLevel1Map.put("properties", level1Map);
            returnLevel2Map.put("type", "Point");
            if (level1Map.get("coordinate") != null) {
                //判断是否为空
                if (level1Map.get("coordinate").toString().isEmpty()) {

                }else{
                    Double fromString[] = new Double[2];
                    //循环m数组
                    for (int z = 0; z < level1Map.get("coordinate").toString().split(",").length; z++) {
                        codr = level1Map.get("coordinate").toString().split(",");
                        //判断是否为中文
                        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
                        Matcher matcher = pattern.matcher(codr[z]);
                        //判断是否为中文
                        if(matcher.find()){
                        }else {
                            fromString[z] = Double.parseDouble(codr[z]);
                        }
                    }
                    returnLevel2Map.put("coordinates", fromString);
                 }
            }
            else {
                    i++;
            }
            returnLevel1Map.put("geometry", returnLevel2Map);
            returnLevel1List.add(returnLevel1Map);

        }
            out.println("无坐标点数据：" + i);

            return returnLevel1List;
        }



}
