package com.cn.sundeinfo.main.modular.dataSource.importESData;

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/6/3
 */
public class YingYinZiLiao {
    public void save(ImportExcel importExcel, YingYinZiLiao yingYinZiLiao, RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap1 = yingYinZiLiao.getExcelDataSheet1();
        List<Map<String,Object>> listMap2 = yingYinZiLiao.getExcelDataSheet2();
        List<Map<String,Object>> listMap3 = yingYinZiLiao.getExcelDataYingXiang();
        List<Map<String,Object>> listMap4 = yingYinZiLiao.getExcelDataShangTuYingXiang();

        for(int i = 0; i < listMap1.size(); i++){
            Map<String,Object> map = listMap1.get(i);
            if(ObjectUtil.isNotEmpty(map.get("year"))){
                map.put("year",map.get("year").toString().replace(".0",""));
            }
            map.put("category","影音资料");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap2.size(); i++){
            Map<String,Object> map = listMap2.get(i);
            if(ObjectUtil.isNotEmpty(map.get("year"))){
                map.put("year",map.get("year").toString().replace(".0",""));
            }
            map.put("category","影音资料");
            map.put("terraceSource","复旦大学图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap3.size(); i++){
            Map<String,Object> map = listMap3.get(i);
            if(ObjectUtil.isNotEmpty(map.get("year"))){
                map.put("year",map.get("year").toString().replace(".0",""));
            }
            map.put("category","影音资料");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap4.size(); i++){
            Map<String,Object> map = listMap4.get(i);
            if(ObjectUtil.isNotEmpty(map.get("year"))){
                map.put("year",map.get("year").toString().replace(".0",""));
            }
            map.put("category","影音资料");
            map.put("terraceSource","上海图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
    }

    // 影音资料（20210330）
    public List<Map<String,Object>> getExcelDataSheet1(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\影音资料\\陈毅元帅专题纪录片统计.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","publisherUrb");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisher");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","year");
        map.put("index","C");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","pageCount");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","manufacturingCompany");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","source");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","terraceSource");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","L");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"20210330",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }
    // 影音资料（复旦大学图书馆）
    public List<Map<String,Object>> getExcelDataSheet2(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\影音资料\\陈毅元帅专题纪录片统计.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","title");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","author1");
        map.put("index","C");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","barCode");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","callNumber");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","price");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisher");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","year");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","source");
        map.put("index","S");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","S");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"复旦大学图书馆",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 影像资料
    public List<Map<String,Object>> getExcelDataYingXiang(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公藏书\\陈毅捐赠四库全书清单20170911.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","title");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisher");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","D");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"音像",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 上图影像资料
    public List<Map<String,Object>> getExcelDataShangTuYingXiang(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\影音资料\\影音资料——上图馆藏.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","title");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","subTitle");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","author1");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisherUrb");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisher");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","edition");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","seriesName");
        map.put("index","O");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","R");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","catalogueDate");
        map.put("index","Z");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","AC");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstractInfo");
        map.put("index","AE");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"上图馆藏",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }
}
