package com.maapuu.mereca.util;

/**
 * Created by dell on 2016/10/12.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.maapuu.mereca.base.BaseApplication;

/**
 * dp、sp 转换为 px 的工具类
 *
 * @author fxsky 2012.11.12
 *
 */
public class DisplayUtil {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * 屏幕宽高 dp
     * @param context
     * @return
     */
    public static int getWidthDP(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (wm.getDefaultDisplay().getWidth() / scale + 0.5f);
    }
    public static int getHeightDP(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (wm.getDefaultDisplay().getHeight() / scale + 0.5f);
    }
    public static int getHeight(Context context,float f){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getWidthDP(context)*f, context.getResources().getDisplayMetrics()));
    }
    /**
     * 屏幕宽高 px
     * @param context
     * @return
     */
    public static int getWidthPX(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    public static int getHeightPX(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final float scale = context.getResources().getDisplayMetrics().density;
        return wm.getDefaultDisplay().getHeight();
    }
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px(float dipValue) {
        final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = BaseApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void changeTextSize(TextView tv,String str) {
        Spannable sp = new SpannableString(str) ;
//        sp.setSpan(new AbsoluteSizeSpan(20,true),0,str.length()-2,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(12,true),str.length()-1,str.length()-0,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(sp);
    }

    public static LayerDrawable setProgressColor(String strColor){
        int radius0 = dip2px(5);
        float[] outerR = new float[] { radius0, radius0, radius0, radius0, radius0, radius0, radius0, radius0 };
        RoundRectShape roundRectShape1 = new RoundRectShape(outerR, null, null);
        ShapeDrawable shapeDrawable1 = new ShapeDrawable();
        shapeDrawable1.setShape(roundRectShape1);
        shapeDrawable1.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable1.getPaint().setColor(Color.parseColor(strColor));
        ClipDrawable clipDrawable = new ClipDrawable(shapeDrawable1, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        RoundRectShape roundRectShape0 = new RoundRectShape(outerR, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(roundRectShape0);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(Color.parseColor("#E6E6E6"));

        Drawable[] layers = new Drawable[]{shapeDrawable,clipDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setId(0,android.R.id.background);
        layerDrawable.setId(1,android.R.id.progress);
        return layerDrawable;
    }
}