package com.maapuu.mereca.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.GoodsDetailActivity;
import com.maapuu.mereca.activity.ShoppingCartActivity;
import com.maapuu.mereca.bean.CartBean;
import com.maapuu.mereca.bean.CartGoodsBean;
import com.maapuu.mereca.bean.EvlBean;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.EmojiUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.ReplyDialog;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class ShoppingCartRecyclerAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerAdapter.MyViewHolder>{
    private Context mContext;
    private List<CartBean> list;
    private BaseRecyclerAdapter<CartGoodsBean> childAdapter;

    public static interface OnRecyclerViewItemClickListener {
        void onChooseClick(int position);
        void onChooseItemClick(int position, int pos);
        void onMinusClick(int position, int pos);
        void onPlusClick(int position, int pos);
        void onLlClick(int position, int pos);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public ShoppingCartRecyclerAdapter(Context context, List<CartBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cart_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtShopName.setText(list.get(position).getShop_name());
        holder.ivChoose.setSelected(list.get(position).isBool());
        if(list.get(position).getDetail() == null || list.get(position).getDetail().size() == 0){
            holder.recyclerView.setVisibility(View.GONE);
        }else {
            holder.recyclerView.setVisibility(View.VISIBLE);
//            childAdapter = new BaseRecyclerAdapter<CartGoodsBean>(mContext,list.get(position).getDetail(),R.layout.layout_cart_goods_item) {
//                @Override
//                public void convert(BaseRecyclerHolder cHolder, CartGoodsBean child, final int pos, boolean isScrolling) {
//                    if(pos == list.get(position).getDetail().size() - 1){
//                        cHolder.setVisible(R.id.line,false);
//                    }else {
//                        cHolder.setVisible(R.id.line,true);
//                    }
//                    cHolder.setSelected(R.id.iv_choose,list.get(position).getDetail().get(pos).getIs_check() == 1);
//                    cHolder.setSimpViewImageUri(R.id.iv_goods, Uri.parse(list.get(position).getDetail().get(pos).getItem_img()));
//                    cHolder.setText(R.id.txt_goods_name,list.get(position).getDetail().get(pos).getItem_name()+"");
//                    cHolder.setText(R.id.txt_goods_spec,list.get(position).getDetail().get(pos).getItem_specification()+"");
//                    cHolder.setText(R.id.txt_goods_price,"¥"+list.get(position).getDetail().get(pos).getPrice());
//                    cHolder.setText(R.id.txt_goods_num,list.get(position).getDetail().get(pos).getNum()+"");
//
//                    cHolder.setOnClickListener(R.id.iv_minus, new MyListener(position,pos));
//                    cHolder.setOnClickListener(R.id.iv_add, new MyListener(position,pos));
//                    cHolder.setOnClickListener(R.id.iv_choose, new MyListener(position,pos));
//                    cHolder.setOnClickListener(R.id.ll, new MyListener(position,pos));
//                }
//            };
            holder.recyclerView.setAdapter(new BaseRecyclerAdapter<CartGoodsBean>(mContext,list.get(position).getDetail(),R.layout.layout_cart_goods_item) {
                @Override
                public void convert(BaseRecyclerHolder cHolder, CartGoodsBean child, final int pos, boolean isScrolling) {
                    if(pos == list.get(position).getDetail().size() - 1){
                        cHolder.setVisible(R.id.line,false);
                    }else {
                        cHolder.setVisible(R.id.line,true);
                    }
                    cHolder.setSelected(R.id.iv_choose,list.get(position).getDetail().get(pos).getIs_check() == 1);
                    cHolder.setSimpViewImageUri(R.id.iv_goods, Uri.parse(list.get(position).getDetail().get(pos).getItem_img()));
                    cHolder.setText(R.id.txt_goods_name,list.get(position).getDetail().get(pos).getItem_name()+"");
                    cHolder.setText(R.id.txt_goods_spec,list.get(position).getDetail().get(pos).getItem_specification()+"");
                    cHolder.setText(R.id.txt_goods_price,"¥"+list.get(position).getDetail().get(pos).getPrice());
                    cHolder.setText(R.id.txt_goods_num,list.get(position).getDetail().get(pos).getNum()+"");

                    cHolder.setOnClickListener(R.id.iv_minus, new MyListener(position,pos));
                    cHolder.setOnClickListener(R.id.iv_add, new MyListener(position,pos));
                    cHolder.setOnClickListener(R.id.iv_choose, new MyListener(position,pos));
                    cHolder.setOnClickListener(R.id.ll, new MyListener(position,pos));
                }
            });
        }
        holder.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onChooseClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyListener implements View.OnClickListener {
        private int position;
        private int pos;
        public MyListener(int position,int pos) {
            this.position = position;
            this.pos = pos;
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.iv_minus:
                    if(list.get(position).getDetail().get(pos).getNum() == 1){
                        ToastUtil.show(mContext,"商品数量不能为0");
                        return;
                    }
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onMinusClick(position,pos);
                    }
                    break;
                case R.id.iv_add:
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onPlusClick(position,pos);
                    }
                    break;
                case R.id.iv_choose:
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onChooseItemClick(position,pos);
                    }
                    break;
                case R.id.ll:
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onLlClick(position,pos);
                    }
                    break;
            }
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_shop_name)
        TextView txtShopName;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        @BindView(R.id.recycler_view_goods)
        RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        }
    }
}
