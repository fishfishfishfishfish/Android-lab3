package com.chan.android_lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 61915 on 17/10/21.
 */

//public abstract class CommonAdapter extends RecyclerView.Adapter {
//    private List mDatas;
//    public CommonAdapter(Context context, int layout, List datas){
//        mDatas = datas;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
//    {
//        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
//        return viewHolder;
//    }
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position)
//    {
//        convert(holder, mDatas.get(position));
//    }
//    @Override
//    public int getItemCount(){ return mDatas.size(); }
//    @Override
//    public void convert(ViewHolder holder, Map<String, Objects> s)
//    {
//        TextView name = holder.getView(R.id.name);
//        name.setText(s.get("name").toString());
//        TextView first = holder.getView(R.id.first)
//    }
//}
