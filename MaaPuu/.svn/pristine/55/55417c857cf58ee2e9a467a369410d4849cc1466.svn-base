package com.maapuu.mereca.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;


/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class NestedExpandableListView extends ExpandableListView {
    public NestedExpandableListView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public NestedExpandableListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public NestedExpandableListView(Context context, AttributeSet attrs, int defStyle) {
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
