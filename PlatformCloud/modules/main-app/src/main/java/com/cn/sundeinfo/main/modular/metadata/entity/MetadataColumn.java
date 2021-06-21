/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

XiaoNuo采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改XiaoNuo源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-vue
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-vue
6.若您的项目无法满足以上几点，可申请商业授权，获取XiaoNuo商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.cn.sundeinfo.main.modular.metadata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.sundeinfo.core.pojo.base.entity.BaseEntity;
import com.cn.sundeinfo.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 元数据字段表
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("g_metadata_column")
public class MetadataColumn extends BaseEntity {


    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
     * 名称
     * */
    private String name;


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

}
