package com.maapuu.mereca.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.maapuu.mereca.util.UIUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/3/5.
 */

public class OpenShopActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.iv_yyzz)
    ImageView ivYyzz;
    @BindView(R.id.iv_yyzz_sc)
    ImageView ivYyzzSc;
    @BindView(R.id.iv_sfz_z)
    ImageView ivSfzZ;
    @BindView(R.id.iv_sfz_f)
    ImageView ivSfzF;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;

    private AlertView alertView;
    private SearchAddress address;
    private List<String> uploadList;
    private int imgListPos = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_open_shop);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("我要开店");
        uploadList = new ArrayList<>();
        uploadList.add("");uploadList.add("");uploadList.add("");uploadList.add("");
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                SimpleHUD.dismiss();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"提交成功，请耐心等待审核结果");
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                if (msg.arg1 == 1) {
                    try {
                        JSONObject object = new JSONObject((String) msg.obj);
                        if (object.has("src") && !object.optString("src").equals("")) {
                            JSONObject resultObj = object.optJSONObject("result");
                            uploadList.set(imgListPos,resultObj.optString("src"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(msg.arg2 == uploadList.size()-1){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.open_shop_apply_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                            uploadList.get(0),uploadList.get(1),uploadList.get(2),uploadList.get(3),etShopName.getText().toString(),etName.getText().toString(),
                            etPhone.getText().toString(),txtAddress.getText().toString()+etDetail.getText().toString()),false);
                }else {
                    imgListPos++;
                    image_save_set(uploadList.get(imgListPos), imgListPos, false);
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                SimpleHUD.dismiss();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.iv_yyzz,R.id.iv_yyzz_sc,R.id.iv_sfz_z,R.id.iv_sfz_f,R.id.txt_address,R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.iv_yyzz:
                choosePicture(1001);
                break;
            case R.id.iv_yyzz_sc:
                choosePicture(1002);
                break;
            case R.id.iv_sfz_z:
                choosePicture(1003);
                break;
            case R.id.iv_sfz_f:
                choosePicture(1004);
                break;
            case R.id.txt_address:
                if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    startActivityForResult(new Intent(mContext,LocateActivity.class), AppConfig.ACTIVITY_REQUESTCODE);
                }else {
                    alertView = new AlertView(null, "需开启定位权限", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                JumpPermissionManagement.GoToSetting(OpenShopActivity.this);
                            }
                        }
                    });
                    alertView.show();
                }
                break;
            case R.id.txt_commit:
                if(StringUtils.isEmpty(uploadList.get(0))){
                    ToastUtil.show(mContext,"请上传营业执照");
                    return;
                }
                if(StringUtils.isEmpty(uploadList.get(1))){
                    ToastUtil.show(mContext,"请上传手持营业执照");
                    return;
                }
                if(StringUtils.isEmpty(uploadList.get(2))){
                    ToastUtil.show(mContext,"请上传身份证正面");
                    return;
                }
                if(StringUtils.isEmpty(uploadList.get(3))){
                    ToastUtil.show(mContext,"请上传身份证反面");
                    return;
                }
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请输入姓名");
                    return;
                }
                if(StringUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtil.show(mContext,"请输入联系电话");
                    return;
                }
                if(StringUtils.isEmpty(etShopName.getText().toString())){
                    ToastUtil.show(mContext,"请输入店铺名称");
                    return;
                }
                if(StringUtils.isEmpty(txtAddress.getText().toString())){
                    ToastUtil.show(mContext,"请选择地址");
                    return;
                }
                if(StringUtils.isEmpty(etDetail.getText().toString())){
                    ToastUtil.show(mContext,"请输入详细地址");
                    return;
                }
                image_save_set(uploadList.get(imgListPos), imgListPos, true);
                break;
        }
    }

    private void image_save_set(String imagePath, int position, Boolean isProgress) {
        mHttpModeBase.xutilsPostList(HttpModeBase.HTTP_REQUESTCODE_IMG, imagePath, position, isProgress);
    }

    private void choosePicture(int code){
        PictureSelector.create(OpenShopActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .withAspectRatio(5,4)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .previewImage(true)// 是否可预览图片
//                .freeStyleCropEnabled(true)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .forResult(code);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                address = (SearchAddress) data.getSerializableExtra("searchAddress");
                txtAddress.setText(address.getProvince()+address.getCity()+address.getArea());
                etDetail.setText(address.getName());
                break;
            case RESULT_OK:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(selectList != null && selectList.size() > 0){
                    if(requestCode == 1001){
                        uploadList.set(0,selectList.get(0).getCutPath());
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),ivYyzz);
                    }else if(requestCode == 1002){
                        uploadList.set(1,selectList.get(0).getCutPath());
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),ivYyzzSc);
                    }else if(requestCode == 1003){
                        uploadList.set(2,selectList.get(0).getCutPath());
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),ivSfzZ);
                    }else if(requestCode == 1004){
                        uploadList.set(3,selectList.get(0).getCutPath());
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),ivSfzF);
                    }
                }else {
                    ToastUtil.show(mContext,"选择图片出错");
                }
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
