package com.linghao.mychattest.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linghao.mychattest.model.dao.UserAccountTable;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：创建用户数据表
 */

public class UserAccountDb extends SQLiteOpenHelper{
    public UserAccountDb(Context context) {
        super(context, "account.db", null, 2);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(UserAccountTable.CREAT_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
