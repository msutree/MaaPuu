package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
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
 * 员工信息
 * Created by Jia on 2018/3/16.
 */

public class EmployeeInfoActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;
    @BindView(R.id.image)
    CircleImgView image;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_age)
    TextView txtAge;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_card_no)
    TextView txtCardNo;
    @BindView(R.id.txt_birth)
    TextView txtBirth;
    @BindView(R.id.txt_post_name)
    TextView txtPostName;
    @BindView(R.id.txt_entry_time)
    TextView txtEntryTime;
    @BindView(R.id.txt_intro)
    TextView txtIntro;
    @BindView(R.id.txt_remark)
    TextView txtRemark;

    private String staff_id;
    private String result;
    private boolean isEdit = false;//是否编辑过了
    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_employee_info);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("员工信息");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
        staff_id = getIntent().getStringExtra("staff_id");
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_staff_info_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),staff_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        result = object.optString("result");
                        UIUtils.loadImg(mContext,resultObj.optString("staff_avatar"),image);
                        txtShopName.setText(resultObj.optString("shop_name"));
                        txtName.setText(resultObj.optString("staff_name"));
                        if(resultObj.optString("sex").equals("0")){
                            txtSex.setText("未知");
                        }else if(resultObj.optString("sex").equals("1")){
                            txtSex.setText("男");
                        }else if(resultObj.optString("sex").equals("2")){
                            txtSex.setText("女");
                        }
                        txtAge.setText(resultObj.optString("age"));
                        txtPhone.setText(resultObj.optString("phone"));
                        txtCardNo.setText(resultObj.optString("card_id"));
                        txtBirth.setText(resultObj.optString("birthday"));
                        txtPostName.setText(resultObj.optString("post_name"));
                        txtEntryTime.setText(resultObj.optString("shop_entry_time"));
                        txtIntro.setText(resultObj.optString("staff_intro"));
                        txtRemark.setText(resultObj.optString("staff_memo"));
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
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_right2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                if(isEdit){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                    finish();
                }
                finish();
                break;
            case R.id.txt_right:
                alertView = new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_staff_delete_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),staff_id),true);
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.txt_right2:
                it = new Intent(mContext,AddEmployeeActivity.class);
                it.putExtra("isEdit",true);
                it.putExtra("staff_id",staff_id);
                it.putExtra("result",result);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                isEdit = true;
                initData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            if(isEdit){
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
            }
            super.onBackPressed();
        }
    }
}
