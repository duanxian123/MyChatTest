package com.linghao.mychattest.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.bean.HopeInvited;
import com.linghao.mychattest.utils.LogUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class WriteInvitedActivity extends AppCompatActivity {
    private EditText etAddInvitedcontent;
    private Button invitedOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_invited);
        initView();
        initListener();
    }

    private void initListener() {

        invitedOk.setOnClickListener(new View.OnClickListener() {
            String username= EMClient.getInstance().getCurrentUser();
            public void onClick(View v) {
                String et_content = etAddInvitedcontent.getText().toString();
                final HopeInvited hopeInvited=new HopeInvited(username,et_content);
                hopeInvited.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(WriteInvitedActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        etAddInvitedcontent = (EditText)findViewById( R.id.et_add_invitedcontent );
        invitedOk = (Button)findViewById( R.id.invited_ok );
    }
}


