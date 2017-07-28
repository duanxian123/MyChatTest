package com.linghao.mychattest.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.activity.WriteInvitedActivity;
import com.linghao.mychattest.controller.activity.WriteRecruitActivity;
import com.linghao.mychattest.controller.adapter.HomeAdapter;
import com.linghao.mychattest.model.CustomView.MytoggleButton;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.HopeInvited;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.model.dao.BmobDao;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.controller.adapter.anotherHomeAdapter;


import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by linghao on 2017/7/27.
 * QQ：782695971
 * 作用：
 */

public class HomeFragment extends Fragment {

    private Button bt_add_recruit;
    private RecyclerView home_recycleview;
    private Button bt_add_invited;
    private RecyclerView another_home_recycleview;
    private HomeAdapter homeAdapter;
    private MytoggleButton mytoggleButton;
    private FrameLayout fl_home_fragment;
    private FrameLayout another_fragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bmob.initialize(getActivity(), Constant.Bmobinit);
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        iniView(view);
        return view;

    }

    private void iniView(View view) {
        bt_add_recruit = (Button) view.findViewById(R.id.bt_add_recruit);
        home_recycleview = (RecyclerView) view.findViewById(R.id.home_recycleview);
        bt_add_invited = (Button) view.findViewById(R.id.bt_add_invited);
        another_home_recycleview = (RecyclerView) view.findViewById(R.id.another_home_recycleview);
        mytoggleButton= (MytoggleButton) view.findViewById(R.id.mytoggleButton);
        fl_home_fragment= (FrameLayout) view.findViewById(R.id.fl_home_fragment);
        another_fragment= (FrameLayout) view.findViewById(R.id.another_fragment);
        mytoggleButton.setmyToggleButtonListener(new MytoggleButton.myToggleButtonListener() {
            @Override
            public void open() {
                fl_home_fragment.setVisibility(View.VISIBLE);
             another_fragment.setVisibility(View.GONE);
            }

            @Override
            public void close() {
                fl_home_fragment.setVisibility(View.GONE);
                another_fragment.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }

    private void initListener() {
        bt_add_recruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteRecruitActivity.class);
                startActivity(intent);
            }
        });
        bt_add_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteInvitedActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        home_recycleview.setLayoutManager(linearLayoutManager);
                   //招募页面适配器
         Model.getInstance().getBmobDao().getSomeColumnfromBmob("title,content", new BmobDao.OnDataReceiveSuccessListener() {
            @Override
            public void onSuccess(List<Recruit> list) {
                homeAdapter = new HomeAdapter(getActivity(), list);
                home_recycleview.setAdapter(homeAdapter);

            }

         });
        //希望被邀请页面适配器
        HopeInvitedFromBmob();

    }

    private void HopeInvitedFromBmob() {
        BmobQuery<HopeInvited> query = new BmobQuery<HopeInvited>();
        query.addQueryKeys("username,content,createdAt");
        query.findObjects(new FindListener<HopeInvited>() {
            @Override
            public void done(List<HopeInvited> list, BmobException e) {
                if (e == null) {
                    Log.e("bmob", "成功" );
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                    another_home_recycleview.setLayoutManager(linearLayoutManager1);
                    anotherHomeAdapter anotherHomeAdapter=new anotherHomeAdapter(getActivity(),list);
                    another_home_recycleview.setAdapter(anotherHomeAdapter);
                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
