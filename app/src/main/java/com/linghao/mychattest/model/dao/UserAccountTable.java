package com.linghao.mychattest.model.dao;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：
 */

public class UserAccountTable {
    public static final  String TAB_NAME="tab_account";
    public static final  String COL_NAME="name";
    public static final  String COL_HXID="hxid";
    public static final  String COL_NICH="nick";
    public static final  String COL_PHOTO="photo";
    public  static final String CREAT_TAB="create table "
            +TAB_NAME+" ("
            +COL_HXID+" text primary key,"
            +COL_NAME+" text,"
            +COL_NICH+" text,"
            +COL_PHOTO+" text);";
}
//    create table ( hxid text primary key, name text, nick text, photo text);