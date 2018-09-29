package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SearchAddress;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/5.
 */

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.txt_choose_address)
    TextView txtChooseAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.txt_default)
    TextView txtDefault;

    private SearchAddress address;
    private String address_id;
    private String district_name;
    private String lat;
    private String lng;

    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("保存");txtRight.setVisibility(View.VISIBLE);
        address_id = getIntent().getStringExtra("address_id");
        if(address_id.equals("0")){
            txtTitle.setText("添加新地址");
        }else {
            txtTitle.setText("编辑地址");
        }
    }

    @Override
    public void initData() {
        if(!address_id.equals("0")){
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,UrlUtils.address_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                    address_id),true);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        address_id = resultObj.optString("address_id");
                        lat = resultObj.optString("lat");
                        lng = resultObj.optString("lng");
                        district_name = resultObj.optString("district_name");
                        etName.setText(resultObj.optString("receiver"));
                        etPhone.setText(resultObj.optString("receiver_phone"));
                        txtChooseAddress.setText(resultObj.optString("city_district_name"));
                        etDetail.setText(resultObj.optString("address_detail"));
                        txtDefault.setSelected(resultObj.optString("is_default").equals("1"));
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
                        ToastUtil.show(mContext,"保存成功");
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
    @OnClick({R.id.txt_left,R.id.txt_choose_address,R.id.txt_default,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_default:
                txtDefault.setSelected(!txtDefault.isSelected());
                break;
            case R.id.txt_choose_address:
                if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    startActivityForResult(new Intent(mContext,LocateActivity.class), AppConfig.ACTIVITY_REQUESTCODE);
                }else {
                    alertView = new AlertView(null, "需开启定位权限", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                JumpPermissionManagement.GoToSetting(AddAddressActivity.this);
                            }
                        }
                    });
                    alertView.show();
                }
                break;
            case R.id.txt_right:
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请输入收货人");
                    return;
                }
                if(StringUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtil.show(mContext,"请输入联系方式");
                    return;
                }
                if(etPhone.getText().toString().length() != 11){
                    ToastUtil.show(mContext,"请输入正确的联系方式");
                    return;
                }
                if(StringUtils.isEmpty(txtChooseAddress.getText().toString())){
                    ToastUtil.show(mContext,"请选择省市区");
                    return;
                }
                if(StringUtils.isEmpty(etDetail.getText().toString())){
                    ToastUtil.show(mContext,"请输入详细地址");
                    return;
                }
                if(StringUtils.isEmpty(lat)||lat.equals("0")
                        ||StringUtils.isEmpty(lng)||lng.equals("0")){
                    ToastUtil.show(mContext,"未能获取到地址经纬度");
                    return;
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.address_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        address_id,etName.getText().toString(),etPhone.getText().toString(),district_name,etDetail.getText().toString(),lat,lng,
                        txtDefault.isSelected()?"1":"0"),true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                address = (SearchAddress) data.getSerializableExtra("searchAddress");
                district_name = address.getArea();
                lat = address.getLatitude();
                lng = address.getLongitude();
                txtChooseAddress.setText(address.getProvince()+address.getCity()+address.getArea());
                etDetail.setText(address.getName());
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
