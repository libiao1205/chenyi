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
package com.cn.sundeinfo.main.modular.metadata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataColumn;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
public interface MetadataTableService extends IService<MetadataTable> {

    PageResult<MetadataTable> page(MetadataTableParam metadataTableParam);

    List<MetadataTable> list(MetadataTableParam metadataTableParam);

    /**
     * 查询元数据表及表字段，返回树状
     *@创建人  libiao
     *@创建时间  2021/4/20
     */
    PageResult<MetadataTable> findColumnTree(MetadataTableParam metadataTableParam);

    /**
     * 导出元数据表及字段
     *@创建人  libiao
     *@创建时间  2021/4/20
     */
    void exportTableAndColumn(MetadataTableParam metadataTableParam, HttpServletResponse response) throws IOException;

    void getTemplate(HttpServletResponse response) throws IOException;

    Map<String,Integer> importExcel(MultipartFile file) throws IOException;

    MetadataTable findOne(MetadataTableParam metadataTableParam);

    void add(MetadataTableParam metadataTableParam);

    void edit(MetadataTableParam metadataTableParam);

    Integer updateVersion(MetadataTableParam metadataTableParam);

    void delete(MetadataTableParam metadataTableParam);
}
