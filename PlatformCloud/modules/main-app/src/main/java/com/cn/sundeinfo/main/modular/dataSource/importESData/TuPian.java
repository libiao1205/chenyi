package com.cn.sundeinfo.main.modular.dataSource.importESData;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.sys.config.http.HttpResult;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/6/11
 */
public class TuPian {

    public void save(ImportExcel importExcel, TuPian tuPian, RestHighLevelClient client, IndexRequest request){
        List<Map<String,Object>> listMap1 = tuPian.getExcelDataSheet1();

        List<Map<String,Object>> listMap2 = tuPian.getExcelDataSheet2();

        List<Map<String,Object>> listMap3 = tuPian.getExcelDataSheet3();

        List<Map<String,Object>> listMap4 = tuPian.getExcelDataSheet4();

        for(int i = 0; i < listMap1.size(); i++){
            Map<String,Object> map = listMap1.get(i);
            map.put("category","历史照片");
            if(ObjectUtil.isNull(map.get("imgName"))){
                continue;
            }
            File file = this.sheet1ReadImg(map.get("imgName").toString());
            String imgUri = this.addFile(file);
            if(ObjectUtil.isEmpty(imgUri)){
                continue;
            }

            map.put("filePath",imgUri);
            this.jxGroupYear(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap2.size(); i++){
            Map<String,Object> map = listMap2.get(i);
            map.put("category","历史照片");
            if(ObjectUtil.isNull(map.get("imgName"))){
                continue;
            }
            File file = this.sheet2ReadImg(map.get("imgName").toString());
            String imgUri = this.addFile(file);
            if(ObjectUtil.isEmpty(imgUri)){
                continue;
            }
            map.put("filePath",imgUri);
            this.jxGroupYear(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap3.size(); i++){
            Map<String,Object> map = listMap3.get(i);
            map.put("category","历史照片");
            if(ObjectUtil.isNull(map.get("imgName"))){
                continue;
            }
            File file = this.sheet3ReadImg(map.get("imgName").toString());
            String imgUri = this.addFile(file);
            if(ObjectUtil.isEmpty(imgUri)){
                continue;
            }
            map.put("filePath",imgUri);
            this.jxGroupYear(map);
            importExcel.saveES(map,client,request,null);
        }

        for(int i = 0; i < listMap4.size(); i++){
            Map<String,Object> map = listMap4.get(i);
            map.put("category","历史照片");
            if(ObjectUtil.isNull(map.get("imgName"))){
                continue;
            }
            File file = this.sheet4ReadImg(map.get("imgName").toString());
            String imgUri = this.addFile(file);
            if(ObjectUtil.isEmpty(imgUri)){
                continue;
            }
            map.put("filePath",imgUri);
            this.jxGroupYear(map);
            importExcel.saveES(map,client,request,null);
        }
    }

    private void jxGroupYear(Map<String,Object> map){
        if(ObjectUtil.isNull(map.get("title"))){
            return;
        }
        String title = map.get("title").toString();
        if(title.length() > 3){
            String str = title.trim().replace("\u200B","");
            if(str.contains("1943年11月")){
                System.out.printf(str +"\n" + title);
            }
            //"\u200B1943年11月，陈毅赴延安，途经新四军4师，与诸同志合影。右起：朱茂绪（时任2师团长）、张爱萍，张云逸、冯定（时任淮北区党委宣传部长）、陈毅、韦国清（时任4师9旅旅长）、张震（时任4师参谋长) 、 彭雪枫（时任4师师长）。";
            map.put("groupYear",str.substring(0,4));
        }
    }


    // \\192.168.1.27\chenyi\“陈毅专题数据库”设计素材\陈毅元帅生平视频及图片素材（已按时间命名）
    public List<Map<String,Object>> getExcelDataSheet4(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅元帅生平视频及图片素材（已按时间命名）\\陈毅元帅生平——小视频制作使用20200702 - 修改稿.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","imgName");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","D");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","E");
        columnList.add(map);


        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"Sheet1",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // \\192.168.1.27\chenyi\“陈毅专题数据库”设计素材\历次展览资料\毅公书屋在线展览热点资料\展柜1热点
    public List<Map<String,Object>> getExcelDataSheet3(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\历次展览资料\\毅公书屋在线展览热点资料\\展柜1热点\\陈毅元帅相册描述.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","imgName");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","B");
        columnList.add(map);


        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"Sheet1",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // \\192.168.1.27\chenyi\“陈毅专题数据库”设计素材\历次展览资料\毅公书屋在线展览热点资料\墙面热点\照片
    public List<Map<String,Object>> getExcelDataSheet2(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\历次展览资料\\毅公书屋在线展览热点资料\\墙面热点\\照片\\毅公书屋墙上照片描述.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","imgName");
        map.put("index","A");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","B");
        columnList.add(map);


        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"Sheet1",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 历史照片
    public List<Map<String,Object>> getExcelDataSheet1(){
        ImportExcelService importExcelService = new ImportExcelService();
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅资料整理（诗稿 相册 印章 签名 藏书等）\\陈毅元帅相册整理扫描件_20200610\\陈毅元帅相册整理20200610.xlsx");
        List<Map<String,String>> columnList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("key","imgName");
        map.put("index","B");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","abstract");
        map.put("index","C");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","title");
        map.put("index","E");
        columnList.add(map);

        map = new HashMap<>();
        map.put("key","fileType2");
        map.put("index","F");
        columnList.add(map);

        List<Map<String,Object>> listMap = null;
        try {
            listMap = importExcelService.getExcelDataByXlsx(file,"删除后共360张",2,columnList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    // 读取照片
    private File sheet4ReadImg(String imgName){
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅元帅生平视频及图片素材（已按时间命名）\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }
        return null;
    }

    // 读取照片
    private File sheet3ReadImg(String imgName){
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\历次展览资料\\毅公书屋在线展览热点资料\\展柜1热点\\1 陈毅张茜照片集（第一集）1919年-1934年\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }
        file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\历次展览资料\\毅公书屋在线展览热点资料\\展柜1热点\\2 陈毅张茜照片集（第二集）1937年-1945年\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }
        return null;
    }

    // 读取照片
    private File sheet2ReadImg(String imgName){
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\历次展览资料\\毅公书屋在线展览热点资料\\墙面热点\\照片\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }
        return null;
    }

    // 读取照片
    private File sheet1ReadImg(String imgName){
        File file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅资料整理（诗稿 相册 印章 签名 藏书等）\\陈毅元帅相册整理扫描件_20200610\\扫描照片\\1、革命之路120张\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }

        file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅资料整理（诗稿 相册 印章 签名 藏书等）\\陈毅元帅相册整理扫描件_20200610\\扫描照片\\2、建国之路120张\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }

        file = new File("\\\\192.168.1.27\\chenyi\\“陈毅专题数据库”设计素材\\陈毅资料整理（诗稿 相册 印章 签名 藏书等）\\陈毅元帅相册整理扫描件_20200610\\扫描照片\\3、家庭生活120张\\WPS图片批量处理\\"+imgName+".jpg");
        if(file.exists()){
            return file;
        }
        return null;
    }

    private String addFile(File file){
        if(ObjectUtil.isNull(file)){
            return "";
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("parentUri","http://192.168.1.27:38080/rest/chenyi/uat");
            HttpResult httpResult = this.postFile("http://192.168.1.27:38082/fedoraApi/fileUpload",file,"http://192.168.1.27:38080/rest/chenyi/uat");
            JSONObject jo = JSONObject.parseObject(httpResult.getBody());
            if(Integer.valueOf(jo.get("code").toString()) == 0){
                return "";
            }else{
                return jo.get("uri").toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将文件提交至文件服务器
     * @param file 文件对象
     * @return FileStatus 上传结果
     */
    public HttpResult postFile(String url, File file,String parentUri) throws Exception  {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.RFC6532);
        builder.addTextBody("parentUri",parentUri,contentType);
        builder.addBinaryBody("multipartFile", new FileInputStream(file),ContentType.DEFAULT_BINARY,file.getName());

        httpPost.setEntity(builder.build());
        try {
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            return new HttpResult(response.getStatusLine().getStatusCode(), result);
        } catch (IOException e) {
            e.printStackTrace();
            httpClient.close();
            return null;
        }
    }

}
