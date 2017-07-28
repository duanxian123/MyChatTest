package com.linghao.mychattest.controller.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.utils.LogUtil;

import java.sql.Time;
import java.util.Calendar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.id;

public class WriteRecruitActivity extends Activity {
    private EditText etTitle;
    private EditText etContent;
    private TextView tvTime;
    private EditText etMaxperson;
    private TextView tv_Type;
    private TextView tv_data;
    private Button btOk;
    private Button bt_choose_type;
    private Button bt_choose_data;
    private Button bt_choose_time;
    private Button choose_maxperson;
    private String groupId;
    private String groupName;
    private String groupDesc;
    private String time;
    private String maxperson;
    private String type;
    private String groupOwner=EMClient.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, Constant.Bmobinit);
        setContentView(R.layout.activity_write_recruit);
        initView();
        initListener();
    }

    private void initListener() {
        choose_maxperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMaxPerson();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatGroup();
            }
        });
        bt_choose_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTextSizeDialog();
            }
        });

        bt_choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
               int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(WriteRecruitActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                   tvTime.setText("     "+String.format("%d:%d",hourOfDay,minute));
                    }
                },hour,minute,true).show();
            }
        });
        bt_choose_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(WriteRecruitActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_data.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                    }
                },year,month,day).show();
            }
        });
    }
int choosePerson=0;
    private void chooseMaxPerson() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择最大人数");
        final String[] items = new String[30];
        for(int i=0;i<30;i++){
            items[i]= String.valueOf(i);
        }

        dialog.setSingleChoiceItems(items, choosePerson, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosePerson=which;
            }

        });
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                etMaxperson.setText(items[choosePerson]);
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    private int choose=0;
private void ChangeTextSizeDialog() {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    dialog.setTitle("选择项目");
    final String[] items = {"篮球", "跑步", "旅游", "足球", "游泳"};

    dialog.setSingleChoiceItems(items, choose, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            choose=which;
        }

    });
    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            tv_Type.setText(items[choose]);
        }
    });
    dialog.setNegativeButton("取消", null);
    dialog.show();

}
//    private void initData() {
//        int Mysize = EMClient.getInstance().groupManager().getAllGroups().size();
//        for (int i=0;i<Mysize;i++){
//            EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(i);
//            LogUtil.e(emGroup.getGroupId());
//        }
//    }

    private void creatGroup() {
        // 群名称
        groupName = etTitle.getText().toString();
        // 群描述
        groupDesc = etContent.getText().toString();
         final String []members=new String[0];
        time = tvTime.getText().toString();
        maxperson = etMaxperson.getText().toString();
        type =tv_Type.getText().toString();
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器创建群
                // 参数一：群名称；参数二：群描述；参数三：群成员；参数四：原因；参数五：参数设置

                EMGroupManager.EMGroupOptions options = new EMGroupManager.EMGroupOptions();
                options.inviteNeedConfirm=true;
                options.maxUsers = 200;//群最多容纳多少人
                EMGroupManager.EMGroupStyle groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                options.style=groupStyle;
                try {
                    final EMGroup emgroup = EMClient.getInstance().groupManager().createGroup(groupName, groupDesc, members, "申请创群", options);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            groupId =emgroup.getGroupId();
                            LogUtil.e(groupId);
                            //同时保存进入bmob
                           Model.getInstance().getBmobDao().saveToBmob(groupName, groupId, groupOwner, maxperson,groupDesc, time, type);
                            Toast.makeText(WriteRecruitActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WriteRecruitActivity.this, "创建群失败"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    private void initView() {
        etTitle = (EditText)findViewById( R.id.et_title );
        etContent = (EditText)findViewById( R.id.et_content );
        tvTime = (TextView) findViewById( R.id.tv_time );
        etMaxperson = (EditText)findViewById( R.id.et_maxperson );
        tv_Type = (TextView) findViewById( R.id.tv_type );
        btOk = (Button)findViewById( R.id.bt_ok );
        bt_choose_type= (Button) findViewById(R.id.choose_type);
        bt_choose_data= (Button) findViewById(R.id.choose_data);
        bt_choose_time= (Button) findViewById(R.id.choose_time);
        tv_data= (TextView) findViewById(R.id.tv_data);
        choose_maxperson= (Button) findViewById(R.id.choose_maxperson);


    }
}


