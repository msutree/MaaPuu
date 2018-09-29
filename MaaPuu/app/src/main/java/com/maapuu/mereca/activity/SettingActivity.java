package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.AppManager;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.base.BaseApplication;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DataCleanManager;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/2/28.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("设置");
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_clear_cache,R.id.txt_change_pwd,R.id.txt_change_phone,R.id.txt_pay_pwd,R.id.txt_about_us,R.id.txt_quit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_clear_cache:
                try {
                    alertView = new AlertView(null, "确定清除缓存吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                DataCleanManager.cleanInternalCache(BaseApplication.getInstance());
                                DataCleanManager.cleanExternalCache(BaseApplication.getInstance());
                                DataCleanManager.cleanFiles(BaseApplication.getInstance());
                            }
                        }
                    });
                    alertView.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_change_pwd:
                startActivity(new Intent(mContext,ChangePwdActivity.class));
                break;
            case R.id.txt_change_phone:
                startActivity(new Intent(mContext,ChangePhoneActivity1.class));
                break;
            case R.id.txt_pay_pwd:
                startActivity(new Intent(mContext,SetPayPwdActivity.class));
                break;
            case R.id.txt_about_us:
                startActivity(new Intent(mContext,AboutUsActivity.class));
                break;
            case R.id.txt_quit:
                alertView = new AlertView(null, "确认退出当前账号？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            quit();
                            logout();
                            ToastUtil.show(mContext,"退出登录成功");
                            LoginUtil.clear(mContext);
//                        EventBus.getDefault().post(new EventEntity(0,0,null));
//                        AppManager.getAppManager().finishOtherActivity();
                            it = new Intent(mContext,MainActivity.class);
                            it.putExtra("logout",true);
                            startActivity(it);
                            AppManager.getAppManager().finishAllActivity();
                        }
                    }
                });
                alertView.show();
                break;
        }
    }

    private void quit(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.user_exit_set(LoginUtil.getInfo("uid"),LoginUtil.getInfo("token")),false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {

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

    public void logout() {
        if(AppConfig.mIMKit != null){
            // openIM SDK提供的登录服务
            IYWLoginService mLoginService = AppConfig.mIMKit.getLoginService();
            mLoginService.logout(new IWxCallback() {
                //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
                @Override
                public void onSuccess(Object... arg0) {
                    LogUtil.e("------退出登录成功");}

                @Override
                public void onProgress(int arg0) {}

                @Override
                public void onError(int arg0, String arg1) {}
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
