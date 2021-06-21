package com.cn.sundeinfo.main.modular.elasticsearch.entity;

/**
 * @Author:KangZD
 * @Date:2021/6/8
 * @Description:com.cn.sundeinfo.main.modular.elasticsearch.entity
 * @version:1.0
 */

public class Incident {

    //坐标
    String coordinate;

    //内容
    String content;

    //标题
    String title;

    //父级菜单的子节点ID
    String childrenIncidentId;

    //事件ID
    String incidentId;

    //子节点菜单中的子节点ID
    String pid;

    String searchStartDate;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChildrenIncidentId() {
        return childrenIncidentId;
    }

    public void setChildrenIncidentId(String childrenIncidentId) {
        this.childrenIncidentId = childrenIncidentId;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
