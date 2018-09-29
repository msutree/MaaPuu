package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class ChargeSuccessActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.txt_pay_amount)
    TextView txtPayAmount;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge_success);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("充值成功");
        txtPayType.setText(getIntent().getStringExtra("type"));
        txtPayAmount.setText("¥ "+getIntent().getStringExtra("amount"));
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_complete})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
            case R.id.txt_complete:
                finish();
                break;
        }
    }
}
