package com.cn.sundeinfo.main.modular.elasticsearch.service;

import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.main.modular.metadata.param.MetadataTableParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 〈es service层处理类〉
 *
 * @author menshikai
 * @create 2021/5/26
 * @since 1.0.0
 */
public interface ResourcePermissionHandlingService {

    //根据登录用户获取当前用户权限
    public ResponseData getDataFilteringArray(HttpServletRequest request);

}
