package com.cn.sundeinfo.main.modular.dataSource.service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.DataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataSource.entity.DataSource;
import com.cn.sundeinfo.main.modular.dataSource.param.DataSourceParam;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/25
 */
public interface DataSourceService extends IService<DataSource> {

    PageResult<DataSource> page(DataSourceParam dataSourceParam);

    void add(DataSourceParam dataSourceParam);

    String addFile(String type,MultipartFile file);

    JSONObject getFile(String uri);

    void edit(DataSourceParam dataSourceParam);

    void delete(DataSourceParam dataSourceParam);

    void execExtract(DataSourceParam dataSourceParam,HttpServletResponse response);
}
