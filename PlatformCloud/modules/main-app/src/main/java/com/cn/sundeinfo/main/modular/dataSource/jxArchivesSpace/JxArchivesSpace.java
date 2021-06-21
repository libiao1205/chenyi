package com.cn.sundeinfo.main.modular.dataSource.jxArchivesSpace;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSourceColumn;
import com.cn.sundeinfo.main.modular.dataSource.enums.DataSourceExceptionEnum;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import com.cn.sundeinfo.sys.config.http.HttpService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/5/12
 */
@Component
public class JxArchivesSpace {

    @Autowired
    HttpService httpService;

    // 判断json字段是什么类型，区分json和数组
    private void jxASTo(List<MetadataColumnResponse> metadataColumnResponses,Object jsonRow,Object obj){
        // 判断两点
        // 1：判断数据源是什么类型是数组还是普通字段，
        // 2：判断元数据是什么类型，是json还是普通字段
        if(JSONArray.class.isInstance(jsonRow)){
            // 数据源是数组的情况下循环数据源
            for(int i = 0; i < ((JSONArray) jsonRow).size(); i++){
                JSONObject json = (JSONObject)((JSONArray) jsonRow).get(i);
                // 判断元数据的类型，进入不同的处理方法，
                // list：处理json,map：处理普通字段
                if(List.class.isInstance(obj)){
                    this.jxASSaveJson(metadataColumnResponses,json,(List)obj);
                }else{
                    this.jxAS(metadataColumnResponses,json,(Map)obj);
                }
            }
        }else{
            JSONObject json = (JSONObject) jsonRow;
            if(List.class.isInstance(obj)){
                this.jxASSaveJson(metadataColumnResponses,json,(List)obj);
            }else{
                this.jxAS(metadataColumnResponses,json,(Map)obj);
            }
        }
    }
    // 递归抽取数据存储普通字段
    public void jxAS(List<MetadataColumnResponse> metadataColumnResponses,JSONObject jsonRow,Map<String,Object> map){
        metadataColumnResponses.forEach(metadataColumnResponse -> {
            Object fields = jsonRow.get(metadataColumnResponse.getAsFieldName());
            // 如果该字段是json就用listMap存储
            if((JSONArray.class.isInstance(fields) || JSONObject.class.isInstance(fields)) && metadataColumnResponse.getChildren().size() > 0){
                List childrenList = new ArrayList<>();
                map.put(metadataColumnResponse.getCode(),childrenList);
                this.jxASTo(metadataColumnResponse.getChildren(), fields, childrenList);
            }
            // 如果该字段不是json就直接存储
            if(ObjectUtil.isNull(map.get(metadataColumnResponse.getCode()))){
                map.put(metadataColumnResponse.getCode(),fields);
            }
        });

    }

    // 递归抽取数据存储数组
    public void jxASSaveJson(List<MetadataColumnResponse> metadataColumnResponses,JSONObject jsonRow,List list){
        Map<String, Object> newMap = new HashMap<>();
        metadataColumnResponses.forEach(metadataColumnResponse -> {
            Object fields = jsonRow.get(metadataColumnResponse.getAsFieldName());
            // 数组中如果又出现json字段（数组嵌套数组），就调用抽取json的方法
            if((JSONArray.class.isInstance(fields) || JSONObject.class.isInstance(fields)) && metadataColumnResponse.getChildren().size() > 0){
                Map childrenMap = new HashMap<String, String>();
                list.add(childrenMap);
                this.jxASTo(metadataColumnResponse.getChildren(), fields, childrenMap);
            }
            // 如果不是json字段，就存储到临时的newMap中,循环结束后添加到对象中
            newMap.put(metadataColumnResponse.getCode(),fields);
        });
        // 存储数据
        if(newMap.size() > 0){
            list.add(newMap);
        }
    }

