package com.maapuu.mereca.openim;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.contact.IYWContactHeadClickListener;
import com.alibaba.mobileim.contact.IYWContactService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.maapuu.mereca.activity.ChatDetailActivity;
import com.maapuu.mereca.activity.PersonalHomepageActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.util.LoginUtil;

/**
 * 用户自定义昵称和头像
 *
 * @author zhaoxu
 */
public class UserProfileSampleHelper {

    private static boolean enableUseLocalUserProfile = true;

    //初始化，建议放在登录之前
    public static void initProfileCallback() {
        if (!enableUseLocalUserProfile){
            //目前SDK会自动获取导入到OpenIM的帐户昵称和头像，如果用户设置了回调，则SDK不会从服务器获取昵称和头像
            return;
        }
        if(AppConfig.mIMKit == null){
            AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
        }
        final IYWContactService contactManager = AppConfig.mIMKit.getContactService();

        //头像点击的回调（开发者可以按需设置）
        contactManager.setContactHeadClickListener(new IYWContactHeadClickListener() {
            @Override
            public void onUserHeadClick(Fragment fragment, YWConversation conversation, String userId, String appKey, boolean isConversationListPage) {
                if(userId.equals(LoginUtil.getInfo("uid"))){
                    return;
                }
                Intent it = new Intent(fragment.getActivity(), PersonalHomepageActivity.class);
                it.putExtra("userpage_uid",userId);
                it.putExtra("is_staff","0");
                fragment.getActivity().startActivity(it);
            }

            @Override
            public void onTribeHeadClick(Fragment fragment, YWConversation conversation, long tribeId) {}

            @Override
            public void onCustomHeadClick(Fragment fragment, YWConversation conversation) {}
        });

    }

}
