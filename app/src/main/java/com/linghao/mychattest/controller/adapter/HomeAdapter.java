package com.linghao.mychattest.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.linghao.mychattest.R;
import com.linghao.mychattest.controller.activity.addGroupActivity;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.utils.Constant;
import com.linghao.mychattest.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.linghao.mychattest.R.id.et_groupid;

/**
 * Created by linghao on 2017/7/27.
 * QQ：782695971
 * 作用：
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private String mType;
    private List<Recruit> mList=new ArrayList<>();

    static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        Button btRequest;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById( R.id.title );
            content = (TextView)view.findViewById( R.id.content );
            btRequest = (Button)view.findViewById( R.id.bt_request );
        }
    }
    public HomeAdapter(Context context,List<Recruit> list){
        mContext=context;
        mList=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Recruit recruit=mList.get(position);
        holder.title.setText(recruit.getTitle());
        holder.content.setText(recruit.getContent());
        holder.btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<mList.size();i++){
                    LogUtil.e("aaaaaa"+mList.get(position).toString());
                }
                final String groupId = recruit.getGroupId();
                LogUtil.e(groupId+"");
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().applyJoinToGroup(groupId, "求加入");
            LogUtil.e("申请成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Toast.makeText(mContext, "申请中", Toast.LENGTH_SHORT).show();
//                BmobQuery<Recruit> query = new BmobQuery<Recruit>();
//                LogUtil.e("1-----------");
//                query.addQueryKeys("groupId");
//                LogUtil.e("2------------");
//                final String[] mGroupId = new String[1];
//                query.findObjects(new FindListener<Recruit>() {
//                    @Override
//                    public void done(List<Recruit> list, BmobException e) {
//                        if (e == null) {
//                            for (int i = 0; i < list.size(); i++) {
//                                LogUtil.e(list.get(i).toString());
//                            }
//                            mGroupId[0] =list.get(position).getGroupId();
//                            LogUtil.e(mGroupId[0]);
//                            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        LogUtil.e(mGroupId[0]+"22222222222222222");
//                                        EMClient.getInstance().groupManager().applyJoinToGroup(mGroupId[0], "求加入");
//                                        Log.e("---------------*******","success");
//                                    } catch (HyphenateException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        } else {
//                            Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                        }
//                    }
//                });


            }
        });
    }



    @Override
    public int getItemCount() {
        return mList==null ?  0:mList.size();
    }

}
