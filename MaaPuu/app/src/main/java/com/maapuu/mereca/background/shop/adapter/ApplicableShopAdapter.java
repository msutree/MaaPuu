package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适用店铺Adapter
 * Created by Jia on 2018/3/13.
 */

public class ApplicableShopAdapter extends RecyclerView.Adapter<ApplicableShopAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ShopBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public ApplicableShopAdapter(Context context, List<ShopBean> list){
        this.mContext = context;
        this.list = list;
    }

    public ApplicableShopAdapter(Context context){
        this(context,new ArrayList<ShopBean>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_applicable_shop,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ShopBean bean = list.get(position);
        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.rightMenu != null) {
                holder.rightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onRightItemClick(v,position);
                        holder.mSwipeItemLayout.close();
                    }
                });
            }
        }

        UIUtils.loadImg(mContext,bean.getShop_logo(),holder.icon);
        holder.shopNameTv.setText(bean.getShop_name());
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

        @BindView(R.id.right_menu)
        TextView rightMenu;

        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        @BindView(R.id.asc_icon)
        CircleImgView icon;
        @BindView(R.id.asc_shop_name_tv)
        TextView shopNameTv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addList(List<ShopBean> data) {
        if(data != null){
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<ShopBean> getList() {
        return list;
    }

}
