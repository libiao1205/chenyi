package com.cn.sundeinfo.main.modular.dataCollect.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.main.modular.dataCollect.entity.MetadataTableDataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.mapper.MetadataTableDataCollectMapper;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.service.MetadataTableDataCollectService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
@Service
public class MetadataTableDataCollectServiceImpl extends ServiceImpl<MetadataTableDataCollectMapper, MetadataTableDataCollect> implements MetadataTableDataCollectService {

    @Override
    public List<MetadataTableDataCollect> list(MetadataTableDataCollectParam metadataTableDataCollectParam) {
        //构造条件
        LambdaQueryWrapper<MetadataTableDataCollect> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(metadataTableDataCollectParam)) {
        }
        //查询未删除的
        queryWrapper.ne(MetadataTableDataCollect::getStatus, CommonStatusEnum.DELETED.getCode());

        //返回分页查询结果
        return this.list(queryWrapper);
    }

    @Override
    public List<String> findMetadataTableCodeString(MetadataTableDataCollectParam metadataTableDataCollectParam) {
        List<MetadataTableDataCollect> metadataTableDataCollects = this.queryDataCollect(metadataTableDataCollectParam);
        List<String> resourceList = new ArrayList<>();
        metadataTableDataCollects.forEach(metadataTableDataCollect -> {
            resourceList.add(metadataTableDataCollect.getTableCode());
        });
        return resourceList;
    }

    @Override
    public List<MetadataTableDataCollect> findMetadataTableCodeList(MetadataTableDataCollectParam metadataTableDataCollectParam) {
        return this.queryDataCollect(metadataTableDataCollectParam);
    }

    @Override
    public void delete(String collectCode) {
        //构造条件
        LambdaQueryWrapper<MetadataTableDataCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataTableDataCollect::getCollectCode,collectCode);
        this.remove(queryWrapper);
    }

    private List<MetadataTableDataCollect> queryDataCollect(MetadataTableDataCollectParam metadataTableDataCollectParam){
        LambdaQueryWrapper<MetadataTableDataCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataTableDataCollect::getCollectCode,metadataTableDataCollectParam.getCollectCode());
        return this.list(queryWrapper);
    }

}
