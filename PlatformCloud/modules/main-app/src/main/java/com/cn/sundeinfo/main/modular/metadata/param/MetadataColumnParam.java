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
package com.cn.sundeinfo.main.modular.metadata.param;

import com.cn.sundeinfo.core.pojo.base.param.BaseParam;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MetadataColumnParam extends BaseParam {

    /**
     * id
     */
    @NotNull(message = "主键id不能为空，请检查id字段", groups = {edit.class, detail.class, delete.class, start.class, stop.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称name不能为空，请检查name字段", groups = {add.class, edit.class})
    private String name;

    /*
     * 元数据类型code
     * */
    @NotBlank(message = "元数据表tableCode不能为空，请检查tableCode字段", groups = {add.class, edit.class})
    private String tableCode;

    /*
     * 父级
     * */
    private String parentCode;

    /*
     * 数据类型，关联数据词典code
     * */
    @NotBlank(message = "数据类型dataTypeCode不能为空，请检查dataTypeCode字段", groups = {add.class, edit.class})
    private String dataTypeCode;

    /*
     * 是否允许多列
     * */
    @NotNull(message = "是否允许多列moreColumn不能为空，请检查moreColumn字段", groups = {add.class, edit.class})
    private Integer moreColumn;

    /*
     * 唯一标识
     * */
    @NotBlank(message = "唯一标识code不能为空，请检查code字段", groups = {add.class, edit.class})
    private String code;


    /*
     * 版本号
     * */
    private Integer version;

    /*
     * 状态（字典 0正常 1停用 2删除）
     * */
    @NotNull(message = "状态status不能为空，请检查status字段", groups = change.class)
    private Integer status;

    /**
     * 排序
     */
    @NotNull(message = "排序sort不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Integer sort;

    @NotNull(message = "长度length不能为空，请检查length参数", groups = {add.class, edit.class})
    private Integer length;

    @NotNull(message = "是否非空isNull不能为空，请检查isNull参数", groups = {add.class, edit.class})
    private Integer isNull;

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer pageNum = 1;

    /*
    * 角色code
    * */
    private String roleCode;

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
    * 字段约束
    * */
    private List<MetadataConstraint> metadataConstraints;

}
