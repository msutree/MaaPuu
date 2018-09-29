package com.maapuu.mereca.background.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.LocateActivity;
import com.maapuu.mereca.background.shop.bean.CutBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SearchAddress;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 新增店铺
 * Created by Jia on 2018/3/13.
 */

public class AddShopActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.as_cover_rl)
    RelativeLayout as_cover_rl;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.iv_shop_logo)
    CircleImgView ivShopLogo;
    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.et_shop_phone)
    EditText etShopPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.et_kefu_account)
    EditText etKefuAccount;
    @BindView(R.id.et_admin_account)
    EditText etAdminAccount;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String shop_id;
    private String result;

    private String shop_cover_url;
    private String shop_logo_url;
    private String district_name;
    private String lng;
    private String lat;

    private List<LocalMedia> selectList = new ArrayList<>();
    private String upload_path = "";
    private String upload_path_1 = "";

    private AlertView alertView;
    private SearchAddress address;

    private List<CutBean> list;
    private BaseRecyclerAdapter<CutBean> adapter;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_shop);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");
        shop_id = getIntent().getStringExtra("shop_id");
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) as_cover_rl.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        as_cover_rl.setLayoutParams(lp);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));
        if(!StringUtils.isEmpty(shop_id)){
            txtTitle.setText("编辑店铺");
            result = getIntent().getStringExtra("result");
            try {
                JSONObject resultObj = new JSONObject(result);
                UIUtils.loadImg(mContext,resultObj.optString("shop_cover"),image);
                UIUtils.loadImg(mContext,resultObj.optString("shop_logo"),ivShopLogo);
                etShopName.setText(resultObj.optString("shop_name"));
                etShopPhone.setText(resultObj.optString("shop_tel"));
                txtAddress.setText(resultObj.optString("city_name"));
                etDetail.setText(resultObj.optString("address_detail"));
                etKefuAccount.setText(resultObj.optString("shop_service_phone"));
                etAdminAccount.setText(resultObj.optString("shop_admin_phone"));

                shop_cover_url = resultObj.optString("shop_cover_url");
                shop_logo_url = resultObj.optString("shop_logo_url");
                district_name = resultObj.optString("district_name");
                lat = resultObj.optString("lat");
                lng = resultObj.optString("lng");
                if(resultObj.has("fullcutset") && resultObj.optJSONArray("fullcutset").length() > 0){
                    list = FastJsonTools.getPersons(resultObj.optString("fullcutset"),CutBean.class);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            txtTitle.setText("新增店铺");
            list.add(new CutBean());
        }
        adapter = new BaseRecyclerAdapter<CutBean>(mContext,list,R.layout.layout_add_shop_youhui_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, CutBean item, int position, boolean isScrolling) {
                EditText etFull = holder.getView(R.id.et_full);
                EditText etCut = holder.getView(R.id.et_cut);

                TextSwitcher switcher = new TextSwitcher(position,1);
                if (etFull.getTag() instanceof TextSwitcher) {
                    etFull.removeTextChangedListener((TextSwitcher) etFull.getTag());
                }
                etFull.setText(list.get(position).getFullcut_amount());
                etFull.addTextChangedListener(switcher);
                etFull.setTag(switcher);
                TextSwitcher switcher1 = new TextSwitcher(position,2);
                if (etCut.getTag() instanceof TextSwitcher) {
                    etCut.removeTextChangedListener((TextSwitcher) etCut.getTag());
                }
                etCut.setText(list.get(position).getCut_amount());
                etCut.addTextChangedListener(switcher1);
                etCut.setTag(switcher1);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    class TextSwitcher implements TextWatcher {
        private int position;
        private int type;
        public TextSwitcher(int position,int type) {
            this.position = position;
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !StringUtils.isEmpty(s.toString())) {
                if(type == 1){
                    list.get(position).setFullcut_amount(s.toString());
                }else {
                    list.get(position).setCut_amount(s.toString());
                }
            }else {
                if(type == 1){
                    list.get(position).setFullcut_amount("");
                }else {
                    list.get(position).setCut_amount("");
                }
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
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (object.has("src") && !object.optString("src").equals("")) {
                            shop_logo_url = object.optString("src");
                        }
                        save(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleHUD.dismiss();
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (object.has("src") && !object.optString("src").equals("")) {
                            shop_cover_url = object.optString("src");
                        }
                        if(!StringUtils.isEmpty(upload_path_1)){
                            mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_2, upload_path_1,false);
                        }else {
                            save(false);
                        }
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
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_data_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                shop_id,shop_cover_url,shop_logo_url,etShopName.getText().toString(),etShopPhone.getText().toString(),district_name,
                etDetail.getText().toString(),lat,lng,etKefuAccount.getText().toString(),etAdminAccount.getText().toString(),getJson()),bool);
    }

    /**
     * 条件不为空
     * @return
     */
    private boolean isFullEmpty(){
        boolean bool = true;
        for (CutBean bean : list){
            if(StringUtils.isEmpty(bean.getFullcut_amount())){
                bool = false;
                break;
            }
        }
        return bool;
    }

    /**
     * 满减不为空
     * @return
     */
    private boolean isCutEmpty(){
        boolean bool = true;
        for (CutBean bean : list){
            if(StringUtils.isEmpty(bean.getCut_amount())){
                bool = false;
                break;
            }
        }
        return bool;
    }

    private String getJson(){
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++){
                JSONObject object = new JSONObject();
                object.put("fullcut_amount",list.get(i).getFullcut_amount());
                object.put("cut_amount",list.get(i).getCut_amount());
                array.put(i,object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_add,R.id.txt_del,R.id.txt_right,R.id.as_cover_rl,R.id.iv_shop_logo,R.id.txt_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_add:
                list.add(new CutBean());
                adapter.notifyDataSetChanged();
                break;
            case R.id.txt_del:
                if(list.size() > 0){
                    list.remove(list.size()-1);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.txt_right:
                if(StringUtils.isEmpty(shop_cover_url) && StringUtils.isEmpty(upload_path)){
                    ToastUtil.show(mContext,"请上传店铺图片");
                    return;
                }
                if(StringUtils.isEmpty(shop_logo_url) && StringUtils.isEmpty(upload_path_1)){
                    ToastUtil.show(mContext,"请上传店铺logo");
                    return;
                }
                if(StringUtils.isEmpty(etShopName.getText().toString())){
                    ToastUtil.show(mContext,"请上传店铺图片");
                    return;
                }
                if(StringUtils.isEmpty(etShopPhone.getText().toString())){
                    ToastUtil.show(mContext,"请输入店铺电话");
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
                if(StringUtils.isEmpty(lat)||lat.equals("0")
                        ||StringUtils.isEmpty(lng)||lng.equals("0")){
                    ToastUtil.show(mContext,"未能获取到地址经纬度");
                    return;
                }
                if(StringUtils.isEmpty(etKefuAccount.getText().toString())){
                    ToastUtil.show(mContext,"请输入客服账号");
                    return;
                }
                if(StringUtils.isEmpty(etAdminAccount.getText().toString())){
                    ToastUtil.show(mContext,"请输入管理员账号");
                    return;
                }
                if(!isFullEmpty()){
                    ToastUtil.show(mContext,"满减条件不能为空");
                    return;
                }
                if(!isCutEmpty()){
                    ToastUtil.show(mContext,"满减金额不能为空");
                    return;
                }
                if(!StringUtils.isEmpty(upload_path)){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, upload_path,true);
                }else {
                    if(!StringUtils.isEmpty(upload_path_1)){
                        mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_2, upload_path_1,true);
                    }else {
                        save(true);
                    }
                }
                break;
            case R.id.as_cover_rl:
                PictureSelector.create(AddShopActivity.this)
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
            case R.id.iv_shop_logo:
                PictureSelector.create(AddShopActivity.this)
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
                        .forResult(102);//结果回调onActivityResult code
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
                                JumpPermissionManagement.GoToSetting(AddShopActivity.this);
                            }
                        }
                    });
                    alertView.show();
                }
                break;
        }
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
                if(requestCode == 101){
                    upload_path = selectList.get(0).getCutPath();
                    // 显示在图片控件上
                    UIUtils.loadImg(mContext,upload_path,image);
                }else if(requestCode == 102){
                    upload_path_1 = selectList.get(0).getCutPath();
                    // 显示在图片控件上
                    UIUtils.loadImg(mContext,upload_path_1,ivShopLogo);
                }
                break;
            case AppConfig.ACTIVITY_RESULTCODE:
                address = (SearchAddress) data.getSerializableExtra("searchAddress");
                district_name = address.getArea();
                lat = address.getLatitude();
                lng = address.getLongitude();
                txtAddress.setText(address.getProvince()+address.getCity()+address.getArea());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(this);
    }
}
