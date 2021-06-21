package com.cn.sundeinfo.main.modular.dataGather.tasks;

import com.cn.sundeinfo.core.timer.TimerTaskRunner;
import com.cn.sundeinfo.main.modular.dataGather.entity.DataGather;
import com.cn.sundeinfo.main.modular.dataGather.param.DataGatherParam;
import com.cn.sundeinfo.main.modular.dataGather.service.DataGatherService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/5/14
 */
@Component
public class DataGatherTaskRunner implements TimerTaskRunner {

    @Resource
    DataGatherService dataGatherService;

    @Override
    public void action() {
        System.out.println("---------------------开始执行采集任务---------------------");
        List<DataGather> dataGathers = dataGatherService.findAllAutoGather();
        dataGathers.forEach(dataGather ->{
            DataGatherParam dataGatherParam = new DataGatherParam();
            dataGatherParam.setId(dataGather.getId());
            dataGatherParam.setResultFilePath(dataGather.getResultFilePath());
            dataGatherParam.setSourceFilePath(dataGather.getSourceFilePath());
            dataGatherService.exec(dataGatherParam);
        });

    }
}
