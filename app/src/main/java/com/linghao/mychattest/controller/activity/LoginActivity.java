package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.UserInfo;

public class LoginActivity extends Activity {
    private EditText etLoginName;
    private EditText etLoginPwd;
    private Button btnRegist;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }
    private void initView() {
        etLoginName = (EditText)findViewById( R.id.et_login_name );
        etLoginPwd = (EditText)findViewById( R.id.et_login_pwd );
        btnRegist = (Button)findViewById( R.id.btn_regist );
        btnLogin = (Button)findViewById( R.id.bt_login );
    }
    private void initListener() {
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final String loginName = etLoginName.getText().toString();
        final String loginPsw = etLoginPwd.getText().toString();
        if(TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPsw)){
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(loginName, loginPsw, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //对模型侧数据的处理
                        Model.getInstance().loginSuccess(new UserInfo(loginName));
                        //将用户帐号保存到本地数据库
                        Model.getInstance().getUserAccountDao().addAcount(new UserInfo(loginName));
                        //跳转到主界面
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }

                    @Override
                    public void onError(int i, final String s) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Toast.makeText(LoginActivity.this, "登录失败"+s, Toast.LENGTH_SHORT).show();

                     }
                 });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

            }
        });

    }

    private void register() {
        final String loginName = etLoginName.getText().toString();
        final String loginPsw = etLoginPwd.getText().toString();
        if(TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPsw)){
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(loginName,loginPsw);
                    runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
            });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "注册失败"+e, Toast.LENGTH_SHORT).show();
                }
            });
                }
            }
        });
    }


}







