package com.cn.sundeinfo.main.modular.dataSource.importESData;

import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.main.test.test;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/6/7
 */
public class Test {
    int i = 0;
    public static void main(String[] args) throws IOException {
//        String address = "柏林";
//        HttpClient client = HttpClients.createDefault();// 创建默认http连接
//        // HttpGet get = new HttpGet("http://api.map.baidu.com/geocoder?ak=iTSLffv8IAApAfplBzPcX9PCWh0lSl0D&output=json&coordtype=wgs84ll&address="+address);
//
//        HttpGet get = new HttpGet("http://restapi.amap.com/v3/geocode/geo?key=a51345b1c0549d6209426d850d6eb7ed&s=rsv3&city=35&address="+address);
//        HttpResponse response = client.execute(get);// 用http连接去执行get请求并且获得http响应
//        if (response.getStatusLine().getStatusCode() == 200) {
//            HttpEntity entity = response.getEntity();// 从response中取到响实体
//            String jsonStr = EntityUtils.toString(entity);// 把响应实体转成文本
//            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//            JSONObject result = (JSONObject) jsonObject.get("result");
//            JSONObject location = (JSONObject) result.get("location");
//            System.out.println(location.get("lat")+","+location.get("lng"));
//        }
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost",28001);
        // 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
        jedis.auth("WE&^skjfdh*jud");
        System.out.println("连接成功");
        Map map = new HashMap();
        map.put("name","李彪");
        jedis.hmset("map",map);
        jedis.hset("map","sex","男");
        //jedis.set("str","字符串-->李");
        //jedis.lpush("list","张三","李四");
        jedis.expire("list", 3);
        jedis.sadd("set1","李","王");
        jedis.sadd("set1","赵");

        jedis.zadd("zset",1,"男");
        jedis.zadd("zset",0,"女");
        //jedis.lset("list",0L,"李");
        System.out.println("String:"+jedis.get("str"));
        System.out.println("map:"+jedis.hmget("map","name","sex"));
        System.out.println("list:"+jedis.lrange("list",0,-1));
        System.out.println("set:"+jedis.smembers("set1"));
        System.out.println("set:"+jedis.zrange("zset",0,3));
    }
}
