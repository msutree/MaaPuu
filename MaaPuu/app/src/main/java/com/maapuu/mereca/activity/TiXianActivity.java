package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_wx_name)
    TextView txtWxName;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.txt_zfb_name)
    TextView txtZfbName;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.txt_code)
    TextView txtCode;

    @BindView(R.id.ll_withdraw)
    LinearLayout llWithdraw;
    @BindView(R.id.txt_wx_unbind)
    TextView txtWxUnbind;//解绑微信
    @BindView(R.id.txt_zfb_unbind)
    TextView txtZfbUnbind;//解绑支付宝

    private String balance_id;
    private String balance;
    private int account_type = 0;
    private boolean isWxBind = false;
    private boolean isZfbBind = false;

    private TimeCount time;
    private String sms_id = ""; //验证码的id
    private String phone;

    private String shop_id = "";//不为空则是由商铺 财务管理界面进入

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tixian);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("提现申请");
        balance_id = getIntent().getStringExtra("balance_id");
        shop_id = getIntent().getStringExtra("shop_id");
        time = new TimeCount(60000, 1000);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence == null || charSequence.length() == 0){
                    return;
                }
                if ((charSequence.toString().startsWith("0")||charSequence.toString().startsWith(".")) && charSequence.toString().trim().length() > 0){
                    etAmount.setText("");
                    etAmount.setSelection(0);
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public void initData() {
        if(!TextUtils.isEmpty(shop_id)){
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                    UrlUtils.s_account_apply_init_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
        } else {
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                    UrlUtils.account_apply_init_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),balance_id),true);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        balance_id = resultObj.optString("balance_id");
                        phone = resultObj.optString("phone");
                        if(!StringUtils.isEmpty(resultObj.optString("wechat_real_name"))){
                            isWxBind = true;
                            txtWxName.setText(resultObj.optString("wechat_real_name"));
                            iv1.setImageResource(R.drawable.bg_choose_selector);
                        }else {
                            isWxBind = false;
                            txtWxName.setText("未绑定");
                            iv1.setImageResource(R.mipmap.right_arrow);
                        }
                        if(!StringUtils.isEmpty(resultObj.optString("alipay_real_name"))){
                            isZfbBind = true;
                            txtZfbName.setText(resultObj.optString("alipay_real_name"));
                            iv2.setImageResource(R.drawable.bg_choose_selector);
                        }else {
                            isZfbBind = false;
                            txtZfbName.setText("未绑定");
                            iv2.setImageResource(R.mipmap.right_arrow);
                        }
                        balance = resultObj.optString("balance");
                        phone = resultObj.optString("phone");
                        etAmount.setHint("当前最多可提现"+resultObj.optString("balance")+"元");

                        //微信 支付宝都没绑定时，需隐藏提现操作
                        if(!isWxBind && !isZfbBind){
                            llWithdraw.setVisibility(View.GONE);
                        } else {
                            llWithdraw.setVisibility(View.VISIBLE);
                        }
                        txtWxUnbind.setVisibility(isWxBind?View.VISIBLE:View.GONE);
                        txtZfbUnbind.setVisibility(isZfbBind?View.VISIBLE:View.GONE);

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
            case HttpModeBase.HTTP_REQUESTCODE_3:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        it = new Intent(mContext,TiXianResultActivity.class);
                        it.putExtra("result",object.optString("result"));
                        startActivity(it);
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_4:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新
                        initData();
                    }
                    ToastUtil.show(mContext,object.optString("message"));
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
    @OnClick({R.id.txt_left,R.id.ll_wx,R.id.ll_zfb,R.id.txt_code,R.id.txt_commit,R.id.txt_wx_unbind,R.id.txt_zfb_unbind})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.ll_wx:
                if(isWxBind){
                    account_type = 2;
                    iv1.setSelected(true);
                    if(isZfbBind){
                        iv2.setSelected(false);
                    }
                }else {
                    it = new Intent(mContext,BindPayAccountActivity.class);
                    it.putExtra("balance_id",balance_id);
                    it.putExtra("shop_id",shop_id);//由商铺 财务管理界面进入
                    it.putExtra("account_type",2);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                }
                break;
            case R.id.ll_zfb:
                if(isZfbBind){
                    account_type = 1;
                    iv2.setSelected(true);
                    if(isWxBind){
                        iv1.setSelected(false);
                    }
                }else {
                    it = new Intent(mContext,BindPayAccountActivity.class);
                    it.putExtra("balance_id",balance_id);
                    it.putExtra("shop_id",shop_id);//由商铺 财务管理界面进入
                    it.putExtra("account_type",1);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                }
                break;
            case R.id.txt_code:
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.cmm_sms_code_get(phone,"8"),true);
                break;
            case R.id.txt_commit:
                if(account_type == 0){
                    ToastUtil.show(mContext,"请选择提现账户类型");
                    return;
                }
                if(StringUtils.isEmpty(etAmount.getText().toString())){
                    ToastUtil.show(mContext,"请输入提现金额");
                    return;
                }
                if(Double.parseDouble(etAmount.getText().toString()) > Double.parseDouble(balance)){
                    ToastUtil.show(mContext,"请输入提现金额");
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
                if(!TextUtils.isEmpty(shop_id)){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.s_account_apply_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            shop_id,account_type+"",etAmount.getText().toString(),sms_id,etCode.getText().toString()),true);
                } else {
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.account_apply_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            balance_id,account_type+"",etAmount.getText().toString(),sms_id,etCode.getText().toString()),true);
                }

                break;

            case R.id.txt_wx_unbind:
                removeAccount("2");
                break;

            case R.id.txt_zfb_unbind:
                removeAccount("1");
                break;
        }
    }

    private void removeAccount(String account_type){
        //绑定的账户类型：1支付宝；2微信
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4,
                UrlUtils.remove_account_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),balance_id,account_type),true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                account_type = data.getIntExtra("account_type",0);
                initData();
//                if(account_type == 1){
//                    isZfbBind = true;
//                    iv2.setImageResource(R.drawable.bg_choose_selector);
//                    iv2.setSelected(true);
//                    if(isWxBind){
//                        iv1.setSelected(false);
//                    }
//                }else if(account_type == 2){
//                    isWxBind = true;
//                    iv1.setImageResource(R.drawable.bg_choose_selector);
//                    iv1.setSelected(true);
//                    if(isZfbBind){
//                        iv2.setSelected(false);
//                    }
//                }
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
