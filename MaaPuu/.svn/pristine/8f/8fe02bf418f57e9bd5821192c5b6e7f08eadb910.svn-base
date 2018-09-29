package com.maapuu.mereca.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class NestedRecyclerView extends RecyclerView {

    public NestedRecyclerView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public NestedRecyclerView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public NestedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}