package com.cn.sundeinfo.main.modular.dataCollect.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.DataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.entity.MetadataTableDataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataCollect.param.MetadataTableDataCollectParam;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;

import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
public interface MetadataTableDataCollectService extends IService<MetadataTableDataCollect> {

    List<MetadataTableDataCollect> list(MetadataTableDataCollectParam metadataTableDataCollectParam);

    /*
    * 返回TableCode
    * */
    List<String> findMetadataTableCodeString(MetadataTableDataCollectParam metadataTableDataCollectParam);

    /*
     * 返回对象
     * */
    List<MetadataTableDataCollect> findMetadataTableCodeList(MetadataTableDataCollectParam metadataTableDataCollectParam);

    void delete(String collectCode);
}
