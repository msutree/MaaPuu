package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/1.
 */

public class ChangePhoneActivity1 extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_bind_phone)
    TextView txtBindPhone;
    @BindView(R.id.et_old_phone)
    EditText etOldPhone;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("手机绑定");
        if(!StringUtils.isEmpty(LoginUtil.getInfo("phone"))){
            txtBindPhone.setText("当前绑定手机号********"+LoginUtil.getInfo("phone").substring(7,11));
        }else {
            txtBindPhone.setText("当前绑定手机号********");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_next})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_next:
                if (StringUtils.isEmpty(etOldPhone.getText().toString())) {
                    ToastUtil.show(mContext, "请输入手机号码");
                    return;
                }
                if (etOldPhone.getText().toString().length() != 11) {
                    ToastUtil.show(mContext, "手机号格式错误");
                    return;
                }
                if (!etOldPhone.getText().toString().trim().equals(etOldPhone.getText().toString().trim())) {
                    ToastUtil.show(mContext, "输入的不是当前绑定的手机号");
                    return;
                }
                it = new Intent(mContext,ChangePhoneActivity2.class);
                it.putExtra("old_phone",etOldPhone.getText().toString());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
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
}
