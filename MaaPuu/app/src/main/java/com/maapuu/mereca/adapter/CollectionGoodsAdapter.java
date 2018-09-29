package com.maapuu.mereca.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.GoodsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class CollectionGoodsAdapter extends RecyclerView.Adapter<CollectionGoodsAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<GoodsBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public CollectionGoodsAdapter(Context context, List<GoodsBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_collection_goods_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ivGoods.setImageURI(Uri.parse(list.get(position).getItem_img()));
        holder.txtGoodsName.setText(list.get(position).getItem_name());
        holder.txtPrice.setText("¥"+list.get(position).getPrice());
        holder.txtSaleNum.setText("已售"+list.get(position).getSale_num()+"件");

        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.txtRightMenu != null) {
                holder.txtRightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onRightItemClick(v,position);
                        holder.mSwipeItemLayout.close();
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
        @BindView(R.id.iv_goods)
        SimpleDraweeView ivGoods;
        @BindView(R.id.txt_goods_name)
        TextView txtGoodsName;
        @BindView(R.id.txt_price)
        TextView txtPrice;
        @BindView(R.id.txt_sale_num)
        TextView txtSaleNum;

        @BindView(R.id.txt_right_menu)
        TextView txtRightMenu;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
