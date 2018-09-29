package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ActDetailBean;
import com.maapuu.mereca.background.shop.bean.RedDataBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DateUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.wangchen.simplehud.SimpleHUD;

/**
 * （营销活动）添加红包
 * Created by Jia on 2018/3/16.
 */

public class AddHongBaoActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.txt_label)
    TextView txtLabel;
    @BindView(R.id.txt_child_label)
    TextView txtChildLabel;

    @BindView(R.id.red_amount_et)
    EditText redAmountEt;
    @BindView(R.id.full_cut_amount_et)
    EditText fullCutAmountEt;
    @BindView(R.id.limit_days_et)
    EditText limitDaysEt;

    @BindView(R.id.ah_shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.ah_item_name_tv)
    TextView itemNameTv;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;
    @BindView(R.id.delivery_mode_tv)
    TextView deliveryModeTv;

    int act_type = 3; //活动类型：1会员卡；2套餐活动；3红包
    String red_type = "";  //红包类型:1项目红包；2商品红包
    String red_act_id = "";
    String delivery_mode = "";//投放方式，选项由接口返回
    String shop_ids = "";
    String item_ids = "";
    String shop_name = "";
    String item_names = "";

    ActDetailBean bean;
    List<ActDetailBean.ActDelOptionsBean> optionsList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_add_hongbao);
    }

    @Override
    public void initView() {
        red_type = getIntent().getStringExtra("red_type");
        bean = (ActDetailBean) getIntent().getSerializableExtra("ActDetailBean");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setVisibility(View.VISIBLE);txtRight.setText("确定");

        //红包类型:1项目红包；2商品红包
        switch (red_type){
            case "2":
                txtTitle.setText("添加商品红包");
                txtLabel.setText("店铺和商品");
                txtChildLabel.setText("商品");
                break;
            case "1":
                txtTitle.setText("添加项目红包");
                txtLabel.setText("店铺和项目");
                txtChildLabel.setText("项目");
                break;
        }
        setUI(bean);
    }

    @Override
    public void initData() {
    }

    private void setUI(ActDetailBean bean) {
        if(bean != null) {//编辑
            //shop_ids = bean.getShop_ids();
            item_ids = bean.getItem_ids();
            RedDataBean red = bean.getRed_data();
            if(red != null){
                red_act_id = red.getRed_act_id();
                delivery_mode = red.getDelivery_mode();
                shop_ids = bean.getShop_ids();
                item_ids = bean.getItem_ids();

                redAmountEt.setText(red.getRed_amount());
                fullCutAmountEt.setText(red.getFullcut_amount());
                limitDaysEt.setText(red.getLimit_days());
                shopNameTv.setText(bean.getShop_names());
                itemNameTv.setText(bean.getItem_names());

                txtStartTime.setText(red.getDeadline_begin());
                txtEndTime.setText(red.getDeadline_end());
                deliveryModeTv.setText(red.getDelivery_mode_text());
            }

            //使用期限
            optionsList = bean.getAct_del_options();

        } else {//新增
            red_act_id = "0";
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

    private void addActRed(boolean isShowProgress){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_act_red_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        red_act_id,
                        red_type,
                        redAmountEt.getText().toString().trim(),
                        fullCutAmountEt.getText().toString().trim(),
                        limitDaysEt.getText().toString().trim(),
                        delivery_mode,
                        shop_ids,
                        item_ids,
                        txtStartTime.getText().toString().trim(),
                        txtEndTime.getText().toString().trim()),isShowProgress);
    }

    @Override
    @OnClick({R.id.txt_left, R.id.txt_right,R.id.txt_start_time, R.id.txt_end_time,R.id.delivery_mode_tv,R.id.ah_add_project_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://保存
                if(checkParams()){
                    addActRed(true);
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

            case R.id.delivery_mode_tv:
                //投放方式
                //弹出滚轮选择
                if(optionsList != null && optionsList.size()>0){
                    getLimitPicker().show();
                }

                break;

            case R.id.ah_add_project_shop:
                //添加商铺和项目
                it = new Intent(mContext,MulPackActProjectListActivity.class);
                it.putExtra("act_type",act_type);
                it.putExtra("business_id",red_act_id);
                it.putExtra("shop_ids",shop_ids);
                it.putExtra("item_type",red_type);//红包才使用
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);

                break;
        }
    }

    private boolean checkParams() {

        if(TextUtils.isEmpty(redAmountEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入红包金额");
            return false;
        }
        if(TextUtils.isEmpty(fullCutAmountEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入可用条件");
            return false;
        }
        if(TextUtils.isEmpty(limitDaysEt.getText().toString().trim())){
            ToastUtil.show(mContext,"请输入使用期限");
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
            if("1".equals(red_type)){
                ToastUtil.show(mContext,"请选择项目");
            } else if("2".equals(red_type)){
                ToastUtil.show(mContext,"请选择商品");
            }

            return false;
        }
        if(TextUtils.isEmpty(delivery_mode)){
            ToastUtil.show(mContext,"请选择投放模式");
            return false;
        }

        return true;
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

            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            bean = FastJsonTools.getPerson(object.optString("result"), ActDetailBean.class);
                            if(bean != null){
                                //使用期限
                                optionsList = bean.getAct_del_options();
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
                            date.getTime() >= DateUtil.string2Date(txtEndTime.getText().toString().trim(),DateUtil.FORMAT_DATE).getTime()){
                        ToastUtil.show(mContext,"结束时间不能早于开始时间");
                        return;
                    }
                    txtStartTime.setText(StringUtils.getTime(date));
                } else if (type == 2) {
                    //结束时间不能早于开始时间
                    if(!TextUtils.isEmpty(txtStartTime.getText().toString().trim()) &&
                            date.getTime() <= DateUtil.string2Date(txtStartTime.getText().toString().trim(),DateUtil.FORMAT_DATE).getTime()){
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
    private OptionsPickerView getLimitPicker() {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (optionsList != null && optionsList.size() > 0) {
                    deliveryModeTv.setText(optionsList.get(options1).getOption_text());
                    delivery_mode = optionsList.get(options1).getOption();
                }
            }
        })
                .setSelectOptions(1)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                //.setLabels("小时","分钟","")
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(optionsList, null, null);

        return pvNoLinkOptions;
    }

}
