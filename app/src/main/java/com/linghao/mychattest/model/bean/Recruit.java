package com.linghao.mychattest.model.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by linghao on 2017/7/27.
 * QQ：782695971
 * 作用：
 */

public class Recruit extends BmobObject {
    private String title;
    private String groupId;
    private String groupOwner;
    private String maxPerson;
    private String content;
    private String time;
    private String type;

    public Recruit( String title, String groupId, String groupOwner, String maxPerson, String content, String time, String type) {
        this.title = title;
        this.groupId = groupId;
        this.groupOwner = groupOwner;
        this.maxPerson = maxPerson;
        this.content = content;
        this.time = time;
        this.type = type;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(String maxPerson) {
        this.maxPerson = maxPerson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Recruit{" +
                "title='" + title + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupOwner='" + groupOwner + '\'' +
                ", maxPerson='" + maxPerson + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
