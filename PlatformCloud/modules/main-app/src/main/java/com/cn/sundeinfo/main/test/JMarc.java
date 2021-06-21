package com.cn.sundeinfo.main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/4/27
 */
public class JMarc {


    public List<Map<String, String>> jxMarc(String marc){
        //解析集合
        List<Map<String, String>> listMap = new ArrayList<>();
        String marcData[] = marc.split("\\u001D");
        String marcContents[] = marc.split("\\u001E");
        for(int index = 0; index < marcData.length; index++){
            //初始化集合
            Map<String, String> map = originalMap();
            String m = marcData[index];
            String s1 = m.split("\\u001E")[0];
            String s2=(String) s1.subSequence(5, 6);
            if(s2.equals("d")){
                System.err.println("检测到数据已废除！错误发生在：标识区状态为‘d’");
                listMap.add(map);
                continue;
            }
            if(s1.length() < 17){
                System.out.println("小于17：  "+s1);
                System.out.println("m:  "+m);
                return listMap;
            }
            int  s3=Integer.parseInt(s1.substring(12,17));
            String s4=s1.substring(24,s3-1);
            if(s4.length()%12!=0) {
                System.err.println("检测到数据格式错误！错误发生在：地址目次区");
                listMap.add(map);
                continue;
            }
            List<String> list1 = new ArrayList<String>();//目次区集合
            for(int i = 0;i<s4.length()/12;i++) {
                list1.add( s4.substring( 12*i, 12*(i+1) ) );
            }
            int k = m.split("\\u001E").length-1;//第一个数组是标识+目次区
            String[] sz = m.split("\\u001E");
            List<String> list2 = new ArrayList<String>();//记录区集合
            for(int i = 0;i<k;i++) {
                list2.add(sz[i+1]);
            }
            if(list1.size() != list2.size()) {
                System.err.println("检测到目次区与记录区数量不匹配！错误发生在：目次区或数据区，"
                        + "目次区："+list1.size()+"组，记录区："+list2.size()+"条");
                listMap.add(map);
                continue;
            }
            //匹配
            for(int i=0;i<list1.size();i++) {
                matchingMap(list1.get(i).substring(0,3),list2.get(i),map);
            }
            System.out.println("************************************************************************************");
            listMap.add(map);
        }
        return listMap;
    }
    //初始化map
    public Map<String, String> originalMap() {
        Map<String, String> map = new HashMap<>();
        map.put("isbn", null);//ISBN
        map.put("title", null);//标题
        map.put("divisionname", null);//分册名
        map.put("divisionnumber", null);//分册号
        map.put("author", null);//作者
        map.put("oneprice", null);//价格
        map.put("size", null);//尺寸
        map.put("page", null);//页数、x册
        map.put("published", null);//出版地
        map.put("clc", null);//中图分类号
        map.put("press", null);//出版社
        map.put("pubdate", null);//出版时间
        map.put("language", null);//语言
        map.put("type", null);//主题
        map.put("Remarks", null);//内容、简介
        return map;
    }
    //匹配并赋值map
    public void matchingMap(String key1,String key2,Map<String, String> map) {
        System.out.println(key1 +"--" + key2);
        switch (key1) {
            case "010":
                if( key2.split("\\u001Fa").length>1 ) {//isbn
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("isbn", f2);
                }
                if(key2.split("\\u001Fd").length>1) {//价格
                    String f1=key2.split("\\u001Fd")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("oneprice", f2);
                }
                break;
            case "101":
                if(key2.split("\\u001Fa").length>1) {//语言
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("language", f2);
                }
                break;
            case "200":
                if(key2.split("\\u001Ff").length>1) {//作者
                    String f1=key2.split("\\u001Ff")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("author", f2);
                }
                if(key2.split("\\u001Fa").length>1) {//题名
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("title", f2);
                }
                if(key2.split("\\u001Fh").length>1) {//分册号***可能不止一个'@h'
                    String f1=key2.split("\\u001Fh")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("divisionnumber", f2);
                }
                if(key2.split("\\u001Fi").length>1) {//分册名***可能不止一个'@i'
                    String f1=key2.split("\\u001Fi")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("divisionname", f2);
                }
                break;
            case "210":
                if(key2.split("\\u001Fa").length>1) {//出版地
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("published", f2);
                }
                if(key2.split("\\u001Fc").length>1) {//出版社
                    String f1=key2.split("\\u001Fc")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("press", f2);
                }
                if(key2.split("\\u001Fd").length>1) {//出版时间
                    String f1=key2.split("\\u001Fd")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("pubdate",f2);
                }
                break;
            case "215":
                if(key2.split("\\u001Fd").length>1) {//尺寸
                    String f1=key2.split("\\u001Fd")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("size", f2);
                }
                if(key2.split("\\u001Fa").length>1) {//页数***可能是层数
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("page", f2);
                }
                break;
            case "330":
                if(key2.split("\\u001Fa").length>1) {//内容
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("Remarks", f2);
                }
                break;
            case "606":
                if(key2.split("\\u001Fa").length>1) {//主题
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("type", f2);
                }
                break;
            case "690":
                if(key2.split("\\u001Fa").length>1) {//分类号
                    String f1=key2.split("\\u001Fa")[1];
                    String f2=f1.split("\\u001F")[0];
                    map.put("clc", f2);
                }
                break;
            default:
                break;
        }


    }
}
