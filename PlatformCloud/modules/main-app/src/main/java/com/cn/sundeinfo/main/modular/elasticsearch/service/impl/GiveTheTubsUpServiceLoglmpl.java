package com.cn.sundeinfo.main.modular.elasticsearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUp;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUpLog;
import com.cn.sundeinfo.main.modular.elasticsearch.mapper.GiveTheTumbsUpLogMapper;
import com.cn.sundeinfo.main.modular.elasticsearch.mapper.GiveTheTumbsUpMapper;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GiveTheTubsUpLogService;
import com.cn.sundeinfo.main.modular.elasticsearch.service.GiveTheTubsUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service.impl
 * @version:1.0
 */

@Service
public class GiveTheTubsUpServiceLoglmpl implements GiveTheTubsUpLogService {

    @Autowired
    GiveTheTumbsUpLogMapper giveTheTumbsUpLogMapper;

    @Override
    public List<GiveTheTumbsUpLog> getAll() {
        return null;
    }

    @Override
    public GiveTheTumbsUpLog getOne(String id) {
        //构造条件
        GiveTheTumbsUpLog userList = giveTheTumbsUpLogMapper.selectById(id);
        return userList;
    }

    @Override
    public void insert(GiveTheTumbsUpLog user) {
        int userList = giveTheTumbsUpLogMapper.insert(user);
    }

    @Override
    public void update(GiveTheTumbsUpLog user) {
    }

    @Override
    public void delete(String id) {
    }

}
