package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.LogisticsCompanyBean;
import com.maapuu.mereca.background.shop.bean.ShipInitBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品订单发货
 * Created by Jia on 2018/4/25.
 */

public class GoodsOrderShipActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.logistics_company_tv)
    TextView companyTv;

    @BindView(R.id.logistics_num_et)
    EditText logisticsNumEt;

    String oid = "";
    List<LogisticsCompanyBean> companyList;
    String logistics_company_id = "";//物流公司id
    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_goods_order_ship);
    }

    @Override
    public void initView() {
        oid = getIntent().getStringExtra("oid");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("未发货订单详情");
    }

    @Override
    public void initData() {
        getLogistics();
    }

    private void getLogistics() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_commodity_logistics_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        oid),true);
    }

    private void setLogistics(String logistics_no) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_commodity_logistics_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        oid,1, logistics_no,logistics_company_id),true);
    }

    private void setUI(ShipInitBean bean) {
        orderNoTv.setText("订单编号："+bean.getOrder_no());
        companyList = bean.getCompany();

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            ShipInitBean bean = FastJsonTools.getPerson(object.optString("result"), ShipInitBean.class);
                            if(bean != null){
                                setUI(bean);
                            }
                        }
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
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    }
                    if(!TextUtils.isEmpty(object.optString("message"))){
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.logistics_company_rl,R.id.txt_no_time,R.id.go_ship_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_no_time:
                alertView = new AlertView(null, "确认商品已被提取？", "取消",null, new String[]{"确认"}, mContext,
                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                                    UrlUtils.s_commodity_logistics_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                                            oid,1, "",""),true);
                        }
                    }
                });
                alertView.show();
                break;

            case R.id.logistics_company_rl:
                StringUtils.closeKeyBorder(mContext,v);
                //选择物流
                //弹出滚轮选择
                if(companyList != null && companyList.size()>0){
                    getCompanyPicker().show();
                }

                break;

            case R.id.go_ship_tv:
                //确认发货
                if(TextUtils.isEmpty(logisticsNumEt.getText().toString().trim())){
                    ToastUtil.show(mContext,"请输入物流单号");
                    return;
                }
                setLogistics(logisticsNumEt.getText().toString().trim());

                break;
        }
    }

    //使用期限 PV
    private OptionsPickerView getCompanyPicker() {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (companyList != null && companyList.size() > 0) {
                    logistics_company_id = companyList.get(options1).getLogistics_company_id();
                    companyTv.setText(companyList.get(options1).getCompany_name());
                }
            }
        })
                .setSelectOptions(1)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                //.setLabels("小时","分钟","")
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(companyList, null, null);

        return pvNoLinkOptions;
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
