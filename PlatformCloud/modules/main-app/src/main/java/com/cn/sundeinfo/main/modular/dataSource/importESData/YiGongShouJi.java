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
public class YiGongShouJi {
    public void save(ImportExcel importExcel, YiGongShouJi yiGongShouJi, RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap = yiGongShouJi.getExcelDataShouJi();
        for(int i = 0; i < listMap.size(); i++){
            Map<String,Object> map = listMap.get(i);
            if(ObjectUtil.isNotEmpty(map.get("abstractInfo")) && ObjectUtil.isEmpty(map.get("abstract"))){
                map.put("abstract",map.get("abstractInfo"));
            }
            map.remove("abstractInfo");
            if(ObjectUtil.isNotEmpty(map.get("releaseDate"))){
                map.put("releaseDate",map.get("releaseDate").toString().replace(".0",""));
            }
            map.put("category","毅公手迹");
            importExcel.jxYear(map);
            importExcel.jianFanZhuanHuan(map);
            importExcel.jxAuthorNotBlank(map);
            importExcel.saveES(map,client,request,null);
        }
    }

    // 毅公手迹
    public List<Map<String,Object>> getExcelDataShouJi(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\陈毅专题数据库-九大模块\\毅公手迹\\陈毅 题字SWX+CC.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","releaseDate");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","publisherUrb");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","C");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","filePath");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","source");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","terraceSource");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","groupSource");
        map.put("index","F");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"sheet1",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }
}
