package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.bigkoo.alertview.AlertView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.BannerBean;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺装饰 轮播图Adapter
 * Created by Jia on 2018/3/13.
 */

public class DecorateBannerAdapter extends RecyclerView.Adapter<DecorateBannerAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<BannerBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public DecorateBannerAdapter(Context context, List<BannerBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_decorate_banner,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.image.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        holder.image.setLayoutParams(lp);
        UIUtils.loadImg(mContext,list.get(position).getAdv_img(),holder.image);
        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.rightMenu != null) {
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
        ImageView image;

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
