package com.maapuu.mereca.openim;

import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageOperateion;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;

public class ChattingOperationCustomSample extends IMChattingPageOperateion {

    public ChattingOperationCustomSample(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 双击放大文字消息的开关
     * @param fragment
     * @return true:开启双击放大文字 false: 关闭双击放大文字
     */
    @Override
    public boolean enableDoubleClickEnlargeMessageText(Fragment fragment) {
        return false;
    }
}