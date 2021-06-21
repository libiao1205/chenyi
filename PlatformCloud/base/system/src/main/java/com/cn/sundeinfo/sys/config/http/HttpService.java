package com.cn.sundeinfo.sys.config.http;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpService {

     @Autowired
     private CloseableHttpClient httpClient;

     @Autowired
     private RequestConfig config;

    private static final Log log = Log.get();

    public boolean checkUrlIsValid(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000)
                .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        boolean isValid = false;

        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200) {
                isValid = true;
            }
        } catch (Exception e) {

           log.warn("URL:" + url + " 测试连接失败，远程服务不可用");
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValid;
    }

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    public String doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(config);
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }else{
            response.close();
        }
        return null;
    }

    /**
     * 传递token，如果状态码为200，则返回body，如果不为200，则返回null
     */
    public String doGet(String url,String headerName,String headerValue) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(config);
        //装在headers
        httpGet.addHeader(headerName,headerValue);
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }else{
            response.close();
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 不带参数post请求
     */
    public CloseableHttpResponse doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

    /**
     * 带参数的post请求
     */
    public CloseableHttpResponse doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            // 把表单放到post里
            httpPost.setEntity(createUrlEncodedFormEntity(map));
        }
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return response;
    }

    /**
     * 不带参数put请求
     */
    public HttpResult doPut(String url) throws Exception {
        return this.doPut(url, null);
    }

    /**
     * 带参数的put请求
     */
    public HttpResult doPut(String url, Map<String, Object> map) throws Exception {
        // 声明httpPut请求
        HttpPut httpPut = new HttpPut(url);
        // 加入配置信息
        httpPut.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            // 把表单放到put里
            httpPut.setEntity(createUrlEncodedFormEntity(map));
        }
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    private UrlEncodedFormEntity createUrlEncodedFormEntity(Map<String, Object> map) throws UnsupportedEncodingException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        // 构造from表单对象
        return new UrlEncodedFormEntity(list, "UTF-8");
    }

    /**
     * 将文件提交至文件服务器
     * @param multipartFile 文件对象
     * @return FileStatus 上传结果
     */
    public HttpResult postFile(String url, MultipartFile multipartFile,String parentUri) throws Exception  {
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.RFC6532);
        builder.addTextBody("parentUri",parentUri,contentType);
        builder.addBinaryBody("multipartFile", multipartFile.getInputStream(),ContentType.DEFAULT_BINARY,multipartFile.getOriginalFilename());

        httpPost.setEntity(builder.build());
        try {
            response = this.httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            return new HttpResult(response.getStatusLine().getStatusCode(), result);
        } catch (IOException e) {
            e.printStackTrace();
            this.httpClient.close();
            return null;
        }
    }

}
