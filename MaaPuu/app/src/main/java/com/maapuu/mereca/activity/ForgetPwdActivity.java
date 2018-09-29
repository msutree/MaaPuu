package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.txt_code)
    TextView txtCode;

    private TimeCount time;
    private String sms_id = ""; //验证码的id

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pwd);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("忘记密码");
        txtRight.setText("下一步");txtRight.setVisibility(View.VISIBLE);
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
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
    @OnClick({R.id.txt_left,R.id.txt_code,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
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
                it = new Intent(mContext,ResetPwdActivity.class);
                it.putExtra("phone",etPhone.getText().toString());
                it.putExtra("sms_id",sms_id);
                it.putExtra("sms_code",etCode.getText().toString());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_code:
                if (etPhone.getText().toString().length() != 11) {
                    Toast.makeText(mContext, "手机号格式错误", Toast.LENGTH_SHORT).show();
                } else {
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.cmm_sms_code_get(etPhone.getText().toString(),"2"),true);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                finish();
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
