package com.cn.sundeinfo.main.modular.elasticsearch.service;

import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUp;
import com.cn.sundeinfo.main.modular.elasticsearch.entity.GiveTheTumbsUpLog;

import java.util.List;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.service
 * @version:1.0
 */
public interface GiveTheTubsUpLogService {

    List<GiveTheTumbsUpLog> getAll();

    GiveTheTumbsUpLog getOne(String id);

    void insert(GiveTheTumbsUpLog user);

    void update(GiveTheTumbsUpLog user);

    void delete(String id);


}
