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
package com.cn.sundeinfo.main.modular.dataSource.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.cn.sundeinfo.core.annotion.BusinessLog;
import com.cn.sundeinfo.core.annotion.Permission;
import com.cn.sundeinfo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.dataSource.enums.DataSourceExceptionEnum;
import com.cn.sundeinfo.main.modular.dataSource.jxArchivesSpace.JxArchivesSpace;
import com.cn.sundeinfo.main.modular.dataSource.param.DataSourceParam;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceColumnService;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cn.sundeinfo.sys.config.http.HttpService;

/**
 *@创建人  libiao
 *@创建时间  2021/4/25
 */
@RestController
@RequestMapping("/dataSource")
public class DataSourceController {

    @Resource
    DataSourceService dataSourceService;

    @Resource
    DataSourceColumnService dataSourceColumnService;

    @Autowired
    HttpService httpService;

    @Autowired
    JxArchivesSpace jxArchivesSpace;

    /**
     * 查询数据源分页
     */
    @Permission
    @GetMapping("/page")
    @BusinessLog(title = "数据源表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(DataSourceParam dataSourceParam) {
        return new SuccessResponseData(dataSourceService.page(dataSourceParam));
    }

    /**
     * 根据数据源code查询字段
     */
    @Permission
    @GetMapping("/findColumnByCode")
    @BusinessLog(title = "数据源表_查询元数据字段", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData findColumnByCode(DataSourceParam dataSourceParam) {
        return new SuccessResponseData(dataSourceColumnService.list(dataSourceParam));
    }


    /**
     * 添加数据源
     */
    @Permission
    @PostMapping("/add")
    @BusinessLog(title = "数据源表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(DataSourceParam.add.class) DataSourceParam dataSourceParam) {
        dataSourceService.add(dataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 添加文件数据源，例如excel数据或者marc数据
     */
    @Permission
    @PostMapping("/addFile")
    @BusinessLog(title = "数据源表_增加文件数据源", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addFile(@RequestParam("type") String type ,
                                @RequestPart("file") MultipartFile file) {

        return new SuccessResponseData(dataSourceService.addFile(type,file));
    }

    /**
     * 添加文件数据源，例如excel数据或者marc数据
     */
    @Permission
    @GetMapping("/getFile")
    @BusinessLog(title = "数据源表_获取文件数据源", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData getFile(@RequestParam("uri") String uri) {
        return new SuccessResponseData(dataSourceService.getFile(uri));
    }

    /**
     * 更新数据源
     */
    @Permission
    @PostMapping("/edit")
    @BusinessLog(title = "数据源表_更新", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(DataSourceParam.add.class) DataSourceParam dataSourceParam) {
        dataSourceService.edit(dataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 删除数据源
     */
    @Permission
    @PostMapping("/delete")
    @BusinessLog(title = "数据源表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(DataSourceParam.add.class) DataSourceParam dataSourceParam) {
        dataSourceService.delete(dataSourceParam);
        return new SuccessResponseData();
    }

    /**
     * 执行数据源抽取
     */
    @GetMapping("/execExtract")
    @BusinessLog(title = "数据源表_执行抽取任务", opType = LogAnnotionOpTypeEnum.OTHER)
    public void execExtract(@RequestParam("code") String code, @RequestParam("type") String type, @RequestParam("dataCollectCode") String dataCollectCode, HttpServletResponse response) {
        DataSourceParam dataSourceParam = new DataSourceParam();
        dataSourceParam.setCode(code);
        dataSourceParam.setType(type);
        dataSourceParam.setDataCollectCode(dataCollectCode);
        dataSourceService.execExtract(dataSourceParam,response);
    }

    /**
     * 测试连接
     */
    @PostMapping("/testConnect")
    @BusinessLog(title = "数据源表_测试连接", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData execExtract(@RequestParam("ip") String ip,
                                    @RequestParam("port") Integer port,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password) {
        String session = jxArchivesSpace.connection(ip,port,username,password);
        return new SuccessResponseData(session);
    }

}
