package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/1.
 */

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_repeat)
    EditText etPwdRepeat;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pwd);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("修改密码");
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
                        ToastUtil.show(mContext, "密码修改成功");
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
    @OnClick({R.id.txt_left,R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_commit:
                if (StringUtils.isEmpty(etOldPwd.getText().toString().trim())) {
                    ToastUtil.show(mContext, "请输入旧密码");
                    return;
                }
                if (StringUtils.isEmpty(etPwd.getText().toString().trim())) {
                    ToastUtil.show(mContext, "请输入新密码");
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
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.user_pwd_update_set(LoginUtil.getInfo("token,"),LoginUtil.getInfo("phone"),
                        etPwd.getText().toString(),etOldPwd.getText().toString()),true);
                break;
        }
    }
}
