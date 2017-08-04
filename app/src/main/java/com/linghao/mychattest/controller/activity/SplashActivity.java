package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.UserInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends Activity {
    @InjectView(R.id.splash)
    RelativeLayout splash;
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (isFinishing()) {
//                return;
//            } else {
//                toMainOrLogin();
//            }
//        }
//    };

    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()) {
                    //获取当前登录用户的信息
                    UserInfo userInfo = Model.getInstance().getUserAccountDao().getUserInfoById(EMClient.getInstance().getCurrentUser());
                    Log.e("查询出", userInfo.toString());

                    Log.e("jinlai", "2");
                    if (userInfo == null) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Model.getInstance().loginSuccess(userInfo);
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        setAnimation();
//        handler.sendMessageDelayed(Message.obtain(), 2000);
    }


    private void setAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                toMainOrLogin();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splash.startAnimation(alphaAnimation);

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
//    }
}
