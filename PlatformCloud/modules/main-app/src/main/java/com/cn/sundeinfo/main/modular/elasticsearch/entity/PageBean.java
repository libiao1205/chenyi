package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import java.util.List;
import java.util.Map;

/**
 * @Author:KangZD
 * @Date:2021/6/3
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */
public class PageBean {

    private List<Map<String,Object>>    bean ;            // 存放实体类集合
    private int  currentPage ;       // 当前页
    private int  pageSize ;          // 每页显示的条数
    private int  totalPage ;         // 总页数
    private int  totalCount ;        // 总条数
    private List<Map<String,Object>>  group_field_arr;

    public List<Map<String, Object>> getGroup_field_arr() {
        return group_field_arr;
    }

    public String searchWord;

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public void setGroup_field_arr(List<Map<String, Object>> group_field_arr) {
        this.group_field_arr = group_field_arr;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<Map<String, Object>> getBean() {
        return bean;
    }

    public void setBean(List<Map<String, Object>> bean) {
        this.bean = bean;
    }

    public int getCurrentPage() {
        return currentPage ;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage ;
    }

    public int getPageSize() {
        return pageSize ;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize ;
    }

    public int getTotalPage() {
        return (totalCount + pageSize - 1) / pageSize ;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount ;
    }

    public PageBean() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

    public PageBean(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}



