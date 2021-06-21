package com.cn.sundeinfo.sys.modular.dict.response;

import com.cn.sundeinfo.core.pojo.page.PageResult;
import com.cn.sundeinfo.sys.modular.dict.entity.SysDictData;
import lombok.Data;
import java.util.List;

/**
 * @创建人 libiao
 * @创建时间 2021/4/15
 */
@Data
public class SysDictDataResponse {

    private List<SysDictData> result;

    private List<Long> idList;

    private PageResult<SysDictData> pageResult;

    private List<List<Long>> ids;
}
