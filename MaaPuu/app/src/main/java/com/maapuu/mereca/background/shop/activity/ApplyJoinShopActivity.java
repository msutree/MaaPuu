package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/4/13.
 */

public class ApplyJoinShopActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.image)
    CircleImgView image;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_locate)
    TextView txtLocate;

    private String shop_id;
    private String result;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_apply_join_shop);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("加入门店");
        result = getIntent().getStringExtra("result");
        try {
            JSONObject resultObj = new JSONObject(result);
            shop_id = resultObj.optString("shop_id");
            UIUtils.loadImg(mContext,resultObj.optString("shop_logo"),image);
            txtName.setText(resultObj.optString("shop_name"));
            txtLocate.setText(resultObj.optString("district_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
//        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_staff_code_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_code),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        shop_id = resultObj.optString("shop_id");
                        UIUtils.loadImg(mContext,resultObj.optString("shop_logo"),image);
                        txtName.setText(resultObj.optString("shop_name"));
                        txtLocate.setText(resultObj.optString("district_name"));
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"申请加入门店成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
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
    @OnClick({R.id.txt_left,R.id.txt_apply})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_apply:
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_staff_apply_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
                break;
        }
    }
}
