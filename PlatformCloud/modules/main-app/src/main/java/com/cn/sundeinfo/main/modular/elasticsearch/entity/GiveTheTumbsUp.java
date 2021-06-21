package com.cn.sundeinfo.main.modular.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.sundeinfo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/**
 * @Author:KangZD
 * @Date:2021/6/16
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */

@Data
@TableName("give_the_tumbs_up")
public class GiveTheTumbsUp{

    //产品ID
    String id;
    //用户ID
    String uid;
    //点赞次数
    String count;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
