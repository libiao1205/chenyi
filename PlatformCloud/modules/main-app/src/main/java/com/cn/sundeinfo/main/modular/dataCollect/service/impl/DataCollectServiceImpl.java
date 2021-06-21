package com.cn.sundeinfo.main.modular.dataCollect.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.DataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.entity.MetadataTableDataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.enums.DataCollectExceptionEnum;
import com.cn.sundeinfo.main.modular.dataCollect.mapper.DataCollectMapper;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.service.DataCollectService;
import com.cn.sundeinfo.main.modular.dataCollect.service.MetadataTableDataCollectService;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataTableService;
import com.cn.sundeinfo.sys.modular.dict.enums.SysDictDataExceptionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
@Service
public class DataCollectServiceImpl extends ServiceImpl<DataCollectMapper, DataCollect> implements DataCollectService {

    @Resource
    MetadataTableDataCollectService metadataTableDataCollectService;

    @Resource
    MetadataTableService metadataTableService;

    @Override
    public PageResult<DataCollect> page(DataCollectParam dataCollectParam) {
        //构造条件
        LambdaQueryWrapper<DataCollect> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(dataCollectParam)) {
            //根据数据集的编码模糊查询
            if (ObjectUtil.isNotEmpty(dataCollectParam.getCode())) {
                queryWrapper.like(DataCollect::getCode, dataCollectParam.getCode());
            }
            //根据数据集的内容模糊查询
            if (ObjectUtil.isNotEmpty(dataCollectParam.getName())) {
                queryWrapper.like(DataCollect::getName, dataCollectParam.getName());
            }
        }
        //查询未删除的
        queryWrapper.ne(DataCollect::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(DataCollect::getSort);
        //返回分页查询结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<DataCollect> listInfo() {
        LambdaQueryWrapper<DataCollect> queryWrapper = new LambdaQueryWrapper<>();
        //查询未删除的
        queryWrapper.ne(DataCollect::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(DataCollect::getSort);
        return this.list(queryWrapper);
    }

    @Transactional
    @Override
    public void add(DataCollectParam dataCollectParam) {
        //校验参数，检查是否存在重复的编码，不排除当前添加的这条记录
        checkParam(dataCollectParam, false);

        //将dto转为实体
        DataCollect dataCollect = new DataCollect();
        BeanUtil.copyProperties(dataCollectParam, dataCollect);

        //设置状态为启用
        dataCollect.setStatus(CommonStatusEnum.ENABLE.getCode());

        boolean flag = this.save(dataCollect);
        if(flag){
            this.addMetadataTableDataCollect(dataCollectParam.getTableCodes(),dataCollect.getCode());
        }
    }

    @Override
    public void edit(DataCollectParam dataCollectParam) {
        //校验参数，检查是否存在重复的编码，排除当前编辑的这条记录
        checkParam(dataCollectParam,true);

        DataCollect dataCollect = this.queryDataCollect(dataCollectParam);

        BeanUtil.copyProperties(dataCollectParam,dataCollect);

        boolean flag = this.updateById(dataCollect);
        if(flag){
            this.addMetadataTableDataCollect(dataCollectParam.getTableCodes(),dataCollect.getCode());
        }
    }
    private void addMetadataTableDataCollect(List<String> tableCodes, String collectCode){
        //先删除本数据集下的所有元数据表，然后再添加
        metadataTableDataCollectService.delete(collectCode);
        if(ObjectUtil.isNull(tableCodes)){
            return;
        }
        tableCodes.forEach(tableCode ->{
            MetadataTableParam metadataTableParam = new MetadataTableParam();
            metadataTableParam.setCode(tableCode);
            MetadataTable metadataTable = metadataTableService.findOne(metadataTableParam);
            MetadataTableDataCollect metadataTableDataCollect = new MetadataTableDataCollect();
            metadataTableDataCollect.setCollectCode(collectCode);
            metadataTableDataCollect.setTableCode(tableCode);
            metadataTableDataCollect.setVersion(metadataTable.getVersion());
            metadataTableDataCollect.setStatus(CommonStatusEnum.ENABLE.getCode());
            metadataTableDataCollectService.save(metadataTableDataCollect);
        });
    }

    @Override
    public void delete(DataCollectParam dataCollectParam) {
        //根据id查询实体
        DataCollect dataCollect = this.queryDataCollect(dataCollectParam);

        //逻辑删除，修改状态
        dataCollect.setStatus(CommonStatusEnum.DELETED.getCode());

        //更新实体
        this.updateById(dataCollect);
    }

    private DataCollect queryDataCollect(DataCollectParam dataCollectParam){
        LambdaQueryWrapper<DataCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataCollect::getId,dataCollectParam.getId());
        return this.getOne(queryWrapper);
    }

    /**
     * 校验参数，校验是否存在相同的编码
     *
     * @author xuyuxiang
     * @date 2020/3/31 20:56
     */
    private void checkParam(DataCollectParam dataCollectParam, boolean isExcludeSelf) {
        Long id = dataCollectParam.getId();
        String code = dataCollectParam.getCode();

        //构建带code的查询条件
        LambdaQueryWrapper<DataCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataCollect::getCode, code)
                .ne(DataCollect::getStatus, CommonStatusEnum.DELETED.getCode());

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapper.ne(DataCollect::getId, id);
        }

        //查询重复记录的数量
        int countByCode = this.count(queryWrapper);

        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByCode >= 1) {
            throw new ServiceException(DataCollectExceptionEnum.CODE_REPEAT);
        }
    }
}
