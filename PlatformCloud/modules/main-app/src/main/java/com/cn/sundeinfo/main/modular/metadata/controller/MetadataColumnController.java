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
package com.cn.sundeinfo.main.modular.metadata.controller;

import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.pojo.base.param.BaseParam;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataConstraint;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataConstraintParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataColumnService;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataConstraintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
@RestController
@RequestMapping("/metadata")
public class MetadataColumnController {

    @Resource
    MetadataColumnService metadataColumnService;

    @Resource
    MetadataConstraintService metadataConstraintService;

    /**
     * 查询元数据字段分页
     */
    @Permission
    @GetMapping("/metadataColumn/page")
    @BusinessLog(title = "元数据字段_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(MetadataColumnParam metadataColumnParam) {
        return new SuccessResponseData(metadataColumnService.pageInfo(metadataColumnParam));
    }

    /**
     * 检索元数据字段分页
     */
    @Permission
    @GetMapping("/metadataColumn/searchPage")
    @BusinessLog(title = "元数据字段_检索", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData searchPage(MetadataColumnParam metadataColumnParam) {
        return new SuccessResponseData(metadataColumnService.searchPage(metadataColumnParam));
    }

    /**
     * 根据父级ID或Code查询所有子级
     */
    @Permission
    @GetMapping("/metadataColumn/children/list")
    @BusinessLog(title = "元数据字段_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData childrenList(MetadataColumnParam metadataColumnParam) {
        return new SuccessResponseData(metadataColumnService.childrenList(metadataColumnParam));
    }

    /**
     * 添加元数据字段
     */
    @Permission
    @PostMapping("/metadataColumn/add")
    @BusinessLog(title = "元数据字段_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(MetadataColumnParam.add.class) MetadataColumnParam metadataColumnParam) {
        metadataColumnService.add(metadataColumnParam);
        return new SuccessResponseData();
    }

    /**
     * 更新元数据字段
     */
    @Permission
    @PostMapping("/metadataColumn/edit")
    @BusinessLog(title = "元数据字段_更新", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(MetadataColumnParam.edit.class) MetadataColumnParam metadataColumnParam) {
        metadataColumnService.edit(metadataColumnParam);
        return new SuccessResponseData();
    }

    /**
     * 删除元数据字段
     */
    @Permission
    @PostMapping("/metadataColumn/delete")
    @BusinessLog(title = "元数据字段_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(MetadataColumnParam.delete.class) MetadataColumnParam metadataColumnParam) {
        metadataColumnService.delete(metadataColumnParam);
        return new SuccessResponseData();
    }

    /**
     * 添加元数据字段约束
     */
    @Permission
    @PostMapping("/metadataColumn/fieldConstraint")
    @BusinessLog(title = "元数据字段_约束", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData fieldConstraint(@RequestBody @Validated(MetadataColumnParam.constraintField.class) MetadataColumnParam metadataColumnParam) {
        metadataConstraintService.addConstraint(metadataColumnParam.getMetadataConstraints());
        return new SuccessResponseData();
    }

    /**
     * 查询元数据字段约束列表
     */
    @Permission
    @GetMapping("/metadataColumn/fieldConstraintList")
    @BusinessLog(title = "元数据字段_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fieldConstraintList(MetadataConstraintParam metadataConstraintParam) {
        return new SuccessResponseData(metadataConstraintService.list(metadataConstraintParam));
    }
}
