package com.cn.sundeinfo.sys.core.fedora;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.sys.config.http.HttpResult;
import com.cn.sundeinfo.sys.config.http.HttpService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/5/14
 */
@Component
public class Fedora {

    @Autowired
    HttpService httpService;

    @Value("${fedora.host}")
    private String fedoraHost;

    @Value("${fedora.parentUri}")
    private String fedoraParentUri;

    @Value("${temp.file-save-address}")
    private String fileSaveAddressTemp;

    @Value("${system.environment}")
    private String systemEnvironment;

    public String addFile(MultipartFile multipartFile){
        System.out.println("进入fedora新增方法");
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("parentUri",fedoraParentUri);
            HttpResult httpResult = httpService.postFile(fedoraHost+"/fedoraApi/fileUpload",multipartFile,fedoraParentUri);
            JSONObject jo = JSONObject.parseObject(httpResult.getBody());
            System.out.println("fedora新增结束，状态码："+jo.get("code"));
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

    public JSONObject getFile(String uri){
        if(ObjectUtil.isEmpty(uri)){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("uri",uri);
        try {
            // 获取元数据
            CloseableHttpResponse httpResponse = httpService.doPost(fedoraHost+"/fedoraApi/sourceRdfQuery",map);
            String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            if(ObjectUtil.isEmpty(result)){
                return null;
            }
            JSONObject resultJson = JSONObject.parseObject(result);
            if("0".equals(resultJson.get("code"))){
                return null;
            }
            JSONObject jo = (JSONObject)resultJson.get("data");
            jo.put("filename", URLDecoder.decode(jo.get("filename").toString()));
            httpResponse.close();
            // 获取文件流
            CloseableHttpResponse httpResponse2 = httpService.doPost(fedoraHost+"/fedoraApi/sourceQuery2",map);
            try {
                HttpEntity entityResponse = httpResponse2.getEntity();
                byte[] bytes = new byte[1024];
                String fileSavePath = "";
                String path = "";
                if("windows".equals(systemEnvironment)){
                    fileSavePath = fileSaveAddressTemp + "\\" + new Date().getTime();
                    path = fileSavePath + "\\" + jo.get("filename").toString();
                }else if("linux".equals(systemEnvironment)){
                    fileSavePath = fileSaveAddressTemp + "/" + new Date().getTime();
                    path = fileSavePath + "/" + jo.get("filename").toString();
                }
                // 文件夹路径
                new File(fileSavePath).mkdirs();
                // 文件路径
                File file = new File(path);
                OutputStream output = new FileOutputStream(file);
                InputStream inputStream = entityResponse.getContent();
                int k = inputStream.read(bytes);
                while (k != -1){
                    output.write(bytes,0,k);
                    k = inputStream.read(bytes);
                }
                jo.put("file",file);
                jo.put("fileSavePath",fileSavePath);
                inputStream.close();
                output.close();
                return jo;
            } finally {
                httpResponse2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf1.parse("2021-12-15 23:59:59.000000 +08:00");
            System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
