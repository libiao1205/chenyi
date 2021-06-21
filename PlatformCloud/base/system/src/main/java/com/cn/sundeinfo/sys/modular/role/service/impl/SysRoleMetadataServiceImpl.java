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
package com.cn.sundeinfo.sys.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.sys.modular.role.entity.SysRoleMetadata;
import com.cn.sundeinfo.sys.modular.role.mapper.SysRoleMetadataMapper;
import com.cn.sundeinfo.sys.modular.role.param.SysRoleMetadataParam;
import com.cn.sundeinfo.sys.modular.role.param.SysRoleParam;
import com.cn.sundeinfo.sys.modular.role.service.SysRoleMetadataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统资源server借口实现类
 *@创建人  libiao
 *@创建时间  2021/4/19
 */
@Service
public class SysRoleMetadataServiceImpl extends ServiceImpl<SysRoleMetadataMapper, SysRoleMetadata> implements SysRoleMetadataService {


    @Override
    public SysRoleMetadata findByRoleAndMetadataId(SysRoleParam sysRoleParam) {
        LambdaQueryWrapper<SysRoleMetadata> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMetadata::getMetadataColumnCode,sysRoleParam.getMetadataColumnCode());
        queryWrapper.eq(SysRoleMetadata::getRoleCode,sysRoleParam.getCode());
        return this.getOne(queryWrapper);
    }

    @Transactional
    @Override
    public void grantMetadataColumn(SysRoleParam sysRoleParam) {
        LambdaQueryWrapper<SysRoleMetadata> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMetadata::getRoleCode,sysRoleParam.getCode());
        this.remove(queryWrapper);
        sysRoleParam.getGrantMetadataPermissionList().forEach(sysRoleMetadataParam -> {
            SysRoleMetadata sysRoleMetadata = new SysRoleMetadata();
            sysRoleMetadata.setRoleCode(sysRoleParam.getCode());
            sysRoleMetadata.setMetadataColumnCode(sysRoleMetadataParam.getMetadataColumnCode());
            sysRoleMetadata.setPermission(sysRoleMetadataParam.getPermission());
            this.save(sysRoleMetadata);
        });
    }

    @Override
    public List<SysRoleMetadata> getListByRoleCodes(List codeList) {
        QueryWrapper<SysRoleMetadata> wrapper = new QueryWrapper<SysRoleMetadata>();
        wrapper.in("role_code", codeList);
        List<SysRoleMetadata> roleMetadataList = baseMapper.selectList(wrapper);
        return roleMetadataList;
    }

}
