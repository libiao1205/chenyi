package com.cn.sundeinfo.main.modular.dataGather.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.factory.PageFactory;
import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.main.modular.dataGather.entity.DataGather;
import com.cn.sundeinfo.main.modular.dataGather.enums.GatherExceptionEnum;
import com.cn.sundeinfo.main.modular.dataGather.mapper.DataGatherMapper;
import com.cn.sundeinfo.main.modular.dataGather.param.DataGatherParam;
import com.cn.sundeinfo.main.modular.dataGather.service.DataGatherService;
import com.cn.sundeinfo.main.modular.dataSource.enums.DataSourceExceptionEnum;
import com.cn.sundeinfo.sys.core.export.ExportZip;
import com.cn.sundeinfo.sys.core.fedora.Fedora;
import com.cn.sundeinfo.sys.core.file.FileInfo;
import com.cn.sundeinfo.sys.modular.dict.enums.SysDictDataExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/23
 */
@Service
public class DataGatherServiceImpl extends ServiceImpl<DataGatherMapper, DataGather> implements DataGatherService {

    @Autowired
    ExportZip exportZip;

    @Autowired
    FileInfo fileInfo;

    @Autowired
    Fedora fedora;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${gather.instrument.path}")
    private String gatherInstrumentPath;

    @Value("${system.environment}")
    private String systemEnvironment;


