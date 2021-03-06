package com.cn.sundeinfo.core.tenant.exception;

import com.cn.sundeinfo.core.exception.ServiceException;
import com.cn.sundeinfo.core.exception.enums.abs.AbstractBaseExceptionEnum;

/**
 * 多租户的异常
 *
 * @author xuyuxiang
 * @date 2020/9/3
 */
public class TenantException extends ServiceException {

    public TenantException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

}
