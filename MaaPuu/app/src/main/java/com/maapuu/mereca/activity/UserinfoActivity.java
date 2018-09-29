package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.AreaBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JsonUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * Created by dell on 2018/3/5.
 */

public class UserinfoActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.et_signature)
    EditText etSignsture;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_birth)
    TextView txtBirth;
    @BindView(R.id.txt_area)
    TextView txtArea;
    @BindView(R.id.iv_img1)
    SimpleDraweeView ivImg1;
    @BindView(R.id.iv_img2)
    SimpleDraweeView ivImg2;
    @BindView(R.id.iv_img3)
    SimpleDraweeView ivImg3;

    private List<LocalMedia> selectList = new ArrayList<>();
    private Bitmap bm;
    private String avatar = "";
    private int sex = 0;
    private String city_id = "";
    private String is_staff = "";
    private List<AreaBean> areas;
    private List<String> list;

    private TimePickerView pvCustomTime;
    private OptionsPickerView pickerView;
    private ArrayList<AreaBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<AreaBean>> options2Items = new ArrayList<ArrayList<AreaBean>>();

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_userinfo);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("保存");txtRight.setVisibility(View.VISIBLE);
        areas = new ArrayList<>();
        list = new ArrayList<>();
        if(!StringUtils.isEmpty(LoginUtil.getInfo("avatar"))){
            ivIcon.setImageURI(Uri.parse(LoginUtil.getInfo("avatar")));
        }
        etNickName.setText(LoginUtil.getInfo("nick_name"));
        etSignsture.setText(LoginUtil.getInfo("signature"));
        if(LoginUtil.getInfo("sex").equals("1")){
            sex = 1;
            txtSex.setText("男");
        }else if(LoginUtil.getInfo("sex").equals("2")){
            sex = 2;
            txtSex.setText("女");
        }
        txtBirth.setText(LoginUtil.getInfo("birthday"));
        city_id = LoginUtil.getInfo("city_id");
        txtArea.setText(LoginUtil.getInfo("city_name"));
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.user_page_info_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),1),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        is_staff = resultObj.optString("is_staff");
                        if(resultObj.has("user_pics") && !StringUtils.isEmpty(resultObj.optString("user_pics")) && resultObj.optJSONArray("user_pics").length() > 0){
                            JSONArray array = resultObj.optJSONArray("user_pics");
                            list.clear();
                            for (int i = 0; i < array.length(); i++){
                                list.add(array.optJSONObject(i).optString("content"));
                            }
                        }
                        if(list.size() == 0){
                            ivImg1.setVisibility(View.GONE);ivImg2.setVisibility(View.GONE);ivImg3.setVisibility(View.GONE);
                        }else if(list.size() == 1){
                            ivImg1.setVisibility(View.GONE);ivImg2.setVisibility(View.GONE);ivImg3.setVisibility(View.VISIBLE);
                            ivImg3.setImageURI(list.get(0));
                        }else if(list.size() == 2){
                            ivImg1.setVisibility(View.GONE);ivImg2.setVisibility(View.VISIBLE);ivImg3.setVisibility(View.VISIBLE);
                            ivImg2.setImageURI(list.get(1));
                            ivImg3.setImageURI(list.get(0));
                        }else {
                            ivImg1.setVisibility(View.VISIBLE);ivImg2.setVisibility(View.VISIBLE);ivImg3.setVisibility(View.VISIBLE);
                            ivImg1.setImageURI(list.get(2));
                            ivImg2.setImageURI(list.get(1));
                            ivImg3.setImageURI(list.get(0));
                        }
                        if(resultObj.has("province_citys") && !StringUtils.isEmpty(resultObj.optString("province_citys")) && resultObj.optJSONArray("province_citys").length() > 0){
                            areas = FastJsonTools.getPersons(resultObj.optString("province_citys"),AreaBean.class);
                        }
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
                        ToastUtil.show(mContext,"信息修改成功");
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            Map<String, String> userMap = JsonUtils.jsonStrToMap(object.optString("result"));
                            userMap.put(AppConfig.APP_NAME, object.optString("result"));
                            LoginUtil.writeMapInfo(userMap);
                        }
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
                            avatar = object.optString("src");
                        }
                        setInfo();
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
    @OnClick({R.id.txt_left,R.id.iv_icon,R.id.txt_sex,R.id.txt_birth,R.id.txt_area,R.id.txt_right,R.id.ll_homepage})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.iv_icon:
                PictureSelector.create(UserinfoActivity.this)
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
            case R.id.txt_sex:
                StringUtils.closeKeyBorder(mContext,view);
                getSex();
                break;
            case R.id.txt_birth:
                StringUtils.closeKeyBorder(mContext,view);
                getBirth();
                break;
            case R.id.txt_area:
                if(areas.size() > 0){
                    StringUtils.closeKeyBorder(mContext,view);
                    getArea();
                }
                break;
            case R.id.txt_right:
                if(bm != null){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, selectList.get(0).getCutPath(),true);
                }else {
                    setInfo();
                }
                break;
            case R.id.ll_homepage:
                it = new Intent(mContext,PersonalHomepageActivity.class);
                it.putExtra("is_staff",is_staff);
                it.putExtra("userpage_uid",LoginUtil.getInfo("uid"));
                startActivity(it);
                break;
        }
    }

    private void setInfo(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.user_info_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                avatar,etNickName.getText().toString(),etSignsture.getText().toString(),sex,
                txtBirth.getText().toString(),city_id),true);
    }

    /**
     * 性别
     */
    private void getSex() {
        final List<String> strs = new ArrayList<>();
        strs.add("男");strs.add("女");
        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                sex = options1 + 1;
                txtSex.setText(strs.get(options1));
            }
        }).setSelectOptions(0).setCancelColor(UIUtils.getColor(R.color.text_99))
                .setSubmitColor(UIUtils.getColor(R.color.main_color))
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(strs, null, null);
        pvNoLinkOptions.show();
    }
    /**
     * 生日
     */
    private void getBirth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                txtBirth.setText(StringUtils.getTime(date));
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

    private void getArea(){
        options1Items.clear();
        options2Items.clear();
        for (int i = 0; i < areas.size(); i++) {
            options1Items.add(areas.get(i));
            ArrayList<AreaBean> optItems2 = new ArrayList<>();
            for (int j = 0; j < areas.size(); j++) {
                if(areas.get(i).getId().equals(areas.get(j).getPid())){
                    optItems2.add(areas.get(j));
                }
            }
            options2Items.add(optItems2);
        }
        pickerView = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                city_id = options2Items.get(options1).get(option2).getId();
                txtArea.setText(options1Items.get(options1).getName()+options2Items.get(options1).get(option2).getName());
            }
        })
                .setSubCalSize(18)//确定和取消文字大小
                .setSubmitColor(UIUtils.getColor(R.color.main_color))//确定按钮文字颜色
                .setCancelColor(UIUtils.getColor(R.color.text_99))//取消按钮文字颜色
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        pickerView.setPicker(options1Items, options2Items, null);//添加数据源
        pickerView.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if(requestCode == PictureConfig.CHOOSE_REQUEST){
                    selectList.clear();
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    if(selectList != null && selectList.size() > 0){
                        BitmapFactory.Options option = new BitmapFactory.Options();
                        // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
                        option.inSampleSize = 3;
                        // 得到图片的旋转角度
                        bm = BitmapFactory.decodeFile(selectList.get(0).getCutPath(), option);
                        // 显示在图片控件上
                        ivIcon.setImageBitmap(bm);
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
