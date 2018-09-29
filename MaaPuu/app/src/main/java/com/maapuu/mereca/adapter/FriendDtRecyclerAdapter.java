package com.maapuu.mereca.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ArticleDetailActivity;
import com.maapuu.mereca.activity.VideoPlayActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.bean.MoBean;
import com.maapuu.mereca.bean.MoCommentBean;
import com.maapuu.mereca.bean.PraiseBean;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.DividerGridItemDecoration;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.EmojiUtil;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.SoftKeyBoardListener;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.ExpandableView;
import com.maapuu.mereca.view.ReplyDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/6/15.
 */

public class FriendDtRecyclerAdapter extends RecyclerView.Adapter<FriendDtRecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ReplyDialog replyDialog;
    private List<MoBean> list;
    private BaseRecyclerAdapter<ImageTextBean> imgAdapter;
    private BaseRecyclerAdapter<MoCommentBean> plAdapter;
    private BaseRecyclerAdapter<PraiseBean> txAdapter;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onIconClick(int position);
        void onZanClick(int position);
        void onDelClick(int position);
        void onCommentClick(int position,String content);
        void onChildCommentDelClick(int position,int pos);
        void onChildCommentClick(int position,int pos,String content);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public FriendDtRecyclerAdapter(Context context, List<MoBean> list){
        this.mContext = context;
        this.list = list;
        SoftKeyBoardListener.setListener((Activity) mContext, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {}

            @Override
            public void keyBoardHide(int height) {
                if(replyDialog != null){
                    replyDialog.dismiss();
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_friends_dt_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.ivIconSmall.setImageURI(Uri.parse(list.get(position).getAvatar()));
        UIUtils.loadImg(mContext,list.get(position).getAvatar(),holder.ivIconSmall,true);
        holder.txtName.setText(list.get(position).getNick_name());
        holder.txtExpand.setText(EmojiUtil.utf8ToString(list.get(position).getContent()));
        if(!StringUtils.isEmpty(list.get(position).getAddress_detail())){
            holder.txtAddress.setVisibility(View.VISIBLE);
            holder.txtAddress.setText(list.get(position).getAddress_detail());
        }else {
            holder.txtAddress.setVisibility(View.GONE);
        }
        holder.txtTime.setText(list.get(position).getTime_text());
        if(list.get(position).getIs_praise() == 1){
            holder.txtZan.setSelected(true);
        }else {
            holder.txtZan.setSelected(false);
        }
        if(list.get(position).getIs_delete() == 1){
            holder.txtDel.setVisibility(View.VISIBLE);
        }else {
            holder.txtDel.setVisibility(View.GONE);
        }
        holder.txtZan.setText(list.get(position).getPraise_num().equals("0")?"点赞":list.get(position).getPraise_num());

        if(list.get(position).getTrans_type().equals("0")){
            holder.llArticle.setVisibility(View.GONE);
            holder.recyclerViewImg.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.GONE);
            if(list.get(position).getDetail().size() == 0){
                holder.recyclerViewImg.setVisibility(View.GONE);
                holder.rlVideo.setVisibility(View.GONE);
            }else {
                if(list.get(position).getDetail().get(0).getContent_type().equals("2")){
                    holder.recyclerViewImg.setVisibility(View.VISIBLE);
                    holder.rlVideo.setVisibility(View.GONE);
                    imgAdapter = new BaseRecyclerAdapter<ImageTextBean>(mContext,list.get(position).getDetail(),R.layout.layout_goods_comment_image_item) {
                        @Override
                        public void convert(BaseRecyclerHolder holder, ImageTextBean item, int position, boolean isScrolling) {
                            holder.setImage(R.id.image,item.getContent(),false);
//                            holder.setSimpViewImageUri(R.id.image, Uri.parse(item.getContent()));
                        }
                    };
                    holder.recyclerViewImg.setAdapter(imgAdapter);
                    imgAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int pos) {
                            PictureSelectUtil.show((Activity)mContext,pos,list.get(position).getDetail());
                        }
                    });
                }else {
                    holder.recyclerViewImg.setVisibility(View.GONE);
                    holder.rlVideo.setVisibility(View.VISIBLE);
                    UIUtils.loadImg(mContext,list.get(position).getDetail().get(0).getFirst_frame(),holder.ivPic);
//                    holder.ivPic.setImageURI(Uri.parse(list.get(position).getDetail().get(0).getFirst_frame()));
                }
            }
        }else {
            holder.rlVideo.setVisibility(View.GONE);
            holder.llArticle.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.GONE);
            if(StringUtils.isEmpty(list.get(position).getTrans_img())){
                holder.ivArticlePic.setVisibility(View.GONE);
            }else {
                holder.ivArticlePic.setVisibility(View.VISIBLE);
                UIUtils.loadImg(mContext,list.get(position).getTrans_img(),holder.ivArticlePic);
            }
            holder.txtArticleContent.setText(EmojiUtil.utf8ToString(list.get(position).getTrans_title()));
        }

        if(list.get(position).getPraise_users().size() == 0){
            holder.recyclerViewH.setVisibility(View.GONE);
        }else {
            holder.recyclerViewH.setVisibility(View.VISIBLE);
            txAdapter = new BaseRecyclerAdapter<PraiseBean>(mContext,list.get(position).getPraise_users(),R.layout.layout_dongtai_dianzan_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, PraiseBean item, int position, boolean isScrolling) {
                    holder.setImage(R.id.iv_icon_small,item.getAvatar(),true,R.mipmap.morentouxiang);
//                    holder.setSimpViewImageUri(R.id.iv_icon_small, Uri.parse(item.getAvatar()));
                }
            };
            holder.recyclerViewH.setAdapter(txAdapter);
        }

        if(list.get(position).getComments().size() == 0){
            holder.recyclerView.setVisibility(View.GONE);
        }else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            plAdapter = new BaseRecyclerAdapter<MoCommentBean>(mContext,list.get(position).getComments(),R.layout.layout_dongtai_pinglun_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, MoCommentBean item, int position, boolean isScrolling) {
                    TextView txt= (TextView)holder.getView(R.id.txt);
                    String str = null;
                    if(StringUtils.isEmpty(item.getReply_uid()) || item.getReply_uid().equals("0")){
                        str = item.getCmm_nick_name()+"："+ EmojiUtil.utf8ToString(item.getContent().trim());
                    }else {
                        str = item.getCmm_nick_name()+"回复"+item.getRp_nick_name()+"："+ EmojiUtil.utf8ToString(item.getContent().trim());
                    }
                    SpannableStringBuilder builder = new SpannableStringBuilder(str);
                    ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
                    ForegroundColorSpan blueSpan1 = new ForegroundColorSpan(Color.parseColor("#ed4272"));
                    ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(Color.parseColor("#333333"));
                    builder.setSpan(blueSpan, 0, item.getCmm_nick_name().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(!StringUtils.isEmpty(item.getReply_uid()) && !item.getReply_uid().equals("0")){
                        builder.setSpan(blueSpan1, item.getCmm_nick_name().length(), item.getCmm_nick_name().length()+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        builder.setSpan(blueSpan2, item.getCmm_nick_name().length()+2, item.getCmm_nick_name().length()+item.getRp_nick_name().length()+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    txt.setText(builder);
                }
            };
            holder.recyclerView.setAdapter(plAdapter);
            plAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, final View view, final int pos) {
                    if(list.get(position).getComments().get(pos).getComment_uid().equals(LoginUtil.getInfo("uid"))){
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onChildCommentDelClick(position,pos);
                        }
                    }else {
                        replyDialog = new ReplyDialog(mContext);
                        replyDialog.setCanceledOnTouchOutside(true);
                        replyDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                StringUtils.closeKeyBorder(mContext,view);
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
                                    mOnItemClickListener.onChildCommentClick(position,pos,replyDialog.getContent());
                                }
                            }
                        }).show();
                    }
                }
            });
        }

        holder.llArticle.setOnClickListener(new MyListener(holder,position));
        holder.rlVideo.setOnClickListener(new MyListener(holder,position));
        holder.ivIconSmall.setOnClickListener(new MyListener(holder,position));
        holder.txtDel.setOnClickListener(new MyListener(holder,position));
        holder.txtZan.setOnClickListener(new MyListener(holder,position));
        holder.txtComment.setOnClickListener(new MyListener(holder,position));
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

    class MyListener implements View.OnClickListener {
        private int position;
        private MyViewHolder holder;
        private Intent it;
        public MyListener(MyViewHolder holder,int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.iv_icon_small:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onIconClick(position);
                    }
                    break;
                case R.id.ll_article:
                    it = new Intent(mContext, ArticleDetailActivity.class);
                    it.putExtra("circle_id",list.get(position).getTrans_id());
                    mContext.startActivity(it);
                    break;
                case R.id.rl_video:
//                    PictureSelector.create((Activity)mContext).externalPictureVideo(list.get(position).getDetail().get(0).getContent());
                    it = new Intent(mContext, VideoPlayActivity.class);
                    it.putExtra("entity",list.get(position).getDetail().get(0));
                    mContext.startActivity(it);
                    break;
                case R.id.txt_zan:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onZanClick(position);
                    }
                    break;
                case R.id.txt_del:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onDelClick(position);
                    }
                    break;
                case R.id.txt_comment:
                    replyDialog = new ReplyDialog(mContext);
                    replyDialog.setCanceledOnTouchOutside(true);
                    replyDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
