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
package com.cn.sundeinfo.main.modular.dataCollect.enums;

import com.cn.sundeinfo.core.annotion.ExpEnumType;
import com.cn.sundeinfo.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.cn.sundeinfo.core.factory.ExpEnumCodeFactory;
import com.cn.sundeinfo.sys.core.consts.MainExpEnumConstant;

/**
 * 数据集异常枚举
 *@创建人  libiao
 *@创建时间  2021/4/26
 */
@ExpEnumType(module = MainExpEnumConstant.SUNDEINFO_MAIN_MODULE_EXP_CODE, kind = MainExpEnumConstant.DATA_COLLECT_EXCEPTION_ENUM)
public enum DataCollectExceptionEnum implements AbstractBaseExceptionEnum {


    CODE_REPEAT(1, "唯一编码重复，请检查code参数");

    private final Integer code;

    private final String message;

    DataCollectExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }}
