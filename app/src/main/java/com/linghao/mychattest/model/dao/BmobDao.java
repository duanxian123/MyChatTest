package com.linghao.mychattest.model.dao;

import android.content.Context;
import android.util.Log;

import com.baidu.platform.comapi.map.A;
import com.linghao.mychattest.controller.adapter.HomeAdapter;
import com.linghao.mychattest.model.bean.HopeInvited;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
//
//import static android.R.attr.key;
//import static android.R.attr.type;
//import static com.linghao.mychattest.R.id.home_recycleview;

/**
 * Created by linghao on 2017/7/28.
 * QQ：782695971
 * 作用：
 */

public class BmobDao {
    public BmobDao() {
    }

    //查询Recruit整列数据
    public static void getSomeColumnfromBmob(String key, final OnDataReceiveSuccessListener listener) {
        final List<Recruit> A = new ArrayList();
        BmobQuery<Recruit> query = new BmobQuery<Recruit>();
        query.addQueryKeys(key);
        query.findObjects(new FindListener<Recruit>() {
            @Override
            public void done(List<Recruit> list, BmobException e) {
                if (e == null) {

                    for (Recruit a : list) {
                        A.add(a);
                        LogUtil.e(a.toString());
                    }
                    if (listener != null) {
                        listener.onSuccess(A);
                    }
                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }

        });
    }

    public interface OnDataReceiveSuccessListener{
        void onSuccess(List<Recruit> list);
    }

    //保存进bmob recruit中
    public void saveToBmob(String title, String groupId, String groupOwner, String maxPerson, String content, String time, String type) {
        final Recruit recruit = new Recruit(title, groupId, groupOwner, maxPerson,content, time, type);
        recruit.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.e("保存成功" + recruit.toString());
                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }
}
