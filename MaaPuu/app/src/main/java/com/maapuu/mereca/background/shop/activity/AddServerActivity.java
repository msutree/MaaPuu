package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * 项目 添加服务
 * Created by Jia on 2018/3/19.
 */

public class AddServerActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.as_cover_iv)
    ImageView image;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_duration)
    EditText etDuration;

    private String upload_path;
    private String srv_type;
    private String srv_id = "0";
    private String srv_img;

    private int pos;
    private SrvBean srvBean;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_server);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        pos = getIntent().getIntExtra("pos",-1);
        srv_type = getIntent().getStringExtra("srv_type");
        srvBean = (SrvBean) getIntent().getSerializableExtra("srvBean");
        if(srvBean != null){
            txtTitle.setText("编辑服务");
            llAdd.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            srv_id = srvBean.getSrv_id();
            UIUtils.loadImg(mContext,srvBean.getSrv_img(),image);
            srv_img = srvBean.getSrv_img();
            etName.setText(srvBean.getSrv_name());
            etName.setSelection(srvBean.getSrv_name().length());
            etDuration.setText(srvBean.getSrv_duration());
        }else {
            txtTitle.setText("添加服务");
            llAdd.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }
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
                        ToastUtil.show(mContext,"保存成功");
                        it = new Intent();
                        it.putExtra("pos",pos);
                        it.putExtra("srvBean",FastJsonTools.getPerson(object.optJSONObject("result").optString("srv"),SrvBean.class));
                        setResult(AppConfig.ACTIVITY_RESULTCODE,it);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                SimpleHUD.dismiss();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        srv_img = resultObj.optString("src");
                        save(false);
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void save(boolean b) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_service_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),srv_id,
                etName.getText().toString(),srv_type,srv_img,etDuration.getText().toString()),false);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.as_cover_rl,R.id.pac_confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.as_cover_rl:
                PictureSelector.create(AddServerActivity.this)
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
            case R.id.pac_confirm_tv:
                if(srvBean == null && StringUtils.isEmpty(upload_path)){
                    ToastUtil.show(mContext,"请上传图片");
                    return;
                }
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请输入服务名称");
                    return;
                }
                if(StringUtils.isEmpty(etDuration.getText().toString())){
                    ToastUtil.show(mContext,"请输入服务时长");
                    return;
                }
                if(!StringUtils.isEmpty(upload_path)){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, upload_path,true);
                }else {
                    save(true);
                }
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(requestCode == PictureConfig.CHOOSE_REQUEST){
                    if(selectList != null && selectList.size() > 0){
                        llAdd.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
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
