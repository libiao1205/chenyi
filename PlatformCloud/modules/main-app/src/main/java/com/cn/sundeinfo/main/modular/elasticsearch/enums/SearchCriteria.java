package com.cn.sundeinfo.main.modular.elasticsearch.enums;

public enum SearchCriteria implements  SearchCriteriaEnum{

    //判断条件
    MUST(0,"与"),

    SHOULD(1,"或"),

    MUSTNOT(2,"非");
    private final Integer Key;

    private final String  Value;
    SearchCriteria(Integer key, String value) {
        Key = key;
        Value = value;
    }


    @Override
    public int getKey() {
        return Key;
    }

    @Override
    public String getValue() {
        return Value;
    }


}
