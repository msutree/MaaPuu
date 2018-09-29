package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class TiXianResultActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_label_1)
    TextView txtLabel1;
    @BindView(R.id.txt_label_2)
    TextView txtLabel2;
    @BindView(R.id.txt_time_1)
    TextView txtTime1;
    @BindView(R.id.txt_time_2)
    TextView txtTime2;

    private String result;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tixian_result);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("结果详情");
        result = getIntent().getStringExtra("result");
        try {
            JSONObject resultObj = new JSONObject(result);
            JSONObject beginObj = resultObj.optJSONObject("detail_begin");
            txtLabel1.setText(beginObj.optString("desc"));
            txtTime1.setText(beginObj.optString("begin_time_text"));
            JSONObject endObj = resultObj.optJSONObject("detail_end");
            txtLabel2.setText(endObj.optString("desc"));
            txtTime2.setText(endObj.optString("end_time_text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
