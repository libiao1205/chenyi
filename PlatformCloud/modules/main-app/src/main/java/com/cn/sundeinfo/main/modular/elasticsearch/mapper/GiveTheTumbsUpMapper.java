package com.cn.sundeinfo.main.modular.elasticsearch.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.mapper.mapping
 * @version:1.0
 */

@Mapper
public interface GiveTheTumbsUpMapper extends BaseMapper<GiveTheTumbsUp> {
    // public List<GiveTheTumbsUp> selectList();

}
