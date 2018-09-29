package com.maapuu.mereca.background.shop.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图文混排 Adapter
 * Created by Jia on 2018/4/14.
 */

public class ImgTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ImageTextBean> items;

    private static final int TEXT = 1;
    private static final int PICTURE = 2;

    private Activity mContext;
    private LayoutInflater mInflater;

    public ImgTextAdapter(Activity mContext, List<ImageTextBean> items){
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageTextBean item = items.get(position);
        if(getItemViewType(position) == PICTURE){ //图片
            final ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
            if(item.getWidth() != 0 && item.getHeight() != 0){
                ViewGroup.LayoutParams params = imgViewHolder.imgIv.getLayoutParams();
                params.width  = DisplayUtil.getWidthPX(mContext) - DisplayUtil.dip2px(mContext,20);
                params.height = item.getHeight() * params.width / item.getWidth();
                imgViewHolder.imgIv.setLayoutParams(params);
            }

            UIUtils.loadImg(mContext,item.getContent(),imgViewHolder.imgIv);
            imgViewHolder.imgIv.setTag(R.string.app_name,position);
            imgViewHolder.imgIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) imgViewHolder.imgIv.getTag(R.string.app_name);
                    PictureSelectUtil.show(mContext,pos,items);
                }
            });
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

        if("2".equals(items.get(position).getContent_type())){ //图片布局
            return 2;
        } else { //文本布局
            return 1;
        }
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

    public void addAll(List<ImageTextBean> lists) {
        if (lists != null) {
            this.items.addAll(lists);
            notifyItemRangeInserted(this.items.size(), lists.size());
        }
    }

    public final void addItem(ImageTextBean item) {
        if (item != null) {
            this.items.add(item);
            notifyItemChanged(items.size());
        }
    }

    public final void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public List<ImageTextBean> getItems(){
        return this.items;
    }

}
