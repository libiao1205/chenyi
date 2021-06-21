package com.cn.sundeinfo.main.modular.elasticsearch.enums;

public enum SearchOper implements SearchCriteriaEnum{

    //判断条件
    MUST(0,"精准"),

    SHOULD(1,"模糊");


    private final Integer Key;

    private final String  Value;

    SearchOper(Integer key, String value) {
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
