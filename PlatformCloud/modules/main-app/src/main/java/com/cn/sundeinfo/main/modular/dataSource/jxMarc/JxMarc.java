package com.cn.sundeinfo.main.modular.dataSource.jxMarc;

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析marc文件
 * @创建人 libiao
 * @创建时间 2021/4/28
 */
@Component
public class JxMarc {

    public List<Map<String, Object>> jxMarcInfo(File file, List<MetadataColumnResponse> metadataColumnResponses) {
        StringBuilder marcStr = new StringBuilder();
        String fileName = file.getName();
        try{
            BufferedReader br = null;
            if(".iso".equals(fileName.substring(fileName.lastIndexOf(".")))){
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            }else{
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            }
            String s = null;
            while((s = br.readLine())!=null){
                marcStr.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        //解析集合
        List<Map<String, Object>> listMap = new ArrayList<>();
        String marcData[] = marcStr.toString().split("\\u001D");
        for(int index = 0; index < marcData.length; index++){

            //初始化集合
            Map<String, Object> map = new HashMap<>();
            String m = marcData[index];
            String s1 = m.split("\\u001E")[0].replace("\r","").replace("\n","");
            if(s1.length() < 6){
                continue;
            }
            String s2 = (String) s1.subSequence(5, 6);
            if(s2.equals("d")){
                System.err.println("检测到数据已废除！错误发生在：标识区状态为‘d’");
                listMap.add(map);
                continue;
            }
            if(s1.length() < 17){
                return listMap;
            }
            int  s3 = Integer.parseInt(s1.substring(12,17));
            String s4 = s1.substring(24,s3-1);
            if(s4.length() % 12 != 0) {
                System.err.println("检测到数据格式错误！错误发生在：地址目次区");
                listMap.add(map);
                continue;
            }
            List<String> list1 = new ArrayList<String>();//目次区集合
            for(int i = 0; i < s4.length() / 12; i++) {
                list1.add(s4.substring( 12 * i, 12 * (i + 1) ) );
            }
            int k = m.split("\\u001E").length-1;//第一个数组是标识+目次区
            String[] sz = m.split("\\u001E");
            List<String> list2 = new ArrayList<String>();//记录区集合
            for(int i = 0; i < k; i++) {
                list2.add(sz[i+1]);
            }
            if(list1.size() != list2.size()) {
                System.err.println("检测到目次区与记录区数量不匹配！错误发生在：目次区或数据区，"
                        + "目次区：" + list1.size() + "组，记录区：" + list2.size() + "条");
                listMap.add(map);
                continue;
            }
            // 循环文件中所有目次编号
            for(int i = 0; i < list1.size(); i++) {
                String title = list1.get(i).substring(0, 3).replace(" ", "");
                String content = list2.get(i);
                this.matchingMap(title, content, map, metadataColumnResponses);
            }
            listMap.add(map);
        }
        return listMap;
    }

    //根据对象的不同进入不同的处理方法
    public void matchingTo(String title,String content,Object obj,List<MetadataColumnResponse> metadataColumnResponses) {
        if(List.class.isInstance(obj)){
            this.matchingJson(title, content,(List)obj,metadataColumnResponses);
        }else{
            this.matchingMap(title, content,(Map)obj,metadataColumnResponses);
        }
    }

    //处理普通字段
    public void matchingMap(String title,String content,Map<String, Object> map,List<MetadataColumnResponse> metadataColumnResponses) {

        // 循环元数据字段
        for(int j = 0; j < metadataColumnResponses.size(); j++){
            MetadataColumnResponse metadataColumnResponse = metadataColumnResponses.get(j);
            if(metadataColumnResponse.getChildren().size() > 0) {
                List childrenList;
                if(ObjectUtil.isNull(map.get(metadataColumnResponse.getCode()))){
                    childrenList = new ArrayList<>();
                    map.put(metadataColumnResponse.getCode(), childrenList);
                }else{
                    childrenList = (List)map.get(metadataColumnResponse.getCode());
                }
                this.matchingTo(title, content, childrenList, metadataColumnResponse.getChildren());
            }
            this.getField(content,title,metadataColumnResponse,map);
        }
    }

    // 处理json字段
    public void matchingJson(String title,String content,List list,List<MetadataColumnResponse> metadataColumnResponses) {

        Map<String, Object> newMap = new HashMap<>();

        // 循环元数据字段
        for(int j = 0; j < metadataColumnResponses.size(); j++){
            MetadataColumnResponse metadataColumnResponse = metadataColumnResponses.get(j);
            if(metadataColumnResponse.getChildren().size() > 0) {
                Map childrenMap = new HashMap<String, String>();
                list.add(childrenMap);
                this.matchingTo(title, content, childrenMap, metadataColumnResponse.getChildren());
            }
            this.getField(content,title,metadataColumnResponse,newMap);
        }

        // 存储数据
        if(newMap.size() > 0){
            list.add(newMap);
        }
    }

    private void getField(String content,String title,MetadataColumnResponse metadataColumnResponse,Map<String, Object> map){

        if(ObjectUtil.isEmpty(metadataColumnResponse.getMarcChildrenField())){
            return;
        }
        String childrenField = "\\u001F" + metadataColumnResponse.getMarcChildrenField();
        if(!title.equals(metadataColumnResponse.getMarcField().toString()) || content.split(childrenField).length < 2) {
            return;
        }

        // 字段指示符
        String indicator = content.split(childrenField)[0].split("\\u001F")[0].replace(" ","");
        String f1 = content.split(childrenField)[1];
        String f2 = f1.split("\\u001F")[0];

        // 判断是否采用了字段指示符
        if(ObjectUtil.isNotEmpty(metadataColumnResponse.getMarcIndicator()) && !metadataColumnResponse.getMarcIndicator().equals(indicator)){
            return;
        }

        // 如果该字段不是json就直接存储
        if(ObjectUtil.isNull(map.get(metadataColumnResponse.getCode()))) {
            map.put(metadataColumnResponse.getCode(), f2);
        }
    }
}
