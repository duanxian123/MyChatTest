package com.linghao.mychattest.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.HopeInvited;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linghao on 2017/7/28.
 * QQ：782695971
 * 作用：
 */

public class anotherHomeAdapter extends RecyclerView.Adapter<anotherHomeAdapter.ViewHolder> {
    private Context mContext;
    private List<HopeInvited> mList=new ArrayList<>();

static class ViewHolder extends RecyclerView.ViewHolder{
    Button bt_invited;
    TextView anotherhome_content;
    TextView username;
    TextView time;
    public ViewHolder(View view) {
        super(view);
        anotherhome_content = (TextView)view.findViewById( R.id.anotherhome_content );
        username = (TextView)view.findViewById( R.id.username );
        time = (TextView)view.findViewById( R.id.time );
        bt_invited = (Button)view.findViewById( R.id.bt_invited);

    }
}

    public anotherHomeAdapter(Context context, List<HopeInvited> list) {
        mContext=context;
        mList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.another_home_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.anotherhome_content.setText(mList.get(position).getContent());
        holder.username.setText(mList.get(position).getUsername());
        holder.time.setText(mList.get(position).getCreatedAt());
        holder.bt_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String myName=EMClient.getInstance().getCurrentUser();
                    EMClient.getInstance().contactManager().addContact(mList.get(position).getUsername(),myName+"请求添加您为好友");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
