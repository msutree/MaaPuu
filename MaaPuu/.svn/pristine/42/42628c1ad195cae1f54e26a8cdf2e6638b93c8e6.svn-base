package com.maapuu.mereca.background.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ImgTxtBean;
import com.maapuu.mereca.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图文混排编辑 Adapter
 * Created by Jia on 2018/3/16.
 */

public class ImgTxtEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ImgTxtBean> items;

    private static final int TEXT = 1;
    private static final int PICTURE = 2;

    private Context mContext;
    private LayoutInflater mInflater;

    public ImgTxtEditAdapter(Context mContext, List<ImgTxtBean> items){
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         if(viewType == PICTURE){

             return new ImgViewHolder(mInflater.inflate(R.layout.my_item_img_edit,
                     parent,false));

         } else if(viewType == TEXT){

             return new TxtViewHolder(mInflater.inflate(R.layout.my_item_txt_edit,
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

            //删除事件
            pictureViewHolder.imgDeleteIv.setTag(position);
            pictureViewHolder.imgDeleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    removeItem(position);
                }
            });

            } else if(getItemViewType(position) == TEXT){ //文字

            TxtViewHolder textViewHolder = (TxtViewHolder) holder;
            // 1.0移除监听
            if(textViewHolder.contentEt.getTag() instanceof TextSwitcher){
                textViewHolder.contentEt.removeTextChangedListener(
                        (TextSwitcher)textViewHolder.contentEt.getTag());
            }
            // 2.0设置内容
            textViewHolder.contentEt.setText(item.getContent());
            // 3.0添加文本监听
            TextSwitcher switcher = new TextSwitcher(textViewHolder,position);
            textViewHolder.contentEt.addTextChangedListener(switcher);
            textViewHolder.contentEt.setTag(switcher);

            final EditText et = textViewHolder.contentEt;
            textViewHolder.contentEt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(canVerticalScroll(et)){
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                    return false;
                }
            });


            //删除事件
            textViewHolder.txtDeleteIv.setTag(position);
            textViewHolder.txtDeleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    removeItem(position);
                }
            });

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

    //自定义EditText的监听类
    class TextSwitcher implements TextWatcher {

        private TxtViewHolder mHolder;
        private int position;

        public TextSwitcher(TxtViewHolder mHolder, int position) {
            this.mHolder = mHolder;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s!=null){
                items.get(position).setContent(s.toString());
            }
        }
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.my_img_iv)
        ImageView imgIv;
        @BindView(R.id.my_img_delete_iv)
        ImageView imgDeleteIv;

        public ImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class TxtViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.my_txt_et)
        EditText contentEt;
        @BindView(R.id.my_txt_delete_iv)
        ImageView txtDeleteIv;

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

    /**
     * EditText竖直方向是否可以滚动
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

}