//                            InputMethodManager inputMgr = (InputMethodManager) mContext
//                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//                            inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                            StringUtils.closeKeyBorder(mContext,v);
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
                                        mOnItemClickListener.onCommentClick(position,replyDialog.getContent());
                                    }
                                }
                            }).show();
                    break;
            }
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_icon_small)
        ImageView ivIconSmall;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.expand_text_view)
        ExpandableView txtExpand;
        @BindView(R.id.txt_address)
        TextView txtAddress;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_del)
        TextView txtDel;
        @BindView(R.id.txt_zan)
        TextView txtZan;
        @BindView(R.id.txt_comment)
        TextView txtComment;
        @BindView(R.id.recycler_view_img)
        RecyclerView recyclerViewImg;
        @BindView(R.id.rl_video)
        RelativeLayout rlVideo;
        @BindView(R.id.ll_article)
        LinearLayout llArticle;
        @BindView(R.id.iv_article_pic)
        ImageView ivArticlePic;
        @BindView(R.id.txt_artical_content)
        TextView txtArticleContent;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.recycler_view_h)
        RecyclerView recyclerViewH;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            recyclerViewImg.setHasFixedSize(true);
            recyclerViewImg.setNestedScrollingEnabled(false);
            recyclerViewImg.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
            //这句就是添加我们自定义的分隔线
            recyclerViewImg.addItemDecoration(new DividerGridItemDecoration(mContext,10
                    ,mContext.getResources().getColor(R.color.white)));

            recyclerViewH.setHasFixedSize(true);
            recyclerViewH.setNestedScrollingEnabled(false);
            recyclerViewH.setLayoutManager(new FullyGridLayoutManager(mContext,10, LinearLayoutManager.VERTICAL,false));
            //这句就是添加我们自定义的分隔线
            recyclerViewH.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,10
                    ,mContext.getResources().getColor(R.color.white)));

            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
            //这句就是添加我们自定义的分隔线
            recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,10
                    ,mContext.getResources().getColor(R.color.white)));
        }
    }
}
