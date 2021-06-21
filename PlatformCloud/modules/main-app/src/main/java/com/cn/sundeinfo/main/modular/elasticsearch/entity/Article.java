package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "article")
public class Article {
    @Id
    String code;


}
