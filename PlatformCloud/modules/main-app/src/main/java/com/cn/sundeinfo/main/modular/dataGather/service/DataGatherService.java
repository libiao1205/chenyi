package com.cn.sundeinfo.main.modular.dataGather.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataCollect.entity.DataCollect;
import com.cn.sundeinfo.main.modular.dataCollect.param.DataCollectParam;
import com.cn.sundeinfo.main.modular.dataGather.entity.DataGather;
import com.cn.sundeinfo.main.modular.dataGather.param.DataGatherParam;
import com.cn.sundeinfo.main.modular.metadata.entity.MetadataTable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/23
 */
public interface DataGatherService extends IService<DataGather> {

    PageResult<DataGather> page(DataGatherParam dataGatherParam);

    List<DataGather> findAllAutoGather();

    void add(MultipartFile multipartFile);

    void updateGatherType(DataGatherParam dataGatherParam);

    void delete(DataGatherParam dataGatherParam);

    /*
    * 执行采集任务
    * */
    void exec(DataGatherParam dataGatherParam);

    void downFolder(DataGatherParam dataGatherParam, HttpServletResponse response);

    String getGatherLog(Long id);
}
