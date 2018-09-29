package com.maapuu.mereca.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.CircleBean;
import com.maapuu.mereca.util.EmojiUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class CollectionArticleAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private List<CircleBean> list;

    //定义三种常量  表示三种条目类型
    public static final int TYPE_PULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGE = 2;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onRightItemClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public CollectionArticleAdapter(Context context, List<CircleBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_PULL_IMAGE:
                View v = mInflater.inflate(R.layout.layout_collection_article_item1, parent, false);
                holder = new ViewHolder1(v);
                v.setOnClickListener(this);
                break;
            case TYPE_RIGHT_IMAGE:
                View v1 = mInflater.inflate(R.layout.layout_collection_article_item2, parent, false);
                holder = new ViewHolder2(v1);
                v1.setOnClickListener(this);
                break;
            case TYPE_THREE_IMAGE:
                View v2 = mInflater.inflate(R.layout.layout_collection_article_item3, parent, false);
                holder = new ViewHolder3(v2);
                v2.setOnClickListener(this);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder1){
            ((ViewHolder1) holder).txtTitle.setText(EmojiUtil.utf8ToString(list.get(position).getCircle_title()));
            ((ViewHolder1) holder).ivImage1.setImageURI(Uri.parse(list.get(position).getDetail().get(0).getContent()));
            ((ViewHolder1) holder).ivImage2.setImageURI(Uri.parse(list.get(position).getDetail().get(1).getContent()));
            ((ViewHolder1) holder).ivImage3.setImageURI(Uri.parse(list.get(position).getDetail().get(2).getContent()));
            ((ViewHolder1) holder).txtCommentNum.setText(list.get(position).getComment_num());
            ((ViewHolder1) holder).txtTime.setText(list.get(position).getTime_text());
            ((ViewHolder1) holder).txtZan.setVisibility(View.GONE);

            ((ViewHolder1)holder).mSwipeItemLayout.setSwipeEnable(true);
            if (mOnItemClickListener != null) {
                ((ViewHolder1)holder).itemView.setOnClickListener(this);

                if (((ViewHolder1)holder).txtRightMenu != null) {
                    ((ViewHolder1)holder).txtRightMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onRightItemClick(v,position);
                            ((ViewHolder1)holder).mSwipeItemLayout.close();
                        }
                    });
                }
            }
        }else if(holder instanceof ViewHolder2){
            ((ViewHolder2) holder).txtTitle.setText(EmojiUtil.utf8ToString(list.get(position).getCircle_title()));
            ((ViewHolder2) holder).ivImage1.setImageURI(Uri.parse(list.get(position).getDetail().get(0).getContent()));
            ((ViewHolder2) holder).txtCommentNum.setText(list.get(position).getComment_num());
            ((ViewHolder2) holder).txtTime.setText(list.get(position).getTime_text());
            ((ViewHolder2) holder).txtZan.setText(list.get(position).getPraise_num());
            ((ViewHolder2) holder).txtZan.setVisibility(View.GONE);

            ((ViewHolder2)holder).mSwipeItemLayout.setSwipeEnable(true);
            if (mOnItemClickListener != null) {
                ((ViewHolder2)holder).itemView.setOnClickListener(this);

                if (((ViewHolder2)holder).txtRightMenu != null) {
                    ((ViewHolder2)holder).txtRightMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onRightItemClick(v,position);
                            ((ViewHolder2)holder).mSwipeItemLayout.close();
                        }
                    });
                }
            }
        }else {
            ((ViewHolder3) holder).txtTitle.setText(EmojiUtil.utf8ToString(list.get(position).getCircle_title()));
            if(list.get(position).getDetail().size() != 0){
                ((ViewHolder3) holder).llImage.setVisibility(View.VISIBLE);
                ((ViewHolder3) holder).ivImage1.setImageURI(Uri.parse(list.get(position).getDetail().get(0).getContent()));
                ((ViewHolder3) holder).ivImage2.setImageURI(Uri.parse(list.get(position).getDetail().get(1).getContent()));
            }else {
                ((ViewHolder3) holder).llImage.setVisibility(View.GONE);
            }
            ((ViewHolder3) holder).txtCommentNum.setText(list.get(position).getComment_num());
            ((ViewHolder3) holder).txtTime.setText(list.get(position).getTime_text());
            ((ViewHolder3) holder).txtZan.setVisibility(View.GONE);
            
            ((ViewHolder3)holder).mSwipeItemLayout.setSwipeEnable(true);
            if (mOnItemClickListener != null) {
                ((ViewHolder3)holder).itemView.setOnClickListener(this);

                if (((ViewHolder3)holder).txtRightMenu != null) {
                    ((ViewHolder3)holder).txtRightMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onRightItemClick(v,position);
                            ((ViewHolder3)holder).mSwipeItemLayout.close();
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //根据条件返回条目的类型
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getDetail().size() == 0) {
            return TYPE_THREE_IMAGE;
        } else if (list.get(position).getDetail().size()== 1) {
            return TYPE_RIGHT_IMAGE;
        } else if (list.get(position).getDetail().size()== 2) {
            return TYPE_THREE_IMAGE;
        } else{
            return TYPE_PULL_IMAGE;
        }
    }


    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.iv_image_1)
        SimpleDraweeView ivImage1;
        @BindView(R.id.iv_image_2)
        SimpleDraweeView ivImage2;
        @BindView(R.id.iv_image_3)
        SimpleDraweeView ivImage3;
        @BindView(R.id.txt_comment_num)
        TextView txtCommentNum;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_zan)
        TextView txtZan;
        
        @BindView(R.id.txt_right_menu)
        TextView txtRightMenu;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;
        public ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.iv_image_1)
        SimpleDraweeView ivImage1;
        @BindView(R.id.txt_comment_num)
        TextView txtCommentNum;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_zan)
        TextView txtZan;
        
        @BindView(R.id.txt_right_menu)
        TextView txtRightMenu;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;
        public ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    class ViewHolder3 extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.ll_image)
        LinearLayout llImage;
        @BindView(R.id.iv_image_1)
        SimpleDraweeView ivImage1;
        @BindView(R.id.iv_image_2)
        SimpleDraweeView ivImage2;
        @BindView(R.id.txt_comment_num)
        TextView txtCommentNum;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_zan)
        TextView txtZan;
        
        @BindView(R.id.txt_right_menu)
        TextView txtRightMenu;
        @BindView(R.id.swipe_layout)
        SwipeItemLayout mSwipeItemLayout;
        public ViewHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
