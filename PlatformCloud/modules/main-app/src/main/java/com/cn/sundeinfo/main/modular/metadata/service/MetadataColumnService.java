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
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataColumn;;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;

import java.util.List;


/**
 *@创建人  libiao
 *@创建时间  2021/4/14
 */
public interface MetadataColumnService extends IService<MetadataColumn> {

    PageResult<MetadataColumnResponse> pageInfo(MetadataColumnParam metadataColumnParam);

    List<MetadataColumnResponse> listInfo(MetadataColumnParam metadataColumnParam);

    MetadataColumnResponse searchPage(MetadataColumnParam metadataColumnParam);

    List<MetadataColumnResponse> childrenList(MetadataColumnParam metadataColumnParam);

    /**
     * 根据tableCode查找所有字段，返回树状
     *@创建人  libiao
     *@创建时间  2021/4/20
     */
    List<MetadataColumnResponse> findByTableCodeTree(MetadataColumnParam metadataColumnParam);

    /**
     * 根据tableCode和version查找所有字段，返回树状
     *@创建人  libiao
     *@创建时间  2021/5/11
     */
    List<MetadataColumnResponse> findByTableCodeAndVersionTree(MetadataColumnParam metadataColumnParam);

    void add(MetadataColumnParam metadataColumnParam);

    void batchAdd(List<String[]> sheetList,String parentCode,List<String[]> columnFailedList,List<String[]> columnSuccessList);

    void edit(MetadataColumnParam metadataColumnParam);

    void delete(MetadataColumnParam metadataColumnParam);
}
