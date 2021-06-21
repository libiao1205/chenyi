package com.cn.sundeinfo.main.modular.dataSource.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSource;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSourceColumn;
import com.cn.sundeinfo.main.modular.dataSource.mapper.DataSourceColumnMapper;
import com.cn.sundeinfo.main.modular.dataSource.mapper.DataSourceMapper;
import com.cn.sundeinfo.main.modular.dataSource.param.DataSourceParam;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceColumnService;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceService;
import com.cn.sundeinfo.sys.modular.dict.enums.SysDictDataExceptionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/25
 */
@Service
public class DataSourceColumnServiceImpl extends ServiceImpl<DataSourceColumnMapper, DataSourceColumn> implements DataSourceColumnService {

    @Override
    public List<DataSourceColumn> list(DataSourceParam dataSourceParam) {
        //构造条件
        LambdaQueryWrapper<DataSourceColumn> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(dataSourceParam)) {
            //根据数据源的编码精确查询
            if (ObjectUtil.isNotEmpty(dataSourceParam.getCode())) {
                queryWrapper.eq(DataSourceColumn::getDataSourceCode, dataSourceParam.getCode());
            }
        }
        //返回查询结果
        return this.list(queryWrapper);
    }

    @Transactional
    @Override
    public void add(List<DataSourceColumn> dataSourceColumns) {
        //将dto转为实体
        dataSourceColumns.forEach(dataSourceColumn -> {
            this.save(dataSourceColumn);
        });
    }
    /*
    * 先删除再添加
    * */
    @Transactional
    @Override
    public void edit(DataSourceParam dataSourceParam) {
        this.delete(dataSourceParam);
        this.add(dataSourceParam.getDataSourceColumns());
    }
    @Transactional
    @Override
    public boolean delete(DataSourceParam dataSourceParam) {
        LambdaQueryWrapper<DataSourceColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSourceColumn::getDataSourceCode,dataSourceParam.getCode());
        return this.remove(queryWrapper);
    }

    private DataSourceColumn queryDataSource(DataSourceParam dataSourceParam){
        LambdaQueryWrapper<DataSourceColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSourceColumn::getDataSourceCode,dataSourceParam.getCode());
        return this.getOne(queryWrapper);
    }

}
