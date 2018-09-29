package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ImgTxtBean;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图文混排 Adapter 测试
 * Created by Jia on 2018/3/16.
 */

public class ImgTxtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ImgTxtBean> items;

    private static final int TEXT = 1;
    private static final int PICTURE = 2;

    private Context mContext;
    private LayoutInflater mInflater;

    public ImgTxtAdapter(Context mContext, List<ImgTxtBean> items){
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         if(viewType == PICTURE){

             return new ImgViewHolder(mInflater.inflate(R.layout.my_item_img,
                     parent,false));

         } else if(viewType == TEXT){

             return new TxtViewHolder(mInflater.inflate(R.layout.my_item_txt,
                     parent,false));
         }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImgTxtBean item = items.get(position);
        if(getItemViewType(position) == PICTURE){ //图片

            ImgViewHolder pictureViewHolder = (ImgViewHolder) holder;

//            ImageLoader.loadImage(Glide.with(mContext),pictureViewHolder.iv_publish_logo,
//                    AppConfig.getImagePath(item.getRemark()),R.mipmap.publish_picture,R.mipmap.publish_picture);
            UIUtils.loadImg(mContext,item.getContent(),pictureViewHolder.imgIv);

            } else if(getItemViewType(position) == TEXT){ //文字

                TxtViewHolder txtViewHolder = (TxtViewHolder) holder;
                txtViewHolder.contentTv.setText(item.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public int getItemViewType(int position) {

        if(items.get(position).getType() == 2){ //图片布局
            return 2;
        } else if(items.get(position).getType() == 1){ //文本布局
            return 1;
        }

        return 1;
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.my_img_iv)
        ImageView imgIv;

        public ImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class TxtViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.my_txt_tv)
        TextView contentTv;

        public TxtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //移除
    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0,this.items.size());
        }
    }

    public void addAll(List<ImgTxtBean> lists) {
        if (lists != null) {
            this.items.addAll(lists);
            notifyItemRangeInserted(this.items.size(), lists.size());
        }
    }

    public final void addItem(ImgTxtBean item) {
        if (item != null) {
            this.items.add(item);
            notifyItemChanged(items.size());
        }
    }

    public final void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public List<ImgTxtBean> getItems(){
        return this.items;
    }

}
