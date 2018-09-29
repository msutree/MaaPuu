package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.bigkoo.alertview.AlertView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类别Adapter
 * Created by Jia on 2018/3/13.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<CatalogBean> list;
    private AlertView alertView;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick1(View v, int position);
        void onRightItemClick2(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public CategoryAdapter(Context context, List<CatalogBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_category_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        UIUtils.loadImg(mContext,list.get(position).getCatalog_img(),holder.image);
        holder.txtName.setText(list.get(position).getCatalog_name());

        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(this);

            if (holder.rightMenu1 != null) {
                holder.rightMenu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        mOnItemClickListener.onRightItemClick1(v,position);
                        holder.mSwipeItemLayout.close();
                    }
                });
            }
            if (holder.rightMenu2 != null) {
                holder.rightMenu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        alertView = new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                                AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int pos) {
                                if (pos == 0) {
                                    mOnItemClickListener.onRightItemClick2(v,position);
                                    holder.mSwipeItemLayout.close();
                                }
                            }
                        });
                        alertView.show();
                    }
                });
            }
        }
    }

    public AlertView getAlertView(){
        return alertView;
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
        @BindView(R.id.txt_name)
        TextView txtName;

        @BindView(R.id.right_menu_1)
        TextView rightMenu1;
        @BindView(R.id.right_menu_2)
        TextView rightMenu2;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
