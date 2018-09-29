package com.maapuu.mereca.openim;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

/**
 * Created by dell on 2016/9/26.
 */
public class ConversationListUICustomSample extends IMConversationListUI {
    public ConversationListUICustomSample(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 是否隐藏会话列表标题栏
     * @param fragment
     * @return true: 隐藏标题栏， false：不隐藏标题栏
     */
    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }

}
