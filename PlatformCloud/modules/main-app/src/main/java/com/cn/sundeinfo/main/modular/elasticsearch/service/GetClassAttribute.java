package com.cn.sundeinfo.main.modular.elasticsearch.service;

import java.lang.reflect.Field;

/**
 * @Author:KangZD
 * @Date:2021/6/8
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service
 * @version:1.0
 */
public class GetClassAttribute {


    /**
    *
    * 返回这个类下的所有属性名称
    * */
    public String[] GetClassAttribute(Object Class){
        //获取类的所有属性（不包括父类）
        Field[] fields = Class.getClass().getDeclaredFields();
        String[] attributeArr = new String[fields.length];

        //判断是否为空并且循环赋值
        if(fields.length>0){
            for(int i = 0;i<fields.length;i++){
               attributeArr[i] = fields[i].getName();
            }
        }
        return attributeArr;
    }

}
