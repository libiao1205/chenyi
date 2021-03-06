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
package com.cn.sundeinfo.main.modular.dataCollect.controller;

import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.service.DataCollectService;
import com.cn.sundeinfo.main.modular.dataCollect.service.MetadataTableDataCollectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *@创建人  libiao
 *@创建时间  2021/4/15
 */
@RestController
@RequestMapping("/dataCollect")
public class DataCollectController {

    @Resource
    DataCollectService dataCollectService;

    @Resource
    MetadataTableDataCollectService metadataTableDataCollectService;

    /**
     * 查询数据集分页
     */
    @Permission
    @GetMapping("/page")
    @BusinessLog(title = "数据集表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(DataCollectParam dataCollectParam) {
        return new SuccessResponseData(dataCollectService.page(dataCollectParam));
    }

    /**
     * 查询数据集不分页
     */
    @Permission
    @GetMapping("/list")
    @BusinessLog(title = "数据集表_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list() {
        return new SuccessResponseData(dataCollectService.listInfo());
    }

    /**
     * 查询某个数据集下的所有元数据表
     */
    @Permission
    @GetMapping("/findByCodeMetadataTable")
    @BusinessLog(title = "数据集表_查询该数据集下所有的元数据表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData findByCodeMetadataTable(DataCollectParam dataCollectParam) {
        MetadataTableDataCollectParam metadataTableDataCollectParam = new MetadataTableDataCollectParam();
        metadataTableDataCollectParam.setCollectCode(dataCollectParam.getCode());
        return new SuccessResponseData(metadataTableDataCollectService.findMetadataTableCodeString(metadataTableDataCollectParam));
    }

    /**
     * 添加数据集
     */
    @Permission
    @PostMapping("/add")
    @BusinessLog(title = "数据集表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(DataCollectParam.add.class) DataCollectParam dataCollectParam) {
        dataCollectService.add(dataCollectParam);
        return new SuccessResponseData();
    }

    /**
     * 更新数据集
     */
    @Permission
    @PostMapping("/edit")
    @BusinessLog(title = "数据集表_更新", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(DataCollectParam.add.class) DataCollectParam dataCollectParam) {
        dataCollectService.edit(dataCollectParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据集
     */
    @Permission
    @PostMapping("/delete")
    @BusinessLog(title = "数据集表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(DataCollectParam.add.class) DataCollectParam dataCollectParam) {
        dataCollectService.delete(dataCollectParam);
        return new SuccessResponseData();
    }

}
