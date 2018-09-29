package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PayUitl;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.alipay.PayResult;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * Created by dell on 2018/3/5.
 */

public class ChargeActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_amount)
    EditText etAmount;

    private String balance_id;
    private String order_no;
    private String oid;

    private int pay_type = 1;
    private IWXAPI api;//微信支付参数
    public static ChargeActivity activity;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge);
        activity = this;
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("充值");
        balance_id = getIntent().getStringExtra("balance_id");

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

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        order_no = object.optJSONObject("result").optString("order_no");
                        oid = object.optJSONObject("result").optString("oid");
                        popPay();
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
                        if(pay_type == 1){
                            JSONObject payObj = new JSONObject(object.optString("result"));
                            doAlipay(payObj.optString("partner"),payObj.optString("seller"),payObj.optString("order_title"),payObj.optString("productName"),
                                    payObj.optString("tradeNO"),payObj.optString("notifyURL"),payObj.optString("amount"));
                        }else if(pay_type == 2){
                            JSONObject payObj = new JSONObject(object.optString("result"));
                            doWeiXinPay(payObj.optString("appid"),payObj.optString("partnerid"),payObj.optString("prepayid"),payObj.optString("noncestr"),
                                    payObj.optString("timestamp"),payObj.optString("package"),payObj.optString("sign"));
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
            case PayUitl.SDK_PAY_FLAG:
                PayResult payResult = new PayResult((String) msg.obj);
                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();
                String resultStatus = payResult.getResultStatus();

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    it = new Intent(mContext,ChargeSuccessActivity.class);
                    it.putExtra("type","支付宝支付");
                    it.putExtra("amount",etAmount.getText().toString());
                    startActivity(it);
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                    finish();
                }else if (TextUtils.equals(resultStatus, "8000")) {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    ToastUtil.show(mContext,"支付结果确认中");
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    ToastUtil.show(mContext,"支付失败");
                }
                break;
            case PayUitl.SDK_CHECK_FLAG:
                ToastUtil.show(mContext,"检查结果为：" + msg.obj);
                break;
            case 3: //微信支付成功
                it = new Intent(mContext,ChargeSuccessActivity.class);
                it.putExtra("type","支付宝支付");
                it.putExtra("amount",etAmount.getText().toString());
                startActivity(it);
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
                break;
            case 4: //微信支付失败
                ToastUtil.show(mContext,"支付取消");
                break;
            case 5: //微信支付失败
                ToastUtil.show(mContext,"支付失败，请重试");
                break;
        }
        return false;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void doAlipay(String partner,String seller_id,String subject,String body,String out_trade_no,String notify_url,String amount) {
        // 订单
        String orderInfo = PayUitl.getOrderInfo(partner,seller_id,subject,body,out_trade_no, notify_url, amount);
        // 对订单做RSA 签名
        String sign = PayUitl.sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + PayUitl.getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                SimpleHUD.dismiss();
                PayTask alipay = new PayTask(ChargeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo,true);

                Message msg = new Message();
                msg.what = PayUitl.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void doWeiXinPay(String appId, String partnerId, String prepayId, String nonceStr,
                            String timeStamp,String packageValue, String sign) {
        SimpleHUD.dismiss();
        api = WXAPIFactory.createWXAPI(mContext,null);
        api.registerApp(appId);
        PayReq req = new PayReq();
        req.appId			= appId;
        req.partnerId		= partnerId;
        req.prepayId		= prepayId;
        req.nonceStr		= nonceStr;
        req.timeStamp		= timeStamp;
        req.packageValue	= packageValue;
        req.sign			= sign;

        Toast.makeText(this, "调起支付...", Toast.LENGTH_SHORT).show();
        api.sendReq(req);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_confirm})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_confirm:
                if(StringUtils.isEmpty(etAmount.getText().toString())){
                    ToastUtil.show(mContext,"请输入充值金额");
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.account_recharge_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),balance_id,etAmount.getText().toString()),true);
                break;
        }
    }

    private void popPay(){
        NiceDialog.init().setLayoutId(R.layout.pop_pay)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        ImageView ivClose = holder.getView(R.id.iv_close);
                        TextView txtPayAmount = holder.getView(R.id.txt_pay_amount);
                        txtPayAmount.setText(etAmount.getText().toString());
                        final TextView txtAlipay = holder.getView(R.id.txt_alipay);
                        final TextView txtWxpay = holder.getView(R.id.txt_wxpay);
                        final TextView txtYue = holder.getView(R.id.txt_yue);
                        txtYue.setVisibility(View.GONE);
                        TextView txtConfirmPay = holder.getView(R.id.txt_confirm_pay);
                        txtAlipay.setSelected(true);
                        pay_type = 1;
                        txtAlipay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_type = 1;
                                txtAlipay.setSelected(true);txtWxpay.setSelected(false);
                            }
                        });
                        txtWxpay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_type = 2;
                                txtAlipay.setSelected(false);txtWxpay.setSelected(true);
                            }
                        });
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        txtConfirmPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.order_pay_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                        order_no,oid,pay_type),true);
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(true).setHeight(300)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
