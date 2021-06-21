package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author:KangZD
 * @Date:2021/6/17
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */

@Data
@TableName("give_the_tumbs_up_log")
public class GiveTheTumbsUpLog {

    String id;

    String pid;

    String uid;

    String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
