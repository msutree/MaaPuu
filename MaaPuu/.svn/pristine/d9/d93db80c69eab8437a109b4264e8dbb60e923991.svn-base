package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ImageTextEditAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DateUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.NestedRecyclerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

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
 * （项目管理）添加项目
 * Created by Jia on 2018/3/16.
 */

public class PmAddProjectActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.et_goods_spec)
    EditText etGoodsSpec;
    @BindView(R.id.et_srv_times)
    EditText etSrvTimes;
    @BindView(R.id.et_cost)
    EditText etCost;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_market_price)
    EditText etMarketPrice;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;
    @BindView(R.id.rl_times)
    RelativeLayout rlTimes;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.txt_shop)
    TextView txtShop;
    @BindView(R.id.txt_item)
    TextView txtItem;
    @BindView(R.id.pm_ap_contain_service_tv)
    TextView txtContainSrvs;
    @BindView(R.id.pm_ap_choose_shop_tv)
    TextView txtChooseShop;
    @BindView(R.id.et_promotion_price)
    EditText etPromotionPrice;
    @BindView(R.id.pm_ap_choose_category_tv)
    TextView chooseCategoryTv;
    @BindView(R.id.pm_ap_choose_date_start_tv)
    TextView startDateTv;
    @BindView(R.id.pm_ap_choose_date_end_tv)
    TextView endDateTv;
    @BindView(R.id.add_content_img)
    TextView addContentImgTv;
    @BindView(R.id.add_content_txt)
    TextView addContentTxtTv;
    @BindView(R.id.pm_ap_project_type_tv)
    TextView txtProjectType;//大小项目

    @BindView(R.id.pm_content_rv)
    NestedRecyclerView contentRv;

    private ImageTextEditAdapter adapter;
    private final int select_photo_item = 102;
    private final int select_photo_detail = 103;
    private String upload_path;
    private List<ImageTextBean> list;
    private int imgListPos = 0;//上传的图片下标

    private String pack_type;
    private String item_id ;
    private String result;
    private String item_img;
    private String shop_ids = "";
    private String srv_ids = "";
    private String sub_item_ids = "";
    private String catalog_id = "";
    private String project_type = "";//project_type 项目类型：1小项目；2大项目

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_pm_add_project);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");

        addContentImgTv.setTypeface(StringUtils.getFont(mContext));
        addContentTxtTv.setTypeface(StringUtils.getFont(mContext));
        list = new ArrayList<>();

        item_id = getIntent().getStringExtra("item_id");
        if(!StringUtils.isEmpty(item_id)){
            txtTitle.setText("编辑项目");
            result = getIntent().getStringExtra("result");
            try {
                JSONObject resultObj = new JSONObject(result);
                JSONObject itemObj = resultObj.optJSONObject("item_data");
                item_img = itemObj.optString("item_img");
                UIUtils.loadImg(mContext,itemObj.optString("item_img"),image);
                etGoodsName.setText(itemObj.optString("item_name"));
                etGoodsName.setSelection(itemObj.optString("item_name").length());
                etGoodsSpec.setText(itemObj.optString("item_desc"));
                pack_type = itemObj.optString("pack_type");
                catalog_id = itemObj.optString("catalog_id");
                chooseCategoryTv.setText(itemObj.optString("catalog_name"));
                if(pack_type.equals("1")){
                    rlShop.setVisibility(View.VISIBLE);
                    rlTimes.setVisibility(View.GONE);
                    llShop.setVisibility(View.GONE);
                    txtContainSrvs.setText(resultObj.optString("srv_names"));
                    txtChooseShop.setText(resultObj.optString("shop_names"));
                }else if(pack_type.equals("2")){
                    rlShop.setVisibility(View.VISIBLE);
                    rlTimes.setVisibility(View.VISIBLE);
                    llShop.setVisibility(View.GONE);
                    etSrvTimes.setText(itemObj.optString("srv_num"));
                    txtContainSrvs.setText(resultObj.optString("srv_names"));
                    txtChooseShop.setText(resultObj.optString("shop_names"));
                }else if(pack_type.equals("3")){
                    rlShop.setVisibility(View.GONE);
                    rlTimes.setVisibility(View.GONE);
                    llShop.setVisibility(View.VISIBLE);
                    txtContainSrvs.setText(resultObj.optString("srv_names"));
                    txtShop.setText(resultObj.optString("shop_names"));
                    txtItem.setText(resultObj.optString("sub_item_names"));
                }
                etCost.setText(itemObj.optString("cost_price"));
                etPrice.setText(itemObj.optString("price"));
                etMarketPrice.setText(itemObj.optString("market_price"));
                startDateTv.setText(itemObj.optString("promotion_begin_time"));
                endDateTv.setText(itemObj.optString("promotion_end_time"));
                if( !StringUtils.isEmpty(itemObj.optString("promotion_begin_time")) && itemObj.optDouble("promotion_price") != 0){
                    etPromotionPrice.setText(itemObj.optString("promotion_price"));
                }
                shop_ids = resultObj.optString("shop_ids");
                srv_ids = resultObj.optString("srv_ids");
                sub_item_ids = resultObj.optString("sub_item_ids");
                if(itemObj.has("detail") && !StringUtils.isEmpty(itemObj.optString("detail")) && itemObj.optJSONArray("detail").length() > 0){
                    list = FastJsonTools.getPersons(itemObj.optString("detail"),ImageTextBean.class);
                }

                //project_type 项目类型：1小项目；2大项目
                project_type = itemObj.optString("project_type");
                if("1".equals(project_type)){
                    txtProjectType.setText("小项目");
                } else if("2".equals(project_type)){
                    txtProjectType.setText("大项目");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            txtTitle.setText("添加项目");
            item_id = "0";
            pack_type = getIntent().getStringExtra("pack_type");
            if(pack_type.equals("1")){
                rlShop.setVisibility(View.VISIBLE);
                rlTimes.setVisibility(View.GONE);
                llShop.setVisibility(View.GONE);
            }else if(pack_type.equals("2")){
                rlShop.setVisibility(View.VISIBLE);
                rlTimes.setVisibility(View.VISIBLE);
                llShop.setVisibility(View.GONE);
            }else if(pack_type.equals("3")){
                rlShop.setVisibility(View.GONE);
                rlTimes.setVisibility(View.GONE);
                llShop.setVisibility(View.VISIBLE);
            }
        }
        setContentEditRv();
    }

    private void setContentEditRv() {
        if(list.size() == 0){
            ImageTextBean bean = new ImageTextBean();
            bean.setContent_type("1");
            list.add(bean);
        }
        contentRv.setNestedScrollingEnabled(false);
        contentRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,
                UIUtils.dip2px(mContext,5),getResources().getColor(R.color.background)));
        contentRv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ImageTextEditAdapter(mContext,list);
        contentRv.setAdapter(adapter);
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
                SimpleHUD.dismiss();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        item_img = resultObj.optString("src");
                        if (isNeedUploadImage()) {
                            imgListPos = getFirstPos();
                            image_save_set(list.get(imgListPos).getContent(), imgListPos, false);
                        } else {
                            publish(true);
                        }
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
                            list.get(imgListPos).setContent_url(resultObj.optString("src"));
                            list.get(imgListPos).setWidth(resultObj.optInt("width"));
                            list.get(imgListPos).setHeight(resultObj.optInt("height"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(msg.arg2 == getLastPos()){
                    publish(false);
                }else {
                    imgListPos++;
                    if(list.get(imgListPos).getContent_type().equals("1") || list.get(imgListPos).getContent().contains("http")){
                        imgListPos ++;
                    }
                    image_save_set(list.get(imgListPos).getContent(), imgListPos, false);
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

    private void publish(boolean bool) {
        switch (pack_type){
            case "1":
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_projmgr_once_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,item_img,etGoodsName.getText().toString(),etGoodsSpec.getText().toString(),catalog_id,srv_ids,shop_ids,
                        etCost.getText().toString(),etPrice.getText().toString(),etMarketPrice.getText().toString(),startDateTv.getText().toString(),
                        endDateTv.getText().toString(),etPromotionPrice.getText().toString(),project_type,getFiles()),bool);
                break;
            case "2":
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_projmgr_single_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,item_img,etGoodsName.getText().toString(),etGoodsSpec.getText().toString(),catalog_id,srv_ids,shop_ids,etSrvTimes.getText().toString(),
                        etCost.getText().toString(),etPrice.getText().toString(),etMarketPrice.getText().toString(),startDateTv.getText().toString(),
                        endDateTv.getText().toString(),etPromotionPrice.getText().toString(),project_type,getFiles()),bool);
                break;
            case "3":
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_projmgr_multi_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,item_img,etGoodsName.getText().toString(),etGoodsSpec.getText().toString(),catalog_id,srv_ids,shop_ids,sub_item_ids,
                        etCost.getText().toString(),etPrice.getText().toString(),etMarketPrice.getText().toString(),startDateTv.getText().toString(),
                        endDateTv.getText().toString(),etPromotionPrice.getText().toString(),project_type,getFiles()),bool);
                break;
        }
    }

    private boolean isNeedUploadImage(){
        boolean bool = false;
        for (ImageTextBean bean : list){
            if(bean.getContent_type().equals("2") && StringUtils.isEmpty(bean.getContent_url())){
                bool = true;
                break;
            }
        }
        return bool;
    }

    private int getFirstPos(){
        int pos = 0;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getContent_type().equals("2") && StringUtils.isEmpty(list.get(i).getContent_url())){
                pos = i;
                break;
            }
        }
        return pos;
    }

    private int getLastPos(){
        int pos = 0;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getContent_type().equals("2") && !list.get(imgListPos).getContent().contains("http")){
                pos = i;
            }
        }
        return pos;
    }

    private String getFiles(){
        String str = "";
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getContent_type().equals("1")){
                if(!StringUtils.isEmpty(list.get(i).getContent())){
                    str += "{\"type\":\"1\",\"content\":\""+list.get(i).getContent()+"\"},";
                }
            }else {
                str += "{\"type\":\"2\",\"content\":\""+list.get(i).getContent_url()+"\",\"width\":\"" +
                        list.get(i).getWidth() + "\",\"height\":\"" + list.get(i).getHeight() + "\"},";
            }
        }
        if(str.endsWith(",")){
            str = str.substring(0,str.length()-1);
        }
        str = "[" + str + "]";
        return str;
    }

    private void image_save_set(String imagePath, int position, Boolean isProgress) {
        mHttpModeBase.xutilsPostList(HttpModeBase.HTTP_REQUESTCODE_IMG, imagePath, position, isProgress);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.pm_ap_cover_rl, R.id.pm_ap_choose_category_tv,R.id.pm_ap_contain_service_tv,
            R.id.pm_ap_choose_shop_tv,R.id.pm_ap_choose_date_start_tv,R.id.pm_ap_choose_date_end_tv,R.id.pm_ap_add_project_shop,
            R.id.add_content_img,R.id.add_content_txt,R.id.pm_ap_project_type_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://保存
                if(StringUtils.isEmpty(item_img) && StringUtils.isEmpty(upload_path)){
                    ToastUtil.show(mContext,"请上传项目图片");
                    return;
                }
                if(StringUtils.isEmpty(etGoodsName.getText().toString())){
                    ToastUtil.show(mContext,"请输入项目名称");
                    return;
                }
                if(StringUtils.isEmpty(catalog_id)){
                    ToastUtil.show(mContext,"请选择类别");
                    return;
                }
                if(StringUtils.isEmpty(project_type)){
                    ToastUtil.show(mContext,"请选择大小项目");
                    return;
                }
                if(StringUtils.isEmpty(srv_ids)){
                    ToastUtil.show(mContext,"请选择包含服务");
                    return;
                }
                if(StringUtils.isEmpty(shop_ids)){
                    ToastUtil.show(mContext,"请选择店铺");
                    return;
                }
                if(pack_type.equals("2") && StringUtils.isEmpty(etSrvTimes.getText().toString())){
                    ToastUtil.show(mContext,"请输入服务次数");
                    return;
                }
                if(StringUtils.isEmpty(etCost.getText().toString())){
                    ToastUtil.show(mContext,"请输入成本");
                    return;
                }
                if(StringUtils.isEmpty(etPrice.getText().toString())){
                    ToastUtil.show(mContext,"请输入价格");
                    return;
                }
                if(!StringUtils.isEmpty(startDateTv.getText().toString())){
                    if(StringUtils.isEmpty(endDateTv.getText().toString())){
                        ToastUtil.show(mContext,"请选择结束时间");
                        return;
                    }
                    if(StringUtils.isEmpty(etPromotionPrice.getText().toString())){
                        ToastUtil.show(mContext,"请输入促销价格");
                        return;
                    }
                }
                if(!StringUtils.isEmpty(endDateTv.getText().toString())){
                    if(StringUtils.isEmpty(startDateTv.getText().toString())){
                        ToastUtil.show(mContext,"请选择开始时间");
                        return;
                    }
                    if(StringUtils.isEmpty(etPromotionPrice.getText().toString())){
                        ToastUtil.show(mContext,"请输入促销价格");
                        return;
                    }
                }
                if(!StringUtils.isEmpty(etPromotionPrice.getText().toString())){
                    if(StringUtils.isEmpty(startDateTv.getText().toString())){
                        ToastUtil.show(mContext,"请选择开始时间");
                        return;
                    }
                    if(StringUtils.isEmpty(endDateTv.getText().toString())){
                        ToastUtil.show(mContext,"请选择结束时间");
                        return;
                    }
                }
                if(!StringUtils.isEmpty(upload_path)){
                    mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_2, upload_path,true);
                }else {
                    if (isNeedUploadImage()) {
                        imgListPos = getFirstPos();
                        image_save_set(list.get(imgListPos).getContent(), imgListPos, false);
                    } else {
                        publish(true);
                    }
                }
                break;
            case R.id.pm_ap_cover_rl:
                PictureSelector.create(PmAddProjectActivity.this)
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
                        .forResult(select_photo_item);//结果回调onActivityResult code
                break;
            case R.id.pm_ap_choose_category_tv:
                it = new Intent(mContext,ProjectChooseCategoryActivity.class);
                it.putExtra("catalog_type","1");
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.pm_ap_contain_service_tv:
                it = new Intent(mContext,ProjectContainServiceActivity.class);
                it.putExtra("srv_ids",srv_ids);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.pm_ap_choose_shop_tv:
                it = new Intent(mContext,ChooseShopActivity.class);
                it.putExtra("shop_ids",shop_ids);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.pm_ap_choose_date_start_tv:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(1);
                break;
            case R.id.pm_ap_choose_date_end_tv:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(2);
                break;
            case R.id.pm_ap_add_project_shop:
                //只在 多项套餐 时可操作
                it = new Intent(mContext,MulMealProjectListActivity.class);
                it.putExtra("item_id",item_id);
                it.putExtra("sub_item_ids",sub_item_ids);
