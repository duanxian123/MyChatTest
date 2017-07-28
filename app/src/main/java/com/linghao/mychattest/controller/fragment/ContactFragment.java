package com.linghao.mychattest.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.activity.ChatActivity;
import com.linghao.mychattest.controller.activity.GroupListActivity;
import com.linghao.mychattest.controller.activity.InviteActivity;
import com.linghao.mychattest.controller.activity.addContactActivity;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.UserInfo;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：
 */

public class ContactFragment extends EaseContactListFragment{

    private ImageView iv_red;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            iv_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
        }
    };
    private LinearLayout ll_contact_invite;
    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshContact();
        }
    };
    private String hxid;
    private BroadcastReceiver GroupInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            iv_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
        }
    };

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.em_add);
        View headView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(headView);
        iv_red = (ImageView) headView.findViewById(R.id.iv_contact_red);
        ll_contact_invite = (LinearLayout) headView.findViewById(R.id.ll_contact_invite);
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                if(user==null){
                    return;
                }
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                startActivity(intent);
            }
        });
        // 跳转到群组列表页面
        LinearLayout ll_contact_group = (LinearLayout) headView.findViewById(R.id.ll_contact_group);
        ll_contact_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupListActivity.class);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void setUpView() {
        super.setUpView();
        Log.i("联系人实验","1");

        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addContactActivity.class);
                startActivity(intent);
            }
        });
        //初始化和红点显示
        boolean isNewInvite = SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_red.setVisibility(isNewInvite ? View.VISIBLE:View.GONE);
        //注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactChangeReceiver,new IntentFilter(Constant.CONTACT_CHANGED));
        mLBM.registerReceiver(ContactInviteChangeReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
       mLBM.registerReceiver(GroupInviteChangeReceiver,new IntentFilter(Constant.GROUP_INVITE_CHANGED));
        //邀请信息条目点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,false);
                Intent intent=new Intent(getActivity(),InviteActivity.class);
                startActivity(intent);
            }
        });
        //从环信服务器取出联系人
        getContactFromHx();
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.delete,menu);
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);
        hxid = easeUser.getUsername();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.contact_delete){

            deleteContact();

        }
        return super.onContextItemSelected(item);
    }

    private void deleteContact() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //从环信服务器删除
                    //从本地删除
                    EMClient.getInstance().contactManager().deleteContact(hxid);
                    Model.getInstance().getDBManager().getContactTableDao().deleteContactByHxId(hxid);
                   if(getActivity()==null){
                       return;
                   }
                    //刷新页面
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            refreshContact();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getContactFromHx() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> hxids = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if ((hxids!=null&&hxids.size()>=0)){
                        List<UserInfo> contacts=new ArrayList<UserInfo>();
                        for(String hxid:hxids){
                            UserInfo user= new UserInfo(hxid);
                            contacts.add(user);
                        }
                        //保存联系人到本地数据库
                        Model.getInstance().getDBManager().getContactTableDao().saveContacts(contacts,true);
                        //刷新页面
                        if(getActivity()==null){
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshContact();
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshContact() {
        List<UserInfo> contacts = Model.getInstance().getDBManager().getContactTableDao().getContacts();
        //转换
        if ((contacts!=null&&contacts.size()>=0)){
            Map<String, EaseUser> EaseUsers=new HashMap<>();
           for (UserInfo contact:contacts){
               EaseUser easeUser = new EaseUser(contact.getHxid());
               EaseUsers.put(contact.getHxid(),easeUser);
           }
            setContactsMap(EaseUsers);
            //环信自身方法
            refresh();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);//关闭广播
        mLBM.unregisterReceiver(ContactChangeReceiver);//关闭广播
    }


}
