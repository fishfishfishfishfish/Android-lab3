package com.chan.android_lab3;

import android.content.Context;
import com.chan.android_lab3.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 61915 on 17/10/21.
 */

public abstract class CommonAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<Map<String, Object>> mDatas;
    protected LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mOnItemClickListener = null;

    public CommonAdapter(Context context, int layout, List<Map<String, Object>> datas){
        mContext = context;
        mLayoutId = layout;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        convert(holder, mDatas.get(position));
    }
    @Override
    public int getItemCount(){ return mDatas.size(); }

    public abstract void convert(ViewHolder holder, Map<String, Object> s);
}
