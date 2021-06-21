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
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataColumn;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.enums.MetadataExceptionEnum;
import com.cn.sundeinfo.main.modular.metadata.mapper.MetadataColumnMapper;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataColumnService;
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
public class MetadataColumnServiceImpl extends ServiceImpl<MetadataColumnMapper, MetadataColumn> implements MetadataColumnService  {

    @Resource
    MetadataTableService metadataTableService;

    @Resource
    MetadataColumnMapper metadataColumnMapper;

    @Resource
    private SysRoleMetadataService sysRoleMetadataService;

    @Override
    public PageResult<MetadataColumnResponse> pageInfo(MetadataColumnParam metadataColumnParam) {
        metadataColumnParam.setPageNum((metadataColumnParam.getPageNo() - 1) * metadataColumnParam.getPageSize());
        List<MetadataColumnResponse> metadataColumnParams = metadataColumnMapper.pageInfo(metadataColumnParam);
        int count = metadataColumnMapper.count(metadataColumnParam);
        Page page = new Page(metadataColumnParam.getPageNo(),metadataColumnParam.getPageSize(),count);
        page.setRecords(metadataColumnParams);
        return new PageResult(page);
    }

    @Override
    public List<MetadataColumnResponse> listInfo(MetadataColumnParam metadataColumnParam) {
        List<MetadataColumnResponse> metadataColumnParams = metadataColumnMapper.listInfo(metadataColumnParam);
        return metadataColumnParams;
    }

