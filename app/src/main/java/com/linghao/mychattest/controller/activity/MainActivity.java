package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.fragment.ChatFragment;
import com.linghao.mychattest.controller.fragment.ContactFragment;
import com.linghao.mychattest.controller.fragment.HomeFragment;
import com.linghao.mychattest.controller.fragment.SettingFragment;
import com.linghao.mychattest.utils.Constant;

import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity {
    private FrameLayout flMain;
    private RadioGroup rgMain;
    private RadioButton rbChat;
    private RadioButton rbContact;
    private RadioButton rbSetting;
    private RadioButton rbHome;
    private ChatFragment chatFragment;
    private ContactFragment contactFragment;
    private SettingFragment settingFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Fragment fragment=null;
                switch (checkedId){
                    case R.id.rb_home:
                        fragment=homeFragment;
                        break;
                    case R.id.rb_chat:
                        fragment=chatFragment;
                        break;
                    case R.id.rb_contact:
                        fragment=contactFragment;
                        break;
                    case R.id.rb_setting:
                        fragment=settingFragment;
                        break;
                }
                switchFragment(fragment);

            }
        });
        rgMain.check(R.id.rb_home);
    }

    private void switchFragment(Fragment fragment) {
        Log.e("zhuanhuanl","1");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main,fragment).commit();
    }

    private void initData() {
        chatFragment = new ChatFragment();
        contactFragment = new ContactFragment();
        settingFragment = new SettingFragment();
        homeFragment=new HomeFragment();

    }


    private void initView() {
        flMain = (FrameLayout) findViewById(R.id.fl_main);
        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        rbChat = (RadioButton) findViewById(R.id.rb_chat);
        rbHome= (RadioButton) findViewById(R.id.rb_home);
        rbContact = (RadioButton) findViewById(R.id.rb_contact);
        rbSetting = (RadioButton) findViewById(R.id.rb_setting);

    }

}

