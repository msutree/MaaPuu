package com.maapuu.mereca.background.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ImageTextEditAdapter;
import com.maapuu.mereca.background.shop.bean.GoodsDetailBean;
import com.maapuu.mereca.background.shop.bean.GoodsItemBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DateUtil;
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
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * （商品管理）添加商品
 * Created by Jia on 2018/3/20.
 */

public class AddGoodsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.ag_img)
    ImageView img;

    @BindView(R.id.ag_item_name_et)
    EditText itemNameEt;
    @BindView(R.id.ag_gg_et)
    EditText ggEt;//规格

    @BindView(R.id.ag_choose_category_tv)
    TextView categoryNameTv;//类别
    @BindView(R.id.ag_choose_shop_tv)
    TextView chooseShopTv;//选择店铺

    @BindView(R.id.ag_cost_price_et)
    EditText costPriceEt;
    @BindView(R.id.ag_price_et)
    EditText priceEt;
    @BindView(R.id.ag_market_price_et)
    EditText marketPriceEt;

    @BindView(R.id.ag_promotion_begin_time_tv)
    TextView promotionBeginTimeTv;
    @BindView(R.id.ag_promotion_end_time_tv)
    TextView promotionEndTimeTv;

    @BindView(R.id.ag_promotion_price_tv)
    EditText promotionPriceEt;

    @BindView(R.id.add_content_img)
    TextView addContentImgTv;
    @BindView(R.id.add_content_txt)
    TextView addContentTxtTv;

    @BindView(R.id.ag_content_rv)
    NestedRecyclerView contentRv;

    ImageTextEditAdapter adapter;
    private final int select_photo_item = 100;
    private final int take_photo_content = 102;
    private final int select_photo_content = 103;

    String item_id = "0";//商品id
    GoodsDetailBean bean;

    String shop_ids = "";
    String shop_names = "";

    String item_img_path = "";//本地路径
    String item_img = "";//上传后的url
    String catalog_id = "";//类别id

    private int imgListPos = 0;//上传的图片下标


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_goods);
    }

    @Override
    public void initView() {
        item_id = getIntent().getStringExtra("item_id");
        bean = (GoodsDetailBean) getIntent().getSerializableExtra("GoodsDetailBean");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("添加商品");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");

        addContentImgTv.setTypeface(StringUtils.getFont(mContext));
        addContentTxtTv.setTypeface(StringUtils.getFont(mContext));

        setContentEditRv();
        setUI(bean);
    }

    private void setUI(GoodsDetailBean bean) {

        if(bean != null){//编辑
            shop_ids = bean.getShop_ids();
            shop_names = bean.getShop_names();

            GoodsItemBean itemBean = bean.getItem_data();
            if(itemBean != null){
                item_img = itemBean.getItem_img();
                item_img_path = item_img;//这里赋任意非空值即可
                UIUtils.loadImg(mContext,item_img,img);

                if(!TextUtils.isEmpty(itemBean.getItem_name())){
                    itemNameEt.setText(itemBean.getItem_name());
                    itemNameEt.setSelection(itemBean.getItem_name().length());
                }

                ggEt.setText(itemBean.getItem_specification());
                //类别
                categoryNameTv.setText(itemBean.getCatalog_name());
                catalog_id = itemBean.getCatalog_id();
                costPriceEt.setText(itemBean.getCost_price());
                priceEt.setText(itemBean.getPrice());
                marketPriceEt.setText(itemBean.getMarket_price());
                promotionBeginTimeTv.setText(itemBean.getPromotion_begin_time());
                promotionEndTimeTv.setText(itemBean.getPromotion_end_time());
//                promotionPriceEt.setText(itemBean.getPromotion_price());
                if( !StringUtils.isEmpty(itemBean.getPromotion_begin_time()) && new Double(itemBean.getPromotion_price()).intValue() != 0){
                    promotionPriceEt.setText(itemBean.getPromotion_price());
                }
                //图文
                List<ImageTextBean> imgTextList = itemBean.getDetail();
                if(imgTextList != null && imgTextList.size()>0){
                    adapter.clear();
                    adapter.addAll(imgTextList);
                }
            }

        } else {//新增
            item_id = "0";
            adapter.addItem(new ImageTextBean("1",""));
        }

    }

    private void setContentEditRv() {
        contentRv.setNestedScrollingEnabled(false);
        contentRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,
                UIUtils.dip2px(mContext,5),getResources().getColor(R.color.background)));
        contentRv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ImageTextEditAdapter(mContext);
        contentRv.setAdapter(adapter);
    }

    @Override
    public void initData() {
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.ag_img, R.id.ag_choose_category_tv,R.id.ag_choose_shop_tv,
            R.id.ag_promotion_begin_time_tv,R.id.ag_promotion_end_time_tv,R.id.add_content_img,R.id.add_content_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //保存
               if(checkParams()){
                   if (isNeedUploadImage()) {
                       imgListPos = getFirstPos();
                       image_save_set(adapter.getItems().get(imgListPos).getContent(), imgListPos, false);
                   } else {
                       save(true);
                   }
               }

                break;

            case R.id.ag_img:
                //上传图片
                selectItemImg();

                break;

            case R.id.ag_choose_category_tv:
                it = new Intent(mContext,ProjectChooseCategoryActivity.class);
                it.putExtra("catalog_type","2");//分类：1项目分类；2商品分类
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);

                break;

            case R.id.ag_choose_shop_tv:
                it = new Intent(mContext,ChooseShopActivity.class);
                it.putExtra("shop_ids",shop_ids);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);

                break;

            case R.id.ag_promotion_begin_time_tv:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(1);

                break;

            case R.id.ag_promotion_end_time_tv:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(2);

                break;

            case R.id.add_content_img:
                //发布图片
                requestSD();

                break;

            case R.id.add_content_txt:
                //发布文字
                if(adapter != null){
                    adapter.addItem(new ImageTextBean("1",""));
                    contentRv.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }

                break;

