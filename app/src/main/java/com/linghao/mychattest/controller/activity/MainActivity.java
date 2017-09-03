package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.fragment.ChatFragment;
import com.linghao.mychattest.controller.fragment.ContactFragment;
import com.linghao.mychattest.controller.fragment.HomeFragment;
import com.linghao.mychattest.controller.fragment.SettingFragment;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.utils.LogUtil;

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
    private boolean isFlag = true;
    private static final int MESSAGE_BACK = 1;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case MESSAGE_BACK :
                    isFlag = true;//在2时，恢复isFlag的变量值
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        SharedPreferences sp = getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        boolean isOpen = sp.getBoolean("isOpen", false);
        LogUtil.e("*****"+isOpen);
        if (isOpen){
            LogUtil.e("*****"+1111);
            Intent intent1=new Intent(MainActivity.this,GestureVerifyActivity.class);
            startActivity(intent1);
            LogUtil.e("*****"+2222);
        }
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
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {


        if(keyCode == KeyEvent.KEYCODE_BACK && isFlag){//如果操作的是“返回键”
            isFlag = false;
            Toast.makeText(MainActivity.this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
            //发送延迟消息
            handler.sendEmptyMessageDelayed(MESSAGE_BACK,2000);
            return true;

        }

        return super.onKeyUp(keyCode, event);
    }

}

