package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ThirdLoginBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JsonUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.VersionUtil;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;

/**
 * Created by dell on 2018/3/5.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;

    private PushAgent mPushAgent;
    private ThirdLoginBean thirdLoginBean;
    private int times = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
    }

    @Override
    public void initData() {
        mPushAgent = PushAgent.getInstance(this);
        thirdLoginBean = new ThirdLoginBean();
        thirdLoginBean.setDevice_tokens(mPushAgent.getRegistrationId());
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_3:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(StringUtils.isEmpty(object.optString("result"))){
                            it = new Intent(mContext,BindPhoneActivity.class);
                            it.putExtra("thirdLoginBean",thirdLoginBean);
                            startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                        }else {
                            ToastUtil.show(mContext, "登录成功");
                            LoginUtil.setLoginState(true);
                            if (!StringUtils.isEmpty(object.optString("result"))) {
                                Map<String, String> userMap = JsonUtils.jsonStrToMap(object.optString("result"));
                                userMap.put(AppConfig.APP_NAME, object.optString("result"));
                                LoginUtil.writeMapInfo(userMap);
                                initChat();
                            }
                            finish();
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_wx,R.id.txt_qq,R.id.txt_wb,R.id.txt_login_phone,R.id.txt_register})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_wx:
                if (!VersionUtil.isAppInstall(mContext,"com.tencent.mm")){
                    ToastUtil.show(mContext, "您还没有安装微信");
                }else{
                    UMShareAPI.get(mContext).getPlatformInfo(LoginActivity.this, WEIXIN, authListener);
                }
                break;
            case R.id.txt_qq:
                if (!VersionUtil.isAppInstall(mContext,"com.tencent.mobileqq")){
                    ToastUtil.show(mContext, "您还没有安装QQ");
                }else{
                    UMShareAPI.get(mContext).getPlatformInfo(LoginActivity.this, QQ, authListener);
                }
                break;
            case R.id.txt_wb:
//                if (!VersionUtil.isAppInstall(mContext,"com.sina.weibo")){
//                    ToastUtil.show(mContext, "您还没有安装微博");
//                }else{
//                    UMShareAPI.get(mContext).getPlatformInfo(LoginActivity.this, SINA, authListener);
//                }
                break;
            case R.id.txt_login_phone:
                it = new Intent(mContext,LoginPhoneActivity.class);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_register:
                it = new Intent(mContext,RegisterActivity.class);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {}

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.i("abc",data.toString());
            switch (platform){
                case SINA:
                    thirdLoginBean.setLogin_type(1);
                    thirdLoginBean.setThird_userid(data.get("id"));
                    thirdLoginBean.setNick_name(data.get("screen_name"));
                    thirdLoginBean.setSex(data.get("gender").equals("男")?"1":"2");
                    thirdLoginBean.setAvatar(data.get("avatar_hd"));
//                    thirdLogin(1,data.get("id"),data.get("screen_name"),data.get("gender").equals("男")?"1":"2",data.get("avatar_hd"));
                    break;
                case QQ:
                    thirdLoginBean.setLogin_type(2);
                    thirdLoginBean.setThird_userid(data.get("openid"));
                    thirdLoginBean.setNick_name(data.get("screen_name"));
                    thirdLoginBean.setSex(data.get("gender").equals("男")?"1":"2");
                    thirdLoginBean.setAvatar(data.get("profile_image_url"));
//                    thirdLogin(2,data.get("openid"),data.get("screen_name"),data.get("gender").equals("男")?"1":"2",data.get("profile_image_url"));
                    break;
                case WEIXIN:
                    thirdLoginBean.setLogin_type(3);
                    thirdLoginBean.setThird_userid(data.get("unionid"));
                    thirdLoginBean.setNick_name(data.get("screen_name"));
                    thirdLoginBean.setSex(data.get("gender").equals("0")?"1":"2");
                    thirdLoginBean.setAvatar(data.get("profile_image_url"));
//                    thirdLogin(3,data.get("unionid"),data.get("screen_name"),data.get("gender").equals("0")?"1":"2",data.get("profile_image_url"));
                    break;
            }
            thirdLogin();
            UMShareAPI.get(mContext).deleteOauth(LoginActivity.this, platform, null);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.show(mContext,"授权失败");
            Log.i("abc",t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.show(mContext,"取消授权");
        }
    };

    private void thirdLogin() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.user_third_login_get(thirdLoginBean.getLogin_type(),thirdLoginBean.getThird_userid()
                ,thirdLoginBean.getNick_name(),thirdLoginBean.getAvatar(),thirdLoginBean.getSex(), thirdLoginBean.getDevice_tokens(),"","",""),true);
    }

    /**
     * 阿里百川的聊天
     */
    private void initChat() {
        AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
        IYWLoginService loginService = AppConfig.mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(LoginUtil.getInfo("uid"), "e10adc3949ba59abbe56e057f20f883e", AppConfig.APP_KEY);
        loginService.login(loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                LogUtil.e("------登录成功");
            }

            @Override
            public void onProgress(int arg0) {}

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息 -2 登录超时
                if(times <= 3 && errCode == -2){
                    times ++;
                    initChat();
                }
                LogUtil.e("------登陆失败" + description);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
