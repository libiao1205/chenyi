package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import com.cn.sundeinfo.core.elasticsearch.model.PagesBase;
import com.cn.sundeinfo.core.elasticsearch.model.SearchListBase;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.ConditionalSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCitions {

    private PageBean pages;
    private List<ConditionalSearch> items = new ArrayList<ConditionalSearch>();
    private Map<String,Object> conditionsMap = new HashMap<String,Object>();
    private String[] group_field_arr = new String[]{};

    public SearchCitions(){
        if(pages==null){
            pages = new PageBean(1,10);
        }
    }

    //重载
    public SearchCitions(PageBean pages){
        this.pages=pages;
    }

    //重载
    public SearchCitions(int from,int size){
        if(pages==null){
            pages = new PageBean(from,size);
        }
    }

    public PageBean getPages() {
        return pages;
    }

    public void setPages(PageBean pages) {
        this.pages = pages;
    }

    public List<ConditionalSearch> getItems() {
        return items;
    }

    public void setItems(List<ConditionalSearch> items) {
        this.items = items;
    }

    public Map<String, Object> getConditionsMap() {
        return conditionsMap;
    }

    public void setConditionsMap(Map<String, Object> conditionsMap) {
        this.conditionsMap = conditionsMap;
    }

    public String[] getGroup_field_arr() {
        return group_field_arr;
    }

    public void setGroup_field_arr(String[] group_field_arr) {
        this.group_field_arr = group_field_arr;
    }
}
