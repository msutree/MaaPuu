package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppManager;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class BanKaSuccessActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.txt_card_name)
    TextView txtCardName;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_birth)
    TextView txtBirth;
    @BindView(R.id.txt_order_no)
    TextView txtOrderNo;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.txt_order_time)
    TextView txtOrderTime;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_banka_success);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("办卡成功");
        txtLabel.setText("您已成功办理"+getIntent().getStringExtra("shop_name")+getIntent().getStringExtra("card_type")+"一张");
        txtCardName.setText(getIntent().getStringExtra("card_name"));
        txtPrice.setText("¥"+getIntent().getStringExtra("price"));
        txtName.setText(getIntent().getStringExtra("name"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtBirth.setText(getIntent().getStringExtra("birth"));
        txtOrderNo.setText("订单编号："+getIntent().getStringExtra("order_no"));
        txtPayType.setText("支付方式："+getIntent().getStringExtra("pay_type"));
        txtOrderTime.setText("下单时间："+StringUtils.getTime1(new Date()));
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_card_center})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                if(AppManager.getActivity(BuyMembershipCardActivity.class) != null){
                    AppManager.finishActivity(BuyMembershipCardActivity.class);
                }
                if(AppManager.getActivity(MembershipDetailActivity.class) != null){
                    AppManager.finishActivity(MembershipDetailActivity.class);
                }
                if(AppManager.getActivity(MembershipCenterActivity.class) != null){
                    AppManager.finishActivity(MembershipCenterActivity.class);
                }
                if(KaBaoActivity.activity != null){
                    KaBaoActivity.activity.mHandler.sendEmptyMessage(999);
                }
                finish();
                break;
            case R.id.txt_card_center:
                if(AppManager.getActivity(BuyMembershipCardActivity.class) != null){
                    AppManager.finishActivity(BuyMembershipCardActivity.class);
                }
                if(AppManager.getActivity(MembershipDetailActivity.class) != null){
                    AppManager.finishActivity(MembershipDetailActivity.class);
                }
                if(AppManager.getActivity(MembershipCenterActivity.class) != null){
                    AppManager.finishActivity(MembershipCenterActivity.class);
                }
                if(KaBaoActivity.activity != null){
                    KaBaoActivity.activity.mHandler.sendEmptyMessage(999);
                }
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(AppManager.getActivity(BuyMembershipCardActivity.class) != null){
            AppManager.finishActivity(BuyMembershipCardActivity.class);
        }
        if(AppManager.getActivity(MembershipDetailActivity.class) != null){
            AppManager.finishActivity(MembershipDetailActivity.class);
        }
        if(AppManager.getActivity(MembershipCenterActivity.class) != null){
            AppManager.finishActivity(MembershipCenterActivity.class);
        }
        if(KaBaoActivity.activity != null){
            KaBaoActivity.activity.mHandler.sendEmptyMessage(999);
        }
        finish();
        super.onBackPressed();
    }
}
