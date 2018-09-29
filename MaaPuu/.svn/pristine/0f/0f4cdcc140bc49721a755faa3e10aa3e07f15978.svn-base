package com.maapuu.mereca.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by dell on 2016/3/26.
 */
public class StartAdapter extends PagerAdapter {

    //界面列表
    private ArrayList<ImageView> views;

    public StartAdapter(ArrayList<ImageView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(View view, int position) {

        ((ViewPager) view).addView(views.get(position), 0);

        return views.get(position);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(views.get(position));
    }
}
