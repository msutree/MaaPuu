package com.maapuu.mereca.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.maapuu.mereca.base.AppConfig;

import org.xutils.common.util.LogUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2016/2/1.
 */
public class StringUtils {

    public static void initChat() {
        AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
        IYWLoginService loginService = AppConfig.mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(LoginUtil.getInfo("uid"), "e10adc3949ba59abbe56e057f20f883e");
        loginService.login(loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                LogUtil.e("------登录成功");
            }

            @Override
            public void onProgress(int arg0) {}

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                if(errCode == 1){
                    initChat();
                }
                LogUtil.e("------登陆失败" + description);
            }
        });
    }

    public static void closeKeyBorder(Context mContext, View view){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Typeface getFont(Context mContext){
        //得到AssetManager
        AssetManager mgr=mContext.getAssets();
        //根据路径得到Typeface
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/iconfont.ttf");
        return tf;
    }
    /**
     * 格式化小数
     * @return
     */
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }

    /**
     * 格式化数字为千分位显示；
     * @return
     */
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if(text.indexOf(".") > 0) {
            if(text.length() - text.indexOf(".")-1 == 0) {
                df = new DecimalFormat("###,##0.");
            }else if(text.length() - text.indexOf(".")-1 == 1) {
                df = new DecimalFormat("###,##0.0");
            }else {
                df = new DecimalFormat("###,##0.00");
            }
        }else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTime1(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        return format.format(date);
    }
    public static String getTime2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM");
        return format.format(date);
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (TextUtils.isEmpty(input) || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /*
     * 字符串转整数
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /*
     * 对象转整数
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /*
     * 字符串转整数
     * @param str
     * @param defValue
     * @return
     */
    public static Double toDouble(String str, Double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return defValue;
    }
}