    //读取json文件
    public String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //连接as
    public String connection(List<DataSourceColumn> dataSourceColumns){
        if(dataSourceColumns.size() < 4){
            throw new ServiceException(DataSourceExceptionEnum.CONNECTION_FAILED);
        }
        String ip = "";
        Integer port = null;
        String username = "";
        String password = "";
        for(int i = 0; i < dataSourceColumns.size(); i++){
            DataSourceColumn dataSourceColumn = dataSourceColumns.get(i);
            if("ip".equals(dataSourceColumn.getCode())){
                ip = dataSourceColumn.getValue();
            }else if("port".equals(dataSourceColumn.getCode())){
                port = Integer.valueOf(dataSourceColumn.getValue());
            }else if("username".equals(dataSourceColumn.getCode())){
                username = dataSourceColumn.getValue();
            }else if("password".equals(dataSourceColumn.getCode())){
                password = dataSourceColumn.getValue();
            }
        }
        return this.connection(ip,port,username,password);

    }

    //连接as
    public String connection(String ip,Integer port,String username,String password){
        CloseableHttpResponse response;
        if(ObjectUtil.isEmpty(ip) || ObjectUtil.isEmpty(port) || ObjectUtil.isEmpty(username) || ObjectUtil.isEmpty(password)){
            throw new ServiceException(DataSourceExceptionEnum.CONNECTION_FAILED);
        }
        try {
            response = httpService.doPost("http://" + ip + ":" + port + "/users/" + username + "/login?password=" + password);
            int code = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject json = JSONObject.parseObject(result);
            if(json.size() <= 1 || code != 200){
                throw new ServiceException(DataSourceExceptionEnum.CONNECTION_FAILED);
            }
            return json.get("session").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取数据
    public JSONArray getData(List<DataSourceColumn> dataSourceColumns,String api,String session){

        String ip = "";
        String port = "";
        for(int i = 0; i < dataSourceColumns.size(); i++){
            DataSourceColumn dataSourceColumn = dataSourceColumns.get(i);
            if("ip".equals(dataSourceColumn.getCode())){
                ip = dataSourceColumn.getValue();
            }else if("port".equals(dataSourceColumn.getCode())){
                port = dataSourceColumn.getValue();
            }
        }
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject json = this.executeGetData("http://" + ip + ":" + port + api + "?page=1&page_size=100",session);
            int totalPage = (int) json.get("last_page");

            jsonArray.addAll((JSONArray)json.get("results"));
            for(int i = 2; i <= totalPage; i++){
                String url = "http://" + ip+ ":" + port + api + "?page=" + i + "&page_size=100";
                JSONObject json1 = this.executeGetData(url,session);
                jsonArray.addAll((JSONArray)json1.get("results"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private JSONObject executeGetData(String url,String session){
        String result = null;
        try {
            result = httpService.doGet(url,"X-ArchivesSpace-Session",session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ObjectUtil.isEmpty(result)){
            throw new ServiceException(DataSourceExceptionEnum.GET_DATA_FAILED);
        }
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }



   /* public static void main(String[] args) {
        //构建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;

        String ip = "10.55.101.128";
        String port = "8089";
        String username = "simonhjsong";
        String password = "song800728$";
        int currPage = 1;
        String url = "http://"+ip+":"+port+"/agents/corporate_entities?page="+currPage+"&page_size=100";
//        HttpPost httpPost = new HttpPost("http://"+ip+":"+port+"/users/"+username+"/login?password="+password);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("X-ArchivesSpace-Session","f96c37ef5e17aeb04972fd938a1c962b8423a4477743afc3ff1274494acf9721");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.RFC6532);
        try {
            response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject json = JSONObject.parseObject(result);
            if(json.size() <= 1){
                return;
            }
            int totalPage = (int) json.get("last_page");
            if(totalPage > 1){

            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll((JSONArray)json.get("results"));
            for(currPage = 2; currPage <= totalPage; currPage++){
                httpGet = new HttpGet(url);
                httpGet.addHeader("X-ArchivesSpace-Session","f96c37ef5e17aeb04972fd938a1c962b8423a4477743afc3ff1274494acf9721");
                response = httpClient.execute(httpGet);
                String result1 = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject json1 = JSONObject.parseObject(result1);
                if(json1.size() <= 1){
                    continue;
                }
                jsonArray.addAll((JSONArray)json1.get("results"));
            }
            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
