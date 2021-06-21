package com.cn.sundeinfo.main.modular.dataSource.importESData;

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @创建人 libiao
 * @创建时间 2021/6/3
 */
public class ShiJianShu {

    public static List<Map<String,Object>> coordinateList = new ArrayList<>();


    public void save(ImportExcel importExcel, ShiJianShu shiJianShu, RestHighLevelClient client, IndexRequest request){
        // 获取经纬度
        this.getCoordinateList();

        List<Map<String,Object>> listMap1 = shiJianShu.getExcelDataIncident("网络资源");

        List<Map<String,Object>> listMap2 = shiJianShu.getExcelDataIncident("《陈毅年谱》");

        List<Map<String,Object>> listMap3 = shiJianShu.getExcelDataIncident("《陈毅同志日记摘抄》");

        List<Map<String,Object>> listMap4 = shiJianShu.getExcelDataIncident("《陈毅传》");
        List<Map<String,Object>> listMap = new ArrayList<>();
        listMap.addAll(listMap1);
        listMap.addAll(listMap2);
        listMap.addAll(listMap3);
        listMap.addAll(listMap4);
        List<Map<String,Object>> parentList = shiJianShu.getExcelDataHostIncident();
        List<Map<String,Object>> parentCopyList = Arrays.asList(new Map[parentList.size()]);
        Collections.copy(parentCopyList,parentList);

        Map<String, Set<String>> mapId = new HashMap();
        // 获取主事件的子事件id
        for(int i = 0; i < parentList.size(); i++){
            Map<String,Object> mapParent = parentList.get(i);
            if(ObjectUtil.isNotEmpty(mapParent.get("incidentId"))){
                mapParent.put("incidentId",mapParent.get("incidentId").toString().replace(".0",""));
            }
            if(ObjectUtil.isNotEmpty(mapParent.get("childrenIncidentId"))){
                mapParent.put("childrenIncidentId",mapParent.get("childrenIncidentId").toString().replace(".0",""));
            }
            Set<String> ids = mapId.get(mapParent.get("incidentId"));
            if(ObjectUtil.isNull(ids)){
                ids = new HashSet<>();
                ids.add(mapParent.get("childrenIncidentId").toString());
                mapId.put(mapParent.get("incidentId").toString(),ids);
            }else{
                ids.add(mapParent.get("childrenIncidentId").toString());
                parentList.remove(i);
                i--;
            }
        }


        //录入子事件
        for(int i = 0; i < listMap.size(); i++){
            Map<String,Object> mapIncident = listMap.get(i);
            mapIncident.put("category","陈毅事件");
            if(ObjectUtil.isNotEmpty(mapIncident.get("incidentId"))){
                mapIncident.put("incidentId",mapIncident.get("incidentId").toString().replace(".0",""));
            }
            for(int j = 0; j < parentCopyList.size(); j++){
                Map<String,Object> mapParent = parentCopyList.get(j);
                if(ObjectUtil.isNotEmpty(mapParent.get("childrenIncidentId"))){
                    mapParent.put("childrenIncidentId",mapParent.get("childrenIncidentId").toString().replace(".0",""));
                }
                if(ObjectUtil.isNotEmpty(mapParent.get("incidentId"))){
                    mapParent.put("incidentId",mapParent.get("incidentId").toString().replace(".0",""));
                }
                if(mapIncident.get("incidentId").toString().equals(mapParent.get("childrenIncidentId"))){
                    mapIncident.put("pid",mapParent.get("incidentId"));
                    this.jxStartDate(mapIncident);
                    this.jxEndDate(mapIncident);
                    this.jxNumber(mapIncident);
                    importExcel.saveES(mapIncident,client,request,Integer.valueOf(mapIncident.get("incidentId").toString()));
                    break;
                }else if(j == parentCopyList.size() - 1){
                    Set<String> childrenIncidentId = mapId.get(mapIncident.get("incidentId"));
                    if(ObjectUtil.isNull(childrenIncidentId)){
                        mapIncident.put("pid","");
                        if(ObjectUtil.isNotEmpty(mapIncident.get("incidentId"))){
                            this.jxStartDate(mapIncident);
                            this.jxEndDate(mapIncident);
                            this.jxNumber(mapIncident);
                            importExcel.saveES(mapIncident,client,request,Integer.valueOf(mapIncident.get("incidentId").toString()));
                        }else{
                            System.out.println("id------------------->"+mapIncident.get("id"));
                        }
                    }
                }
            }
        }

        // 录入主事件
        for(int i = 0; i < parentList.size(); i++){
            Map<String,Object> mapParent = parentList.get(i);
            Set<String> childrenIncidentId = mapId.get(mapParent.get("incidentId"));
            mapParent.put("childrenIncidentId",childrenIncidentId);
            mapParent.put("category","陈毅事件");
            for(int j = 0; j < listMap.size(); j++){
                Map<String,Object> mapIncident = listMap.get(j);
                if(ObjectUtil.isNotEmpty(mapIncident.get("incidentId"))){
                    mapIncident.put("incidentId",mapIncident.get("incidentId").toString().replace(".0",""));
                }
                if(mapParent.get("incidentId").toString().equals(mapIncident.get("incidentId").toString())){
                    mapIncident.put("childrenIncidentId",childrenIncidentId);
                    shiJianShu.jxStartDate(mapIncident);
                    shiJianShu.jxEndDate(mapIncident);
                    this.jxNumber(mapIncident);
                    importExcel.saveES(mapIncident,client,request,Integer.valueOf(mapIncident.get("incidentId").toString()));
                    break;
                }else if(j == listMap.size() - 1){
                    shiJianShu.jxStartDate(mapParent);
                    shiJianShu.jxEndDate(mapParent);
                    this.jxNumber(mapParent);
                    importExcel.saveES(mapParent,client,request,Integer.valueOf(mapParent.get("incidentId").toString()));
                }
            }
        }

    }

