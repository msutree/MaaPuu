package com.maapuu.mereca.background.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ImageTextEditAdapter;
import com.maapuu.mereca.background.shop.bean.ActDetailBean;
import com.maapuu.mereca.background.shop.bean.CardDataBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DateUtil;
import com.maapuu.mereca.util.DisplayUtil;
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
 * （营销活动）添加会员卡
 * Created by Jia on 2018/3/16.
 */

public class AddVipCardActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.pm_ap_cover_rl)
    RelativeLayout coverRl;
    @BindView(R.id.as_cover_iv)
    ImageView ivImage;

    @BindView(R.id.et_vipcard_name)
    EditText etVipCardName;
    @BindView(R.id.card_desc_et)
    EditText cardDescEt;

    @BindView(R.id.rl_server_times)
    RelativeLayout rlServerTimes;
    @BindView(R.id.et_server_times)
    EditText etServerTimes;

    @BindView(R.id.et_charge_money)
    EditText etChargeMoney;
    @BindView(R.id.rl_give_money)
    RelativeLayout rlGiveMoney;
    @BindView(R.id.et_give_money)
    EditText etGiveMoney;

    @BindView(R.id.txt_use_limit)
    TextView txtUseLimit;//使用期限

    @BindView(R.id.et_discount_type)
    EditText etDiscountType;
    @BindView(R.id.txt_discount_type_label)
    TextView txtDiscountTypeLabel;

    @BindView(R.id.av_shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.av_item_name_tv)
    TextView itemNameTv;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;

    @BindView(R.id.add_content_img)
    TextView addContentImgTv;
    @BindView(R.id.add_content_txt)
    TextView addContentTxtTv;

    @BindView(R.id.pm_content_rv)
    NestedRecyclerView contentRv;

    ImageTextEditAdapter adapter;
    private final int select_photo_cover = 102;
    private final int select_photo_content = 103;

    int card_type;// card_type 会员类型：1会籍；2项目卡；3充值卡
    String card_id = "0";
    String card_img = "";
    String card_img_path = "";

    String shop_ids = "";
    String item_ids = "";
    String shop_name = "";
    String item_names = "";
    String limit_months = "";//使用期限，单位为月

    private int imgListPos = 0;//上传的图片下标
    ActDetailBean bean;
    int act_type = 1; //活动类型：1会员卡；2套餐活动；3红包
    List<ActDetailBean.ActLimitOptionsBean> limitList = new ArrayList<>();

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_vipcard);
    }

    @Override
    public void initView() {
        card_type = getIntent().getIntExtra("card_type",0);
        bean = (ActDetailBean) getIntent().getSerializableExtra("ActDetailBean");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setVisibility(View.VISIBLE);txtRight.setText("确定");
        addContentImgTv.setTypeface(StringUtils.getFont(mContext));
        addContentTxtTv.setTypeface(StringUtils.getFont(mContext));

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) coverRl.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        coverRl.setLayoutParams(lp);

        switch (card_type){
            case 3:
                txtTitle.setText("添加充值卡");
                rlServerTimes.setVisibility(View.GONE);rlGiveMoney.setVisibility(View.VISIBLE);
                etDiscountType.setVisibility(View.VISIBLE);txtDiscountTypeLabel.setText("折");

                break;
            case 2:
                txtTitle.setText("添加项目卡");
                rlServerTimes.setVisibility(View.VISIBLE);rlGiveMoney.setVisibility(View.GONE);
                etDiscountType.setVisibility(View.GONE);txtDiscountTypeLabel.setText("免费");

                break;
            case 1:
                txtTitle.setText("添加会籍");
                rlServerTimes.setVisibility(View.GONE);rlGiveMoney.setVisibility(View.GONE);
                etDiscountType.setVisibility(View.GONE);txtDiscountTypeLabel.setText("免费");

                break;
        }

        setContentEditRv();
        setUI(bean);
    }

    private void setUI(ActDetailBean bean) {
        if(bean != null) {//编辑
            shop_ids = bean.getShop_ids();
            item_ids = bean.getItem_ids();
            CardDataBean card = bean.getCard_data();
            if(card != null){
                card_id = card.getCard_id();
                card_img = card.getCard_img();
                card_img_path = card_img;//保证值不为空即可

                etVipCardName.setText(card.getCard_name());
                UIUtils.loadImg(mContext,card_img,ivImage);
                cardDescEt.setText(card.getCard_desc());
                etChargeMoney.setText(card.getRecharge_amount());
                txtUseLimit.setText(card.getLimit_months_text());
                limit_months = card.getLimit_months();

                shopNameTv.setText(bean.getShop_names());
                itemNameTv.setText(bean.getItem_names());

                txtStartTime.setText(card.getDeadline_begin());
                txtEndTime.setText(card.getDeadline_end());
                //图文
                List<ImageTextBean> imgTextList = card.getDetail();
                if(imgTextList != null && imgTextList.size()>0){
                    adapter.clear();
                    adapter.addAll(imgTextList);
                }
            }

            //使用期限
            limitList = bean.getAct_limit_options();

        } else {//新增
            card_id = "0";
            adapter.addItem(new ImageTextBean("1",""));
            //请求接口，获取limitList
            getActDetail();
        }
    }

    private void getActDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_act_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        act_type,
                        "0"),true);
    }

    private void setContentEditRv() {
        contentRv.setNestedScrollingEnabled(false);
        contentRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,
                UIUtils.dip2px(mContext, 5), getResources().getColor(R.color.background)));
        contentRv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ImageTextEditAdapter(mContext);
        contentRv.setAdapter(adapter);
    }

    @Override
    public void initData() {
    }

    private void addActCard(boolean isShowProgress){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_act_card_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        card_id,
                        card_type+"",
                        card_img,
                        etVipCardName.getText().toString().trim(),
                        cardDescEt.getText().toString().trim(),
                        shop_ids,
                        item_ids,
                        limit_months,
                        etChargeMoney.getText().toString().trim(),
                        etGiveMoney.getText().toString().trim(),
                        card_type == 2?etServerTimes.getText().toString().trim():"0",// 次数
                        card_type == 3?etDiscountType.getText().toString().trim():"0",// 折扣 充值卡--折扣比例，会籍和项目卡传0
                        txtStartTime.getText().toString().trim(),
                        txtEndTime.getText().toString().trim(),
                        getFiles()),isShowProgress);
    }

    @Override
    @OnClick({R.id.txt_left, R.id.txt_right, R.id.pm_ap_cover_rl, R.id.txt_use_limit, R.id.txt_start_time, R.id.txt_end_time,
            R.id.add_content_img, R.id.add_content_txt,R.id.ac_add_project_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://保存
                if(checkParams()){
                    if (isNeedUploadImage()) {
                        imgListPos = getFirstPos();
                        image_save_set(adapter.getItems().get(imgListPos).getContent(), imgListPos, false);
                    } else {
                        addActCard(true);
                    }
                }

                break;
            case R.id.pm_ap_cover_rl://上传店铺图片
                selectImg();

                break;
            case R.id.txt_use_limit:
                //弹出滚轮选择
                if(limitList != null && limitList.size()>0){
                    getLimitMonthPicker().show();
                }

                break;

            case R.id.txt_start_time:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(1);
                break;
            case R.id.txt_end_time:
                KeyBoardUtils.closeKeybord(view,mContext);
                showTimePv(2);
                break;
            case R.id.add_content_img://发布图片
                requestSD();
                break;

            case R.id.add_content_txt://发布文字
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

            case R.id.ac_add_project_shop:
                //添加商铺和项目
                it = new Intent(mContext,MulPackActProjectListActivity.class);
                it.putExtra("act_type",act_type);
                it.putExtra("business_id",card_id);
                it.putExtra("shop_ids",shop_ids);
                //it.putExtra("item_type",item_type);//红包才使用
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);

                break;
        }
    }

    private void selectImg() {
        PictureSelector.create(AddVipCardActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .withAspectRatio(5,2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                //.selectionMedia(selectList)// 是否传入已选图片
                .compress(true)// 是否压缩 true or false
                .forResult(select_photo_cover);//结果回调onActivityResult code
    }

    private boolean isNeedUploadImage(){
        for (ImageTextBean bean : adapter.getItems()){
            if(bean.getContent_type().equals("2") && StringUtils.isEmpty(bean.getContent_url())){
                return true;
            }
        }
        return false;
    }

    private boolean checkParams() {
        // card_type 会员类型：1会籍；2项目卡；3充值卡
        if(StringUtils.isEmpty(card_img)){
            ToastUtil.show(mContext,"请上传图片");
            return false;
        }
        if(TextUtils.isEmpty(etVipCardName.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入会员卡名称");
            return false;
        }
        if(TextUtils.isEmpty(cardDescEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入会员说明");
            return false;
        }
        if(TextUtils.isEmpty(limit_months)){
            ToastUtil.show(mContext,"请选择使用期限");
            return false;
        }

        if(card_type != 2 && TextUtils.isEmpty(etChargeMoney.getText().toString().trim())){//项目卡不能充值
            ToastUtil.show(mContext,"请输入充值金额");
            return false;
        }

        if(card_type == 3 && TextUtils.isEmpty(etGiveMoney.getText().toString().trim())){//充值卡--赠送金额，会籍和项目卡传0
            ToastUtil.show(mContext,"请输入充值金额");
            return false;
        }

        if(TextUtils.isEmpty(txtStartTime.getText().toString().trim())){
            ToastUtil.show(mContext,"请选择开始时间");
            return false;
        }
        if(TextUtils.isEmpty(txtEndTime.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入结束时间");
            return false;
        }
        if(TextUtils.isEmpty(shop_ids)){
            ToastUtil.show(mContext,"请选择店铺");
            return false;
        }
        if(TextUtils.isEmpty(item_ids)){
            ToastUtil.show(mContext,"请选择项目");
            return false;
        }

        return true;
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
                            card_img = object.optString("src");
                            UIUtils.loadImg(mContext,AppConfig.getImagePath(card_img),ivImage);
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
                    addActCard(false);
                } else {
                    imgListPos++;
                    if(list.get(imgListPos).getContent_type().equals("1") || list.get(imgListPos).getContent().contains("http")){
                        imgListPos ++;
                    }
                    image_save_set(list.get(imgListPos).getContent(), imgListPos, false);
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            bean = FastJsonTools.getPerson(object.optString("result"), ActDetailBean.class);
                            if(bean != null){
                                //使用期限
                                limitList = bean.getAct_limit_options();
                            }
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case select_photo_cover:{
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if(selectList != null && selectList.size() > 0){
                        card_img_path = selectList.get(0).getCutPath();
                        //UIUtils.loadImg(mContext,item_img_path,img);
                        if(!TextUtils.isEmpty(card_img_path)){
                            //上传图片
                            mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, card_img_path,false);
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

                case AppConfig.ACTIVITY_REQUESTCODE://店铺项目
                    shop_ids = data.getStringExtra("shop_ids");
                    shop_name = data.getStringExtra("shop_name");
                    item_ids = data.getStringExtra("item_ids");
                    item_names = data.getStringExtra("item_names");

                    shopNameTv.setText(shop_name);
                    itemNameTv.setText(item_names);

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
                if (type == 1) {
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(txtEndTime.getText().toString().trim()) &&
                            date.getTime() >= DateUtil.string2Date(txtEndTime.getText().toString().trim(), DateUtil.FORMAT_DATE).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    txtStartTime.setText(StringUtils.getTime(date));
                } else if (type == 2) {
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(txtStartTime.getText().toString().trim()) &&
                            date.getTime() <= DateUtil.string2Date(txtStartTime.getText().toString().trim(), DateUtil.FORMAT_DATE).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    txtEndTime.setText(StringUtils.getTime(date));
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
                                if (pvCustomTime != null) {
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
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();

        pvCustomTime.show();
    }

    //使用期限 PV
    private OptionsPickerView getLimitMonthPicker() {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (limitList != null && limitList.size() > 0) {
                    txtUseLimit.setText(limitList.get(options1).getMonth_text());
                    limit_months = limitList.get(options1).getMonth();
                }
            }
        })
                .setSelectOptions(1)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                //.setLabels("小时","分钟","")
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(limitList, null, null);

        return pvNoLinkOptions;
    }

    @AfterPermissionGranted(AppConfig.PERMISSION_SD)
    public void requestSD() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //PictureSelectUtil.pickPhoto(this, select_photo_content, false, true);

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
        ToastUtil.show(mContext, UIUtils.getString(R.string.no_permission_sd));
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
