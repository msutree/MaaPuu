package com.maapuu.mereca.background.employee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完成服务 废弃 2018/4/10.
 * Created by Jia on 2018/3/7.
 */

public class FinishServiceActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.fs_sex)
    TextView sexTv;

    @BindView(R.id.fs_finished_tv)
    TextView finishTv;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.em_activity_finish_service);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("完成服务");

        sexTv.setTypeface(StringUtils.getFont(mContext));
        sexTv.setText("\ue694");//女 &#xe694;   男&#xe695;
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.fs_finished_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.fs_finished_tv:
                Intent intent = new Intent(mContext,MyServiceActivity.class);
                intent.putExtra("selectedPosition",1);
                UIUtils.startActivity(mContext,intent);

                finish();

                break;
        }
    }

}
