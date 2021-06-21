package com.cn.sundeinfo.main.modular.dataSource.jxArchivesSpace;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.main.modular.dataSource.createJson.CreateJsonUtil;
import com.cn.sundeinfo.sys.config.http.HttpService;
import com.cn.sundeinfo.sys.core.export.ExportExcel;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/5/12
 */
@Component
public class JxArchivesSpaceSaveES {

    @Autowired
    HttpService httpService;

    static JSONArray itemsInfo;

    static List<Map<String,Object>> listMap;

    JxArchivesSpace jxArchivesSpace = new JxArchivesSpace();

    static List<String[]> contentFilePath = new ArrayList<>();


//    @Autowired
//    JxArchivesSpace jxArchivesSpace;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        JxArchivesSpaceSaveES jxArchivesSpaceSaveES = new JxArchivesSpaceSaveES();
        JxArchivesSpace jxArchivesSpace = new JxArchivesSpace();
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("10.55.102.38", 9200, "http")));
    /*    CreateIndexRequest createIndexRequest = new CreateIndexRequest("chenyi2");
        String itemsString1 = jxArchivesSpace.readJsonFile("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\文章数据.json");
        JSONObject json = JSONObject.parseObject(itemsString1);
        JSONObject setting = (JSONObject)json.get("setting");
        JSONObject mapping = (JSONObject)json.get("mapping");
        createIndexRequest.settings(setting);
        createIndexRequest.mapping(mapping);
        try {
            client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        IndexRequest request = new IndexRequest("chenyi");

        String itemsString = jxArchivesSpace.readJsonFile("D:\\asChenYiData\\zhaiyaoitems.json");
        itemsInfo = JSONArray.parseArray(itemsString);

        // 查询所有爬虫链接
        listMap = jxArchivesSpaceSaveES.importExcelContent();

        jxArchivesSpaceSaveES.saveZhaoPianItems(client,request,"历史照片","陈毅相册","D:\\asChenYiData\\陈毅相册items.json",false);

        jxArchivesSpaceSaveES.saveItems(client,request,"书画作品","书画作品","D:\\asChenYiData\\书画作品和实物资源items.json",true);

        jxArchivesSpaceSaveES.saveItems(client,request,"毅公著述","毅公著述","D:\\asChenYiData\\毅公著述items.json",false);

        jxArchivesSpaceSaveES.saveTreeItems(client,request);


        //导出json文件
        // jxArchivesSpaceSaveES.exportJson();
        long entTime = System.currentTimeMillis();
        long time =  entTime - startTime;
        System.out.println("存入数据耗时"+(time/1000/60) +"分 "+ (time/1000%60) +"秒");
    }

    public void saveZhaoPianItems(RestHighLevelClient client,IndexRequest request,String category,String fileType,String url,boolean isSubjects){
        JxArchivesSpace jxArchivesSpace = new JxArchivesSpace();
        String xiangceItemsString = jxArchivesSpace.readJsonFile(url);
        JSONArray xiangceItems = JSONArray.parseArray(xiangceItemsString);

        String zhaopianItemsString = jxArchivesSpace.readJsonFile("D:\\asChenYiData\\相册照片items.json");
        JSONArray zhaopianItems = JSONArray.parseArray(zhaopianItemsString);
        JSONObject jsonMap = new JSONObject();

        for(int i = 0; i < xiangceItems.size(); i++){
            jsonMap.clear();
            JSONObject xiangceItemDetail =(JSONObject)xiangceItems.get(i);
            jsonMap.put("category",category);
            jsonMap.put("fileType2",fileType);
            jsonMap.put("source",xiangceItemDetail.get("title"));
            String xiangceUri = xiangceItemDetail.get("uri").toString();
            for(int j = 0; j < zhaopianItems.size(); j++){
                JSONObject zhaopianItemDetail =(JSONObject)zhaopianItems.get(j);
                String zhaopianParentUri = ((JSONObject)zhaopianItemDetail.get("parent")).get("ref").toString();
                if(!zhaopianParentUri.equals(xiangceUri)){
                    continue;
                }
                String uri = zhaopianItemDetail.get("uri").toString();
                int index = uri.lastIndexOf("/");
                String id = "";
                if(index >= 0  && uri.length() > index + 1){
                    id = uri.substring(index+1);
                }
                this.matchingField(jsonMap,zhaopianItemDetail,id,client,request,false,isSubjects);
            }
        }
        ExportExcel exportExcel = new ExportExcel();
        String[] titles = {"链接"};
        //exportExcel.excelSaveLocal(titles,contentFilePath);
    }

    public void saveItems(RestHighLevelClient client,IndexRequest request,String category,String fileType,String url,boolean isSubjects){
        JxArchivesSpace jxArchivesSpace = new JxArchivesSpace();
        String itemsString = jxArchivesSpace.readJsonFile(url);
        JSONArray items = JSONArray.parseArray(itemsString);
        JSONObject jsonMap = new JSONObject();
        for(int i = 0; i < items.size(); i++){
            jsonMap.clear();
            jsonMap.put("category",category);
            jsonMap.put("fileType2",fileType);
            JSONObject itemDetail =(JSONObject)items.get(i);
            String uri = itemDetail.get("uri").toString();
            int index = uri.lastIndexOf("/");
            String id = "";
            if(index >= 0  && uri.length() > index + 1){
                id = uri.substring(index+1);
            }
            this.matchingField(jsonMap,itemDetail,id,client,request,false,isSubjects);
        }
    }

    public void saveTreeItems(RestHighLevelClient client,IndexRequest request){
        try {
            String jsonString = jxArchivesSpace.readJsonFile("D:\\asChenYiData\\26Trees.json");
            String itemsString = jxArchivesSpace.readJsonFile("D:\\asChenYiData\\items.json");
            JSONArray items = JSONArray.parseArray(itemsString);
            JSONArray categoryArray = JSONArray.parseArray(jsonString);
            JSONObject jsonMap = new JSONObject();
            // 遍历资源类型
            for(int i = 0; i < categoryArray.size(); i++){
                JSONObject fileType = (JSONObject)categoryArray.get(i);
                String categoryStr = fileType.get("title").toString();
                System.out.println("分类："+categoryStr);
                JSONArray children = (JSONArray) fileType.get("children");
                // 遍历类型下的items
                for(int j = 0; j < children.size(); j++){
                    JSONObject detail = (JSONObject) children.get(j);
                    jsonMap.clear();
                    jsonMap.put("fileType2",categoryStr);
                    if(ObjectUtil.isEmpty(detail.get("record_uri"))){
                        continue;
                    }
                    String record_uri = detail.get("record_uri").toString();
                    int index = record_uri.lastIndexOf("/");
                    String id = "";
                    if(index >= 0 && record_uri.length() > index + 1){
                        id = record_uri.substring(index + 1);
                    }
                    for(int k = 0; k < items.size(); k++) {
                        JSONObject itemDetail = (JSONObject) items.get(k);
                        String uri = itemDetail.get("uri").toString();
                        if (record_uri.equals(uri)) {
                            this.matchingField(jsonMap, itemDetail,id,client,request,true,false);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 匹配字段
     * */
    private void matchingField(JSONObject jsonMap,
                               JSONObject itemDetail,
                               String id,
                               RestHighLevelClient client,
                               IndexRequest request,
                               boolean isCnki,
                               boolean isSubjects){
        jsonMap.put("title",itemDetail.get("title"));
        jsonMap.put("subTitle",itemDetail.get("display_string"));

        JSONArray externalDocuments = (JSONArray)itemDetail.get("external_documents");
        boolean flag = this.jxExternalDocuments(jsonMap,externalDocuments);
        // 判断原文链接中是否包含cnki
        if(isCnki){
            if(flag){
                jsonMap.put("category","研究文献");
            }else{
                jsonMap.put("category","媒体报道");
            }
        }
        // 判断subject 是否是/subjects/201
        JSONArray subjects = (JSONArray)itemDetail.get("subjects");
        boolean f = this.jxSubjects(subjects);

        // 实物资源和书画作品 共用一个uri在此判断数据属于哪个类型
        if(isSubjects && !f){
            jsonMap.put("category","实物资源");
        }
        JSONArray notes = (JSONArray)itemDetail.get("notes");
        this.jxNotes(jsonMap,notes);
        JSONArray extents = (JSONArray)itemDetail.get("extents");
        this.jxExtents(jsonMap,extents);
        for(int i = 0; i < listMap.size(); i++){
            Map map = listMap.get(i);
            if(map.get("uri").equals(jsonMap.get("filePath"))){
                if(ObjectUtil.isNotEmpty(map.get("number"))){
                    String contentString = jxArchivesSpace.readJsonFile("\\\\192.168.1.27\\chenyi\\AS链接爬取数据\\"+map.get("number")+"\\content.txt");
                    jsonMap.put("content",contentString);
                }
                break;
            }
        }
        //jsonMap.put("groupYear","2021");
        this.saveES(jsonMap, id, client, request);
    }

    private boolean jxSubjects(JSONArray subjects){
        boolean f = false;
        if(subjects.size() > 0){
            StringBuffer sbf = new StringBuffer();
            for(int i = 0; i< subjects.size(); i++){
                JSONObject externalDocument = (JSONObject) subjects.get(i);
                if("/subjects/201".equals(externalDocument.get("ref"))){
                    f = true;
                    break;
                }
            }
        }
        return f;
    }

    private void jxExtents(JSONObject jsonMap,JSONArray extents){
        if(extents.size() > 0){
            StringBuffer sbf = new StringBuffer();
            JSONObject externalDocument = (JSONObject) extents.get(0);
            if(ObjectUtil.isNull(externalDocument.get("container_summary"))){
                return;
            }
            sbf.append(externalDocument.get("container_summary"));
            jsonMap.put("source",sbf.toString());
        }
    }

    private boolean jxExternalDocuments(JSONObject jsonMap,JSONArray externalDocuments){
        if(externalDocuments.size() > 0){
            StringBuffer sbf = new StringBuffer();
            JSONObject externalDocument = (JSONObject) externalDocuments.get(0);
            sbf.append(externalDocument.get("location"));
            jsonMap.put("filePath",sbf.toString());
            String[] filePaths = {sbf.toString()};
            contentFilePath.add(filePaths);
            return sbf.toString().contains("cnki");
        }
        return false;
    }

    private void jxNotes(JSONObject jsonMap,JSONArray notes){
        if(ObjectUtil.isNull(notes) || notes.size() == 0){
            return;
        }
        if(notes.size() > 1){
            for(int i = 0; i < notes.size(); i++){
                JSONObject note = (JSONObject)notes.get(i);
                if(ObjectUtil.isNull(note)){
                    return;
                }
                JSONArray subNotes = (JSONArray)note.get("subnotes");
                if(ObjectUtil.isNull(subNotes) || subNotes.size() == 0){
                    return;
                }
                JSONObject subNote1 = (JSONObject) subNotes.get(0);
                String content = subNote1.get("content").toString();
                if("摘要".equals(note.get("label"))){
                    jsonMap.put("abstract",content);
                }else if("关键词".equals(note.get("label"))){
                    jsonMap.put("keywords",content);
                }else if("专辑".equals(note.get("label"))){
                    jsonMap.put("album",content);
                }else if("专题".equals(note.get("label"))){
                    jsonMap.put("special",content);
                }else if("分类号".equals(note.get("label"))){
                    jsonMap.put("classNumber",content);
                }
            }
        }else{
            JSONObject note1 = (JSONObject) notes.get(0);
            if(ObjectUtil.isNull(note1)){
                return;
            }
            JSONArray subNotes = (JSONArray)note1.get("subnotes");
            if(ObjectUtil.isNull(subNotes) || subNotes.size() == 0){
                return;
            }
            JSONObject subNote1 = (JSONObject) subNotes.get(0);
            String content = subNote1.get("content").toString();

            String[] arrInfo = content.split("_");
            String archival = "";
            if(arrInfo.length > 0){
                archival = arrInfo[0];
            }
            // 从另一个archival种查询摘要
            if("archival".equals(archival)){
                String abstractId = arrInfo[arrInfo.length - 1];
                JSONArray notesInfo = null;
                for(int i = 0; i < itemsInfo.size(); i++){
                    JSONObject detail = (JSONObject) itemsInfo.get(i);
                    String uri = detail.get("uri").toString();
                    int index = uri.lastIndexOf("/");
                    String id = "";
                    if(index >= 0 && uri.length() > index + 1){
                        id = uri.substring(index + 1);
                    }
                    if(abstractId.equals(id)){
                        notesInfo = (JSONArray)detail.get("notes");
                        break;
                    }
                }
                this.jxNotes(jsonMap,notesInfo);
            }else{
                String[] arr1 = content.split("关键词： ");
                jsonMap.put("abstract",arr1[0].replace("摘要：",""));
                if(arr1.length > 1){
                    String[] arr2 = arr1[1].split("专辑： ");
                    if(arr2.length > 1){
                        jsonMap.put("keywords",arr2[0]);
                        String[] arr3 = arr2[1].split("专题： ");
                        if(arr3.length > 1){
                            jsonMap.put("album",arr3[0]);
                            String[] arr4 = arr3[1].split("分类号：  ");
                            if(arr4.length > 1){
                                jsonMap.put("special",arr4[0]);
                                jsonMap.put("classNumber",arr4[1]);
                            }else{
                                jsonMap.put("special",arr3[1]);
                            }
                        }else{
                            jsonMap.put("album",arr3[0]);
                        }
                    }else{
                        jsonMap.put("keywords",arr2[0]);
                    }
                }
            }
        }

    }

    public void saveES(JSONObject jsonObject,String id,RestHighLevelClient client,IndexRequest request){
        try {
            request.id(id);
            jsonObject.put("terraceSource","archivesspace");
            String jsonString = jsonObject.toString();
            request.source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            try {
//                client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private List<Map<String,Object>> importExcelContent(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("C:\\Users\\libiao\\Desktop\\复旦陈毅项目\\复旦AS数据.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","number");
        map.put("index","A");
        columnList.add(map);
        map = new HashMap<>();
        map.put("key","uri");
        map.put("index","E");
        columnList.add(map);
        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"AS链接爬取数据",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    public void exportJson(){
        String ip = "10.55.101.128";
        Integer port = 8089;
        String username = "simonhjsong";
        String password = "song800728$";
        // jxArchivesSpace.connection(ip,port,username,password);
        String session = "c6a38b7e871774295e4637a1521979a81766d6483d381a70a5b8d7323e1b4dd1";
        String url = "http://"+ip+":"+port+"/repositories/2/resources/26/tree";
        String result = null;
        try {
            result = this.doGet(url,"X-ArchivesSpace-Session",session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = JSONObject.parseObject(result);
        JSONArray categoryArray = new JSONArray();
        categoryArray.addAll((JSONArray)json.get("children"));
        CreateJsonUtil createJsonUtil = new CreateJsonUtil();

        // 导出trees
        createJsonUtil.createJsonFile("asChenYiData",categoryArray.toString(),"26Trees");
        JSONArray itemsAll = new JSONArray();
        // 导出items
        for(int i = 0; i < categoryArray.size(); i++){
            JSONObject category = (JSONObject) categoryArray.get(i);
            String id = category.get("id").toString();
            JSONArray items = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089/repositories/2/archival_objects/"+id+"/children");
            itemsAll.addAll(items);
        }
        //导出摘要items
        JSONArray abstractItems = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089/repositories/2/archival_objects/21495/children");
        createJsonUtil.createJsonFile("asChenYiData",abstractItems.toString(),"zhaiyaoitems");

        // 导出陈毅相册items
        JSONArray chenyixiangceitems = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089/repositories/2/archival_objects/20624/children");
        createJsonUtil.createJsonFile("asChenYiData",chenyixiangceitems.toString(),"陈毅相册items");

        JSONArray xiangcezhaopian = new JSONArray();
        for(int i = 0; i < chenyixiangceitems.size(); i++){
            JSONObject item = (JSONObject)chenyixiangceitems.get(i);
            JSONArray arr = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089"+item.get("uri").toString()+"/children");
            if(ObjectUtil.isNull(arr)){
                continue;
            }
            xiangcezhaopian.addAll(arr);
        }
        createJsonUtil.createJsonFile("asChenYiData",xiangcezhaopian.toString(),"相册照片items");

        // 导出书画作品和实物资源items
        JSONArray shuhuazuopinitems = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089/repositories/2/archival_objects/20617/children");
        createJsonUtil.createJsonFile("asChenYiData",shuhuazuopinitems.toString(),"书画作品和实物资源items");

        // 导出毅公著述items
        JSONArray yigongzhushuitems = this.findItemDetail(ip,port,session,"http://10.55.101.128:8089/repositories/2/archival_objects/21654/children");
        createJsonUtil.createJsonFile("asChenYiData",yigongzhushuitems.toString(),"毅公著述items");
    }

    public JSONArray findItemDetail(String ip,Integer port,String session,String url){
        try {
            String result1 = this.doGet(url,"X-ArchivesSpace-Session",session);
            JSONArray jsonArray = JSONArray.parseArray(result1);
            if(jsonArray.size() <= 1){
                return null;
            }
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doGet(String url,String headerName,String session){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(headerName,session);
        String result1 = "";
        try {
            response = httpClient.execute(httpGet);
            result1 = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result1;
    }
}
