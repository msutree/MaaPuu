package com.maapuu.mereca.background.employee.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 取消服务  废弃 2018/4/10.
 * Created by Jia on 2018/3/7.
 */

public class CancelServiceActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.em_activity_cancel_service);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("取消服务");

    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

        }
    }
}
