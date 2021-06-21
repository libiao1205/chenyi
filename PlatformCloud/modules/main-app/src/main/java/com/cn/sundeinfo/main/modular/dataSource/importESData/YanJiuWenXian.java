package com.cn.sundeinfo.main.modular.dataSource.importESData;

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/6/3
 */
public class YanJiuWenXian {

    public void save(ImportExcel importExcel,YanJiuWenXian yanJiuWenXian, RestHighLevelClient client, IndexRequest request){

        List<Map<String,Object>> listMap1 = yanJiuWenXian.getExcelDataCNKI1And2("CNKI-1");

        List<Map<String,Object>> listMap2 = yanJiuWenXian.getExcelDataCNKI1And2("CNKI-２");

         List<Map<String,Object>> listMap3 = yanJiuWenXian.getExcelDataCNKI3();

         List<Map<String,Object>> listMap4 = yanJiuWenXian.getExcelDataFuDan();

        List<Map<String,Object>> listMap5 = yanJiuWenXian.getExcelDataShangTu();


        for(int i = 0; i < listMap1.size(); i++){
            Map<String,Object> map = listMap1.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().split(" ")[0]);
            }
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            map.put("category","研究文献");
            map.put("terraceSource","CNKI");
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap2.size(); i++){
            Map<String,Object> map = listMap2.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().split(" ")[0]);
            }
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            map.put("category","研究文献");
            map.put("terraceSource","CNKI");
            importExcel.saveES(map,client,request,null);
        }
        for(int i = 0; i < listMap3.size(); i++){
            Map<String,Object> map = listMap3.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().split(" ")[0]);
            }
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            map.put("category","研究文献");
            map.put("terraceSource","CNKI");
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap4.size(); i++){
            Map<String,Object> map = listMap4.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().split(" ")[0]);
            }
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            map.put("category","研究文献");
            map.put("terraceSource","复旦大学图书馆");
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap5.size(); i++){
            Map<String,Object> map = listMap5.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().split(" ")[0]);
            }
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            map.put("category","研究文献");
            map.put("terraceSource","上海图书馆");
            importExcel.saveES(map,client,request,null);
        }
    }

    // 研究文献（CNKI-1,CNKI-2）
    public List<Map<String,Object>> getExcelDataCNKI1And2(String sheetName){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\研究文献\\研究文献.xlsx");
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
        // CNKI1
        if("CNKI-1".equals(sheetName)){
            map = new HashMap<>();
            map.put("key","organization");
            map.put("index","N");
            columnList.add(map);

            map = new HashMap<>();
            map.put("key","filePath");
            map.put("index","O");
            columnList.add(map);
        }else{
            // CNKI2
            map = new HashMap<>();
            map.put("key","organization");
            map.put("index","P");
            columnList.add(map);

            map = new HashMap<>();
            map.put("key","filePath");
            map.put("index","Q");
            columnList.add(map);
        }

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,sheetName,2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 研究文献（CNKI3）
    public List<Map<String,Object>> getExcelDataCNKI3(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\研究文献\\研究文献.xlsx");
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
        map.put("key","conferenceAddress");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","conferenceName");
        map.put("index","N");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","organization");
        map.put("index","O");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","P");
        columnList.add(map);
        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"CNKI-3",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 复旦馆藏（复旦馆藏）
    public List<Map<String,Object>> getExcelDataFuDan(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\研究文献\\研究文献.xlsx");
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
        map.put("key","seriesName");
        map.put("index","P");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","N");
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

    // 复旦馆藏（上图馆藏）
    public List<Map<String,Object>> getExcelDataShangTu(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\研究文献\\研究文献.xlsx");
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
