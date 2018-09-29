package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.YWAPI;
import com.alipay.sdk.app.PayTask;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.OrderChildBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PayUitl;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.alipay.PayResult;
import com.maapuu.mereca.view.CustomerKeyboard;
import com.maapuu.mereca.view.PayPsdInputView;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * Created by dell on 2018/3/5.
 */

public class OrderGoodsDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_srv_price)
    TextView txtSrvPrice;
    @BindView(R.id.txt_youhui_price)
    TextView txtYouhuiPrice;
    @BindView(R.id.txt_order_price)
    TextView txtOrderPrice;
    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.txt_pay_amount)
    TextView txtPayAmount;
    @BindView(R.id.txt_order_no)
    TextView txtOrderNo;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.txt_order_time)
    TextView txtOrderTime;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.txt_btn_1)
    TextView txtBtn1;
    @BindView(R.id.txt_btn_2)
    TextView txtBtn2;
    @BindView(R.id.txt_btn_3)
    TextView txtBtn3;

    private LinearLayoutManager mLayoutManager;
    private List<OrderChildBean> list;
    private BaseRecyclerAdapter<OrderChildBean> adapter;

    private String oid;
    private String shop_service;
    private AlertView alertView;
    private String order_no;
    private String pay_amount;
    private String balance;
    private String status;
    private boolean isPay = false;
    private int logistics_type;

    private int pay_type = 1;
    private IWXAPI api;//微信支付参数
    public static OrderGoodsDetailActivity activity;
    private String order_title;
    private int is_pay_pwd ;
    private NiceDialog payDialog;
    private NiceDialog yueDialog;
    private PayPsdInputView etPwd;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_order_detail);
        activity = this;
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("订单详情");
        oid = getIntent().getStringExtra("oid");

        list = new ArrayList<>();

        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,UrlUtils.commodity_order_detail_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"), oid),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("shop_data") && !StringUtils.isEmpty(resultObj.optString("shop_data")) && !(resultObj.opt("shop_data") instanceof Boolean)){
                            JSONObject shopObj = resultObj.optJSONObject("shop_data");
                            txtShopName.setText(shopObj.optString("shop_name"));
                            shop_service = shopObj.optString("shop_service");
                        }
                        if(resultObj.has("address_info") && !StringUtils.isEmpty(resultObj.optString("address_info")) && !(resultObj.opt("address_info") instanceof Boolean)){
                            JSONObject addressObj = resultObj.optJSONObject("address_info");
                            txtName.setText(addressObj.optString("receiver"));
                            txtPhone.setText(addressObj.optString("receiver_phone"));
                            txtAddress.setText("收货地址："+addressObj.optString("address_detail"));
                        }
                        if(resultObj.has("items") && !StringUtils.isEmpty(resultObj.optString("items")) && !(resultObj.opt("items") instanceof Boolean)){
                            list = FastJsonTools.getPersons(resultObj.optString("items"),OrderChildBean.class);
                        }
                        if(resultObj.has("order_detail") && !StringUtils.isEmpty(resultObj.optString("order_detail")) && !(resultObj.opt("order_detail") instanceof Boolean)){
                            JSONObject orderObj = resultObj.optJSONObject("order_detail");
                            order_no = orderObj.optString("order_no");
                            txtSrvPrice.setText("¥"+orderObj.optString("order_amount"));
                            txtYouhuiPrice.setText("-¥"+orderObj.optString("dis_amount"));
                            txtOrderPrice.setText("¥"+orderObj.optString("pay_amount"));
                            pay_amount = orderObj.optString("pay_amount");
                            txtPayAmount.setText("¥"+orderObj.optString("pay_amount"));
                            txtOrderNo.setText("订单编号："+orderObj.optString("order_no"));
                            order_title = orderObj.optString("order_title");
                            status = orderObj.optString("status");
                            logistics_type = orderObj.optInt("logistics_type");
                            txtStatus.setText(status);
                            switch (status){
                                case "待付款":
                                    txtLabel.setText("需付款");
                                    txtPayType.setVisibility(View.GONE);
                                    ivStatus.setImageResource(R.mipmap.daifuk);
                                    txtBtn1.setVisibility(View.GONE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.VISIBLE);
                                    txtBtn2.setText("删除订单");txtBtn3.setText("立即支付");
                                    break;
                                case "待发货":
                                    txtLabel.setText("已付款");
                                    txtPayType.setVisibility(View.VISIBLE);
                                    ivStatus.setImageResource(R.mipmap.daishouhuobai);
                                    txtBtn1.setVisibility(View.GONE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.GONE);
                                    txtBtn2.setText("提醒发货");
                                    if(isAbleClick()){
                                        txtBtn2.setTextColor(getResources().getColor(R.color.text_33));
                                        txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_99_radius20));
                                    }else {
                                        txtBtn2.setTextColor(getResources().getColor(R.color.text_d9));
                                        txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                    }
                                    break;
                                case "待收货":
                                    txtLabel.setText("已付款");
                                    txtPayType.setVisibility(View.VISIBLE);
                                    ivStatus.setImageResource(R.mipmap.daifahuobai);
                                    txtBtn1.setVisibility(View.VISIBLE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.VISIBLE);
                                    if(isAbleClick()){
                                        if(logistics_type == 1){
                                            txtBtn2.setTextColor(getResources().getColor(R.color.text_33));
                                            txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_99_radius20));
                                        }else {
                                            txtBtn2.setTextColor(getResources().getColor(R.color.text_d9));
                                            txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                        }
                                        txtBtn1.setTextColor(getResources().getColor(R.color.text_33));
                                        txtBtn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_99_radius20));
                                        txtBtn3.setTextColor(getResources().getColor(R.color.text_price));
                                        txtBtn3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_red_radius20));
                                    }else {
                                        txtBtn1.setTextColor(getResources().getColor(R.color.text_d9));
                                        txtBtn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                        txtBtn2.setTextColor(getResources().getColor(R.color.text_d9));
                                        txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                        txtBtn3.setTextColor(getResources().getColor(R.color.text_d9));
                                        txtBtn3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                    }
                                    txtBtn1.setText("投诉");txtBtn2.setText("查看物流");txtBtn3.setText("确认收货");
                                    break;
                                case "待评价":
                                    txtLabel.setText("已付款");
                                    txtPayType.setVisibility(View.VISIBLE);
                                    ivStatus.setImageResource(R.mipmap.daipingjiabai);
                                    txtBtn1.setVisibility(View.VISIBLE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.VISIBLE);
                                    if(logistics_type == 1){
                                        txtBtn2.setTextColor(getResources().getColor(R.color.text_price));
                                        txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_red_radius20));
                                    }else {
                                        txtBtn2.setTextColor(getResources().getColor(R.color.text_d9));
                                        txtBtn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_solid_stroke_d9_radius20));
                                    }
                                    txtBtn1.setText("投诉");txtBtn2.setText("查看物流");txtBtn3.setText("评价");
                                    break;
                                case "已取消":
                                    txtLabel.setText("已付款");
                                    txtPayType.setVisibility(View.GONE);
                                    ivStatus.setImageResource(R.mipmap.daifuk);
                                    txtBtn1.setVisibility(View.GONE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.GONE);
                                    txtBtn2.setText("删除订单");
                                    break;
                                case "已完成":
                                    txtLabel.setText("已付款");
                                    txtPayType.setVisibility(View.GONE);
                                    ivStatus.setImageResource(R.mipmap.daifuk);
                                    txtBtn1.setVisibility(View.GONE);txtBtn2.setVisibility(View.VISIBLE);txtBtn3.setVisibility(View.GONE);
                                    txtBtn2.setText("删除订单");
                                    break;
                                default:
                                    llBtn.setVisibility(View.GONE);
                                    break;
                            }
                            switch (orderObj.optInt("pay_type")){
                                case 1:
                                    txtPayType.setText("支付方式：支付宝支付");
                                    break;
                                case 2:
                                    txtPayType.setText("支付方式：微信支付");
                                    break;
                                case 3:
                                    txtPayType.setText("支付方式：余额支付");
                                    break;
                            }
                            txtOrderTime.setText("下单时间："+orderObj.optString("create_time_text"));
                        }
                        balance = resultObj.optString("balance");
                        is_pay_pwd = resultObj.optInt("is_pay_pwd");
                        setAdapter();
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
                        ToastUtil.show(mContext,"删除成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
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
                        if(pay_type == 1){
                            JSONObject payObj = new JSONObject(object.optString("result"));
                            doAlipay(payObj.optString("partner"),payObj.optString("seller"),payObj.optString("order_title"),payObj.optString("productName"),
                                    payObj.optString("tradeNO"),payObj.optString("notifyURL"),payObj.optString("amount"));
                        }else if(pay_type == 2){
                            JSONObject payObj = new JSONObject(object.optString("result"));
                            doWeiXinPay(payObj.optString("appid"),payObj.optString("partnerid"),payObj.optString("prepayid"),payObj.optString("noncestr"),
                                    payObj.optString("timestamp"),payObj.optString("package"),payObj.optString("sign"));
                        }else if(pay_type == 3){
                            if(yueDialog != null){
                                yueDialog.dismiss();
                            }
                            if(payDialog != null){
                                payDialog.dismiss();
                            }
                            ToastUtil.show(mContext,"支付成功");
                            isPay = true;
                            initData();
                        }
                    } else {
                        if(pay_type == 3){
                            NiceDialog.init()
                                    .setLayoutId(R.layout.nd_layout_confirm)
                                    .setConvertListener(new ViewConvertListener() {
                                        @Override
                                        public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                                            holder.setText(R.id.title,"提示");
                                            holder.setText(R.id.message,"支付密码不正确");
                                            holder.setText(R.id.cancel,"重新输入");
                                            holder.setText(R.id.ok,"忘记密码");

                                            holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    etPwd.cleanPsd();
                                                }
                                            });

                                            holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    it = new Intent(mContext,SetPayPwdActivity.class);
                                                    startActivityForResult(it,AppConfig.ACTIVITY_RESULTCODE);
                                                }
                                            });
                                        }
                                    })
                                    .setWidth(270)
                                    .setOutCancel(false)
                                    .show(getSupportFragmentManager());
                        }else {
                            ToastUtil.show(mContext,object.optString("message"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_4:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"提醒发货成功");
                        isPay = true;
                        initData();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_5:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"收货成功");
                        isPay = true;
                        initData();
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
                    ToastUtil.show(mContext,"支付成功");
                    isPay = true;
                    initData();
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
                ToastUtil.show(mContext,"支付成功");
                isPay = true;
                initData();
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

    private void setAdapter() {
        adapter = new BaseRecyclerAdapter<OrderChildBean>(mContext,list,R.layout.layout_goods_order_good_item) {
            @Override
            public void convert(BaseRecyclerHolder cHolder, OrderChildBean child, final int pos, boolean isScrolling) {
                cHolder.setSimpViewImageUri(R.id.iv_goods, Uri.parse(child.getItem_img()));
                cHolder.setText(R.id.txt_goods_name,child.getItem_name());
                cHolder.setText(R.id.txt_goods_spec,"规格："+child.getItem_specification());
                cHolder.setText(R.id.txt_goods_price,"¥"+child.getPrice());
                cHolder.setText(R.id.txt_goods_num,"×"+child.getNum());
                if(list.get(pos).getIs_refund().equals("1")){
                    cHolder.setVisible(R.id.txt_apply_refund,true);
                    cHolder.setVisible(R.id.txt_refund_status,false);
                }else {
                    cHolder.setVisible(R.id.txt_apply_refund,false);
                    cHolder.setVisible(R.id.txt_refund_status,true);
                    cHolder.setText(R.id.txt_refund_status,child.getRefund_text());
                }
                cHolder.setOnClickListener(R.id.txt_apply_refund, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        it = new Intent(mContext,ApplyRefundActivity.class);
                        it.putExtra("order_detail_id",list.get(pos).getOrder_detail_id());
                        startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    /**
     * @return	true
     */
    private boolean isAbleClick(){
        boolean flag = false;
        for(int i=0 ; i < list.size() ; i++){
            if ("1".equals(list.get(i).getIs_refund())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void popPay(){
        payDialog = NiceDialog.init();
        payDialog.setLayoutId(R.layout.pop_pay)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        ImageView ivClose = holder.getView(R.id.iv_close);
                        TextView txtPayAmount = holder.getView(R.id.txt_pay_amount);
                        txtPayAmount.setText("¥ "+pay_amount);
                        final TextView txtAlipay = holder.getView(R.id.txt_alipay);
                        final TextView txtWxpay = holder.getView(R.id.txt_wxpay);
                        final TextView txtYue = holder.getView(R.id.txt_yue);
                        String yue = "余额支付(余额："+balance+")";
                        SpannableStringBuilder builder = new SpannableStringBuilder(yue);
                        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#ed4272"));
                        builder.setSpan(span, 8, 8+String.valueOf(balance).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        txtYue.setText(builder);
                        TextView txtConfirmPay = holder.getView(R.id.txt_confirm_pay);
                        txtAlipay.setSelected(true);
                        pay_type = 1;
                        txtAlipay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_type = 1;
                                txtAlipay.setSelected(true);txtWxpay.setSelected(false);txtYue.setSelected(false);
                            }
                        });
                        txtWxpay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_type = 2;
                                txtAlipay.setSelected(false);txtWxpay.setSelected(true);txtYue.setSelected(false);
                            }
                        });
                        txtYue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_type = 3;
                                txtAlipay.setSelected(false);txtWxpay.setSelected(false);txtYue.setSelected(true);
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
                                if(pay_type == 3 && Double.parseDouble(balance) < Double.parseDouble(pay_amount)){
                                    ToastUtil.show(mContext,"余额不足！");
                                    return;
                                }
                                if(pay_type == 3){
                                    if(is_pay_pwd == 0){
                                        it = new Intent(mContext,SetPayPwdActivity.class);
                                        startActivityForResult(it,AppConfig.ACTIVITY_RESULTCODE);
                                    }else {
                                        payConfirm();
                                    }
                                }else {
                                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.order_pay_data_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                            order_no,oid,pay_type),true);
                                }
                            }
                        });
                    }
                })
                .setOutCancel(false).setShowBottom(true).setHeight(300)
                .show(getSupportFragmentManager());
    }

    private void payConfirm(){
        yueDialog = NiceDialog.init();
        yueDialog.setLayoutId(R.layout.pop_yue_pay)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView txtClose = holder.getView(R.id.txt_close);
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        txtClose.setTypeface(StringUtils.getFont(mContext));
                        etPwd = holder.getView(R.id.et_pwd);
                        CustomerKeyboard keyboard = holder.getView(R.id.keyboard);
                        keyboard.setOnCustomerKeyboardClickListener(new CustomerKeyboard.CustomerKeyboardClickListener() {
                            @Override
                            public void click(String number) {
                                etPwd.addPassword(number);
                            }

                            @Override
                            public void delete() {
                                etPwd.deleteLastPassword();
                            }
                        });
                        etPwd.setComparePassword(new PayPsdInputView.onPasswordListener() {
                            @Override
                            public void onDifference(String oldPsd, String newPsd) {}

                            @Override
                            public void onEqual(String psd) {}

                            @Override
                            public void inputFinished(String inputPsd) {
                                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.order_yue_pay(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                        oid,order_no,etPwd.getPasswordString()),true);
                            }

                            @Override
                            public void inputUnFinished(String inputPsd) {}
                        });
                    }
                }).setOutCancel(false).setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_contact,R.id.txt_btn_1,R.id.txt_btn_2,R.id.txt_btn_3})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                if(isPay){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                }
                finish();
                break;
            case R.id.txt_contact:
                if(!StringUtils.isEmpty(shop_service)){
                    if(AppConfig.mIMKit == null){
                        AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                    }
                    Intent chat = AppConfig.mIMKit.getChattingActivityIntent(shop_service);
                    chat.putExtra("uid",shop_service);
                    startActivity(chat);
                }
                break;
            case R.id.txt_btn_1:
                it = new Intent(mContext,ComplaintActivity.class);
                it.putExtra("oid",oid);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_btn_2:
                if(status.equals("待付款") || status.equals("已取消") || status.equals("已完成")){
                    alertView = new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int pos) {
                            if (pos == 0) {
                                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.order_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                        oid),true);
                            }
                        }
                    });
                    alertView.show();
                }else if(status.equals("待发货")){
                    if(isAbleClick()){
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4, UrlUtils.order_commodity_remind_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"), oid), true);
                    }
                }else if(status.equals("待收货") || status.equals("待评价")){
                    if(logistics_type == 1){
                        it = new Intent(mContext, WuLiuActivity.class);
                        it.putExtra("oid", oid);
                        startActivity(it);
                    }
                }
                break;
            case R.id.txt_btn_3:
                if(status.equals("待付款")){
                    if(Double.parseDouble(pay_amount) == 0){
                        if(is_pay_pwd == 0){
                            it = new Intent(mContext,SetPayPwdActivity.class);
                            startActivityForResult(it,AppConfig.ACTIVITY_RESULTCODE);
                        }else {
                            pay_type = 3;
                            payConfirm();
                        }
                    }else {
                        popPay();
                    }
                }else if(status.equals("待收货")){
                    if(isAbleClick()){
                        alertView = new AlertView(null, "确定收货吗？", "取消", null, new String[]{"确定"}, mContext,
                                AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int pos) {
                                if (pos == 0) {
                                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5,UrlUtils.order_receive_set(LoginUtil.getInfo("token"),
                                            LoginUtil.getInfo("uid"), oid),true);
                                }
                            }
                        });
                        alertView.show();
                    }
                }else if(status.equals("待评价")){
                    it = new Intent(mContext,PublishGoodsCommentActivity.class);
                    if(list.size() > 0){
                        it.putExtra("image",list.get(0).getItem_img());
                        it.putExtra("name",order_title);
                    }
                    it.putExtra("oid", oid);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                isPay = true;
                initData();
                break;
            case AppConfig.ACTIVITY_RESULTCODE_PWD:
                is_pay_pwd = 1;
                payConfirm();
                break;
        }
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
                PayTask alipay = new PayTask(OrderGoodsDetailActivity.this);
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
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            if(isPay){
                setResult(AppConfig.ACTIVITY_RESULTCODE);
            }
            finish();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
