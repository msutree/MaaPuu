package com.maapuu.mereca.recycleviewutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.UIUtils;
import com.willy.ratingbar.ScaleRatingBar;

/**
 * 万能的RecyclerView的ViewHolder
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;

    private BaseRecyclerHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>(8);
    }

    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews(){
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseRecyclerHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImage(int viewId,String url,boolean isCircle){
        ImageView iv = getView(viewId);
        UIUtils.loadImg(context,url,iv,isCircle);
        return this;
    }

    public BaseRecyclerHolder setImage(int viewId,String url,boolean isCircle,int resource){
        ImageView iv = getView(viewId);
        UIUtils.loadImg(context,url,iv,isCircle,resource);
        return this;
    }

    public BaseRecyclerHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public BaseRecyclerHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseRecyclerHolder setSimpViewImageUri(int viewId, Uri imageUri) {
        SimpleDraweeView view = getView(viewId);
        view.setImageURI(imageUri);
        return this;
    }

    /**
     * 设置背景
     * @param viewId
     * @param color
     * @return
     */
    public BaseRecyclerHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseRecyclerHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseRecyclerHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    public BaseRecyclerHolder setTextFlags(int viewId, int flags) {
        TextView view = getView(viewId);
        view.getPaint().setFlags(flags); //中划线
        return this;
    }
    public BaseRecyclerHolder setScaleRatingBar(int viewId, float rating) {
        ScaleRatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BaseRecyclerHolder setParams(int viewId, ViewGroup.LayoutParams lp) {
        View view = getView(viewId);
        view.setLayoutParams(lp);
        return this;
    }

    public  ViewGroup.LayoutParams getParams(int viewId) {
        View view = getView(viewId);
        return view.getLayoutParams();
    }

    public BaseRecyclerHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public BaseRecyclerHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public BaseRecyclerHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public BaseRecyclerHolder setVisible(int viewId, int value) {
        View view = getView(viewId);
        view.setVisibility(value);
        return this;
    }

    public BaseRecyclerHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseRecyclerHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
//            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public BaseRecyclerHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseRecyclerHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BaseRecyclerHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public BaseRecyclerHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BaseRecyclerHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BaseRecyclerHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseRecyclerHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseRecyclerHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }
    public BaseRecyclerHolder setSelected(int viewId, boolean selected) {
        View view = getView(viewId);
        view.setSelected(selected);
        return this;
    }
    public BaseRecyclerHolder setClickable(int viewId, boolean selected) {
        View view = getView(viewId);
        view.setClickable(selected);
        return this;
    }
    public boolean isSelected(int viewId){
        View view = getView(viewId);
        return view.isSelected();
    }
    public BaseRecyclerHolder setEnabled(int viewId, boolean value) {
        View view = getView(viewId);
        view.setEnabled(value);
        return this;
    }

    public BaseRecyclerHolder setPadding(int viewId,Context mContext,int left,int top,int right,int bottom) {
        View view = getView(viewId);
        view.setPadding(DisplayUtil.dip2px(mContext,left),DisplayUtil.dip2px(mContext,top),DisplayUtil.dip2px(mContext,right),DisplayUtil.dip2px(mContext,bottom));
        return this;
    }

    public BaseRecyclerHolder setPadding(int viewId,int left,int top,int right,int bottom) {
        View view = getView(viewId);
        view.setPadding(left,top,right,bottom);
        return this;
    }

    public BaseRecyclerHolder setMargins(int viewId,int left, int top, int right, int bottom) {
        View view = getView(viewId);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left,top,right,bottom);
            view.requestLayout();
        }
        return this;
    }
    public BaseRecyclerHolder setMargins(int viewId,Context mContext,int left, int top, int right, int bottom) {
        View view = getView(viewId);
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(DisplayUtil.dip2px(mContext,left),DisplayUtil.dip2px(mContext,top),DisplayUtil.dip2px(mContext,right),DisplayUtil.dip2px(mContext,bottom));
            view.requestLayout();
        }
        return this;
    }

    public BaseRecyclerHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public BaseRecyclerHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnItemClickListener(int viewId,AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    public BaseRecyclerHolder setOnItemLongClickListener(int viewId,AdapterView.OnItemLongClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }
    public BaseRecyclerHolder setOnItemSelectedClickListener(int viewId,AdapterView.OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }
}
