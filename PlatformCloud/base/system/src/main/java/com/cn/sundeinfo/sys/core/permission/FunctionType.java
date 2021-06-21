package com.cn.sundeinfo.sys.core.permission;

public enum FunctionType {

    NONE(0,"none","无权限"),

    VISIBLE(1,"visible","可视"),

    READ(2,"read","预览"),

    MODIFY(4,"modify","重命名"),

    NEW(8,"new", "新增"),

    UPDATE(16, "update","上传新版本"),

    DELETE(32,"delete", "删除"),

    LINK(64,"link", "外链"),

    DOWNLOAD(128,"download", "下载");

    private final int type;

    private final String value;

    private final String comment;

    FunctionType(int type, String value ,String comment) {
        this.type = type;
        this.value = value;
        this.comment = comment;
    }

    public int type() {
        return this.type;
    }

    public String value() {
        return this.value;
    }

    public String comment() {
        return this.comment;
    }

}
