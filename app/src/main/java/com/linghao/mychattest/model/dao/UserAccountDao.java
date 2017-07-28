package com.linghao.mychattest.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.linghao.mychattest.model.bean.UserInfo;
import com.linghao.mychattest.model.db.UserAccountDb;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：用户帐号数据库操作类
 */

public class UserAccountDao {

    private final UserAccountDb mHelper;

    public UserAccountDao(Context context) {
        mHelper = new UserAccountDb(context);
    }

    //添加用户到数据库
    public void addAcount(UserInfo userInfo) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(UserAccountTable.COL_HXID,userInfo.getHxid());
        values.put(UserAccountTable.COL_NAME,userInfo.getName());
        values.put(UserAccountTable.COL_NICH,userInfo.getNick());
        values.put(UserAccountTable.COL_PHOTO,userInfo.getPhoto());
        db.replace(UserAccountTable.TAB_NAME,null,values);

    }
    //根据环信ID查询用户信息
    public  UserInfo getUserInfoById(String hxId){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        String sql="select * from "+UserAccountTable.TAB_NAME+" where "+UserAccountTable.COL_HXID+"=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo=null ;
        if(cursor.moveToNext()){
            userInfo=new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICH)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }
        cursor.close();
        return userInfo;
    }

}
