package com.linghao.mychattest.controller.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.activity.ChongzhiActivity;
import com.linghao.mychattest.controller.activity.GestureEditActivity;
import com.linghao.mychattest.controller.activity.GestureVerifyActivity;
import com.linghao.mychattest.controller.activity.LoginActivity;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.utils.CacheUtils;
import com.linghao.mychattest.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.id.input;
import static com.linghao.mychattest.R.id.chongzhi;

/**
 * Created by linghao on 2017/7/22.
 * QQ：782695971
 * 作用：设置页面碎片
 */

public class SettingFragment extends Fragment {
    @InjectView(R.id.toggle_more_secret)
    ToggleButton toggleMoreSecret;
    @InjectView(R.id.ll_more_reset)
    LinearLayout llMoreReset;
    @InjectView(R.id.ll_more_contact)
    LinearLayout llMoreContact;
    private Button bt_setting_out;
    private TextView tv_chongzhi;
    private SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        iniView(view);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void iniView(View view) {
        bt_setting_out = (Button) view.findViewById(R.id.bt_setting_out);
        tv_chongzhi = (TextView) view.findViewById(chongzhi);

    }

    private void initData() {
        sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        boolean isOpen = sp.getBoolean("isOpen", false);
        toggleMoreSecret.setChecked(isOpen);
        //是否开启手势密码
        isOpenToggle();
        //重置手势密码
        chongzhipsw();
        tv_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChongzhiActivity.class);
                startActivity(intent);
            }
        });
        bt_setting_out.setText("退出登录（" + EMClient.getInstance().getCurrentUser() + ")");
        bt_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Model.getInstance().getDBManager().close();
                                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "退出失败", Toast.LENGTH_SHORT).show();

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
        });
    }

    private void chongzhipsw() {
        llMoreReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = toggleMoreSecret.isChecked();
                if (!checked){
                    Toast.makeText(getActivity(), "您尚未开启手势密码，请先开启", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("重置手势密码")
                            .setMessage("是否重置手势密码")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), GestureEditActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }

            }
        });
    }

    private void isOpenToggle() {
        toggleMoreSecret.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String inputCode = sp.getString("inputCode", "");
                    LogUtil.e(inputCode+"-------");
                    if (TextUtils.isEmpty(inputCode)) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("开启手势密码")
                                .setMessage("是否确定开启手势密码")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getActivity(), "现在设置手势密码", Toast.LENGTH_SHORT).show();
                                        sp.edit().putBoolean("isOpen", true).commit();
                                        Intent intent = new Intent(getActivity(), GestureVerifyActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sp.edit().putBoolean("isOpen", false).commit();
                                        Toast.makeText(getActivity(), "关闭手势密码", Toast.LENGTH_SHORT).show();
                                        toggleMoreSecret.setChecked(false);
                                    }
                                })
                                .show();
                    } else {
                        //CacheUtils.putBoolean(getActivity(), "isOpen", true);
                        sp.edit().putBoolean("isOpen", true).commit();
                        toggleMoreSecret.setChecked(true);
                        Toast.makeText(getActivity(), "开启手势密码", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    sp.edit().putBoolean("isOpen", false).commit();
                    //CacheUtils.putBoolean(getActivity(), "isOpen", false);
                    Toast.makeText(getActivity(), "关闭手势密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
