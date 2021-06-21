package com.cn.sundeinfo.main.modular.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUp;

import java.util.List;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service
 * @version:1.0
 */
public interface GiveTheTubsUpService {

    List<GiveTheTumbsUp> getAll();

    GiveTheTumbsUp getOne(String id);

    void insert(GiveTheTumbsUp user);

    void update(GiveTheTumbsUp user);

    void delete(String id);


}
