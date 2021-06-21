package com.cn.sundeinfo.sys.config.http;

import lombok.Data;

@Data
public class HttpResult{

    private int code;

    private String body;

    public HttpResult(int code, String body) {
        this.code = code;
        this.body = body;
    }
}