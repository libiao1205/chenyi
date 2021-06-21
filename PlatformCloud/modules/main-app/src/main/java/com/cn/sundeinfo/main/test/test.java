package com.cn.sundeinfo.main.test;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/4/27
 */
public class test {
    public static void main(String[] args) throws IOException {
        List<Map<String, String>> listMap;
        JMarc j = new JMarc();
        //解析
        File file = new File("D:\\zhongwenshumu.marc");
        InputStream fin = new FileInputStream(file);
        // 数组bytes用于存放读取的所有字节
        int fileSize = fin.available();
        byte bytes[] = new byte[fileSize];
        fin.read(bytes);
        String marcStr = new String(bytes);
        listMap = j.jxMarc(marcStr);
//        listMap.forEach(map ->{
//            System.out.println(map);
//        });
    }
}
