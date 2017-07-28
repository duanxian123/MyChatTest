package com.linghao.mychattest;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.utils.Constant;

import cn.bmob.v3.Bmob;


/**
 * Created by linghao on 2017/7/21.
 * QQ：782695971
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化EaseUI
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);// 设置需要同意后才能接受邀请
        options.setAutoAcceptGroupInvitation(false);// 设置需要同意后才能接受群邀请

        EaseUI.getInstance().init(this,options);
        Model.getInstance().init(this);
        mContext=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化bmob
        Bmob.initialize(mContext, Constant.Bmobinit);
    }
    public static  Context getGlobalApplication(){
        return mContext;
    }

}
