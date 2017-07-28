package com.linghao.mychattest.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.adapter.GroupListAdapter;
import com.linghao.mychattest.model.Model;

import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    private ListView lv_grouplist;
    private LinearLayout ll_grouplist;
    private GroupListAdapter groupListAdapter;
    private Button bt_add_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        lv_grouplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((position==0)){
                    return;
                }
                Intent intent = new Intent(GroupListActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(position-1);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,emGroup.getGroupId());
                startActivity(intent);
            }
        });
        ll_grouplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupListActivity.this,NewGroupActivity.class);
                startActivity(intent);
            }
        });
        bt_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupListActivity.this,addGroupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        // 初始化listview
        groupListAdapter = new GroupListAdapter(this);

        lv_grouplist.setAdapter(groupListAdapter);
        getGrouplistFromHX();
    }

    private void getGrouplistFromHX() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //从环信获取群列表
                    List<EMGroup>  mGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    //页面刷新
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupListActivity.this, "加载群信息成功", Toast.LENGTH_SHORT).show();
                               refresh();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupListActivity.this, "加载群信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void refresh() {
        groupListAdapter.refresh( EMClient.getInstance().groupManager().getAllGroups());
    }

    private void initView() {
        // 获取listview对象
        lv_grouplist = (ListView)findViewById(R.id.lv_grouplist);
        // 添加头布局
        View headerView = View.inflate(this, R.layout.header_grouplist, null);
        lv_grouplist.addHeaderView(headerView);
        ll_grouplist = (LinearLayout) headerView.findViewById(R.id.ll_grouplist);
        bt_add_group = (Button) headerView.findViewById(R.id.bt_add_group);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}
