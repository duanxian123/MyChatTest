package com.linghao.mychattest.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by linghao on 2017/7/28.
 * QQ：782695971
 * 作用：
 */

public class HopeInvited extends BmobObject {
    public HopeInvited(String username, String content) {
        this.username = username;
        this.content = content;
    }

    private String username;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HopeInvited{" +
                "username='" + username + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
