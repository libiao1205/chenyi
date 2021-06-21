package com.cn.sundeinfo.main.modular.elasticsearch.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cn.sundeinfo.core.exception.AuthException;
import com.cn.sundeinfo.core.pojo.login.SysLoginUser;
import com.cn.sundeinfo.core.pojo.response.ErrorResponseData;
import com.cn.sundeinfo.core.pojo.response.ResponseData;
import com.cn.sundeinfo.core.pojo.response.SuccessResponseData;
import com.cn.sundeinfo.main.modular.elasticsearch.service.ResourcePermissionHandlingService;
import com.cn.sundeinfo.main.modular.metadata.mapper.MetadataColumnMapper;
import com.cn.sundeinfo.sys.modular.auth.service.impl.AuthServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈ResourcePermissionHandlingServiceImpl资源权限处理类 〉
 *
 * @author menshikai
 * @create 2021/5/26
 * @since 1.0.0
 */
@Service
public class ResourcePermissionHandlingServiceImpl implements ResourcePermissionHandlingService {

    @Resource
    private AuthServiceImpl authService;

    @Resource
    private MetadataColumnMapper metadataColumnMapper;


    /**
     * 根据token 判断是否有用户登录 如果有用户登录将会读取权限并且返回
     * 未登录用户将返回具体信息 临时用方法 后期将微调该方法 删除token校验对接认证平台
     * @param request
     * @return
     */
    @Override
    public ResponseData getDataFilteringArray(HttpServletRequest request) {

        // 1.判断token是否为空 如果token不为空则获取用户对象
        SysLoginUser sysLoginUser = null;
        String token = authService.getTokenFromRequest(request);
        if (StrUtil.isNotEmpty(token)) {
            sysLoginUser = authService.getLoginUserByToken(token);
            // 2.判断用户是否为空 如果用户不为空则查询用户角色信息
            if (ObjectUtil.isNotNull(sysLoginUser)) {
                List<String> roleids = new ArrayList<>();
                sysLoginUser.getRoles().forEach(dict -> {
                    roleids.add(dict.getStr("code"));
                });
                if(roleids.size()>0){
                    List<Map<String, String>> permissionByRoleCode = metadataColumnMapper.findPermissionByRoleCode(roleids);
                    String[] es_field_names =(String[]) permissionByRoleCode.stream().map(map -> map.get("es_field_name")).collect(Collectors.toList()).toArray(new String[0]);
//              permissionByRoleCode.forEach(out::println);
//              permissionByRoleCode.stream()
//                    .forEach(map->
//                            map.forEach(
//                                    (k,v)-> System.out.println(k+":"+v)
//                            )
//                    );
                    return new SuccessResponseData(es_field_names);
                }else{
                    return new ErrorResponseData("当前用户未设置角色，请联系管理员进行权限划分");//当不走过滤器的方法获取权限
                }
            }else{
                return new ErrorResponseData("map中无该用户信息,请重新登录");//当不走过滤器的方法获取权限
            }
        }else{
            return new ErrorResponseData("无token信息");//当不走过滤器的方法获取权限
        }
    }
}
