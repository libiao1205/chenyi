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
package com.cn.sundeinfo.main.modular.dataGather.controller;

import cn.hutool.core.util.ObjectUtil;
import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.pojo.login.SysLoginUser;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.service.DataCollectService;
import com.cn.sundeinfo.main.modular.dataCollect.service.MetadataTableDataCollectService;
import com.cn.sundeinfo.main.modular.dataGather.param.DataGatherParam;
import com.cn.sundeinfo.main.modular.dataGather.service.DataGatherService;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.sys.modular.auth.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *@创建人  libiao
 *@创建时间  2021/4/22
 */
@RestController
@RequestMapping("/dataGather")
public class DataGatherController {
    
    @Resource
    DataGatherService dataGatherService;

    @Resource
    private AuthService authService;

    /**
     * 查询数据采取项分页
     */
    @Permission
    @GetMapping("/page")
    @BusinessLog(title = "数据采取项表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(DataGatherParam dataGatherParam) {
        return new SuccessResponseData(dataGatherService.page(dataGatherParam));
    }

    /**
     * 添加数据采取项
     */
    @Permission
    @PostMapping("/add")
    @BusinessLog(title = "数据采取项表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@Validated(DataGatherParam.add.class) @RequestPart("file") MultipartFile file) {
        dataGatherService.add(file);
        return new SuccessResponseData();
    }

    /**
     * 更新数据采取项
     */
    @Permission
    @PostMapping("/updateGatherStatus")
    @BusinessLog(title = "数据采取项表_更新", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateGatherStatus(@RequestBody @Validated(DataGatherParam.edit.class) DataGatherParam dataGatherParam) {
        dataGatherService.updateGatherType(dataGatherParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据采取项
     */
    @Permission
    @PostMapping("/delete")
    @BusinessLog(title = "数据采取项表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(DataGatherParam.delete.class) DataGatherParam dataGatherParam) {
        dataGatherService.delete(dataGatherParam);
        return new SuccessResponseData();
    }

    /**
     * 执行爬虫文件
     */
    @Permission
    @GetMapping("/exec")
    @BusinessLog(title = "执行已保存的数据采取项", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData exec(DataGatherParam dataGatherParam) {
        dataGatherService.exec(dataGatherParam);
        return new SuccessResponseData();
    }

    /**
     * 获取采集日志
     */
    @Permission
    @GetMapping("/getGatherLog")
    @BusinessLog(title = "执行已保存的数据采取项", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getGatherLog(DataGatherParam dataGatherParam) {
        return new SuccessResponseData(dataGatherService.getGatherLog(dataGatherParam.getId()));
    }

    /**
     * 下载采集文件
     */
    @GetMapping("/downFolder")
    @BusinessLog(title = "下载采集文件", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downFolder(@RequestParam String token, @RequestParam Long id, HttpServletResponse response) {
        if(ObjectUtil.isNotEmpty(token)){
            SysLoginUser sysLoginUser = authService.getLoginUserByToken(token);
            if(ObjectUtil.isNotNull(sysLoginUser) && ObjectUtil.isNotNull(id)){
                DataGatherParam dataGatherParam = new DataGatherParam();
                dataGatherParam.setId(id);
                dataGatherService.downFolder(dataGatherParam,response);
            }
        }
    }


}
