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

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.pojo.login.SysLoginUser;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataTableService;
import com.cn.sundeinfo.sys.modular.auth.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
@RestController
@RequestMapping("/metadata")
public class MetadataTableController {

    @Resource
    MetadataTableService metadataTableService;

    @Resource
    private AuthService authService;

    /**
     * 查询元数据表
     *@创建人  libiao
     *@创建时间  2021/4/14
     */
    @Permission
    @GetMapping("/metadataTable/page")
    @BusinessLog(title = "元数据表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(MetadataTableParam metadataTableParam) {
        return new SuccessResponseData(metadataTableService.page(metadataTableParam));
    }

    /**
     * 查询所有元数据表不分页
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @Permission
    @GetMapping("/metadataTable/list")
    @BusinessLog(title = "元数据表_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(MetadataTableParam metadataTableParam) {
        return new SuccessResponseData(metadataTableService.list(metadataTableParam));
    }

    /**
     * 查询所有元数据表分页及字段,树状
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @Permission
    @GetMapping("/metadataTable/findColumnTree")
    @BusinessLog(title = "元数据表_树状", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData findColumnTree(MetadataTableParam metadataTableParam) {
        return new SuccessResponseData(metadataTableService.findColumnTree(metadataTableParam));
    }

    /**
     * 添加元数据表
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @Permission
    @PostMapping("/metadataTable/add")
    @BusinessLog(title = "元数据表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(MetadataTableParam.add.class) MetadataTableParam metadataTableParam) {
        metadataTableService.add(metadataTableParam);
        return new SuccessResponseData();
    }

    /**
     * 更新元数据表
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @Permission
    @PostMapping("/metadataTable/edit")
    @BusinessLog(title = "元数据表_更新", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(MetadataTableParam.add.class) MetadataTableParam metadataTableParam) {
        metadataTableService.edit(metadataTableParam);
        return new SuccessResponseData();
    }

    /**
     * 删除元数据表
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @Permission
    @PostMapping("/metadataTable/delete")
    @BusinessLog(title = "元数据表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(MetadataTableParam.add.class) MetadataTableParam metadataTableParam) {
        metadataTableService.delete(metadataTableParam);
        return new SuccessResponseData();
    }

    /**
     * 导出excel元数据表
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @GetMapping("/metadataTable/exportExcel")
    @BusinessLog(title = "导出excel", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response,
                       @RequestParam String code,
                       @RequestParam String name,
                       @RequestParam String typeCode,
                       @RequestParam String token) throws IOException {
        if(ObjectUtil.isNotEmpty(token)){
            SysLoginUser sysLoginUser = authService.getLoginUserByToken(token);
            if(ObjectUtil.isNotNull(sysLoginUser)){
                MetadataTableParam metadataTableParam = new MetadataTableParam();
                if(ObjectUtil.isNotEmpty(code)){
                    metadataTableParam.setCode(code);
                }
                if(ObjectUtil.isNotEmpty(name)){
                    metadataTableParam.setCode(name);
                }
                if(ObjectUtil.isNotEmpty(typeCode)){
                    metadataTableParam.setCode(typeCode);
                }
                metadataTableService.exportTableAndColumn(metadataTableParam,response);
            }
        }
    }

    /**
     * 下载导入模板
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @GetMapping("/metadataTable/downloadTemplate")
    @BusinessLog(title = "下载导入模板", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadTemplate(HttpServletResponse response,
                       @RequestParam String token) throws IOException {
        if(ObjectUtil.isNotEmpty(token)){
            SysLoginUser sysLoginUser = authService.getLoginUserByToken(token);
            if(ObjectUtil.isNotNull(sysLoginUser)){
                metadataTableService.getTemplate(response);
            }
        }
    }

    /**
     * 导入元数据excel
     *@创建人  libiao
     *@创建时间  2021/4/21
     */
    @PostMapping("/metadataTable/importExcel")
    @BusinessLog(title = "文件信息表_上传文件", opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestPart("file") MultipartFile file) throws IOException {
        Map<String,Integer> resource = metadataTableService.importExcel(file);
        return new SuccessResponseData(resource);
    }
}
