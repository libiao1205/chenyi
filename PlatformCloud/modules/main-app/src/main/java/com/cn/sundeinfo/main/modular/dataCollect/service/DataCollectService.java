package com.cn.sundeinfo.main.modular.dataCollect.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.DataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;

import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
public interface DataCollectService extends IService<DataCollect> {

    PageResult<DataCollect> page(DataCollectParam dataCollectParam);

    List<DataCollect> listInfo();



    void add(DataCollectParam dataCollectParam);

    void edit(DataCollectParam dataCollectParam);

    void delete(DataCollectParam dataCollectParam);
}
