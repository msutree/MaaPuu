package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
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
 * Created by dell on 2018/3/5.
 */

public class BindPayAccountActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.txt_account_label)
    TextView txtAccountLabel;
    @BindView(R.id.et_account)
    EditText etAccount;

    private int account_type ; //2微信 1支付宝
    private String balance_id;
    private String shop_id = "";//不为空则是由商铺 财务管理界面进入

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_pay_account);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        account_type = getIntent().getIntExtra("account_type",1);
        balance_id = getIntent().getStringExtra("balance_id");
        shop_id = getIntent().getStringExtra("shop_id");
        if(account_type == 1){
            txtTitle.setText("绑定支付宝");
            txtAccountLabel.setText("支付宝账号");
            etAccount.setHint("请填写正确的支付宝账号");
        }else {
            txtTitle.setText("绑定微信");
            txtAccountLabel.setText("微信账号");
            etAccount.setHint("请填写正确的微信账号");
        }
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
                        ToastUtil.show(mContext, "绑定成功");
                        it = new Intent();
                        it.putExtra("account_type",account_type);
                        it.putExtra("name",etName.getText().toString());
                        it.putExtra("account",etAccount.getText().toString());
                        setResult(AppConfig.ACTIVITY_RESULTCODE,it);
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
    @OnClick({R.id.txt_left,R.id.txt_save})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_save:
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请填写真实姓名");
                    return;
                }
                if(StringUtils.isEmpty(etAccount.getText().toString())){
                    ToastUtil.show(mContext,etAccount.getHint().toString());
                    return;
                }
                if(!TextUtils.isEmpty(shop_id)){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_bind_account_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            shop_id,account_type+"",etName.getText().toString(),etAccount.getText().toString()),true);
                } else {
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.bind_account_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            balance_id,account_type+"",etName.getText().toString(),etAccount.getText().toString()),true);
                }

                break;
        }
    }
}
