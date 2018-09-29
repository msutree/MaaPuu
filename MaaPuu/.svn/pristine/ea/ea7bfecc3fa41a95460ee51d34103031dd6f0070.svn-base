package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class ResetPwdActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_repeat)
    EditText etPwdRepeat;

    private String phone;
    private String sms_id;
    private String sms_code;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_pwd);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("重置密码");
        txtRight.setText("保存");txtRight.setVisibility(View.VISIBLE);

        phone = getIntent().getStringExtra("phone");
        sms_id = getIntent().getStringExtra("sms_id");
        sms_code = getIntent().getStringExtra("sms_code");
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
                        ToastUtil.show(mContext, "找回密码成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
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
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                if (StringUtils.isEmpty(etPwd.getText().toString().trim())) {
                    ToastUtil.show(mContext, "请输入密码");
                    return;
                }
                if (etPwd.getText().toString().length() < 6) {
                    ToastUtil.show(mContext, "密码至少6位");
                    return;
                }
                if (StringUtils.isEmpty(etPwdRepeat.getText().toString().trim())) {
                    ToastUtil.show(mContext, "请重复密码");
                    return;
                }
                if (!etPwdRepeat.getText().toString().trim().equals(etPwd.getText().toString().trim())) {
                    ToastUtil.show(mContext, "密码不一致");
                    return;
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.user_pwd_forget_set(phone,etPwd.getText().toString(),sms_id,sms_code),true);
                break;
        }
    }
}
