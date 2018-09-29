package com.maapuu.mereca.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by pc on 2017/9/19.
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {}

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 获取指定的Activity
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0;i < activityStack.size(); i++) {
            if (null != activityStack.get(i)  && activityStack.contains(activityStack.get(i))) {
                activityStack.get(i).finish();
                activityStack.remove(activityStack.get(i));
                i--;
            }
        }
        activityStack.clear();
    }

    /**
     * 结束MianActivity外的所有activity
     */
    public void finishOtherActivity() {
        for (int i = 1;i < activityStack.size(); i++) {
            if (null != activityStack.get(i)  && activityStack.contains(activityStack.get(i))) {
                activityStack.get(i).finish();
                activityStack.remove(activityStack.get(i));
                i--;
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // System.exit(0);
        } catch (Exception e) {
        }
    }
}
