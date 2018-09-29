package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.recycleviewutil.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by itservice on 2017/7/5.
 */

public class SrvAdapter extends RecyclerView.Adapter<SrvAdapter.ViewHolder> implements ItemTouchHelperAdapter,View.OnClickListener {
    private Context mContext;
    private List<SrvBean> list;

    @Override
    public void onClick(View view) {
        mOnItemClickListener.onItemClick(view);
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public SrvAdapter(Context context, List<SrvBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_project_contain_service, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getSrv_name());
        holder.cb.setChecked(list.get(position).isChecked());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        /**
         * 在这里进行给原数组数据的移动
         */
        Collections.swap(list, fromPosition, toPosition);
        /**
         * 通知数据移动
         */
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwipe(int position) {
        /**
         * 原数据移除数据
         */
        list.remove(position);
        /**
         * 通知移除
         */
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private CheckBox cb;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.txt_name);
            cb = (CheckBox) itemView.findViewById(R.id.cr_cb);
        }
    }
}
