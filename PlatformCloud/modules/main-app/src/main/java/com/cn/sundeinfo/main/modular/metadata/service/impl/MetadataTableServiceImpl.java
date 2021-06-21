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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.enums.MetadataExceptionEnum;
import com.cn.sundeinfo.main.modular.metadata.mapper.MetadataTableMapper;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataColumnService;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataTableService;
import com.cn.sundeinfo.sys.core.export.ExportExcel;
import com.cn.sundeinfo.sys.core.finals.SysClassConfig;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@创建人  libiao
 *@创建时间  14:28
 */
@Service
public class MetadataTableServiceImpl extends ServiceImpl<MetadataTableMapper, MetadataTable> implements MetadataTableService {

    @Resource
    MetadataColumnService metadataColumnService;

    @Resource
    MetadataTableService metadataTableService;

    @Autowired
    ExportExcel exportExcel;

    @Autowired
    ImportExcelService importExcelService;

    @Override
    public PageResult<MetadataTable> page(MetadataTableParam metadataTableParam) {
        //构造条件
        LambdaQueryWrapper<MetadataTable> queryWrapper = this.searchCondition(metadataTableParam);
        //返回分页查询结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    private LambdaQueryWrapper<MetadataTable> searchCondition(MetadataTableParam metadataTableParam){
        LambdaQueryWrapper<MetadataTable> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(metadataTableParam)) {
            //根据元数据类型查询
            if (ObjectUtil.isNotEmpty(metadataTableParam.getTypeCode())) {
                queryWrapper.eq(MetadataTable::getTypeCode, metadataTableParam.getTypeCode());
            }
            //根据元数据表的编码模糊查询
            if (ObjectUtil.isNotEmpty(metadataTableParam.getCode())) {
                queryWrapper.like(MetadataTable::getCode, metadataTableParam.getCode());
            }
            //根据元数据表的内容模糊查询
            if (ObjectUtil.isNotEmpty(metadataTableParam.getName())) {
                queryWrapper.like(MetadataTable::getName, metadataTableParam.getName());
            }
        }
        //查询未删除的
        queryWrapper.ne(MetadataTable::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(MetadataTable::getSort);
        return queryWrapper;
    }

    @Override
    public List<MetadataTable> list(MetadataTableParam metadataTableParam) {
        //构造条件
        LambdaQueryWrapper<MetadataTable> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(metadataTableParam)) {
            //根据元数据类型查询
            if (ObjectUtil.isNotEmpty(metadataTableParam.getTypeCode())) {
                queryWrapper.eq(MetadataTable::getTypeCode, metadataTableParam.getTypeCode());
            }
        }
        //查询未删除的
        queryWrapper.ne(MetadataTable::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(MetadataTable::getSort);
        //返回分页查询结果
        return this.list(queryWrapper);
    }

    /*
    * 查询该表下的所有字段，树状
    * */
    @Override
    public PageResult<MetadataTable> findColumnTree(MetadataTableParam metadataTableParam) {
        PageResult<MetadataTable> pageResult = this.page(metadataTableParam);
        pageResult.getRows().forEach(metadataTable ->{
            MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
            metadataColumnParam.setTableCode(metadataTable.getCode());
            metadataColumnParam.setRoleCode(metadataTableParam.getRoleCode());
            metadataTable.setChildren(metadataColumnService.findByTableCodeTree(metadataColumnParam));
        });

        return pageResult;
    }