    private void jxNumber(Map<String,Object> map){
        if(ObjectUtil.isNotEmpty(map.get("ISBN"))){
            map.put("ISBN",map.get("ISBN").toString().replace(".0",""));
        }
        if(ObjectUtil.isNotEmpty(map.get("placeId"))){
            map.put("placeId",map.get("placeId").toString().replace(".0",""));
        }
    }

    private void jxStartDate(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("startDate"))){
            return;
        }
        String startDate = map.get("startDate").toString();
        String arr[] = startDate.split("/");
        if(arr.length > 1){
            if(arr[0].length() >= 4){
                map.put("searchStartDate",arr[0]);
            }else{
                String[] arr2 = arr[1].split("/");
                if(arr2.length > 1){
                    map.put("searchStartDate",arr2[1]);
                }
            }
        }else{
            map.put("searchStartDate",startDate);
        }
    }

    private void jxEndDate(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("endDate"))){
            return;
        }
        String startDate = map.get("endDate").toString();
        String arr[] = startDate.split("/");
        if(arr.length > 1){
            if(arr[0].length() >= 4){
                map.put("searchEndDate",arr[0]);
            }else{
                String[] arr2 = arr[1].split("/");
                if(arr2.length > 1){
                    map.put("searchEndDate",arr2[1]);
                }
            }
        }else{
            map.put("searchEndDate",startDate);
        }
    }

    // 具体事件
    public List<Map<String,Object>> getExcelDataIncident(String sheetName){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅时空分析用数据和说明\\陈毅事件拆解.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","source");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","ISBN");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","page");
        map.put("index","C");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","content");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","incidentId");
        map.put("index","F");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","startDate");
        map.put("index","G");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","endDate");
        map.put("index","H");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","dateRemark");
        map.put("index","I");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","place");
        map.put("index","J");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","placeId");
        map.put("index","K");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","main");
        map.put("index","L");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","mainId");
        map.put("index","M");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","second");
        map.put("index","N");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","secondId");
        map.put("index","O");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","relation");
        map.put("index","P");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","personRemark");
        map.put("index","Q");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","predicate");
        map.put("index","R");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","type");
        map.put("index","S");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","work");
        map.put("index","T");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","workId");
        map.put("index","U");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","workRemark");
        map.put("index","V");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","meeting");
        map.put("index","W");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","meetingId");
        map.put("index","X");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","meetingRemark");
        map.put("index","Y");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","otherRemark");
        map.put("index","Z");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,sheetName,2,columnList);
            for(int i = 0; i < listMap.size(); i++){
                Map<String,Object> mapNew = listMap.get(i);
                mapNew.put("coordinate","");
                coordinateList.forEach(mapCoordinate ->{
                    if(mapNew.get("place").toString().equals(mapCoordinate.get("place").toString())){
                        mapNew.put("coordinate",mapCoordinate.get("coordinate"));
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 主事件
    public List<Map<String,Object>> getExcelDataHostIncident(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅时空分析用数据和说明\\陈毅事件树.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","title");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","incidentId");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","childrenIncidentId");
        map.put("index","D");
        columnList.add(map);


        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"Sheet1",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 坐标
    public void getCoordinateList(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\陈毅时空分析用数据和说明\\陈毅事件拆解.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","place");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","coordinate");
        map.put("index","B");
        columnList.add(map);

        try {
            coordinateList = importExcelService.getExcelDataByXlsx(file,"经纬度",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
