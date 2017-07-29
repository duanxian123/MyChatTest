package com.linghao.mychattest.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.linghao.mychattest.model.bean.UserInfo;
import com.linghao.mychattest.model.dao.BmobDao;
import com.linghao.mychattest.model.dao.ContactTableDao;
import com.linghao.mychattest.model.dao.InviteTableDao;
import com.linghao.mychattest.model.dao.UserAccountDao;
import com.linghao.mychattest.model.db.DBManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：数据模型层全局类
 */

public class Model {
    private Context mContext;
    private static Model model=new Model();
    private ExecutorService executors= Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;
    private DBManager dBManager;
    private InviteTableDao inviteTableDao;
    private ContactTableDao contactTableDao;
    private BmobDao bmobDao =new BmobDao();;

    //    // 私有化构造
//    private Model() {
//
//    }
    //获取单例对象
    public static Model getInstance() {
        return model;
    }
//初始化方法
    public void init(Context context){
        mContext=context;
        userAccountDao = new UserAccountDao(mContext);
        EventListener eventListener = new EventListener(context);

    }
    public BmobDao getBmobDao(){
        return bmobDao;
    }
    //获取全局线程池
    public ExecutorService getGlobalThreadPool(){
        return  executors;
    }
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
    public InviteTableDao getInviteTableDao(){
        return inviteTableDao;
    }
    public ContactTableDao getContactTableDao(){
        return contactTableDao;
    }
    public DBManager getDBManager(){
        return dBManager;
    }
    public void loginSuccess(UserInfo userInfo) {
        if(userInfo==null){
            return;
        }
        if(dBManager!=null){
            dBManager.close();
        }
        dBManager=new DBManager(mContext,userInfo.getName());

    }
}
