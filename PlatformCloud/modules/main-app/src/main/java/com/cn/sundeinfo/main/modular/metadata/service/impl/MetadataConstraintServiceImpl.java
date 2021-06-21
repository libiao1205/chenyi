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
package com.cn.sundeinfo.main.modular.metadata.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.exception.enums.StatusExceptionEnum;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataColumn;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataConstraint;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.enums.MetadataExceptionEnum;
import com.cn.sundeinfo.main.modular.metadata.mapper.MetadataColumnMapper;
import com.cn.sundeinfo.main.modular.metadata.mapper.MetadataConstraintMapper;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataConstraintParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataColumnService;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataConstraintService;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataTableService;
import com.cn.sundeinfo.sys.core.finals.SysClassConfig;
import com.cn.sundeinfo.sys.core.permission.PermissionHelper;
import com.cn.sundeinfo.sys.modular.role.entity.SysRoleMetadata;
import com.cn.sundeinfo.sys.modular.role.param.SysRoleParam;
import com.cn.sundeinfo.sys.modular.role.service.SysRoleMetadataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *@创建人  libiao
 *@创建时间  14:28
 */
@Service
public class MetadataConstraintServiceImpl extends ServiceImpl<MetadataConstraintMapper, MetadataConstraint> implements MetadataConstraintService {


    @Override
    public List<MetadataConstraint> list(MetadataConstraintParam metadataConstraintParam) {
        LambdaQueryWrapper<MetadataConstraint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataConstraint::getMetadataColumnCode,metadataConstraintParam.getMetadataColumnCode());
        return this.list(queryWrapper);
    }

    @Transactional
    @Override
    public void addConstraint(List<MetadataConstraint> metadataConstraints) {
        if(metadataConstraints.size() == 0){
            return;
        }
        try {
            LambdaQueryWrapper<MetadataConstraint> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MetadataConstraint::getMetadataColumnCode,metadataConstraints.get(0).getMetadataColumnCode());
            this.remove(queryWrapper);

            metadataConstraints.forEach(metadataConstraint -> {
                this.save(metadataConstraint);
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(MetadataExceptionEnum.COLUMN_FIELD_CONSTRAINT_FAILED);
        }
    }
}
