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
public class YiGongZhuShu {
    public void save(ImportExcel importExcel, YiGongZhuShu yiGongZhuShu, RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap1 = yiGongZhuShu.getExcelDataBaoKan();

        List<Map<String,Object>> listMap2 = yiGongZhuShu.getExcelDataCNKI();

        List<Map<String,Object>> listMap3 = yiGongZhuShu.getExcelDataFuDanGuanCang();

        List<Map<String,Object>> listMap4 = yiGongZhuShu.getExcelDataShangTuGuanCang();

        for(int i = 0; i < listMap1.size(); i++){
            Map<String,Object> map = listMap1.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            map.remove("volumeInfo");
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            yiGongZhuShu.jxAskForNo(map);
            map.put("category","毅公著述");
            map.put("terraceSource","上海图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap2.size(); i++){
            Map<String,Object> map = listMap2.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            map.remove("volumeInfo");
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            map.put("category","毅公著述");
            map.put("terraceSource","CNKI");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap3.size(); i++){
            Map<String,Object> map = listMap3.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            map.remove("volumeInfo");
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            map.put("category","毅公著述");
            map.put("terraceSource","复旦大学图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap4.size(); i++){
            Map<String,Object> map = listMap4.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            map.remove("volumeInfo");
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            map.put("category","毅公著述");
            map.put("terraceSource","上海图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
    }

    private void jxAskForNo(Map<String,Object> map){
        String guanCang = map.get("guanCang").toString();
        if(ObjectUtil.isNotEmpty(guanCang)){
            String[] arrStr = guanCang.split(" 馆藏索取号：");
            if(arrStr.length > 1){
                String [] arrStr2 = arrStr[1].split("分类号：");
                if(arrStr2.length > 1){
                    map.put("askForNo",arrStr2[0]);
                    String[] arrStr3 = arrStr2[1].split(" 是否上传过原文：");
                    if(arrStr3.length > 1){
                        map.put("classNumber",arrStr3[0]);
                    }else{
                        map.put("classNumber",arrStr2[1]);
                    }
                }
            }
        }
        map.remove("guanCang");
    }
    // 毅公著述（上图数据库-报刊篇目）
    public List<Map<String,Object>> getExcelDataBaoKan(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公著述\\毅公著述.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","title");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","author1");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","source");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","year");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","volume");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","guanCang");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","callNumber");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","volumeInfo");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","U");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"上图数据库-报刊篇目",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 毅公著述（CNKI）
    public List<Map<String,Object>> getExcelDataCNKI(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公著述\\毅公著述.xlsx");
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
        map.put("key","source");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","year");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","keywords");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","issue");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","volume");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","pageCount");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","L");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","srcDatabase");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","organization");
        map.put("index","N");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","O");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"CNKI",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 毅公著述（复旦馆藏）
    public List<Map<String,Object>> getExcelDataFuDanGuanCang(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公著述\\毅公著述.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","source");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","subTitle");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","author1");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisherUrb");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisher");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","edition");
        map.put("index","L");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","N");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","seriesName");
        map.put("index","P");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","R");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstractInfo");
        map.put("index","S");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"复旦馆藏",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }


    // 毅公著述（上图馆藏）
    public List<Map<String,Object>> getExcelDataShangTuGuanCang(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公著述\\毅公著述.xlsx");
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
        map.put("key","price");
        map.put("index","Q");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","AC");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstractInfo");
        map.put("index","AE");

        map = new HashMap<>();
        map.put("key","source");
        map.put("index","AQ");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","AQ");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","R");
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
