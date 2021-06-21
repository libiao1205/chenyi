package com.cn.sundeinfo.main.modular.dataSource.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSourceColumn;
import com.cn.sundeinfo.main.modular.dataSource.param.DataSourceParam;

import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/25
 */
public interface DataSourceColumnService extends IService<DataSourceColumn> {

    List<DataSourceColumn> list(DataSourceParam dataSourceParam);

    void add(List<DataSourceColumn> dataSourceColumns);

    void edit(DataSourceParam dataSourceParam);

    boolean delete(DataSourceParam dataSourceParam);
}
