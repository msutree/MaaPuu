package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.BannerBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * 新增轮播图
 * Created by Jia on 2018/3/14.
 */

public class AddBannerActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.as_cover_rl)
    RelativeLayout rlCover;
    @BindView(R.id.as_cover_iv)
    ImageView image;
    @BindView(R.id.ab_choose_category_tv)
    TextView chooseCategoryTv;
    @BindView(R.id.ab_category_name_tv)
    TextView categoryNameTv;
    @BindView(R.id.ab_category_value_tv)
    TextView categoryNameValueTv;
    @BindView(R.id.ab_link_et)
    EditText etWeb;

    @BindView(R.id.ab_category_ll)
    LinearLayout categoryLl;
    @BindView(R.id.ab_link_ll)
    LinearLayout linkLl;

    String categoryName = "";
    private BannerBean banner;
    private String shop_id ;
    private String shop_adv_id = "0";
    private String adv_img = "";
    private int adv_type = -1;
    private String adv_value = "";

    private List<LocalMedia> selectList = new ArrayList<>();
    private String upload_path = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_banner);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("新增轮播图");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlCover.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext) * 2 / 5, mContext.getResources().getDisplayMetrics()));
        rlCover.setLayoutParams(lp);

        shop_id = getIntent().getStringExtra("shop_id");
        banner = (BannerBean) getIntent().getSerializableExtra("banner");
        if (banner != null) {
            shop_adv_id = banner.getShop_adv_id();
            adv_value = banner.getAdv_value();
            adv_type = banner.getAdv_type();
            UIUtils.loadImg(mContext, banner.getAdv_img(), image);
            switch (banner.getAdv_type()) {
                case 1:
                    linkLl.setVisibility(View.GONE);
                    categoryLl.setVisibility(View.VISIBLE);
                    categoryName = "发型师";
                    chooseCategoryTv.setText("发型师");
                    categoryNameValueTv.setText(banner.getAdv_text());
                    break;
                case 2:
                    linkLl.setVisibility(View.GONE);
                    categoryLl.setVisibility(View.VISIBLE);
                    categoryName = "项目";
                    chooseCategoryTv.setText("项目");
                    categoryNameValueTv.setText(banner.getAdv_text());
                    break;
                case 3:
                    linkLl.setVisibility(View.GONE);
                    categoryLl.setVisibility(View.VISIBLE);
                    categoryName = "商品";
                    chooseCategoryTv.setText("商品");
                    categoryNameValueTv.setText(banner.getAdv_text());
                    break;
                case 4:
                    linkLl.setVisibility(View.VISIBLE);
                    categoryLl.setVisibility(View.GONE);
                    categoryName = "链接";
                    chooseCategoryTv.setText("链接");
                    etWeb.setText(banner.getAdv_text());
                    etWeb.setSelection(banner.getAdv_text().length());
                    break;
            }
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
                            adv_img = object.optString("src");
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

    private void save(boolean bool) {
        if (!TextUtils.isEmpty(categoryName) && !"链接".equals(categoryName)) {
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_adv_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                    shop_id,shop_adv_id,adv_img,adv_type,adv_value),bool);
        }else {
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_adv_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                    shop_id,shop_adv_id,adv_img,adv_type,etWeb.getText().toString()),bool);
        }
    }

    @Override
    @OnClick({R.id.txt_left, R.id.txt_right, R.id.as_cover_rl, R.id.ab_choose_category_ll, R.id.ab_category_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                if(banner == null && StringUtils.isEmpty(upload_path)){
                    ToastUtil.show(mContext,"请上传店铺轮播图片");
                    return;
                }
                if(adv_type == -1){
                    ToastUtil.show(mContext,"请选择类型");
                    return;
                }

                if(!"链接".equals(categoryName) && StringUtils.isEmpty(adv_value)){
                    ToastUtil.show(mContext,"请选择" + categoryName);
                    return;
                }
                if("链接".equals(categoryName)){
                    if(StringUtils.isEmpty(etWeb.getText().toString())){
                        ToastUtil.show(mContext,"请输入链接地址");
                        return;
                    }
                    if(!etWeb.getText().toString().startsWith("http")){
                        ToastUtil.show(mContext,"请输入正确的链接地址");
                        return;
                    }
                }
                if(!StringUtils.isEmpty(upload_path)){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, upload_path,true);
                }else {
                    save(true);
                }
                break;
            case R.id.as_cover_rl:
                PictureSelector.create(AddBannerActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .withAspectRatio(5,2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .circleDimmedLayer(false) // 是否圆形裁剪 true or false
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(true)// 是否裁剪
                        .compress(true)// 是否压缩
                        .forResult(101);//结果回调onActivityResult code
                break;
            case R.id.ab_choose_category_ll:
                KeyBoardUtils.closeKeybord(view,mContext);
                ArrayList<String> cList = new ArrayList<>();
                cList.add("发型师");
                cList.add("项目");
                cList.add("商品");
                cList.add("链接");
                getCategoryPicker(cList).show();
                break;
            case R.id.ab_category_ll:
                if(adv_type == -1){
                    ToastUtil.show(mContext,"请选择类型");
                    return;
                }
                if(adv_type == 1){
                    it = new Intent(mContext,AddHairstylistActivity.class);
                    it.putExtra("shop_id",shop_id);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                }else if(adv_type == 2 || adv_type == 3){
                    it = new Intent(mContext, BannerChooseCategoryActivity.class);
                    it.putExtra("title", "选择" + categoryName);
                    it.putExtra("adv_type", adv_type);
                    it.putExtra("shop_id", shop_id);
                    it.putExtra("adv_value", adv_value);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                }
            break;
        }
    }

    private OptionsPickerView getCategoryPicker(final ArrayList<String> data) {
        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (data != null && data.size() > 0) {
                    if(adv_type != options1+1){
                        adv_value = "";
                        categoryNameValueTv.setText("");
                    }
                    adv_type = options1+1;
                    categoryName = data.get(options1);
                    chooseCategoryTv.setText(categoryName);
                    categoryNameTv.setText("选择"+categoryName);
                    if ("链接".equals(categoryName)) {
                        linkLl.setVisibility(View.VISIBLE);
                        categoryLl.setVisibility(View.GONE);
                    } else {
                        linkLl.setVisibility(View.GONE);
                        categoryLl.setVisibility(View.VISIBLE);
                    }
                }
            }
        })
                .setSelectOptions(0)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(data, null, null);

        return pvNoLinkOptions;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                selectList.clear();
                selectList.addAll(PictureSelector.obtainMultipleResult(data));
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                upload_path = selectList.get(0).getCutPath();
                // 显示在图片控件上
                UIUtils.loadImg(mContext,upload_path,image);
                break;
            case AppConfig.ACTIVITY_RESULTCODE:
                adv_value = data.getStringExtra("adv_value");
                categoryNameValueTv.setText(data.getStringExtra("adv_name"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(this);
    }

}
