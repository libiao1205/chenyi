package com.cn.sundeinfo.main.modular.dataSource.service.impl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.MetadataTableDataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.service.MetadataTableDataCollectService;
import com.cn.sundeinfo.main.modular.dataSource.createJson.CreateJsonUtil;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSource;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSourceColumn;
import com.cn.sundeinfo.main.modular.dataSource.enums.DataSourceExceptionEnum;
import com.cn.sundeinfo.main.modular.dataSource.jxArchivesSpace.JxArchivesSpace;
import com.cn.sundeinfo.main.modular.dataSource.jxMarc.JxMarc;
import com.cn.sundeinfo.main.modular.dataSource.mapper.DataSourceMapper;
import com.cn.sundeinfo.main.modular.dataSource.param.DataSourceParam;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceColumnService;
import com.cn.sundeinfo.main.modular.dataSource.service.DataSourceService;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataColumnParam;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;
import com.cn.sundeinfo.main.modular.metadata.response.MetadataColumnResponse;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataColumnService;
import com.cn.sundeinfo.main.modular.metadata.service.MetadataTableService;
import com.cn.sundeinfo.sys.core.export.ExportExcel;
import com.cn.sundeinfo.sys.core.fedora.Fedora;
import com.cn.sundeinfo.sys.core.finals.SysClassConfig;
import com.cn.sundeinfo.sys.core.importExcel.ImportExcelService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * @????????? libiao
 * @???????????? 2021/4/25
 */
