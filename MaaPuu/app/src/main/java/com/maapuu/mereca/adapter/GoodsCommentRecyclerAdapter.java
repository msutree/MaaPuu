package com.maapuu.mereca.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.EvlBean;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
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

public class GoodsCommentRecyclerAdapter extends RecyclerView.Adapter<GoodsCommentRecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<EvlBean> list;
    private boolean hasReplayBtn ;
    private ReplyDialog replyDialog;
    private BaseRecyclerAdapter<ImageTextBean> imgAdapter;

    public static interface OnRecyclerViewItemClickListener {
        void onZanClick(int position);
        void onReplyClick(int position,String content);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public GoodsCommentRecyclerAdapter(Context context, List<EvlBean> list,boolean hasReplayBtn){
        this.mContext = context;
        this.list = list;
        this.hasReplayBtn = hasReplayBtn;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_comment_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.image.setImageURI(Uri.parse(list.get(position).getAvatar()));
        holder.txtName.setText(list.get(position).getNick_name());
        holder.txtContent.setVisibility(StringUtils.isEmpty(list.get(position).getEvl_content())?View.GONE:View.VISIBLE);
        holder.txtContent.setText(EmojiUtil.utf8ToString(list.get(position).getEvl_content()));
        holder.txtTime.setText(list.get(position).getCreate_time().substring(0,10));
        if(!StringUtils.isEmpty(list.get(position).getIs_evl_praise())){
            if(list.get(position).getIs_evl_praise().equals("1")){
                holder.txtZan.setSelected(true);
            }else {
                holder.txtZan.setSelected(false);
            }
        }
        holder.txtGoodsSpec.setText(list.get(position).getOrder_title());
        holder.txtZan.setText(list.get(position).getPraise_num());
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getEvl_level()));
        if(list.get(position).getDetail() == null || list.get(position).getDetail().size() == 0){
            holder.recyclerView.setVisibility(View.GONE);
        }else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            imgAdapter = new BaseRecyclerAdapter<ImageTextBean>(mContext,list.get(position).getDetail(),R.layout.layout_goods_comment_image_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, ImageTextBean item, int position, boolean isScrolling) {
                    holder.setImage(R.id.image,item.getContent(),false);
//                    holder.setSimpViewImageUri(R.id.image, Uri.parse(item.getContent()));
                }
            };
            holder.recyclerView.setAdapter(imgAdapter);
            imgAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int pos) {
                    PictureSelectUtil.show((Activity)mContext,pos,list.get(position).getDetail());
                }
            });
        }
        if(hasReplayBtn){
            holder.txtZan.setVisibility(View.GONE);
            if(list.get(position).getIs_reply().equals("0")){
                holder.txtReply.setVisibility(View.VISIBLE);
            }else {
                holder.txtReply.setVisibility(View.GONE);
            }
        }else {
            holder.txtReply.setVisibility(View.GONE);
            holder.txtZan.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getIs_reply().equals("1")){
            holder.txtReplyContent.setVisibility(View.VISIBLE);
            holder.txtReplyContent.setText("商家回复："+EmojiUtil.utf8ToString(list.get(position).getReply_content()));
        }else {
            holder.txtReplyContent.setVisibility(View.GONE);
        }

        holder.txtReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyDialog = new ReplyDialog(mContext);
                replyDialog.setCanceledOnTouchOutside(true);
                replyDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        InputMethodManager inputMgr = (InputMethodManager) mContext
//                                .getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                        StringUtils.closeKeyBorder(mContext,holder.txtReplyContent);
                        replyDialog.dismiss();
                    }
                });
                replyDialog.setOnBtnCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(StringUtils.isEmpty(replyDialog.getContent())){
                            ToastUtil.show(mContext,"请输入评论");
                            return;
                        }
                        StringUtils.closeKeyBorder(mContext,v);
                        replyDialog.dismiss();
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onReplyClick(position,replyDialog.getContent());
                        }
                    }
                }).show();
            }
        });
        holder.txtZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onZanClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.ratingbar)
        ScaleRatingBar ratingBar;
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.txt_goods_spec)
        TextView txtGoodsSpec;
        @BindView(R.id.txt_reply)
        TextView txtReply;
        @BindView(R.id.txt_reply_content)
        TextView txtReplyContent;
        @BindView(R.id.txt_zan)
        TextView txtZan;
        @BindView(R.id.recycler_view_img)
        RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
            //这句就是添加我们自定义的分隔线
            recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                    ,mContext.getResources().getColor(R.color.white)));
        }
    }
}
