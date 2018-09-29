package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.camera.CaptureActivity;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.SearchEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/1.
 */

public class AddFriendsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.et_search)
    SearchEditText etSearch;

    private AlertView alertView;
    private String alert;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friends);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("添加好友");
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim().equals("")){
                        ToastUtil.show(mContext, "请输入好友手机号或昵称");
                    }else {
                        it = new Intent(mContext,SearchFriendsActivity.class);
                        it.putExtra("keyword",etSearch.getText().toString());
                        startActivity(it);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_my_code,R.id.txt_scan,R.id.txt_phone_contact,R.id.txt_nearby_person})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_my_code:
                startActivity(new Intent(mContext,MyEwmActivity.class));
                break;
            case R.id.txt_scan:
                alert = "使用扫一扫功能，需打开相机权限？";
                requestLocationPermission();
                break;
            case R.id.txt_phone_contact:
                alert = "需开启读取联系人权限？";
                requestLocationPermission1();
//                startActivity(new Intent(mContext,PhoneContactsActivity.class));
                break;
            case R.id.txt_nearby_person:
                startActivity(new Intent(mContext,NearbyPersonActivity.class));
                break;
        }
    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
            it = new Intent(mContext, CaptureActivity.class);
            it.putExtra("isAddFriends",true);
            startActivity(it);
        }else {
            EasyPermissions.requestPermissions(AddFriendsActivity.this,"您需要开启相机权限", AppConfig.PERMISSIONCODE,
                    Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE1)
    private void requestLocationPermission1() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.READ_CONTACTS)) {
            startActivity(new Intent(mContext,PhoneContactsActivity.class));
        }else {
            EasyPermissions.requestPermissions(AddFriendsActivity.this,"需开启读取联系人权限", AppConfig.PERMISSIONCODE1,
                    Manifest.permission.READ_CONTACTS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        alertView = new AlertView(null, alert, "取消", null, new String[]{"确定"}, mContext,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    JumpPermissionManagement.GoToSetting(AddFriendsActivity.this);
                }
            }
        });
        alertView.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
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
