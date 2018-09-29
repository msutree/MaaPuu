package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.OrderRemindBean;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品订单提醒发货信息
 * Created by Jia on 2018/4/25.
 */

public class GoodsOrderRemindShipActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.client_name_tv)
    TextView clientNameTv;
    @BindView(R.id.remind_time_tv)
    TextView remindTimeTv;

    String remind_id = "";//提醒发货id

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_goods_order_remind_ship);
    }

    @Override
    public void initView() {
        remind_id = getIntent().getStringExtra("remind_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("提醒发货详情");
    }

    @Override
    public void initData() {
        getRemind();
    }

    private void getRemind() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_commodity_remind_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        remind_id),true);
    }

    private void setUI(OrderRemindBean bean) {
        orderNoTv.setText("订单编号："+bean.getOrder_no());
        clientNameTv.setText(bean.getNick_name());
        remindTimeTv.setText(bean.getCreate_time_text());
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            OrderRemindBean bean = FastJsonTools.getPerson(object.optString("result"), OrderRemindBean.class);
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

            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                finish();
                break;
        }
    }

}