@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Resource
    DataSourceColumnService dataSourceColumnService;

    @Resource
    MetadataTableDataCollectService metadataTableDataCollectService;

    @Resource
    MetadataColumnService metadataColumnService;

    @Resource
    MetadataTableService metadataTableService;


    @Autowired
    ExportExcel exportExcel;

    @Autowired
    ImportExcelService importExcelService;

    @Autowired
    JxMarc jxMarc;

    @Autowired
    JxArchivesSpace jxArchivesSpace;

    @Autowired
    Fedora fedora;

    @Autowired
    CreateJsonUtil createJsonUtil;

    @Override
    public PageResult<DataSource> page(DataSourceParam dataSourceParam) {
        //????????????
        LambdaQueryWrapper<DataSource> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(dataSourceParam)) {
            //????????????????????????????????????
            if (ObjectUtil.isNotEmpty(dataSourceParam.getCode())) {
                queryWrapper.like(DataSource::getCode, dataSourceParam.getCode());
            }
            //????????????????????????????????????
            if (ObjectUtil.isNotEmpty(dataSourceParam.getName())) {
                queryWrapper.like(DataSource::getName, dataSourceParam.getName());
            }
        }
        //??????????????????
        queryWrapper.ne(DataSource::getStatus, CommonStatusEnum.DELETED.getCode());
        //????????????????????????????????????????????????
        queryWrapper.orderByAsc(DataSource::getSort);
        //????????????????????????
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Transactional
    @Override
    public void add(DataSourceParam dataSourceParam) {
        //???????????????????????????????????????????????????????????????????????????????????????
        checkParam(dataSourceParam, false);

        //???dto????????????
        DataSource DataSource = new DataSource();
        BeanUtil.copyProperties(dataSourceParam, DataSource);

        //?????????????????????
        DataSource.setStatus(CommonStatusEnum.ENABLE.getCode());

        boolean flag = this.save(DataSource);
        if(flag){
            dataSourceColumnService.add(dataSourceParam.getDataSourceColumns());
        }
    }

    @Override
    public String addFile(String type,MultipartFile multipartFile) {
        System.out.println("???????????????????????????");
        int index = multipartFile.getOriginalFilename().indexOf(".");
        if(index < 0){
            throw new ServiceException(DataSourceExceptionEnum.FILE_FORMAT_ERROR);
        }
        if(SysClassConfig.EXCEL_DATASOURCE.equals(type)){
            if(!".xls".equals(multipartFile.getOriginalFilename().substring(index)) && !".xlsx".equals(multipartFile.getOriginalFilename().substring(index))){
                throw new ServiceException(DataSourceExceptionEnum.FILE_FORMAT_ERROR);
            }
        }else{
            if(!".marc".equals(multipartFile.getOriginalFilename().substring(index)) && !".iso".equals(multipartFile.getOriginalFilename().substring(index))){
                throw new ServiceException(DataSourceExceptionEnum.FILE_FORMAT_ERROR);
            }
        }
        return fedora.addFile(multipartFile);
    }

    @Override
    public JSONObject getFile(String uri) {
        return fedora.getFile(uri);
    }

    @Override
    public void edit(DataSourceParam dataSourceParam) {

        //????????????????????????????????????????????????????????????????????????????????????
        checkParam(dataSourceParam,true);
        DataSource dataSource = this.queryDataSource(dataSourceParam);
        BeanUtil.copyProperties(dataSourceParam,dataSource);
        boolean flag = this.updateById(dataSource);
        if(flag){
            dataSourceColumnService.edit(dataSourceParam);
        }
    }
    @Override
    public void delete(DataSourceParam dataSourceParam) {
        //??????id????????????
        DataSource DataSource = this.queryDataSource(dataSourceParam);
        dataSourceColumnService.delete(dataSourceParam);

        //???????????????????????????
        DataSource.setStatus(CommonStatusEnum.DELETED.getCode());

        //????????????
        this.updateById(DataSource);
    }

    @Override
    public void execExtract(DataSourceParam dataSourceParam, HttpServletResponse response) {

        // ??????????????????????????????????????????????????????
        if(ObjectUtil.isEmpty(dataSourceParam.getDataCollectCode())){
            throw new ServiceException(DataSourceExceptionEnum.DATACOLLECT_NULL);
        }
        List<DataSourceColumn> dataSourceColumns = dataSourceColumnService.list(dataSourceParam);
        JSONObject jo = null;
        if(SysClassConfig.MARC_DATASOURCE.equals(dataSourceParam.getType()) || SysClassConfig.EXCEL_DATASOURCE.equals(dataSourceParam.getType())){
            jo = this.getFile(dataSourceColumns.get(0).getValue());
            if(ObjectUtil.isNull(jo) || jo.get("file") == null){
                throw new ServiceException(DataSourceExceptionEnum.UPLOAD_FILE_NULL);
            }
        }

        // ????????????????????????
        //??????????????????????????????????????????????????????map??????
        List<List<Map<String, Object>>> tableList = new ArrayList<>();
        File file = (File)(jo == null ? null : jo.get("file"));
        HSSFWorkbook wb = exportExcel.createWordBook();

        MetadataTableDataCollectParam metadataTableDataCollectParam = new MetadataTableDataCollectParam();
        metadataTableDataCollectParam.setCollectCode(dataSourceParam.getDataCollectCode());

        List<MetadataTableDataCollect> metadataTableDataCollects = metadataTableDataCollectService.findMetadataTableCodeList(metadataTableDataCollectParam);
        metadataTableDataCollects.forEach(metadataTableDataCollect -> {
            MetadataTableParam metadataTableParam = new MetadataTableParam();
            metadataTableParam.setCode(metadataTableDataCollect.getTableCode());
            MetadataTable metadataTable = metadataTableService.findOne(metadataTableParam);

            //?????????????????????????????????
            MetadataColumnParam metadataColumnParam = new MetadataColumnParam();
            metadataColumnParam.setTableCode(metadataTableDataCollect.getTableCode());
            metadataColumnParam.setVersion(metadataTableDataCollect.getVersion());
            List<MetadataColumnResponse> metadataColumnResponse = metadataColumnService.findByTableCodeAndVersionTree(metadataColumnParam);

            // ???????????????????????????????????????????????????
            if ((SysClassConfig.MARC_DATASOURCE.equals(dataSourceParam.getType()) && !SysClassConfig.MARC_MATADATA.equals(metadataTable.getTypeCode())) ||
                    (SysClassConfig.EXCEL_DATASOURCE.equals(dataSourceParam.getType()) && !SysClassConfig.EXCEL_MATADATA.equals(metadataTable.getTypeCode())) ||
                    (SysClassConfig.AS_DATASOURCE.equals(dataSourceParam.getType()) && !SysClassConfig.AS_MATADATA.equals(metadataTable.getTypeCode()))) {
                throw new ServiceException(DataSourceExceptionEnum.METADATATYPE_DATASOURCETYPE_UNMATCHED);
            }

            if (SysClassConfig.MARC_DATASOURCE.equals(dataSourceParam.getType())) {
                this.extractMarc(wb, tableList, metadataTable, metadataColumnResponse, file);

            } else if (SysClassConfig.EXCEL_DATASOURCE.equals(dataSourceParam.getType())) {
                this.extractExcel(wb, tableList, metadataTable, metadataColumnResponse, file);
            } else if (SysClassConfig.AS_DATASOURCE.equals(dataSourceParam.getType())) {
                this.extractAS(wb, tableList, metadataTable, metadataColumnResponse, dataSourceColumns);
            } else if (SysClassConfig.ES_DATASOURCE.equals(dataSourceParam.getType())) {
                this.extractES(dataSourceParam.getDataCollectCode(), metadataTable, metadataColumnResponse);
            }
        });
        if(SysClassConfig.ES_DATASOURCE.equals(dataSourceParam.getType())){
            createJsonUtil.downFolder(dataSourceParam.getDataCollectCode(),response);
        }else{
            exportExcel.downLoadExcel(wb,response,"????????????-"+dataSourceParam.getType());
        }
    }

    /*
     * ??????ES????????????
     * */
    private void extractES(String collectCode,MetadataTable metadataTable,List<MetadataColumnResponse> metadataColumnResponses){
        JSONObject resultJson = new JSONObject();
        String settingStr = "{\n" +
                "  \"index.max_ngram_diff\":99,\n" +
                "  \"index.max_result_window\":20000,\n" +
                "  \"analysis\": {\n" +
                "    \"analyzer\": {\n" +
                "      \"edge_ngram_analyzer\": {\n" +
                "        \"type\": \"custom\",\n" +
                "        \"char_filter\": [],\n" +
                "        \"tokenizer\": \"keyword\",\n" +
                "        \"filter\": [\n" +
                "          \"edge_ngram_filter\",\n" +
                "          \"lowercase\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"ngram_analyzer\": {\n" +
                "        \"type\": \"custom\",\n" +
                "        \"char_filter\": [],\n" +
                "        \"tokenizer\": \"keyword\",\n" +
                "        \"filter\": [\n" +
                "          \"ngram_filter\",\n" +
                "          \"lowercase\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"filter\": {\n" +
                "      \"edge_ngram_filter\": {\n" +
                "        \"type\": \"edge_ngram\",\n" +
                "        \"min_gram\": 1,\n" +
                "        \"max_gram\": 100\n" +
                "      },\n" +
                "      \"ngram_filter\": {\n" +
                "        \"type\": \"ngram\",\n" +
                "        \"min_gram\": 1,\n" +
                "        \"max_gram\": 100\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        JSONObject settingJson = JSONObject.parseObject(settingStr);
        JSONObject mapping = new JSONObject();
        JSONObject properties = new JSONObject();
        mapping.put("dynamic","false");
        this.joinJson(metadataColumnResponses,properties);
        resultJson.put("setting",settingJson);
        mapping.put("properties",properties);
        resultJson.put("mapping",mapping);
        createJsonUtil.createJsonFile(collectCode,resultJson.toJSONString(),metadataTable.getName());
    }

    private void joinJson(List<MetadataColumnResponse> metadataColumnResponses,JSONObject properties){
        metadataColumnResponses.forEach(metadataColumnResponse -> {
            JSONObject property = new JSONObject();
            String dataTypeCode = metadataColumnResponse.getDataTypeCode();
            String dataType = dataTypeCode.substring(dataTypeCode.lastIndexOf("_") + 1,dataTypeCode.length());

            if("json".equals(dataType) && metadataColumnResponse.getChildren().size() > 0){
                JSONObject propertyInfo = new JSONObject();
                property.put("properties",propertyInfo);
                properties.put(metadataColumnResponse.getEsFieldName(),property);
                this.joinJson(metadataColumnResponse.getChildren(), propertyInfo);
            }else{
                if(ObjectUtil.isNull(metadataColumnResponse.getEsFieldIndex())){
                    property.put("index",false);
                }else{
                    property.put("index", metadataColumnResponse.getEsFieldIndex() == 1);
                }
                property.put("type",dataType);
                if("date".equals(dataType)){
                    property.put("format","yyyy/MM/dd HH:mm:ss||yyyy/MM/dd||epoch_millis");
                }
                if(ObjectUtil.isNotEmpty(metadataColumnResponse.getEsFieldAnalyzer()) && !"?????????".equals(metadataColumnResponse.getEsFieldAnalyzer())){
                    property.put("analyzer",metadataColumnResponse.getEsFieldAnalyzer());
                }
                properties.put(metadataColumnResponse.getEsFieldName(),property);
            }
        });
    }

    /*
    * ??????AS??????
    * */
    private void extractAS(HSSFWorkbook wb,List<List<Map<String, Object>>> tableList,MetadataTable metadataTable,List<MetadataColumnResponse> metadataColumnResponses,List<DataSourceColumn> dataSourceColumns){

        String session = jxArchivesSpace.connection(dataSourceColumns);
        if(ObjectUtil.isEmpty(session)){
            throw new ServiceException(DataSourceExceptionEnum.CONNECTION_FAILED);
        }
        JSONArray results = jxArchivesSpace.getData(dataSourceColumns,metadataTable.getDataSourceTableName(),session);

//        String jsonString = jxArchivesSpace.readJsonFile("D:\\ncorporate_entities.json");
//        JSONObject jsonObject = JSONObject.parseObject(jsonString);
//        JSONArray results = (JSONArray)jsonObject.get("results");
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            JSONObject jsonRow = (JSONObject)results.get(i);
            Map<String,Object> map = new HashMap<>();
            jxArchivesSpace.jxAS(metadataColumnResponses,jsonRow,map);
            listMap.add(map);
        }
        tableList.add(listMap);
        this.exportExcel(wb,listMap,metadataColumnResponses,metadataTable.getName());
    }

    /*
    * ??????Excel??????
    * */
    private void extractExcel(HSSFWorkbook wb,List<List<Map<String, Object>>> tableList,MetadataTable metadataTable,List<MetadataColumnResponse> metadataColumnResponse,File file){

        List<Map<String,String>> columnList = new ArrayList<>();
        for(int i = 0; i < metadataColumnResponse.size(); i++){
            Map<String,String> map = new HashMap<>();
            map.put("index",metadataColumnResponse.get(i).getExcelFieldIndex());
            map.put("key",metadataColumnResponse.get(i).getCode());
            columnList.add(map);
        }

        try {
            //??????excel
            String filePath = file.getName();
            List<Map<String,Object>> listMap;
            if(".xls".equals(filePath.substring(filePath.lastIndexOf(".")))){
                listMap = importExcelService.getExcelDataByXls(file,metadataTable.getDataSourceTableName(),metadataTable.getExcelStartRow(),columnList);
            }else{
                listMap = importExcelService.getExcelDataByXlsx(file,metadataTable.getDataSourceTableName(),metadataTable.getExcelStartRow(),columnList);
            }
            tableList.add(listMap);
            this.exportExcel(wb,listMap,metadataColumnResponse,metadataTable.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * ??????marc??????
    * */
    private void extractMarc(HSSFWorkbook wb,List<List<Map<String, Object>>> tableList,MetadataTable metadataTable,List<MetadataColumnResponse> metadataColumnResponse,File file){

        // ??????marc??????
        List<Map<String, Object>> listMap = jxMarc.jxMarcInfo(file,metadataColumnResponse);
        tableList.add(listMap);
        this.exportExcel(wb,listMap,metadataColumnResponse,metadataTable.getName());
    }

    private void exportExcel(HSSFWorkbook wb,List<Map<String,Object>> listMap,List<MetadataColumnResponse> metadataColumnResponse,String fileName){
        // ??????????????????
        String[] titles = new String[metadataColumnResponse.size()];
        for(int i = 0; i < metadataColumnResponse.size(); i++){
            MetadataColumnResponse metadataColumnResponse1 = metadataColumnResponse.get(i);
            titles[i] = metadataColumnResponse1.getCode();
        }

        // ??????????????????
        List<String[]> listContents = new ArrayList<>();
        listMap.forEach(map -> {
            String[] contents = new String[titles.length];
            for(int j = 0; j < titles.length; j ++){
                String key = titles[j];
                if(ObjectUtil.isNotNull(map.get(key))){
                    contents[j] = map.get(key).toString();
                }else{
                    contents[j] = "";
                }
            }
            listContents.add(contents);
        });
        try {
            exportExcel.addSheet(wb,fileName,titles,listContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataSource queryDataSource(DataSourceParam dataSourceParam){
        LambdaQueryWrapper<DataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSource::getId,dataSourceParam.getId());
        return this.getOne(queryWrapper);
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @author xuyuxiang
     * @date 2020/3/31 20:56
     */
    private void checkParam(DataSourceParam dataSourceParam, boolean isExcludeSelf) {
        Long id = dataSourceParam.getId();
        String code = dataSourceParam.getCode();

        //?????????code???????????????
        LambdaQueryWrapper<DataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSource::getCode, code)
                .ne(DataSource::getStatus, CommonStatusEnum.DELETED.getCode());

        //????????????????????????????????????????????????id???????????????id
        if (isExcludeSelf) {
            queryWrapper.ne(DataSource::getId, id);
        }

        //???????????????????????????
        int countByCode = this.count(queryWrapper);

        //???????????????????????????????????????????????????????????????
        if (countByCode >= 1) {
            throw new ServiceException(DataSourceExceptionEnum.CODE_REPEAT);
        }
    }
}