    @Override
    public PageResult<DataGather> page(DataGatherParam dataGatherParam) {
        //构造条件
        LambdaQueryWrapper<DataGather> queryWrapper = new LambdaQueryWrapper<>();
        //查询未删除的
        queryWrapper.ne(DataGather::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(DataGather::getSort);
        //返回分页查询结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<DataGather> findAllAutoGather() {
        LambdaQueryWrapper<DataGather> queryWrapper = new LambdaQueryWrapper<>();
        // 查询自动执行的
        queryWrapper.eq(DataGather::getGatherType,1);
        // 查询状态为正常的（执行过的不在执行）
        queryWrapper.isNull(DataGather::getGatherStatus);
        return this.list(queryWrapper);
    }


    @Transactional
    @Override
    public void add(MultipartFile multipartFile) {
        int index = multipartFile.getOriginalFilename().indexOf(".");
        if(!".xls".equals(multipartFile.getOriginalFilename().substring(index)) && !".xlsx".equals(multipartFile.getOriginalFilename().substring(index))){
            throw new ServiceException(GatherExceptionEnum.FILE_FORMAT_ERROR);
        }
        DataGather dataGather = new DataGather();
        dataGather.setFileName(multipartFile.getOriginalFilename());
        String path = fedora.addFile(multipartFile);
        if(ObjectUtil.isEmpty(path)){
            throw new ServiceException(GatherExceptionEnum.FILE_SAVE_FAILED);
        }
        //保存文件路径
        dataGather.setSourceFilePath(path);
        dataGather.setSort(100);
        //自动执行
        dataGather.setGatherType(1);
        //设置状态为启用
        dataGather.setStatus(CommonStatusEnum.ENABLE.getCode());
        this.save(dataGather);
    }

    @Override
    public void updateGatherType(DataGatherParam dataGatherParam) {
        DataGather dataGather = this.queryDataGather(dataGatherParam);

        BeanUtil.copyProperties(dataGatherParam,dataGather);
        dataGather.setGatherType(dataGather.getGatherType() == 0 ? 1 : 0);
        this.updateById(dataGather);
    }

    @Override
    public void delete(DataGatherParam dataGatherParam) {
        //根据id查询实体
        DataGather dataGather = this.queryDataGather(dataGatherParam);

        if(ObjectUtil.isNotEmpty(dataGather.getResultFilePath())){
            //  如果已有采集文件夹，先把采集文件夹删除
            File file = new File(dataGather.getResultFilePath());
            this.deleteFiles(file);
        }
        //逻辑删除，修改状态
        dataGather.setStatus(CommonStatusEnum.DELETED.getCode());

        //更新实体
        this.updateById(dataGather);
    }

    @Override
    public void exec(DataGatherParam dataGatherParam) {
        if(ObjectUtil.isNotEmpty(dataGatherParam.getResultFilePath())){
            //  如果已有采集文件夹，先把采集文件夹删除
            File file = new File(dataGatherParam.getResultFilePath());
            this.deleteFiles(file);
        }
        JSONObject jsonObject = fedora.getFile(dataGatherParam.getSourceFilePath());
        this.startGather(jsonObject.get("fileSavePath").toString(),jsonObject.get("filename").toString(),dataGatherParam.getId());
        DataGather dataGather = this.queryDataGather(dataGatherParam);
        //采集结果保存位置
        dataGather.setResultFilePath(jsonObject.get("fileSavePath").toString());
        dataGather.setGatherStatus(0);
        this.updateById(dataGather);
    }

    @Override
    public void downFolder(DataGatherParam dataGatherParam,HttpServletResponse response) {
        DataGather dataGather = this.queryDataGather(dataGatherParam);
        //需要压缩的目录
        String filePath = dataGather.getResultFilePath() + "/output";
        File file = new File(filePath);
        if(!file.exists()){
            throw new ServiceException(GatherExceptionEnum.NOT_GATHER_RESULT);
        }
        //压缩后的路径
        String zipFilePath = dataGather.getResultFilePath() + "/output" + ".zip";
        try {
            File zipFile = new File(zipFilePath);
            // 如果已经压缩过了就直接下载
            if(zipFile.exists()){
                exportZip.downFile(zipFile,response);
            }else{
                exportZip.compress(new File(zipFilePath),file.getPath());
                zipFile = new File(zipFilePath);
                exportZip.downFile(zipFile,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataGather queryDataGather(DataGatherParam dataGatherParam){
        LambdaQueryWrapper<DataGather> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataGather::getId,dataGatherParam.getId());
        return this.getOne(queryWrapper);
    }

    /*
     * 开始采集
     * */
    private void startGather(String fileSaverPath,String fileName,Long id){
        System.out.println("开始执行采集任务-->"+fileName);
        Thread thread = new Thread(){
            public void run(){
                // 先清除redis中上次执行采取的日志
                stringRedisTemplate.opsForValue().set(id.toString(),"");
                File file = new File(fileSaverPath + "/output");
                if(file.exists()){
                    file.delete();
                }else{
                    file.mkdirs();
                }
                Runtime rn = Runtime.getRuntime();
                Process proc = null;
                StringBuffer sbf = new StringBuffer();
                try {
                    File dirFile = new File(gatherInstrumentPath);
                    String command = "";
                    if("windows".equals(systemEnvironment)){
                        command = gatherInstrumentPath + "\\Superman.Dac.Test.exe" +
                                " " + fileSaverPath + "\\" + fileName +
                                " " + fileSaverPath + "\\output";
                    }else if("linux".equals(systemEnvironment)){
                        command = gatherInstrumentPath + "/Superman.Dac.Test" +
                                " " + fileSaverPath + "/" + fileName +
                                " " + fileSaverPath + "/output";
                    }
                    proc = rn.exec(command,null,dirFile);
                    InputStream stdin = proc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(stdin,"GBK");
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;

                    while ( (line = br.readLine()) != null){
                        if(line.contains("结束时间：")){
                            sbf.append("<p style='color:black'>" + line + "<p><br>");
                        }else{
                            sbf.append(line + "<br>");
                        }
                        stringRedisTemplate.opsForValue().set(id.toString(),sbf.toString());
                    }
                    int exitVal = proc.waitFor();
                    sbf.append("<p style='color:black'>" + (exitVal == 0 ? "采取成功！" : "采取失败！") + "<p>");
                    stringRedisTemplate.opsForValue().set(id.toString(),sbf.toString());
                    updateGatherStatus(id, exitVal == 0 ? 1 : 2);
                    System.out.println("采集任务执行结束-->"+fileName +",exitVal:" + exitVal);
                } catch (Exception e) {
                    updateGatherStatus(id,2);
                    sbf.append("<p style='color:black'>采取出错，请联系管理员！<p>");
                    stringRedisTemplate.opsForValue().set(id.toString(),sbf.toString());
                    System.out.println("采集任务执行出错-->"+fileName);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /*
    * 获取采集日志
    * */
    @Override
    public String getGatherLog(Long id){
        return stringRedisTemplate.opsForValue().get(id.toString());
    }

    /*
     * 删除目录及目录下的所有文件及
     * */
    private void deleteFiles(File file){
        if(!file.exists()){
            return;
        }
        File[] files = file.listFiles();
        if (files.length>0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }else {
                    deleteFiles(files[i]);
                }
            }
            files = file.listFiles();
            //删除该目录下的文件后，再次判断，如果为空，删除该目录
            if (files.length==0) {
                file.delete();
            }
        }else{
            file.delete();
        }
    }

    /*
     * 修改采集状态
     * */
    private synchronized void updateGatherStatus(Long id, int exitVal){
        DataGatherParam dataGatherParam = new DataGatherParam();
        dataGatherParam.setId(id);
        DataGather dataGather = queryDataGather(dataGatherParam);
        dataGather.setGatherStatus(exitVal);
        updateById(dataGather);
    }

}