//            case R.id.ag_cost_rl:
//                ArrayList<String> cList = new ArrayList<>();
//                cList.add("快递 包邮");
//                cList.add("6元");
//                cList.add("8元");
//                cList.add("10元");
//                cList.add("20元");
//                cList.add("30元");
//                getShippingMethodPicker(cList).show();
//
//                break;

        }
    }

    private boolean isNeedUploadImage(){
        for (ImageTextBean bean : adapter.getItems()){
            if(bean.getContent_type().equals("2") && StringUtils.isEmpty(bean.getContent_url())){
                return true;
            }
        }
        return false;
    }


    private void save(boolean isShowProgress) {
        setGoods(isShowProgress,item_id,item_img,itemNameEt.getText().toString().trim(),ggEt.getText().toString().trim(),catalog_id,shop_ids,costPriceEt.getText().toString().trim(),
                priceEt.getText().toString().trim(),marketPriceEt.getText().toString().trim(),promotionBeginTimeTv.getText().toString().trim(),promotionEndTimeTv.getText().toString().trim(),
                promotionPriceEt.getText().toString().trim(),getFiles());
    }

    private void setGoods(boolean isShowProgress,String item_id,String item_img,String item_name,String item_specification,String catalog_id,String shop_ids,String cost_price,
                          String price,String market_price,String promotion_begin_time,String promotion_end_time,String promotion_price,String files){


        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_commgr_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,
                        item_img,
                        item_name,
                        item_specification,
                        catalog_id,
                        shop_ids,
                        cost_price,
                        price,
                        market_price,
                        promotion_begin_time,
                        promotion_end_time,
                        promotion_price,
                        files),isShowProgress);
    }

    private boolean checkParams(){
        if(StringUtils.isEmpty(item_img)){
            ToastUtil.show(mContext,"请上传商品图片");
            return false;
        }
        if(TextUtils.isEmpty(itemNameEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入商品名称");
            return false;
        }
        if(TextUtils.isEmpty(ggEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入商品规格");
            return false;
        }
        if(TextUtils.isEmpty(catalog_id)){
            ToastUtil.show(mContext,"请选择商品类别");
            return false;
        }
        if(TextUtils.isEmpty(shop_ids)){
            ToastUtil.show(mContext,"请选择店铺");
            return false;
        }
        if(TextUtils.isEmpty(costPriceEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入成本");
            return false;
        }
        if(TextUtils.isEmpty(priceEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入现价");
            return false;
        }
        if(TextUtils.isEmpty(marketPriceEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入市场价");
            return false;
        }
        if(!StringUtils.isEmpty(promotionBeginTimeTv.getText().toString())){
            if(StringUtils.isEmpty(promotionEndTimeTv.getText().toString())){
                ToastUtil.show(mContext,"请选择结束时间");
                return false;
            }
            if(StringUtils.isEmpty(promotionPriceEt.getText().toString())){
                ToastUtil.show(mContext,"请输入促销价格");
                return false;
            }
        }
        if(!StringUtils.isEmpty(promotionEndTimeTv.getText().toString())){
            if(StringUtils.isEmpty(promotionBeginTimeTv.getText().toString())){
                ToastUtil.show(mContext,"请选择开始时间");
                return false;
            }
            if(StringUtils.isEmpty(promotionPriceEt.getText().toString())){
                ToastUtil.show(mContext,"请输入促销价格");
                return false;
            }
        }
        if(!StringUtils.isEmpty(promotionPriceEt.getText().toString())){
            if(StringUtils.isEmpty(promotionBeginTimeTv.getText().toString())){
                ToastUtil.show(mContext,"请选择开始时间");
                return false;
            }
            if(StringUtils.isEmpty(promotionEndTimeTv.getText().toString())){
                ToastUtil.show(mContext,"请选择结束时间");
                return false;
            }
        }

        //图文不在这里限制

        return true;
    }

    private void selectItemImg() {
        PictureSelector.create(this)
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
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //当前界面 上一界面都需要finish
                        setResult(-1,new Intent());
                        finish();

                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
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
                            item_img = object.optString("src");
                            UIUtils.loadImg(mContext,AppConfig.getImagePath(item_img),img);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleHUD.dismiss();
                break;

            case HttpModeBase.HTTP_REQUESTCODE_IMG_MUL:
                List<ImageTextBean> list = adapter.getItems();
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
                    save(false);
                } else {
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
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 点击取消按钮
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if(data != null){
           switch (resultCode){
               case AppConfig.ACTIVITY_RESULTCODE://店铺
                   shop_ids = data.getStringExtra("shop_ids");
                   chooseShopTv.setText(data.getStringExtra("shop_names"));
                   break;

               case AppConfig.ACTIVITY_RESULTCODE_1://类别
                   catalog_id = data.getStringExtra("cata_ids");
                   categoryNameTv.setText(data.getStringExtra("cata_names"));
                   break;
           }
        }

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case select_photo_item:{
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if(selectList != null && selectList.size() > 0){
                        item_img_path = selectList.get(0).getCutPath();
                        //UIUtils.loadImg(mContext,item_img_path,img);
                        if(!TextUtils.isEmpty(item_img_path)){
                            //上传图片
                            mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, item_img_path,false);
                        } else {
                            ToastUtil.show(mContext,"请上传商品图片");
                        }

                    }else {
                        ToastUtil.show(mContext,"选择图片出错");
                    }
                }

                    break;


                case select_photo_content:
                    if(adapter !=null){
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        ImageTextBean bean = new ImageTextBean();
                        bean.setContent_type("2");
                        bean.setContent(selectList.get(0).getCutPath());
                        adapter.addItem(bean);
                        contentRv.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }

                    break;


            }
        }
    }

    private int getFirstPos(){
        List<ImageTextBean> list = adapter.getItems();
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
        List<ImageTextBean> list = adapter.getItems();
        int pos = 0;
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getContent_type().equals("2") && !list.get(imgListPos).getContent().contains("http")){
                pos = i;
            }
        }
        return pos;
    }

    private String getFiles(){
        List<ImageTextBean> list = adapter.getItems();
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
        mHttpModeBase.xutilsPostList(HttpModeBase.HTTP_REQUESTCODE_IMG_MUL, imagePath, position, isProgress);
    }

    //配送方式 PV
    private OptionsPickerView getShippingMethodPicker(final ArrayList<String> data) {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (data != null && data.size() > 0) {

                    //costTv.setText(data.get(options1));
                }
            }
        })
                .setSelectOptions(2)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                //.setLabels("小时","分钟","")
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(data, null, null);

        return pvNoLinkOptions;
    }

    TimePickerView pvCustomTime;
    private void showTimePv(final int type) {

        /**
         * 注意事项：
         * 月份是0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR)-1, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR)+1, 11, 31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                String dateStr = DateUtil.calendar2String(calendar, DateUtil.FORMAT_DATE_TIME);
                if(type == 1){
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(promotionEndTimeTv.getText().toString().trim()) &&
                            date.getTime() >= DateUtil.string2Date(promotionEndTimeTv.getText().toString().trim(),DateUtil.FORMAT_DATE_TIME).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    promotionBeginTimeTv.setText(dateStr);
                } else if(type == 2){
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(promotionBeginTimeTv.getText().toString().trim()) &&
                            date.getTime() <= DateUtil.string2Date(promotionBeginTimeTv.getText().toString().trim(),DateUtil.FORMAT_DATE_TIME).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    promotionEndTimeTv.setText(dateStr);
                }

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.my_pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(pvCustomTime != null){
                                    pvCustomTime.returnData();
                                    pvCustomTime.dismiss();
                                }

                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();

        pvCustomTime.show();
    }

    @AfterPermissionGranted(AppConfig.PERMISSION_SD)
    public void requestSD() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //PictureSelectUtil.pickPhoto(this,select_photo_content,false,true);
            PictureSelector.create(this)
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
                    .forResult(select_photo_content);//结果回调onActivityResult code
        } else {
            EasyPermissions.requestPermissions(this, "", AppConfig.PERMISSION_SD,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtil.show(mContext,UIUtils.getString(R.string.no_permission_sd));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(this);
    }

}
