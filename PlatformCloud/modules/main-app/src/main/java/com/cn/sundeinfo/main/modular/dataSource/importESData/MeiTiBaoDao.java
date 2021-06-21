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
public class MeiTiBaoDao {
    public void save(ImportExcel importExcel,MeiTiBaoDao meiTiBaoDao,RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap1 = meiTiBaoDao.getExcelDataMeiTiBaoDao1();

        List<Map<String,Object>> listMap2 = meiTiBaoDao.getExcelDataMeiTiBaoDao2("报纸发布（法令）");

        List<Map<String,Object>> listMap3 = meiTiBaoDao.getExcelDataMeiTiBaoDao2("上图-报刊篇目");

        List<Map<String,Object>> listMap = new ArrayList<>();

        listMap.addAll(listMap2);
        listMap.addAll(listMap3);
        for(int i = 0; i < listMap.size(); i++){
            Map<String,Object> map = listMap.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            String year = map.get("year").toString();
            if(ObjectUtil.isNotEmpty(year) && year.length() > 4){
                map.put("groupYear",year.substring(0,4));
            }else{
                map.put("groupYear",year);
            }
            map.remove("volumeInfo");
            meiTiBaoDao.jxAskForNo(map);
            map.put("category","媒体报道");
            map.put("terraceSource","上海图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthor(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap1.size(); i++){
            Map<String,Object> map = listMap1.get(i);
            if(ObjectUtil.isNotEmpty(map.get("volumeInfo")) && ObjectUtil.isEmpty(map.get("volume"))){
                map.put("volume",map.get("volumeInfo"));
            }
            map.remove("volumeInfo");
            map.put("category","媒体报道");
            map.put("terraceSource","CNKI");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthor(map);
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

    // 媒体报道（CNKI）
    public List<Map<String,Object>> getExcelDataMeiTiBaoDao1(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\媒体报道\\媒体报道.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map = new HashMap<>();
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
        map.put("key","pageCount");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","srcDatabase");
        map.put("index","L");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","city");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","N");
        columnList.add(map);
        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"CNKI",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }


    // 媒体报道（上图-报刊篇目，报纸发布（法令））
    public List<Map<String,Object>> getExcelDataMeiTiBaoDao2(String sheetName){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\媒体报道\\媒体报道.xlsx");
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
            listMap = importExcelService.getExcelDataByXlsx(file,sheetName,2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }
}
