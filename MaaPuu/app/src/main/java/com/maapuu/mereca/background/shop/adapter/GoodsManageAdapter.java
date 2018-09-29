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
import com.maapuu.mereca.background.shop.bean.GoodsBean;
import com.maapuu.mereca.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品管理Adapter
 * Created by Jia on 2018/3/8.
 */

public class GoodsManageAdapter extends RecyclerView.Adapter<GoodsManageAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<GoodsBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position, String tag);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public GoodsManageAdapter(Context context, List<GoodsBean> list){
        this.mContext = context;
        this.list = list;
    }

    public GoodsManageAdapter(Context context){
        this(context,new ArrayList<GoodsBean>());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_goods_manage,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GoodsBean bean = list.get(position);
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
        holder.itemNameTv.setSelected(bean.getShelf_status()==1?true:false);
        holder.timeTv.setSelected(bean.getShelf_status()==1?true:false);
        holder.rightMenu1.setText(bean.getShelf_status()==1?"下架":"上架");//1 上架  2 下架
        holder.rightMenu2.setText(bean.getIs_top()==1?"取消置顶":"置顶");//1置顶，0非置顶
        holder.zdIv.setVisibility(bean.getIs_top()==1?View.VISIBLE:View.GONE);
        holder.rightMenu3.setText("删除");

        holder.itemNameTv.setText(bean.getItem_name());
        if(StringUtils.isEmpty(list.get(position).getEnd_time()) || list.get(position).getEnd_time().startsWith("-")){
            holder.timeTv.setVisibility(View.GONE);
        }else {
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText("距结束"+list.get(position).getEnd_time());
        }
//        holder.timeTv.setText(!TextUtils.isEmpty(bean.getEnd_time())?"距结束"+bean.getEnd_time():"");
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
        @BindView(R.id.right_menu_3)
        TextView rightMenu3;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        @BindView(R.id.gmc_zd_iv)
        ImageView zdIv;
        @BindView(R.id.gmc_project_name_tv)
        TextView itemNameTv;
        @BindView(R.id.gmc_time_tv)
        TextView timeTv;

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
    public void add(GoodsBean item, int position) {
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

    public void addList(List<GoodsBean> data) {
        if(data != null){
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<GoodsBean> getList() {
        return list;
    }
}
