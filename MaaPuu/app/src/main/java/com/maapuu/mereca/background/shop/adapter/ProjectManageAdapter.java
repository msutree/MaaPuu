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
import com.maapuu.mereca.background.shop.bean.ProjectItemBean;
import com.maapuu.mereca.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目管理Adapter
 * Created by Jia on 2018/3/8.
 */

public class ProjectManageAdapter extends RecyclerView.Adapter<ProjectManageAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ProjectItemBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position, String tag);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public ProjectManageAdapter(Context context, List<ProjectItemBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_project_manage,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtName.setText(list.get(position).getItem_name());
        if(StringUtils.isEmpty(list.get(position).getEnd_time()) || list.get(position).getEnd_time().startsWith("-")){
            holder.txtTime.setVisibility(View.GONE);
        }else {
            holder.txtTime.setVisibility(View.VISIBLE);
            holder.txtTime.setText("距结束"+list.get(position).getEnd_time());
        }
        if(list.get(position).getShelf_status() == 1){
            holder.txtName.setSelected(true);
            holder.rightMenu1.setText("下架");
        }else {
            holder.txtName.setSelected(false);
            holder.rightMenu1.setText("上架");
        }
        if(list.get(position).getIs_top() == 1){
            holder.iv.setVisibility(View.VISIBLE);
            holder.rightMenu2.setText("取消\n置顶");
        }else {
            holder.iv.setVisibility(View.GONE);
            holder.rightMenu2.setText("置顶");
        }
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

            if (holder.rightMenu3 != null) {
                holder.rightMenu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                                AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int pos) {
                                if (pos == 0) {
                                    mOnItemClickListener.onRightItemClick(v,position,holder.rightMenu3.getText().toString());
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
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.pmc_project_name_tv)
        TextView txtName;
        @BindView(R.id.pmc_time_tv)
        TextView txtTime;

        @BindView(R.id.right_menu_1)
        TextView rightMenu1;
        @BindView(R.id.right_menu_2)
        TextView rightMenu2;
        @BindView(R.id.right_menu_3)
        TextView rightMenu3;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
