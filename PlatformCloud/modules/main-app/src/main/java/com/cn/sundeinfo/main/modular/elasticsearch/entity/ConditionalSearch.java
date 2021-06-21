package com.cn.sundeinfo.main.modular.elasticsearch.entity;


import com.cn.sundeinfo.main.modular.dataSource.importESData.SDStringUtils;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchCriteria;
import com.cn.sundeinfo.main.modular.elasticsearch.enums.SearchOper;
import org.springframework.stereotype.Component;

public class ConditionalSearch {

    //0与 1或 2非
    SearchCriteria searchCriteria;
    //0精准 1模糊
    SearchOper searchOper;

    //检索条件
    String field;

    //检索内容
    String value;


    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public SearchOper getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(SearchOper searchOper) {
        this.searchOper = searchOper;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return SDStringUtils.convertZHCNForCharacter(value);
    }

    public void setValue(String value) {
        this.value = value;
    }


}
