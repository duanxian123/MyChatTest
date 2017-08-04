package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.UserInfo;

public class addContactActivity extends Activity {
    private TextView tvAddFind;
    private EditText etAddName;
    private RelativeLayout rlAdd;
    private ImageView ivAddPhoto;
    private TextView tvAddName;
    private Button btAddAdd;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initViews();
        initListener();
    }

    private void initListener() {

        tvAddFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();

            }
        });
        btAddAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void add() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String myName=EMClient.getInstance().getCurrentUser();
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(),myName+"请求添加您为好友");
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(addContactActivity.this, "请求添加成功", Toast.LENGTH_SHORT).show();
                       }
                   });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(addContactActivity.this, "请求添加失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void find() {
        final String name = etAddName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(addContactActivity.this, "帐号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断服务器是否存在此账号，现在是模拟一个
                userInfo = new UserInfo(name);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rlAdd.setVisibility(View.VISIBLE);
                        tvAddName.setText(userInfo.getName());
                    }
                });

            }
        });
    }

    private void initViews() {
        tvAddFind = (TextView)findViewById( R.id.tv_add_find );
        etAddName = (EditText)findViewById( R.id.et_add_name );
        rlAdd = (RelativeLayout)findViewById( R.id.rl_add );
        ivAddPhoto = (ImageView)findViewById( R.id.iv_add_photo );
        tvAddName = (TextView)findViewById( R.id.tv_add_name );
        btAddAdd = (Button)findViewById( R.id.bt_add_add );
    }
}




