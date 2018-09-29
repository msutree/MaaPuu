package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.PostBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * 添加员工
 * Created by Jia on 2018/3/16.
 */

public class AddEmployeeActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.image)
    CircleImgView image;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_card_no)
    EditText etCardNo;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.ae_choose_shop_tv)
    TextView chooseShopTv;
    @BindView(R.id.ae_choose_job_tv)
    TextView chooseJobTv;
    @BindView(R.id.ae_entry_date_tv)
    TextView entryDateTv;

    private boolean isEdit;
    private String staff_apply_id;
    private String staff_id;
    private String shop_id;
    private String post_id;
    private String result;

    private List<ShopBean> shopList;
    private List<PostBean> postList;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String upload_path = "";
    private String staff_avatar;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_employee);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("添加员工");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");
        shopList = new ArrayList<>();
        postList = new ArrayList<>();

        isEdit = getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            staff_id = getIntent().getStringExtra("staff_id");
            result = getIntent().getStringExtra("result");
            try {
                JSONObject resultObj = new JSONObject(result);
                shop_id = resultObj.optString("shop_id");
                post_id = resultObj.optString("post_id");
                UIUtils.loadImg(mContext,resultObj.optString("staff_avatar"),image);
                chooseShopTv.setText(resultObj.optString("shop_name"));
                etName.setText(resultObj.optString("staff_name"));
                etName.setSelection(resultObj.optString("staff_name").length());
                etCardNo.setText(resultObj.optString("card_id"));
                chooseJobTv.setText(resultObj.optString("post_name"));
                entryDateTv.setText(resultObj.optString("shop_entry_time"));
                etIntro.setText(resultObj.optString("staff_intro"));
                etRemark.setText(resultObj.optString("staff_memo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            staff_apply_id = getIntent().getStringExtra("staff_apply_id");
            shop_id = getIntent().getStringExtra("shop_id");
            chooseShopTv.setText(getIntent().getStringExtra("shop_name"));
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.has("result") && !StringUtils.isEmpty(object.optString("result"))){
                            shopList = FastJsonTools.getPersons(object.optString("result"),ShopBean.class);
                        }
                        getShopPicker().show();
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
                        if(object.has("result") && !StringUtils.isEmpty(object.optString("result"))){
                            postList = FastJsonTools.getPersons(object.optString("result"),PostBean.class);
                        }
                        getJobPicker().show();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_3:
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
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (object.has("src") && !object.optString("src").equals("")) {
                            staff_avatar = object.optString("src");
                        }
                        save(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleHUD.dismiss();
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
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.as_cover_rl,R.id.ae_choose_shop_lt,R.id.ae_choose_job_rl,R.id.ae_entry_date_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                if(!isEdit && StringUtils.isEmpty(upload_path)){
                    ToastUtil.show(mContext,"请上传员工头像");
                    return;
                }
                if(StringUtils.isEmpty(shop_id)){
                    ToastUtil.show(mContext,"请选择店铺");
                    return;
                }
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请选择姓名");
                    return;
                }
                if(StringUtils.isEmpty(etCardNo.getText().toString())){
                    ToastUtil.show(mContext,"请输入身份证号");
                    return;
                }
                if(StringUtils.isEmpty(post_id)){
                    ToastUtil.show(mContext,"请选择岗位");
                    return;
                }
                if(StringUtils.isEmpty(entryDateTv.getText().toString())){
                    ToastUtil.show(mContext,"请选择入职日期");
                    return;
                }
                if(!StringUtils.isEmpty(upload_path)){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, upload_path,true);
                }else {
                    save(true);
                }
                break;
            case R.id.as_cover_rl:
                PictureSelector.create(AddEmployeeActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .circleDimmedLayer(false) // 是否圆形裁剪 true or false
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(true)// 是否裁剪
                        .compress(true)// 是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.ae_choose_shop_lt:
                if(isEdit){
                    if(shopList.size() > 0){
                        getShopPicker().show();
                    }else {
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),0),true);
                    }
                }
                break;
            case R.id.ae_choose_job_rl:
                if(shopList.size() > 0){
                    getJobPicker().show();
                }else {
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.s_select_post_list_get(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),shop_id),true);
                }
                break;
            case R.id.ae_entry_date_rl:
                showTimePv();
                break;
        }
    }

    private void save(boolean bool){
        if(isEdit){
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.s_staff_info_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                    staff_id,shop_id,staff_avatar,etName.getText().toString(),etCardNo.getText().toString(),post_id,entryDateTv.getText().toString(),
                    etIntro.getText().toString(),etRemark.getText().toString()),bool);
        }else {
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3, UrlUtils.s_staff_agree_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                    staff_apply_id,staff_avatar,etName.getText().toString(),etCardNo.getText().toString(),post_id,entryDateTv.getText().toString(),
                    etIntro.getText().toString(),etRemark.getText().toString()),bool);

        }
    }

    private OptionsPickerView getShopPicker() {
        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (shopList != null && shopList.size() > 0) {
                    shop_id = shopList.get(options1).getShop_id();
                    chooseShopTv.setText(shopList.get(options1).getShop_name());
                }
            }
        })
                .setSelectOptions(0)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(shopList, null, null);
        return pvNoLinkOptions;
    }

    private OptionsPickerView getJobPicker() {
        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (postList != null && postList.size() > 0) {
                    post_id = postList.get(options1).getPost_id();
                    chooseJobTv.setText(postList.get(options1).getPost_name());
                }
            }
        })
                .setSelectOptions(0)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(postList, null, null);
        return pvNoLinkOptions;
    }

    TimePickerView pvCustomTime;
    private void showTimePv() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                entryDateTv.setText(StringUtils.getTime(date));
            }
        }).setCancelColor(UIUtils.getColor(R.color.text_99))
                .setSubmitColor(UIUtils.getColor(R.color.main_color))
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();
        pvCustomTime.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if(requestCode == PictureConfig.CHOOSE_REQUEST){
                    selectList.clear();
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    if(selectList != null && selectList.size() > 0){
                        upload_path = selectList.get(0).getCutPath();
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),image);
                    }else {
                        ToastUtil.show(mContext,"选择图片出错");
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(this);
    }

}
