package com.maapuu.mereca.openim;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.channel.util.AccountUtils;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWP2PConversationBody;
import com.alibaba.mobileim.conversation.YWTribeConversationBody;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ChatDetailActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;

/**
 * Created by dell on 2016/9/26.
 */
public class ChattingUICustomSample extends IMChattingPageUI {

    public ChattingUICustomSample(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 是否隐藏底部ChattingReplyBar
     *
     * @return
     */
    @Override
    public boolean needHideChattingReplyBar(YWConversation conversation) {
        Log.i("abc",conversation.getConversationId()+"****");
        YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation.getConversationBody();
        boolean bool = false;
        return bool;
    }

    @Override
    public boolean needHideTitleView(Fragment fragment, YWConversation conversation) {
        return false;
    }

    /**
     * isv需要返回自定义的view. openIMSDK会回调这个方法，获取用户设置的view. Fragment 聊天界面的fragment
     */
    @Override
    public View getCustomTitleView(final Fragment fragment, final Context context, LayoutInflater inflater, final YWConversation conversation) {
        final String uid;
        if(StringUtils.isEmpty(fragment.getActivity().getIntent().getStringExtra("uid"))){
            uid = conversation.getConversationId().replace("iw6h3b80","");
        }else {
            uid = fragment.getActivity().getIntent().getStringExtra("uid");
        }
        // 单聊和群聊都会使用这个方法，所以这里需要做一下区分
        // 本demo示例是处理单聊，如果群聊界面也支持自定义，请去掉此判断
        ImmersionBar.with(fragment.getActivity()).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true,0f).init();
        //TODO 重要：必须以该形式初始化view---［inflate(R.layout.**, new RelativeLayout(context),false)］------，以让inflater知道父布局的类型，否则布局**中的高度和宽度无效，均变为wrap_content
        View view = inflater.inflate(R.layout.top2, new LinearLayout(context), false);
        TextView textView = (TextView) view.findViewById(R.id.txt_title);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_right);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(fragment.getActivity(),ChatDetailActivity.class);
                it.putExtra("friend_uid",uid);
                it.putExtra("msg_status","1");
                fragment.getActivity().startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
            }
        });
        String title = null;
        if (conversation.getConversationType() == YWConversationType.P2P) {
            YWP2PConversationBody conversationBody = (YWP2PConversationBody) conversation
                    .getConversationBody();
            if (!TextUtils.isEmpty(conversationBody.getContact().getShowName())) {
                title = conversationBody.getContact().getShowName();
            } else {

                YWIMKit imKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                IYWContact contact = imKit.getContactService().getContactProfileInfo(conversationBody.getContact().getUserId(), conversationBody.getContact().getAppKey());
                //生成showName，According to id。
                if (contact != null && !TextUtils.isEmpty(contact.getShowName())) {
                    title = contact.getShowName();
                }
            }
            //如果标题为空，那么直接使用Id
            if (TextUtils.isEmpty(title)) {
                title = conversationBody.getContact().getUserId();
            }
        } else {
            if (conversation.getConversationBody() instanceof YWTribeConversationBody) {
                title = ((YWTribeConversationBody) conversation.getConversationBody()).getTribe().getTribeName();
                if (TextUtils.isEmpty(title)) {
                    title = "团队群聊";
                }
            } else {
                if (conversation.getConversationType() == YWConversationType.SHOP) { //为OpenIM的官方客服特殊定义了下、
                    title = AccountUtils.getShortUserID(conversation.getConversationId());
                }
            }
        }
        if (conversation.getConversationId().equals("igmwqgs4kefu")){
            textView.setText("客服");
        }else {
            textView.setText(title);
        }
        TextView backView = (TextView) view.findViewById(R.id.txt_left);
        backView.setTypeface(StringUtils.getFont(context));
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragment.getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public int getMsgBackgroundResId(YWConversation conversation, YWMessage message, boolean self) {
        int msgType = message.getSubType();
        if (msgType == YWMessage.SUB_MSG_TYPE.IM_TEXT || msgType == YWMessage.SUB_MSG_TYPE.IM_AUDIO) {
            if (self) {
                return R.drawable.talk_pop_r_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_IMAGE) {
            if (self) {
                return R.drawable.talk_pop_r_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_VIDEO) {
            if (self) {
                return R.drawable.talk_pop_r_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_GEO) {
            if (self) {
                return R.drawable.aliwx_comment_r_bg;
            } else {
                return R.drawable.aliwx_comment_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || msgType == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
            if (self) {
                return -1;
            } else {
                return -1;
            }
        }
        return super.getMsgBackgroundResId(conversation, message, self);
    }

    @Override
    public void onActivityResult(Fragment fragment, YWConversation conversation, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(fragment, conversation, requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                fragment.getActivity().setResult(AppConfig.ACTIVITY_RESULTCODE);
                fragment.getActivity().finish();
                break;
        }
    }
}
