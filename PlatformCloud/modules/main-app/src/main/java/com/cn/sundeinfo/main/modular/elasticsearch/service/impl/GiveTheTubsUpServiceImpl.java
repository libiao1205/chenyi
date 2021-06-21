package com.cn.sundeinfo.main.modular.elasticsearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sundeinfo.main.modular.dataGather.entity.DataGather;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUp;
import com.cn.sundeinfo.main.modular.elasticsearch.mapper.GiveTheTumbsUpMapper;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GiveTheTubsUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service.impl
 * @version:1.0
 */

@Service
public class GiveTheTubsUpServiceImpl implements GiveTheTubsUpService {

    @Autowired
    GiveTheTumbsUpMapper giveTheTumbsUpMapper;

    @Override
    public List<GiveTheTumbsUp> getAll() {
        return null;
    }

    @Override
    public GiveTheTumbsUp getOne(String id) {
        //构造条件
        GiveTheTumbsUp userList = giveTheTumbsUpMapper.selectById(id);
        return userList;
    }

    @Override
    public void insert(GiveTheTumbsUp user) {

        int userList = giveTheTumbsUpMapper.insert(user);

    }

    @Override
    public void update(GiveTheTumbsUp user) { ;
        // 更新添加,未设置条件 sql： UPDATE user SET email=?  ，设置后：UPDATE user SET email=? WHERE (id = ?)
        UpdateWrapper<GiveTheTumbsUp> wrapper1 = new UpdateWrapper<GiveTheTumbsUp>();
        wrapper1.eq("id",user.getId());
        giveTheTumbsUpMapper.update(user,wrapper1);
    }

    @Override
    public void delete(String id) {

    }

}