    @Override
    public void exportTableAndColumn(MetadataTableParam metadataTableParam, HttpServletResponse response) throws IOException {
        //构造条件
        LambdaQueryWrapper<MetadataTable> queryWrapper = this.searchCondition(metadataTableParam);
        List<MetadataTable> metadataTables =  this.list(queryWrapper);
        String fileName = "元数据内容";

        HSSFWorkbook wb = exportExcel.createWordBook();
        List<String[]> tableContentList = new ArrayList<>();
        String [] tableTitles = {"*表名称","*表code","*元数据类型code","数据源表名(excelSheetName或ASApiUrl)","excel类型数据起始行"};
        metadataTables.forEach(metadataTable -> {
            String [] contens = {
                    metadataTable.getName(),
                    metadataTable.getCode(),
                    metadataTable.getTypeCode(),
                    ObjectUtil.isEmpty(metadataTable.getDataSourceTableName()) ? "" : metadataTable.getDataSourceTableName(),
                    ObjectUtil.isNull(metadataTable.getExcelStartRow()) ? "" : metadataTable.getExcelStartRow().toString()};
            tableContentList.add(contens);
        });
        try {
            exportExcel.addSheet(wb,"tables",tableTitles,tableContentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        metadataTables.forEach(metadataTable -> {
            String [] titles = {"*字段名称","*字段code","*父级code","*数据类型code","*长度","*是否非空","*是否可以多列","marc数据子字段","marc数据字段","marc字段指示符","Excel数据字段下标","AS字段"};
            List<String[]> contentList = new ArrayList<>();
            MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
            metadataColumnParam.setTableCode(metadataTable.getCode());
            metadataColumnParam.setRoleCode(metadataTableParam.getRoleCode());
            List<MetadataColumnResponse> metadataColumnResponse = metadataColumnService.findByTableCodeTree(metadataColumnParam);
            this.findChildren(metadataColumnResponse,contentList);
            try {
                exportExcel.addSheet(wb,metadataTable.getCode(),titles,contentList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exportExcel.downLoadExcel(wb,response,fileName);
    }

    @Override
    public void getTemplate(HttpServletResponse response) throws IOException {
        String [] tableTitles = {"*表名称","*表code(不能重复)","*元数据类型code","数据源表名(excelSheetName或ASApiUrl)","excel类型数据起始行"};
        String [] columnTitles = {"*字段名称","*字段code(不能重复)","*父级code(没有父级请填写无)","*数据类型code","*长度","*是否非空(请填是或否)","*是否可以多列(请填是或否)","marc数据子字段","marc数据字段","marc字段指示符","Excel数据字段下标","AS字段"};
        HSSFWorkbook wb = exportExcel.createWordBook();
        exportExcel.addSheet(wb,"tables",tableTitles,null);
        exportExcel.addSheet(wb,"columns",columnTitles,null);
        exportExcel.downLoadExcel(wb,response,"元数据导入模板");
    }

    @Transactional
    @Override
    public Map<String,Integer> importExcel(MultipartFile file) throws IOException {
        List<List<String []>> excelData = importExcelService.getExcelData(file);
        List<String[]> columnFailedList = new ArrayList<>();
        List<String[]> columnSuccessList = new ArrayList<>();
        List<String[]> tableFailedList = new ArrayList<>();
        int tableCount = 0;
        int columnCount = 0;
        for(int i = 0; i < excelData.size(); i++){
            if(i == 0){
                tableCount = excelData.get(i).size();
                this.batchAdd(excelData.get(i),tableFailedList);
            }else{
                columnCount += excelData.get(i).size() > 0 ?  (excelData.get(i).size() - 1) : 0;
                metadataColumnService.batchAdd(excelData.get(i),"无",columnFailedList,columnSuccessList);
            }
        }
        Map<String,Integer> map = new HashMap<>();
        // 减去首行的sheetName
        tableCount = tableCount > 0 ? tableCount - 1 : 0;
        map.put("tableCount",tableCount);
        map.put("columnCount",columnCount);
        map.put("tableSuccessCount",(tableCount - tableFailedList.size()));
        map.put("columnSuccessCount",columnSuccessList.size());
        return map;
    }

    private void batchAdd(List<String[]> sheetList,List<String[]> tableFailedList){

        // 除去首行标题如果没有其他数据，证明表格是空的
        if(sheetList.size() <= 1) {
            return;
        }
        for(int i = 1; i < sheetList.size(); i++){
            String [] cells = sheetList.get(i);

            // 判断读取的列数是否能对应字段所需
            if(ObjectUtil.isNull(cells) || cells.length != 5){
                tableFailedList.add(cells);
                continue;
            }
            // 校验必填字段
            if(ObjectUtil.isEmpty(cells[0]) || ObjectUtil.isEmpty(cells[1]) || ObjectUtil.isEmpty(cells[2])){
                tableFailedList.add(cells);
                continue;
            }
            MetadataTableParam metadataTableParam = new MetadataTableParam();
            metadataTableParam.setName(cells[0]);
            metadataTableParam.setCode(cells[1]);
            metadataTableParam.setTypeCode(cells[2]);
            metadataTableParam.setSort(100);
            metadataTableParam.setDataSourceTableName(ObjectUtil.isEmpty(cells[3]) ? null : cells[3]);
            metadataTableParam.setExcelStartRow(ObjectUtil.isEmpty(cells[4]) ? null : Integer.valueOf(cells[4]));

            //校验参数，检查是否存在重复的编码，不排除当前添加的这条记录
            int countByCode = checkParam(metadataTableParam, false);
            if (countByCode >= 1) {
                tableFailedList.add(cells);
                continue;
            }

            //判断元数据类型code是否正确
            if(!SysClassConfig.EXCEL_MATADATA.equals(metadataTableParam.getTypeCode()) &&
                    !SysClassConfig.MARC_MATADATA.equals(metadataTableParam.getTypeCode()) &&
                    !SysClassConfig.AS_MATADATA.equals(metadataTableParam.getTypeCode())){
                tableFailedList.add(cells);
                continue;
            }

            // 如果导入的是Excel元数据那么excelSheetName、excelStartRow这两列不允许为空
            if(SysClassConfig.EXCEL_MATADATA.equals(metadataTableParam.getTypeCode()) &&
                    (ObjectUtil.isEmpty(metadataTableParam.getDataSourceTableName()) || ObjectUtil.isNull(metadataTableParam.getExcelStartRow()))){
                tableFailedList.add(cells);
                continue;
            }
            this.add(metadataTableParam);
        }
    }

    private void findChildren(List<MetadataColumnResponse> metadataColumnResponse,List<String[]> contentList){
        metadataColumnResponse.forEach(metadataColumnRespon -> {
            String [] contents = {
                    metadataColumnRespon.getName(),
                    metadataColumnRespon.getCode(),
                    ObjectUtil.isEmpty(metadataColumnRespon.getParentCode()) ? "无" : metadataColumnRespon.getParentCode(),
                    metadataColumnRespon.getDataTypeCode(),
                    metadataColumnRespon.getLength().toString(),
                    metadataColumnRespon.getIsNull() == 0 ? "否" : "是",
                    metadataColumnRespon.getMoreColumn() == 0 ? "否" : "是",
                    ObjectUtil.isNull(metadataColumnRespon.getMarcField()) ? "" :  metadataColumnRespon.getMarcField().toString(),
                    metadataColumnRespon.getMarcChildrenField(),
                    metadataColumnRespon.getMarcIndicator(),
                    metadataColumnRespon.getExcelFieldIndex(),
                    metadataColumnRespon.getAsFieldName()};
            contentList.add(contents);
            if(ObjectUtil.isNotNull(metadataColumnRespon.getChildren())){
                this.findChildren(metadataColumnRespon.getChildren(),contentList);
            }
        });
    }

    @Override
    public MetadataTable findOne(MetadataTableParam metadataTableParam) {
        LambdaQueryWrapper<MetadataTable> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(metadataTableParam)) {
            queryWrapper.eq(MetadataTable::getCode,metadataTableParam.getCode());
        }
        //查询未删除的
        queryWrapper.ne(MetadataTable::getStatus, CommonStatusEnum.DELETED.getCode());

        return this.getOne(queryWrapper);
    }

    @Override
    public void add(MetadataTableParam metadataTableParam) {
        //校验参数，检查是否存在重复的编码，不排除当前添加的这条记录
        int countByCode = checkParam(metadataTableParam, false);
        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByCode >= 1) {
            throw new ServiceException(MetadataExceptionEnum.TABLE_CODE_REPEAT);
        }
        //将dto转为实体
        MetadataTable metadataTable = new MetadataTable();
        BeanUtil.copyProperties(metadataTableParam, metadataTable);

        //版本号
        metadataTable.setVersion(1);
        //设置状态为启用
        metadataTable.setStatus(CommonStatusEnum.ENABLE.getCode());

        this.save(metadataTable);
    }

    @Override
    public void edit(MetadataTableParam metadataTableParam) {
        //校验参数，检查是否存在重复的编码，排除当前编辑的这条记录
        int countByCode = checkParam(metadataTableParam, true);
        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByCode > 0) {
            throw new ServiceException(MetadataExceptionEnum.TABLE_CODE_REPEAT);
        }

        MetadataTable metadataTable = this.queryMetadataTable(metadataTableParam);

        BeanUtil.copyProperties(metadataTableParam,metadataTable);

        this.updateById(metadataTable);
    }

    @Override
    public Integer updateVersion(MetadataTableParam metadataTableParam) {
        MetadataTable metadataTable = this.findOne(metadataTableParam);
        if(ObjectUtil.isNotNull(metadataTable)){
            metadataTable.setVersion(metadataTable.getVersion() + 1);
            this.updateById(metadataTable);
            return metadataTable.getVersion();
        }
        return null;
    }

    @Override
    public void delete(MetadataTableParam metadataTableParam) {
        //根据id查询实体
        MetadataTable metadataTable = this.queryMetadataTable(metadataTableParam);

        //逻辑删除，修改状态
        metadataTable.setStatus(CommonStatusEnum.DELETED.getCode());

        //更新实体
        this.updateById(metadataTable);
    }

    private MetadataTable queryMetadataTable(MetadataTableParam metadataTableParam){
        LambdaQueryWrapper<MetadataTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataTable::getId,metadataTableParam.getId());
        return this.getOne(queryWrapper);
    }

    /**
     * 校验参数，校验是否存在相同的编码
     *
     * @author xuyuxiang
     * @date 2020/3/31 20:56
     */
    private int checkParam(MetadataTableParam metadataTableParam, boolean isExcludeSelf) {
        Long id = metadataTableParam.getId();
        String code = metadataTableParam.getCode();

        //构建带code的查询条件
        LambdaQueryWrapper<MetadataTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataTable::getCode, code)
                .ne(MetadataTable::getStatus, CommonStatusEnum.DELETED.getCode());

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapper.ne(MetadataTable::getId, id);
        }

        //查询重复记录的数量
        return this.count(queryWrapper);
    }

}
