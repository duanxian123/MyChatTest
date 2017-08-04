package com.linghao.mychattest.controller.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.baidu.platform.comapi.map.A;
import com.linghao.mychattest.controller.adapter.HomeAdapter;
import com.linghao.mychattest.controller.adapter.anotherHomeAdapter;
import com.linghao.mychattest.model.dao.BmobDao;

import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import static com.xiaomi.push.service.y.m;


/**
 * Created by linghao on 2017/7/29.
 * QQ：782695971
 * 作用：
 */

public class HomeTypePager {
    private Context mContext;
    private List<Recruit> mLists;
    public View rootView;
    private String mtype;
    private HomeAdapter homeAdapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Recruit> real= (List<Recruit>) msg.obj;
                            homeAdapter = new HomeAdapter(mContext, real);
                    recyclerView.setAdapter(homeAdapter);
        }
    };
    private RecyclerView recyclerView;

    public HomeTypePager(Context context,String type){
        mContext=context;
//        mLists=lists;
        mtype=type;
        rootView=initView();

    }



    private View initView() {
        mLists=new ArrayList<>();
        recyclerView = new RecyclerView(mContext);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
            //招募页面适配器
            final String type = mtype;
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                Model.getInstance().getBmobDao().getSomeColumnfromBmob("title,content,type,groupId", new BmobDao.OnDataReceiveSuccessListener() {
                    @Override
                    public void onSuccess(List<Recruit> list) {
                        LogUtil.e(list.size()+"********");
                        List<Recruit> a=new ArrayList<>();
                        for (int i=0;i<list.size();i++){
                            if(list.get(i).getType().equals(type)){
                                a.add(list.get(i));
                            }
                            Message msg=new Message();
                          msg.obj=a;
                            handler.sendMessage(msg);
                        }

            }
        });

//                    for (int i=0;i<a.size();i++){
//                        LogUtil.e(a.get(i).getType());
//                    }
//                    LogUtil.e(a.size()+"********");

//                homeAdapter = new HomeAdapter(mContext, a);
//                    recyclerView.setAdapter(homeAdapter);

                }

            });

              return recyclerView;
    }



}
