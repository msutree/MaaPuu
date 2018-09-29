package com.maapuu.mereca.view;

import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by dell on 2017/8/29.
 */

public class SupportPopupWindow extends PopupWindow {

    public SupportPopupWindow(View contentView, int width, int height){
        super(contentView,width,height);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, a[1] + anchor.getHeight() + 0);
        } else {
            super.showAsDropDown(anchor);
        }
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor, xoff, yoff);
    }
}
