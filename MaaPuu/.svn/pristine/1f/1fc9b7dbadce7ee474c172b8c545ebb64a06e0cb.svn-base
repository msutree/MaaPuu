package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class WhoCanSeeActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;

    private int open_type = 1; //公开方式：1公开；2好友可见；3私密

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_who_can_see);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("谁可以看");
        open_type = getIntent().getIntExtra("open_type",1);
        switch (open_type){
            case 1:
                iv1.setSelected(true);iv2.setSelected(false);iv3.setSelected(false);
                break;
            case 2:
                iv1.setSelected(false);iv2.setSelected(true);iv3.setSelected(false);
                break;
            case 3:
                iv1.setSelected(false);iv2.setSelected(false);iv3.setSelected(true);
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.ll_1,R.id.ll_2,R.id.ll_3})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.ll_1:
                open_type = 1;
                it = new Intent();
                it.putExtra("open_type",open_type);
                setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                finish();
                break;
            case R.id.ll_2:
                open_type = 2;
                it = new Intent();
                it.putExtra("open_type",open_type);
                setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                finish();
                break;
            case R.id.ll_3:
                open_type = 3;
                it = new Intent();
                it.putExtra("open_type",open_type);
                setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                finish();
                break;
        }
    }
}
