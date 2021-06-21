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
package com.cn.sundeinfo.main.modular.dataGather.param;

import com.cn.sundeinfo.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *@创建人  libiao
 *@创建时间  2021/4/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataGatherParam extends BaseParam {

    /**
     * id
     */
    @NotNull(message = "主键id不能为空，请检查id字段", groups = {edit.class, detail.class, delete.class, start.class, stop.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "文件名称不能为空，请检查fileName字段", groups = {add.class, edit.class})
    private String fileName;

    /*
     * 需要采集文件路径
     * */
    private String sourceFilePath;

    /*
     * 采集结果文件路径
     * */
    private String resultFilePath;

    /*
     * 采集方式（0手动 1自动）
     * */
    @NotNull(message = "状态gatherStatus不能为空，请检查gatherStatus字段", groups = change.class)
    private Integer gatherType;


    /*
     * 采集状态（0执行中 1执行成功 2执行失败）
     * */
    private Integer gatherStatus;

    /*
     * 状态（0正常 1停用 2删除）
     * */
    @NotNull(message = "状态status不能为空，请检查status字段", groups = change.class)
    private Integer status;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数")
    private Integer sort;

    /**
     *备注
     */
    private String remark;
}
