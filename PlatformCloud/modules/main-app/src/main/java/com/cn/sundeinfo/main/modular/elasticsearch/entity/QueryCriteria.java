package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import java.util.List;

/**
 * @Author:KangZD
 * @Date:2021/6/10
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */
    public class QueryCriteria {


    //ID
    String id;

    //地点
    String place;

    //开始时间
    String startYear;

    //结束时间
    String endYear;

    //事件
    String category;
    //事变
    List<String> type;

    private PageBean pages;

    public PageBean getPages() {
        return pages;
    }

    public void setPages(PageBean pages) {
        this.pages = pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}
