package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;

public class addGroupActivity extends Activity {
private Button bt_add_group;
    private EditText et_groupid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        initView();
        initListener();
    }

    private void initListener() {
        bt_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("---------------","1");
                            String groupid=et_groupid.getText().toString();
                            Log.e("---------------",groupid);
                            EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");
                            Log.e("---------------","2");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(addGroupActivity.this, "申请中", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void initView() {
        et_groupid= (EditText) findViewById(R.id.et_groupid);
        bt_add_group= (Button) findViewById(R.id.bt_add_group);

    }
}
