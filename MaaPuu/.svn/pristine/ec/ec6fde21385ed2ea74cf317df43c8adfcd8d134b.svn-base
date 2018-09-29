package com.maapuu.mereca.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.maapuu.mereca.activity.BuyMembershipCardActivity;
import com.maapuu.mereca.activity.ChargeActivity;
import com.maapuu.mereca.activity.ConfirmCampaignOrderActivity;
import com.maapuu.mereca.activity.ConfirmOrderActivity;
import com.maapuu.mereca.activity.ConfirmProjectOrderActivity;
import com.maapuu.mereca.activity.KaBaoDetailActivity;
import com.maapuu.mereca.activity.OrderGoodsActivity;
import com.maapuu.mereca.activity.OrderGoodsDetailActivity;
import com.maapuu.mereca.activity.OrderProjectActivity;
import com.maapuu.mereca.activity.OrderProjectDetailActivity;
import com.maapuu.mereca.util.alipay.Keys;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        api = WXAPIFactory.createWXAPI(this, Keys.WEIXIN_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                sendMsg(3);
            } else if (resp.errCode == -2){//支付已取消
                sendMsg(4);
            } else{//支付失败，请重试
                sendMsg(5);
            }
        }
        finish();
    }

    private void sendMsg(int code){
        if(ChargeActivity.activity != null){
            ChargeActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(BuyMembershipCardActivity.activity != null){
            BuyMembershipCardActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(KaBaoDetailActivity.activity != null){
            KaBaoDetailActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(ConfirmOrderActivity.activity != null){
            ConfirmOrderActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(ConfirmProjectOrderActivity.activity != null){
            ConfirmProjectOrderActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(ConfirmCampaignOrderActivity.activity != null){
            ConfirmCampaignOrderActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(OrderGoodsActivity.activity != null){
            OrderGoodsActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(OrderGoodsDetailActivity.activity != null){
            OrderGoodsDetailActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(OrderProjectActivity.activity != null){
            OrderProjectActivity.activity.mHandler.sendEmptyMessage(code);
        }
        if(OrderProjectDetailActivity.activity != null){
            OrderProjectDetailActivity.activity.mHandler.sendEmptyMessage(code);
        }
    }
}