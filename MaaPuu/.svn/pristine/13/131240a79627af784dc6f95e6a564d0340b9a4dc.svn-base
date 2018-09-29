package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
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
 * Created by dell on 2018/3/5.
 */

public class AfterSalesDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.ll_ing)
    LinearLayout llIng;
    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.ll_alert)
    LinearLayout llAlert;
    @BindView(R.id.ll_success)
    LinearLayout llSuccess;
    @BindView(R.id.txt_refun_total_amount)
    TextView txtRefunTotalAmount;
    @BindView(R.id.txt_back_amount)
    TextView txtBackAmount;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.rl_project)
    RelativeLayout rlProject;
    @BindView(R.id.txt_project_name)
    TextView txtProjectName;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_project_price)
    TextView txtProjectPrice;
    @BindView(R.id.rl_goods)
    RelativeLayout rlGoods;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.txt_goods_spec)
    TextView txtGoodsSpec;
    @BindView(R.id.txt_goods_num)
    TextView txtGoodsNum;
    @BindView(R.id.txt_goods_price)
    TextView txtGoodsPrice;
    @BindView(R.id.txt_reason)
    TextView txtReason;
    @BindView(R.id.txt_refun_amount)
    TextView txtRefundAmount;
    @BindView(R.id.txt_num)
    TextView txtNum;
    @BindView(R.id.txt_apply_time)
    TextView txtApplyTime;
    @BindView(R.id.txt_refund_no)
    TextView txtRefundNo;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.txt_btn_1)
    TextView txtBtn1;
    @BindView(R.id.txt_btn_2)
    TextView txtBtn2;

    private AlertView alertView;
    private String refund_id;
    private int type; //1项目   2商品

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_after_sales_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("退款详情");
        refund_id = getIntent().getStringExtra("refund_id");
        type = getIntent().getIntExtra("type",1);
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.refund_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),refund_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        switch (resultObj.optInt("refund_status")){
                            case 1: //退款中
                                ivStatus.setImageResource(R.mipmap.dengdaichuli);
                                txtStatus.setText("请等待商家处理");
                                llIng.setVisibility(View.VISIBLE);
                                llSuccess.setVisibility(View.GONE);
                                txtLabel.setText("您已成功发起退款申请");
                                llAlert.setVisibility(View.VISIBLE);
                                llBtn.setVisibility(View.VISIBLE);
                                break;
                            case 2: //退款成功
                                ivStatus.setImageResource(R.mipmap.tuibai);
                                txtStatus.setText("退款成功");
                                llIng.setVisibility(View.GONE);
                                llSuccess.setVisibility(View.VISIBLE);
                                txtRefunTotalAmount.setText("¥"+resultObj.optString("refund_amount"));
                                txtBackAmount.setText("¥"+resultObj.optString("refund_amount"));
                                llBtn.setVisibility(View.GONE);
                                break;
                            case 3: //取消退款
                                ivStatus.setVisibility(View.GONE);
                                txtStatus.setText("退款关闭");
                                llIng.setVisibility(View.VISIBLE);
                                llSuccess.setVisibility(View.GONE);
                                txtLabel.setText("因您撤销退款申请，退款已关闭，交易将正常进行。如问题未解决，您可再次发起退款申请。");
                                llAlert.setVisibility(View.GONE);
                                llBtn.setVisibility(View.GONE);
                                break;
                            case 4: //商家拒绝
                                ivStatus.setVisibility(View.GONE);
                                txtStatus.setText("商家拒绝退款申请");
                                llIng.setVisibility(View.VISIBLE);
                                llSuccess.setVisibility(View.GONE);
                                txtLabel.setText("因商家拒绝退款申请，退款已关闭，交易将正常进行。如问题未解决，您可再次发起退款申请。");
                                llAlert.setVisibility(View.GONE);
                                llBtn.setVisibility(View.GONE);
                                break;
                        }
                        image.setImageURI(Uri.parse(resultObj.optString("item_img")));
                        if(type == 1){
                            rlProject.setVisibility(View.VISIBLE);
                            rlGoods.setVisibility(View.GONE);
                            txtProjectName.setText(resultObj.optString("item_name"));
                            txtShopName.setText(resultObj.optString("shop_name"));
                            txtProjectPrice.setText("¥"+resultObj.optString("price"));
                        }else {
                            rlProject.setVisibility(View.GONE);
                            rlGoods.setVisibility(View.VISIBLE);
                            txtGoodsName.setText(resultObj.optString("item_name"));
                            txtGoodsSpec.setText(resultObj.optString("item_desc_spec"));
                            txtGoodsPrice.setText("¥"+resultObj.optString("price"));
                            txtGoodsNum.setText("×"+resultObj.optString("num"));
                        }
                        txtReason.setText("退款原因："+resultObj.optString("refund_reason"));
                        txtRefundAmount.setText("退款金额：¥"+resultObj.optString("refund_amount"));
                        txtNum.setText("申请件数："+resultObj.optString("num")+"件");
                        txtApplyTime.setText("申请时间："+resultObj.optString("create_time_text"));
                        txtRefundNo.setText("退款编号："+resultObj.optString("refund_no"));
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
                        ToastUtil.show(mContext,"撤销申请成功");
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
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_btn_1,R.id.txt_btn_2})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_btn_1:
                it = new Intent(mContext,ApplyRefundActivity.class);
                startActivity(it);
                break;
            case R.id.txt_btn_2:
                alertView = new AlertView(null, "您将撤销本次申请，如果问题未解决，\n您可以再次发起。确定继续吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.refund_revoke_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),refund_id),true);
                        }
                    }
                });
                alertView.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
