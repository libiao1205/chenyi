package com.cn.sundeinfo.main.modular.elasticsearch.entity;

/**
 * @Author:KangZD
 * @Date:2021/6/8
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */
public class MapSearchConditions {

    //地点
    String place;

    //时间
    String searchStartDate;

    //坐标
    String coordinate;

    //事件ID
    String incidentId;

    //事件详情
    String title;

    //类型
    String type;


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
