package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ContainItemsBean;
import com.maapuu.mereca.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ContainItemsBean> list;

    public static interface OnRecyclerViewItemClickListener {
        void onMinusClick(int position);
        void onPlusClick(int position);
        void onTextChange(int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public SelectItemAdapter(Context context, List<ContainItemsBean> list){
        this.mContext = context;
        this.list = list;
    }

    public List<ContainItemsBean> getList(){
        return list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_select_items_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtName.setText(list.get(position).getItem_name());
        // 1.0移除监听
        if (holder.etNum.getTag() instanceof TextSwitcher) {
            holder.etNum.removeTextChangedListener(
                    (TextSwitcher) holder.etNum.getTag());
        }
        // 2.0设置内容
        holder.etNum.setText(list.get(position).getNum()+"");
        // 3.0添加文本监听
        TextSwitcher switcher = new TextSwitcher(position);
        holder.etNum.addTextChangedListener(switcher);
        holder.etNum.setTag(switcher);

        if (mOnItemClickListener != null) {
            holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onMinusClick(position);
                }
            });
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onPlusClick(position);
                }
            });
        }
    }

    class TextSwitcher implements TextWatcher {

        private int position;

        public TextSwitcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !StringUtils.isEmpty(s.toString())) {
                list.get(position).setNum(Integer.parseInt(s.toString()));
            }else {
                list.get(position).setNum(0);
            }
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onTextChange(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {}

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.iv_minus)
        ImageView ivMinus;
        @BindView(R.id.et_num)
        EditText etNum;
        @BindView(R.id.iv_add)
        ImageView ivAdd;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