//                if(!StringUtils.isEmpty(shop_ids)){
//                    it.putExtra("shop_id",shop_ids);
//                    it.putExtra("shop_name",txtShop.getText().toString());
//                }
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.add_content_img:
                PictureSelector.create(PmAddProjectActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .enableCrop(true)// 是否裁剪
                        .compress(true)// 是否压缩
                        .forResult(select_photo_detail);//结果回调onActivityResult code
                break;
            case R.id.add_content_txt://发布文字
                if(adapter !=null){
                    ImageTextBean bean = new ImageTextBean();
                    bean.setContent_type("1");
                    list.add(bean);
                    adapter.notifyDataSetChanged();
                    contentRv.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                break;

            case R.id.pm_ap_project_type_tv:
                //选择大小项目   project_type
                final List<String> list = new ArrayList<>();
                list.add("小项目");
                list.add("大项目");
                showAddProjectDialog(list);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(requestCode == select_photo_item){
                    if(selectList != null && selectList.size() > 0){
                        upload_path = selectList.get(0).getCutPath();
                        UIUtils.loadImg(mContext,selectList.get(0).getCutPath(),image);
                    }else {
                        ToastUtil.show(mContext,"选择图片出错");
                    }
                }else if(requestCode == select_photo_detail){
                    if(adapter !=null){
                        ImageTextBean bean = new ImageTextBean();
                        bean.setContent_type("2");
                        bean.setContent(selectList.get(0).getCutPath());
                        list.add(bean);
                        adapter.notifyDataSetChanged();
                        contentRv.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                }
                break;
            case AppConfig.ACTIVITY_RESULTCODE://店铺
                shop_ids = data.getStringExtra("shop_ids");
                txtChooseShop.setText(data.getStringExtra("shop_names"));
                break;
            case AppConfig.ACTIVITY_RESULTCODE_1://类别
                catalog_id = data.getStringExtra("cata_ids");
                chooseCategoryTv.setText(data.getStringExtra("cata_names"));
                break;
            case AppConfig.ACTIVITY_RESULTCODE_2://服务
                srv_ids = data.getStringExtra("srv_ids");
                txtContainSrvs.setText(data.getStringExtra("srv_names"));
                break;
            case AppConfig.ACTIVITY_RESULTCODE_3://多选选择店铺和套餐
                shop_ids = data.getStringExtra("shop_ids");
                sub_item_ids = data.getStringExtra("item_ids");
                txtShop.setText(data.getStringExtra("shop_name"));
                txtItem.setText(data.getStringExtra("item_names"));
                break;
        }
    }

    TimePickerView pvCustomTime;
    private void showTimePv(final int type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                String dateStr = DateUtil.calendar2String(calendar, DateUtil.FORMAT_DATE_TIME);
                if(type == 1){
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(endDateTv.getText().toString().trim()) &&
                            date.getTime() >= DateUtil.string2Date(endDateTv.getText().toString().trim(),DateUtil.FORMAT_DATE_TIME).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    startDateTv.setText(dateStr);
                } else if(type == 2){
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(startDateTv.getText().toString().trim()) &&
                            date.getTime() <= DateUtil.string2Date(startDateTv.getText().toString().trim(),DateUtil.FORMAT_DATE_TIME).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    endDateTv.setText(dateStr);
                }
            }
        }).setCancelColor(UIUtils.getColor(R.color.text_99))
                .setSubmitColor(UIUtils.getColor(R.color.main_color))
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();
        pvCustomTime.show();
    }

    @Override
    public void onBackPressed() {
        if(adapter != null && adapter.getAlertView() != null && adapter.getAlertView().isShowing()){
            adapter.getAlertView().dismiss();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(this);
    }

    private void showAddProjectDialog(final List<String> list) {
        NiceDialog.init().setLayoutId(R.layout.pop_bottom_menu)
                .setConvertListener(new ViewConvertListener() {

                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView cancelTv = holder.getView(R.id.pop_cancel_tv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        RecyclerView rv = holder.getView(R.id.pop_rv);
                        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.pop_item_bottom_menu) {
                            @Override
                            public void convert(BaseRecyclerHolder baseHolder, final String item, int position, boolean isScrolling) {
                                final TextView menuTv = baseHolder.getView(R.id.bm_title);
                                menuTv.setText(item);
                                menuTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (item){
                                            case "小项目":
                                                project_type = "1";
                                                txtProjectType.setText("小项目");
                                                break;
                                            case "大项目":
                                                project_type = "2";
                                                txtProjectType.setText("大项目");
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        };
                        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                                getResources().getColor(R.color.background)));
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        rv.setAdapter(adapter);
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                //.setHeight(270)
                .show(getSupportFragmentManager());
    }

}
