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
import com.maapuu.mereca.bean.HairStylistBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class AttectionHairStylistAdapter extends RecyclerView.Adapter<AttectionHairStylistAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<HairStylistBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public AttectionHairStylistAdapter(Context context, List<HairStylistBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_attection_hair_stylist_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.image.setImageURI(Uri.parse(list.get(position).getStaff_avatar()));
        holder.txtName.setText(list.get(position).getStaff_name());
        holder.txtFansNum.setText("粉丝 "+list.get(position).getFans_num());
        holder.txtWorksNum.setText("作品 "+list.get(position).getWorks_num());
        holder.txtPostName.setText(list.get(position).getPost_name());
        holder.txtShopName.setText(list.get(position).getShop_name());
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
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_fans_num)
        TextView txtFansNum;
        @BindView(R.id.txt_works_num)
        TextView txtWorksNum;
        @BindView(R.id.txt_post_name)
        TextView txtPostName;
        @BindView(R.id.txt_shop_name)
        TextView txtShopName;

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
