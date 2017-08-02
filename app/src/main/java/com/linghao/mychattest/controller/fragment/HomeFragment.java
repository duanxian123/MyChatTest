package com.linghao.mychattest.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hyphenate.chat.adapter.EMACallSession;
import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.activity.WriteInvitedActivity;
import com.linghao.mychattest.controller.activity.WriteRecruitActivity;
import com.linghao.mychattest.controller.adapter.HomeAdapter;
import com.linghao.mychattest.controller.pager.HomeTypePager;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.utils.customview.MytoggleButton;
import com.linghao.mychattest.model.bean.HopeInvited;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.controller.adapter.anotherHomeAdapter;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.R.attr.handle;

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
    private ViewPager viewPager;
    private ArrayList<HomeTypePager> homeTypePagers;
    private TabLayout tablayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bmob.initialize(getActivity(), Constant.Bmobinit);
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        iniView(view);
        return view;

    }

    private void iniView(View view) {
        bt_add_recruit = (Button) view.findViewById(R.id.bt_add_recruit);
//        home_recycleview = (RecyclerView) view.findViewById(R.id.home_recycleview);
        bt_add_invited = (Button) view.findViewById(R.id.bt_add_invited);
        another_home_recycleview = (RecyclerView) view.findViewById(R.id.another_home_reycleview);
        mytoggleButton = (MytoggleButton) view.findViewById(R.id.mytoggleButton);
        fl_home_fragment = (FrameLayout) view.findViewById(R.id.fl_home_fragment);
        another_fragment = (FrameLayout) view.findViewById(R.id.another_fragment);
        progressBar= (ProgressBar) view.findViewById(R.id.progress);
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
        viewPager= (ViewPager) view.findViewById(R.id.home_viewpager);
       tablayout = (TabLayout) view.findViewById(R.id.ide);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
//        fl_home_fragment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
    }




    private void initData() {
        homeTypePagers=new ArrayList<>();
        homeTypePagers.add(new HomeTypePager(getActivity(),"篮球"));
        homeTypePagers.add(new HomeTypePager(getActivity(),"足球"));
        homeTypePagers.add(new HomeTypePager(getActivity(),"跑步"));
        homeTypePagers.add(new HomeTypePager(getActivity(),"游泳"));
        homeTypePagers.add(new HomeTypePager(getActivity(),"旅游"));

        viewPager.setAdapter(new HomeTypeAdapter());
        tablayout.setupWithViewPager(viewPager);

//        希望被邀请页面适配器
        HopeInvitedFromBmob();
        swipeRefreshLayout.setRefreshing(false);
//        isfinish=true;
//        if (isfinish=true){
//            progressBar.setVisibility(View.GONE);
//            isfinish=false;
//        }
    }

    class HomeTypeAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            String typename = null;
            switch (position){
                case 0:
                    typename="篮球";
                    break;
                case 1:
                    typename="足球";
                    break;
                case 2:
                    typename="跑步";
                    break;
                case 3:
                    typename="游泳";
                    break;
                case 4:
                    typename="旅游";
                    break;
                    
            }
            return typename;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            HomeTypePager homeTypePager=homeTypePagers.get(position);
            View rootview=homeTypePager.rootView;
            container.addView(rootview);
//            basePager.initData();
            return rootview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return homeTypePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
             return view==object;
        }
    }
    private void HopeInvitedFromBmob() {
        BmobQuery<HopeInvited> query = new BmobQuery<HopeInvited>();
        query.addQueryKeys("username,content,createdAt");
        query.findObjects(new FindListener<HopeInvited>() {
            @Override
            public void done(List<HopeInvited> list, BmobException e) {
                if (e == null) {
                    Log.e("bmob", "成功");
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                    another_home_recycleview.setLayoutManager(linearLayoutManager1);
                    anotherHomeAdapter anotherHomeAdapter = new anotherHomeAdapter(getActivity(), list);
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
