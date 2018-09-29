package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.bigkoo.alertview.AlertView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ApplyJoinBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 申请加入门店Adapter
 * Created by Jia on 2018/3/13.
 */

public class ApplyJoinShopAdapter extends RecyclerView.Adapter<ApplyJoinShopAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ApplyJoinBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
        void onAgreeClick(int position);
        void onRefuseItemClick(int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public ApplyJoinShopAdapter(Context context, List<ApplyJoinBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_apply_join_shop,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.image.setImageURI(Uri.parse(list.get(position).getAvatar()));
        holder.txtName.setText(list.get(position).getNick_name());
        holder.txtShopName.setText("申请加入"+list.get(position).getShop_name());
        if(list.get(position).getStatus().equals("1")){
            holder.txtAgree.setVisibility(View.VISIBLE);
            holder.txtRefuse.setVisibility(View.VISIBLE);
            holder.txtStatus.setVisibility(View.GONE);
        }else {
            holder.txtAgree.setVisibility(View.GONE);
            holder.txtRefuse.setVisibility(View.GONE);
            holder.txtStatus.setVisibility(View.VISIBLE);
            holder.txtStatus.setText(list.get(position).getStatus().equals("2")?"已添加":"已拒绝");
        }

        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.rightMenu != null) {
                holder.txtAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onAgreeClick(position);
                    }
                });
                holder.txtRefuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onRefuseItemClick(position);
                    }
                });
                holder.rightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                                AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int pos) {
                                if (pos == 0) {
                                    mOnItemClickListener.onRightItemClick(v,position);
                                    holder.mSwipeItemLayout.close();
                                }
                            }
                        }).show();
                    }
                });
            }
        }
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
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_shop_name)
        TextView txtShopName;
        @BindView(R.id.txt_agree)
        TextView txtAgree;
        @BindView(R.id.txt_refuse)
        TextView txtRefuse;
        @BindView(R.id.txt_status)
        TextView txtStatus;

        @BindView(R.id.right_menu)
        TextView rightMenu;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
