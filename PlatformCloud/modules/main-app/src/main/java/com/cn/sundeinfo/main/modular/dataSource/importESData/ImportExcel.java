package com.cn.sundeinfo.main.modular.dataSource.importESData;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.main.modular.dataSource.jxArchivesSpace.JxArchivesSpace;
import com.hankcs.hanlp.HanLP;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @创建人 libiao
 * @创建时间 2021/6/2
 */
public class ImportExcel {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ImportExcel importExcel = new ImportExcel();
        JxArchivesSpace jxArchivesSpace = new JxArchivesSpace();
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.7", 9200, "http")));

        //已经创建索引的话可以不写以下代码
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("incident");
        String itemsString = jxArchivesSpace.readJsonFile("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅事件.json");
        JSONObject json = JSONObject.parseObject(itemsString);
        JSONObject setting = (JSONObject)json.get("setting");
        JSONObject mapping = (JSONObject)json.get("mapping");
        createIndexRequest.settings(setting);
        createIndexRequest.mapping(mapping);
        try {
            client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 事件索引 incident
        // 数据索引 chenyi
        IndexRequest request = new IndexRequest("incident");
        // 导入媒体报道
       /* MeiTiBaoDao meiTiBaoDao = new MeiTiBaoDao();
        meiTiBaoDao.save(importExcel,meiTiBaoDao,client,request);

        // 研究文献
        YanJiuWenXian yanJiuWenXian = new YanJiuWenXian();
        yanJiuWenXian.save(importExcel,yanJiuWenXian,client,request);

        //毅公藏书
        YiGongCangShu yiGongCangShu = new YiGongCangShu();
        yiGongCangShu.save(importExcel,yiGongCangShu,client,request);

        //毅公手迹
        YiGongShouJi yiGongShouJi = new YiGongShouJi();
        yiGongShouJi.save(importExcel,yiGongShouJi,client,request);

        //毅公著述
        YiGongZhuShu yiGongZhuShu = new YiGongZhuShu();
        yiGongZhuShu.save(importExcel,yiGongZhuShu,client,request);

        //影音资料
        YingYinZiLiao yingYinZiLiao = new YingYinZiLiao();
        yingYinZiLiao.save(importExcel,yingYinZiLiao,client,request);*/

        //历史图片
        /*TuPian tuPian = new TuPian();
        tuPian.save(importExcel,tuPian,client,request);*/

        //陈毅事件
        ShiJianShu shiJianShu = new ShiJianShu();
        shiJianShu.save(importExcel,shiJianShu,client,request);

        long entTime = System.currentTimeMillis();
        long time =  entTime - startTime;
        System.out.println("存入数据耗时"+(time/1000/60) +"分" + " " + (time/1000%60));

    }

    public void saveES(Map map,RestHighLevelClient client,IndexRequest request,Integer id){
        try {
//            if(ObjectUtil.isNull(id)){
//                request.id(String.valueOf(System.currentTimeMillis()));
//            }else{
//                request.id(String.valueOf(id));
//            }
            request.id(String.valueOf(System.currentTimeMillis()));
            request.source(map, XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        } catch (Exception e) {
            System.out.println(map);
            e.printStackTrace();
        }
    }
    /*
    * 繁体转繁体
    * */
    public void jianFanZhuanHuan(Map<String,Object> map){
        if(ObjectUtil.isNotNull(map.get("title"))){
            map.put("titleText",convertZHCNForCharacter(map.get("title").toString()));
        }
        if(ObjectUtil.isNotNull(map.get("author1"))){
            map.put("authorText",convertZHCNForCharacter(map.get("author1").toString()));
        }
        if(ObjectUtil.isNotNull(map.get("source"))){
            map.put("sourceText",convertZHCNForCharacter(map.get("source").toString()));
        }
        if(ObjectUtil.isNotNull(map.get("abstract"))){
            map.put("abstractText",convertZHCNForCharacter(map.get("abstract").toString()));
        }
        if(ObjectUtil.isNotNull(map.get("keywords"))){
            map.put("keywordsText",convertZHCNForCharacter(map.get("keywords").toString()));
        }

        if(ObjectUtil.isNotNull(map.get("persons"))){
            map.put("personsText",convertZHCNForCharacter(map.get("persons").toString()));
        }
        if(ObjectUtil.isNotNull(map.get("events"))){
            map.put("eventsText",convertZHCNForCharacter(map.get("events").toString()));
        }

    }

    static public String convertZHCNForCharacter(String data) {
        if (data == null) return null;
        String tmp = HanLP.convertToSimplifiedChinese(data);
        return tmp;
    }

    static public String convertTWCNForCharacter(String data) {
        if (data == null) return null;
        String tmp = HanLP.convertToTraditionalChinese(data);
        return tmp;
    }

    // 上图数据的箸者需要通过空格分割
    public void jxAuthor(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("author1"))){
            return;
        }
        String authorStr = map.get("author1").toString().
                replace(" ","；").
                replace("、","；").
                replace(";","；").
                replace("，","；").
                replace(",","；");
        String authorStrText = map.get("authorText").toString().
                replace(" ","；").
                replace("、","；").
                replace(";","；").
                replace("，","；").
                replace(",","；");
        this.jxAuthorInfo(authorStr,map,"author1");
        this.jxAuthorInfo(authorStrText,map,"authorText");
    }

    // 其他数据的箸者不用通过空格分割
    public void jxAuthorNotBlank(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("author1"))){
            return;
        }
        String authorStr = map.get("author1").toString().
                replace("、","；").
                replace(";","；").
                replace("，","；").
                replace(",","；");
        String authorStrText = map.get("authorText").toString().
                replace("、","；").
                replace(";","；").
                replace("，","；").
                replace(",","；");
        this.jxAuthorInfo(authorStr,map,"author1");
        this.jxAuthorInfo(authorStr,map,"authorText");
    }

    private void jxAuthorInfo(String authorStr,Map<String,Object> map,String key){
        String[] arr = authorStr.split("；");
        StringBuffer sbf = new StringBuffer();
        for(int i = 0; i < arr.length; i++){
            if(ObjectUtil.isEmpty(arr[i].trim())){
                continue;
            }
            sbf.append(arr[i]);
            if(i < arr.length - 1){
                sbf.append("；");
            }
        }
        String[] arr2 = sbf.toString().split("；");
        if(arr2.length == 1 && "".equals(arr2[0])){
            map.put(key,new String[0]);
        }else{
            map.put(key,arr2);
        }
    }

    public void jxYear(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("year"))){
            return;
        }
        String year = map.get("year").toString();
        if(ObjectUtil.isNotEmpty(year) && year.length() > 4){
            map.put("groupYear",year.substring(0,4));
        }else{
            map.put("groupYear",year);
        }
    }
}
