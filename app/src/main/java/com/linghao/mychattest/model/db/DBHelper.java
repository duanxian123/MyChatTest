package com.linghao.mychattest.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.linghao.mychattest.model.dao.ContactTable;
import com.linghao.mychattest.model.dao.InviteTable;

/**
 * Created by linghao on 2017/7/23.
 * QQ：782695971
 * 作用：
 */

public class DBHelper extends SQLiteOpenHelper {
    //根据name动态创建表
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_TAB);
        db.execSQL(InviteTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
