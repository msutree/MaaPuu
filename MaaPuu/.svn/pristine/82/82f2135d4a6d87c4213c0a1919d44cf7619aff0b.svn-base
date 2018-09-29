package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JsonUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/1.
 */

public class ChangePhoneActivity2 extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.txt_code)
    TextView txtCode;

    private TimeCount time;
    private String sms_id = ""; //验证码的id
    private String old_phone;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone1);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("修改绑定");
        old_phone = getIntent().getStringExtra("old_phone");
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext, "绑定新手机成功");
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            Map<String, String> userMap = JsonUtils.jsonStrToMap(object.optString("result"));
                            userMap.put(AppConfig.APP_NAME, object.optString("result"));
                            LoginUtil.writeMapInfo(userMap);
                        }
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext, "短信发送成功，请注意查收");
                        sms_id = object.optJSONObject("result").optString("sms_id");
                        time.start();
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
    @OnClick({R.id.txt_left,R.id.txt_code,R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_code:
                if (StringUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtil.show(mContext, "请输入手机号码");
                    return;
                }
                if (etPhone.getText().toString().length() != 11) {
                    ToastUtil.show(mContext, "手机号格式错误");
                    return;
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.cmm_sms_code_get(etPhone.getText().toString(),"3"),true);
                break;
            case R.id.txt_commit:
                if (StringUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtil.show(mContext, "请输入手机号码");
                    return;
                }
                if (etPhone.getText().toString().length() != 11) {
                    ToastUtil.show(mContext, "手机号格式错误");
                    return;
                }
                if (StringUtils.isEmpty(etCode.getText().toString())) {
                    ToastUtil.show(mContext, "请输入验证码");
                    return;
                }
                if (StringUtils.isEmpty(sms_id)) {
                    ToastUtil.show(mContext, "验证码无效");
                    return;
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,UrlUtils.user_phone_update_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        old_phone,etPhone.getText().toString(),sms_id,etCode.getText().toString()),true);
                break;
        }
    }

    /**
     * 短信倒计时
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtCode.setClickable(false);
            txtCode.setText(((millisUntilFinished - 1) / 1000) + "s");
        }

        @Override
        public void onFinish() {
            txtCode.setText("获取验证码");
            txtCode.setClickable(true);
        }
    }
}
