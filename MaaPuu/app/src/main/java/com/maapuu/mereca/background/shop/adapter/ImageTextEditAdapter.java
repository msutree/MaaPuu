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

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图文混排编辑 Adapter
 * Created by Jia on 2018/3/16.
 */

public class ImageTextEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageTextBean> items;

    private static final int TEXT = 1;
    private static final int PICTURE = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private AlertView alertView;

    public ImageTextEditAdapter(Context mContext, List<ImageTextBean> items) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    public ImageTextEditAdapter(Context mContext) {
        this(mContext,new ArrayList<ImageTextBean>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PICTURE) {
            return new ImgViewHolder(mInflater.inflate(R.layout.my_item_img_edit, parent, false));
        } else if (viewType == TEXT) {
            return new TxtViewHolder(mInflater.inflate(R.layout.my_item_txt_edit, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageTextBean item = items.get(position);
        if (getItemViewType(position) == PICTURE) { //图片

            ImgViewHolder pictureViewHolder = (ImgViewHolder) holder;
            UIUtils.loadImg(mContext, item.getContent(), pictureViewHolder.imgIv);
            //删除事件
            pictureViewHolder.imgDeleteIv.setTag(position);
            pictureViewHolder.imgDeleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    alertView = new AlertView(null, "确定要删除吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int pos) {
                            if (pos == 0) {
                                int position = (int) view.getTag();
                                removeItem(position);
                            }
                        }
                    });
                    alertView.show();

                }
            });
        } else if (getItemViewType(position) == TEXT) { //文字
            TxtViewHolder textViewHolder = (TxtViewHolder) holder;
            // 1.0移除监听
            if (textViewHolder.contentEt.getTag() instanceof TextSwitcher) {
                textViewHolder.contentEt.removeTextChangedListener(
                        (TextSwitcher) textViewHolder.contentEt.getTag());
            }
            // 2.0设置内容
            textViewHolder.contentEt.setText(item.getContent());
            // 3.0添加文本监听
            TextSwitcher switcher = new TextSwitcher(textViewHolder, position);
            textViewHolder.contentEt.addTextChangedListener(switcher);
            textViewHolder.contentEt.setTag(switcher);

            final EditText et = textViewHolder.contentEt;
            textViewHolder.contentEt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (canVerticalScroll(et)) {
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
                public void onClick(final View view) {
                    alertView = new AlertView(null, "确定要删除吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int pos) {
                            if (pos == 0) {
                                int position = (int) view.getTag();
                                removeItem(position);
                            }
                        }
                    });
                    alertView.show();
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
        if (items.get(position).getContent_type().equals("2")) { //图片布局
            return 2;
        } else { //文本布局
            return 1;
        }
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
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null) {
                items.get(position).setContent(s.toString());
            }else {
                items.get(position).setContent("");
            }
        }
    }

    class ImgViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_img_iv)
        ImageView imgIv;
        @BindView(R.id.my_img_delete_iv)
        ImageView imgDeleteIv;

        public ImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class TxtViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_txt_et)
        EditText contentEt;
        @BindView(R.id.my_txt_delete_iv)
        ImageView txtDeleteIv;

        public TxtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //移除
    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, this.items.size());
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

    public List<ImageTextBean> getItems() {
        return this.items;
    }

    public AlertView getAlertView(){
        return alertView;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

}
