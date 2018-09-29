package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.PostTempBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 岗位设置Adapter
 * Created by Jia on 2018/3/8.
 */

public class JobSetAdapter extends RecyclerView.Adapter<JobSetAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<PostTempBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position, String tag);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public JobSetAdapter(Context context, List<PostTempBean> list){
        this.mContext = context;
        this.list = list;
    }

    public JobSetAdapter(Context context){
        this(context, new ArrayList<PostTempBean>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_post_set,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        PostTempBean bean = list.get(position);
        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.rightMenu1 != null) {
                holder.rightMenu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onRightItemClick(v,position,holder.rightMenu1.getText().toString());
                        holder.mSwipeItemLayout.close();
                    }
                });
            }

            if (holder.rightMenu2 != null) {
                holder.rightMenu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onRightItemClick(v,position,holder.rightMenu2.getText().toString());
                        holder.mSwipeItemLayout.close();
                    }
                });
            }
        }

        holder.titleTv.setText(bean.getPost_temp_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.right_menu_1)
        TextView rightMenu1;
        @BindView(R.id.right_menu_2)
        TextView rightMenu2;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        @BindView(R.id.js_title_tv)
        TextView titleTv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 插入一项
     *
     * @param item
     * @param position
     */
    public void add(PostTempBean item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一项
     *
     * @param position 删除位置
     */
    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addList(List<PostTempBean> data) {
        if(data != null){
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<PostTempBean> getList() {
        return list;
    }
}
