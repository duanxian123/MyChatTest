package com.linghao.mychattest.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMGroup;
import com.linghao.mychattest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linghao on 2017/7/25.
 * QQ：782695971
 * 作用：
 */

public class GroupListAdapter extends BaseAdapter {
    private  Context mContext;
    private List<EMGroup> mGroups=new ArrayList<>();
    public GroupListAdapter(Context context){
        mContext=context;
    }
    public  void refresh(List<EMGroup> groups){
        mGroups.clear();
        mGroups.addAll(groups);
        notifyDataSetChanged();


    }
    @Override
    public int getCount() {
        return mGroups==null? 0:mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {viewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_grouplist, null);
            viewHolder.name= (TextView) convertView.findViewById(R.id.tv_grouplist_name);
            convertView.setTag(viewHolder);


        }
        else{
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        EMGroup emGroup=mGroups.get(position);
        viewHolder.name.setText(emGroup.getGroupName());
        return convertView;
    }
    private class ViewHolder{
        TextView name;
    }
}
