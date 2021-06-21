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
public class YiGongCangShu {
    public void save(ImportExcel importExcel, YiGongCangShu yiGongCangShu, RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap1 = yiGongCangShu.getExcelDataZengShu("陈毅赠书（中文图书资料清单）采编清单20170922","17-470");

        List<Map<String,Object>> listMap2 = yiGongCangShu.getExcelDataZengShu("陈毅赠书（西文图书清单543册）采编清单20170921","Sheet1");

        List<Map<String,Object>> listMap3 = yiGongCangShu.getExcelDataSiKu();

        List<Map<String,Object>> listMap = new ArrayList<>();
        listMap.addAll(listMap1);
        listMap.addAll(listMap2);
        listMap.addAll(listMap3);
        for(int i = 0; i < listMap.size(); i++){
            Map<String,Object> map = listMap.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().replace(".0",""));
            }
            map.put("category","毅公藏书");
            map.put("terraceSource","复旦大学图书馆");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
    }

    // 毅公藏书赠书
    public List<Map<String,Object>> getExcelDataZengShu(String fileName,String sheetName){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公藏书\\"+fileName+".xlsx");
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
        map.put("key","releaseDate");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","format");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","Q");
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
            listMap = importExcelService.getExcelDataByXlsx(file,sheetName,2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 四库全书
    public List<Map<String,Object>> getExcelDataSiKu(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公藏书\\陈毅捐赠四库全书清单20170911.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map = new HashMap<>();
        map.put("key","title");
        map.put("index","A");
        columnList.add(map);
        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"四库全书",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }



}
