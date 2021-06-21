package com.cn.sundeinfo.main.modular.metadata.response;

import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataColumn;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
@Data
public class MetadataColumnResponse {
    private PageResult<MetadataColumn> pageResult;

    private List<List<Long>> ids;

    /*
    * 子级
    * */
    private List<MetadataColumnResponse> children;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /*
     * 元数据类型code
     * */
    private String tableCode;

    /*
     * 父级
     * */
    private String parentCode;

    /*
     * 数据类型，关联数据词典code
     * */
    private String dataTypeCode;

    /*
     * 是否允许多列
     * */
    private Integer moreColumn;

    /*
     * 唯一标识
     * */
    private String code;


    /*
     * 版本号
     * */
    private Integer version;

    /*
     * 状态（字典 0正常 1停用 2删除）
     * */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /*
     * 长度
     * */
    private Integer length;

    /*
     * 是否非空（0：否，1：是）
     * */
    private Integer isNull;

    /*
     * marc数据字段编号（只针对marc）
     * */
    private Integer marcField;

    /*
     * marc数据子字段编号（只针对marc）
     * */
    private String marcChildrenField;

    /*
    * marc数据字段指示符（只针对marc）
    * */
    private String marcIndicator;

    /*
     * excel数据字段下标（只针对excel）
     * */
    private String excelFieldIndex;

    /*
     * AS数据字段名（只针对AS）
     * */
    private String asFieldName;

    /*
     * ES数据字段名（只针对ES）
     * */
    private String esFieldName;

    /*
     * ES数据字段索引 1:true 0:false（只针对ES）
     * */
    private Integer esFieldIndex;

    /*
     * ES数据字段分词器（只针对ES）
     * */
    private String esFieldAnalyzer;

    /*
    * 权限
    * */
    private Map<String,Boolean> permissionMap;

    private Integer permission;

    private Date createTime;

    private Long createUser;

    private Date updateTime;

    private Long updateUser;

    public MetadataColumnResponse(){

    }
    public MetadataColumnResponse(Long id,String tableCode,String parentCode,String code,String name,String dataTypeCode,Integer moreColumn,
                                Integer marcField, String marcChildrenField, String marcIndicator, String excelFieldIndex,String asFieldName,
                                  String esFieldName,Integer esFieldIndex, String esFieldAnalyzer, Integer version,Integer status,Integer sort,
                                  Integer length,Integer isNull,Date createTime, Long createUser,Date updateTime,Long updateUser){
        this.id = id;
        this.tableCode = tableCode;
        this.parentCode = parentCode;
        this.code = code;
        this.name = name;
        this.dataTypeCode = dataTypeCode;
        this.moreColumn = moreColumn;
        this.marcField = marcField;
        this.marcChildrenField = marcChildrenField;
        this.marcIndicator = marcIndicator;
        this.excelFieldIndex = excelFieldIndex;
        this.asFieldName = asFieldName;
        this.esFieldName = esFieldName;
        this.esFieldIndex = esFieldIndex;
        this.esFieldAnalyzer = esFieldAnalyzer;
        this.version = version;
        this.status = status;
        this.sort = sort;
        this.length = length;
        this.isNull = isNull;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }
}