    @Override
    public MetadataColumnResponse searchPage(MetadataColumnParam metadataColumnParam) {
        List<List<Long>> lists = this.search(metadataColumnParam);
        List<List<Long>> listIds = new ArrayList<>();
        // 去除一级list中存在重复的二级节点（重复就是二级list中的一级节点重复）
        for (int i = 0; i < lists.size(); i++) {
            for (int j = i + 1; j < lists.size(); j++) {
                List<Long> ids1 = lists.get(i);
                List<Long> ids2 = lists.get(j);
                Long id1 = ids1.get(ids1.size() - 1);
                Long id2 = ids2.get(ids2.size() - 1);
                if (id1.longValue() == id2.longValue()) {
                    if(ids1.size() > ids2.size()){
                        lists.remove(j);
                    }else{
                        lists.remove(i);
                    }
                    j--;
                }
            }
        }
        List<Long> ids = new ArrayList<>();
        lists.forEach(idList ->{
            // 去除二级list重复id
            List<Long> listWithoutDuplicates = idList.stream().distinct().collect(Collectors.toList());
            listIds.add(listWithoutDuplicates);
            // 获取根节点code
            ids.add(listWithoutDuplicates.get(listWithoutDuplicates.size()-1));
        });

        //返回分页查询结果
        MetadataColumnResponse metadataColumnResponse = new MetadataColumnResponse();
        if(ids.size() > 0){
            LambdaQueryWrapper<MetadataColumn> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MetadataColumn::getId,ids);
            //根据排序升序排列，序号越小越在前
            queryWrapper.orderByAsc(MetadataColumn::getSort);
            metadataColumnResponse.setPageResult(new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper)));
        }else{
            metadataColumnResponse.setPageResult(new PageResult<>());
        }
        metadataColumnResponse.setIds(lists);
        return metadataColumnResponse;
    }

    @Override
    public List<MetadataColumnResponse> childrenList(MetadataColumnParam metadataColumnParam) {
        return metadataColumnMapper.listInfo(metadataColumnParam);
    }

    /*
    * 根据元数据表查询所有元数据，返回树状
    * */
    @Override
    public List<MetadataColumnResponse> findByTableCodeTree(MetadataColumnParam metadataColumnParam) {
        metadataColumnParam.setPageNum(null);
        metadataColumnParam.setPageSize(null);
        //查询表下的所有一级字段,pageInfo方法不传分页参数时默认查询所有
        List<MetadataColumnResponse> parentListInfo = metadataColumnMapper.pageInfo(metadataColumnParam);
        for(int i = 0; i < parentListInfo.size(); i++){
            MetadataColumnResponse parentInfo = parentListInfo.get(i);
            SysRoleParam sysRoleParam = new SysRoleParam();
            sysRoleParam.setCode(metadataColumnParam.getRoleCode());
            sysRoleParam.setMetadataColumnCode(parentInfo.getCode());
            SysRoleMetadata sysRoleMetadata = sysRoleMetadataService.findByRoleAndMetadataId(sysRoleParam);
            if(ObjectUtil.isNotNull(sysRoleMetadata)){
                parentInfo.setPermissionMap(PermissionHelper.getPermissionMapByFunction(sysRoleMetadata.getPermission()));
                parentInfo.setPermission(sysRoleMetadata.getPermission());
            }else{
                parentInfo.setPermissionMap(PermissionHelper.getPermissionMapByFunction(0));
                parentInfo.setPermission(0);
            }
            parentInfo.setChildren(this.searchColumnChildren(parentInfo,metadataColumnParam.getRoleCode()));
        }
        return parentListInfo;
    }

    private List<MetadataColumnResponse> searchColumnChildren(MetadataColumnResponse parentInfo,String roleCode){
        MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
        metadataColumnParam.setParentCode(parentInfo.getCode());
        List<MetadataColumnResponse> childrenListInfo = this.childrenList(metadataColumnParam);
        for(int i = 0; i < childrenListInfo.size(); i++){
            MetadataColumnResponse childrenInfo = childrenListInfo.get(i);
            SysRoleParam sysRoleParam = new SysRoleParam();
            sysRoleParam.setCode(roleCode);
            sysRoleParam.setMetadataColumnCode(childrenInfo.getCode());
            SysRoleMetadata sysRoleMetadata = sysRoleMetadataService.findByRoleAndMetadataId(sysRoleParam);
            if(ObjectUtil.isNotNull(sysRoleMetadata)){
                childrenInfo.setPermissionMap(PermissionHelper.getPermissionMapByFunction(sysRoleMetadata.getPermission()));
                childrenInfo.setPermission(sysRoleMetadata.getPermission());
            }else{
                childrenInfo.setPermissionMap(PermissionHelper.getPermissionMapByFunction(0));
                childrenInfo.setPermission(0);
            }
            childrenInfo.setChildren(this.searchColumnChildren(childrenInfo,roleCode));
        }
        return childrenListInfo;
    }

    /*
     * 根据元数据表和版本号查询所有元数据，返回树状
     * */
    @Override
    public List<MetadataColumnResponse> findByTableCodeAndVersionTree(MetadataColumnParam metadataColumnParam) {
        // parentCode传空值，查询所有一级字段
        metadataColumnParam.setParentCode(null);
        List<MetadataColumnResponse> parentListInfo = metadataColumnMapper.findByTableCodeAndVersion(metadataColumnParam);
        for(int i = 0; i < parentListInfo.size(); i++){
            MetadataColumnResponse parentInfo = parentListInfo.get(i);
            // 要小于等于要查询的版本
            parentInfo.setVersion(metadataColumnParam.getVersion());
            parentInfo.setChildren(this.searchColumnChildrenByTableCodeAndVersion(parentInfo));
        }
        return parentListInfo;
    }

    /*
     * 根据元数据表和版本号查询所有子元数据字段
     * */
    private List<MetadataColumnResponse> searchColumnChildrenByTableCodeAndVersion(MetadataColumnResponse parentInfo){
        MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
        metadataColumnParam.setParentCode(parentInfo.getCode());
        metadataColumnParam.setVersion(parentInfo.getVersion());
        List<MetadataColumnResponse> childrenListInfo = metadataColumnMapper.findByTableCodeAndVersion(metadataColumnParam);
        for(int i = 0; i < childrenListInfo.size(); i++){
            MetadataColumnResponse childrenInfo = childrenListInfo.get(i);
            // 要小于等于要查询的版本
            childrenInfo.setVersion(parentInfo.getVersion());
            childrenInfo.setChildren(this.searchColumnChildrenByTableCodeAndVersion(childrenInfo));
        }
        return childrenListInfo;
    }

    @Override
    public void add(MetadataColumnParam metadataColumnParam) {
        //校验参数，检查是否存在重复的编码，以及tableCode是否存在
        boolean statusByCode = checkParam(metadataColumnParam, false);

        //如果存在重复的记录，抛出异常，直接返回前端
        if (!statusByCode) {
            throw new ServiceException(MetadataExceptionEnum.COLUMN_CODE_REPEAT);
        }
        //将dto转为实体
        MetadataColumn metadataColumn = new MetadataColumn();
        BeanUtil.copyProperties(metadataColumnParam, metadataColumn);
        //版本号
        metadataColumn.setVersion(1);
        //设置状态为启用
        metadataColumn.setStatus(CommonStatusEnum.ENABLE.getCode());
        this.save(metadataColumn);
    }
    @Transactional
    @Override
    public void batchAdd(List<String[]> sheetList,String parentCode,List<String[]> columnFailedList,List<String[]> columnSuccessList){

        // 除去首行标题如果没有其他数据，证明表格是空的
        if(sheetList.size() <= 1) {
            return;
        }
        // 第一行保存的是sheetName
        String TableCode = sheetList.get(0)[0];
        for(int i = 1; i < sheetList.size(); i++){
            String [] cells = sheetList.get(i);

            // 判断读取的列数是否能对应字段所需
            if(ObjectUtil.isNull(cells) || cells.length != 12){
                columnFailedList.add(cells);
                continue;
            }
            // 校验必填字段
            if(ObjectUtil.isEmpty(cells[0]) ||
                    ObjectUtil.isEmpty(cells[1]) ||
                    ObjectUtil.isEmpty(cells[3]) ||
                    ObjectUtil.isEmpty(cells[4]) ||
                    ObjectUtil.isEmpty(cells[5]) ||
                    (!"是".equals(cells[5])) && !"否".equals(cells[5]) ||
                    ObjectUtil.isEmpty(cells[4]) ||
                    (!"是".equals(cells[6])) && !"否".equals(cells[6])){

                columnFailedList.add(cells);
                continue;
            }
            MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
            metadataColumnParam.setName(cells[0]);
            metadataColumnParam.setCode(cells[1]);
            metadataColumnParam.setParentCode("无".equals(parentCode) ? "" : parentCode);
            metadataColumnParam.setTableCode(TableCode);
            metadataColumnParam.setDataTypeCode(cells[3]);
            metadataColumnParam.setLength(Integer.valueOf(cells[4]));
            metadataColumnParam.setIsNull("是".equals(cells[5]) ? 1 : 0);
            metadataColumnParam.setMoreColumn("是".equals(cells[6]) ? 1 : 0);
            metadataColumnParam.setMarcField(ObjectUtil.isEmpty(cells[7]) ? null : Integer.parseInt(cells[7]));
            metadataColumnParam.setMarcChildrenField(cells[8]);
            metadataColumnParam.setMarcIndicator(cells[9]);
            metadataColumnParam.setExcelFieldIndex(cells[10]);
            metadataColumnParam.setAsFieldName(cells[11]);
            metadataColumnParam.setSort(100);

            //校验参数，检查是否存在重复的编码，以及tableCode是否存在
            int columnCount = this.checkColumn(metadataColumnParam,false);
            MetadataTable metadataTable = this.checkTable(TableCode);
            int tableCount = ObjectUtil.isNotNull(metadataTable) ? 1 : 0;
            if(tableCount == 0 || columnCount > 0 ) {
                columnFailedList.add(cells);
                continue;
            }

            // 如果导入的是AS元数据那么ASFieldName这列不允许为空
            if(SysClassConfig.AS_MATADATA.equals(metadataTable.getTypeCode()) &&
                    ObjectUtil.isEmpty(metadataColumnParam.getAsFieldName())){
                columnFailedList.add(cells);
                continue;
            }

            // 如果导入的是Excel元数据那么excelFieldIndex这列不允许为空
            if(SysClassConfig.EXCEL_MATADATA.equals(metadataTable.getTypeCode()) &&
                    ObjectUtil.isEmpty(metadataColumnParam.getExcelFieldIndex())){
                columnFailedList.add(cells);
                continue;
            }

            // 如果导入的是marc元数据那么marcField、marcChildrenField这两列不允许为空,json类型数据除外
            if(SysClassConfig.MARC_MATADATA.equals(metadataTable.getTypeCode()) &&
                    !SysClassConfig.MARC_MATADATA_JSON.equals(metadataColumnParam.getDataTypeCode()) &&
                    (ObjectUtil.isNull(metadataColumnParam.getMarcField()) || ObjectUtil.isEmpty(metadataColumnParam.getMarcChildrenField()))){
                columnFailedList.add(cells);
                continue;
            }
            // 首次调用方法时会传入“无”，然后一层层往下找
            // 如果表格中的数据没有父子关系或者没有跟节点（也就是父级code为“无”），这种情况下数据将无法正常导入
            if(!parentCode.equals(cells[2])){
                columnFailedList.add(cells);
                continue;
            }
            this.add(metadataColumnParam);
            columnSuccessList.add(cells);
            this.batchAdd(sheetList,cells[1],columnFailedList,columnSuccessList);
        }
    }

    @Override
    public void edit(MetadataColumnParam metadataColumnParam) {
        MetadataColumn metadataColumn = this.queryMetadataColumn(metadataColumnParam);
        MetadataTableParam metadataTableParam = new MetadataTableParam();
        metadataTableParam.setCode(metadataColumn.getTableCode());
        // 更新元数据表版本号
        Integer version = metadataTableService.updateVersion(metadataTableParam);
        if(ObjectUtil.isNotNull(version)){
            metadataColumnParam.setStatus(metadataColumn.getStatus());
            metadataColumnParam.setParentCode(metadataColumn.getParentCode());
            BeanUtil.copyProperties(metadataColumnParam,metadataColumn);
            metadataColumn.setVersion(version);
            // 清除主键
            metadataColumn.setId(null);
            this.save(metadataColumn);
        }
    }

    @Override
    public void delete(MetadataColumnParam metadataColumnParam) {
        LambdaQueryWrapper<MetadataColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataColumn::getId,metadataColumnParam.getId());
        MetadataColumn metadataColumn = new MetadataColumn();
        //逻辑删除，修改状态
        metadataColumn.setStatus(CommonStatusEnum.DELETED.getCode());
        //更新实体
        this.update(metadataColumn,queryWrapper);
    }

    private List<List<Long>> search(MetadataColumnParam metadataColumnParam){
        List<MetadataColumnResponse> metadataColumns =  metadataColumnMapper.listInfo(metadataColumnParam);
        List<List<Long>> resourceList = new ArrayList<>();
        metadataColumns.forEach(metadataColumnRespon -> {
            List<Long> list = new ArrayList<>();
            list.add(metadataColumnRespon.getId());
            this.serachParentTree(metadataColumnRespon,list);
            resourceList.add(list);
        });
        return resourceList;
    }
    private void serachParentTree(MetadataColumnResponse metadataColumnResponse,List<Long> list){
        if(ObjectUtil.isNotEmpty(metadataColumnResponse.getParentCode()) && metadataColumnResponse.getParentCode() != ""){
            MetadataColumnResponse mc = this.findByParentCode(metadataColumnResponse);
            list.add(mc.getId());
            this.serachParentTree(mc,list);
        }
    }
    private MetadataColumn queryMetadataColumn(MetadataColumnParam metadataColumnParam){
        LambdaQueryWrapper<MetadataColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataColumn::getId,metadataColumnParam.getId());
        return this.getOne(queryWrapper);
    }

    private MetadataColumnResponse findByParentCode(MetadataColumnResponse metadataColumnRespon){
        return metadataColumnMapper.findParentOne(metadataColumnRespon);
    }

    /**
     * 校验参数，校验是否存在相同的编码
     *
     * @author xuyuxiang
     * @date 2020/3/31 20:56
     */
    private boolean checkParam(MetadataColumnParam metadataColumnParam, boolean isExcludeSelf){
        int columnCount = this.checkColumn(metadataColumnParam,isExcludeSelf);
        String tableCode = metadataColumnParam.getTableCode();
        //查询tableCode重是否存在
        MetadataTable metadataTable = this.checkTable(tableCode);
        int tableCount = ObjectUtil.isNotNull(metadataTable) ? 1 : 0;

        if(tableCount == 1 && columnCount == 0){
            return true;
        }else{
            return false;
        }
    }

    private int checkColumn(MetadataColumnParam metadataColumnParam, boolean isExcludeSelf) {
        Long id = metadataColumnParam.getId();
        String code = metadataColumnParam.getCode();

        //构建带code的查询条件
        LambdaQueryWrapper<MetadataColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MetadataColumn::getCode, code)
        .ne(MetadataColumn::getStatus, CommonStatusEnum.DELETED.getCode());

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapper.ne(MetadataColumn::getId, id);
        }
        //查询重复记录的数量
        return this.count(queryWrapper);
    }

    private MetadataTable checkTable(String tableCode){
        LambdaQueryWrapper<MetadataTable> queryTableWrapper = new LambdaQueryWrapper<>();
        queryTableWrapper.eq(MetadataTable::getCode, tableCode)
                .ne(MetadataTable::getStatus, CommonStatusEnum.DELETED.getCode());
        //查询tableCode重是否存在
        return metadataTableService.getOne(queryTableWrapper);
    }
}
