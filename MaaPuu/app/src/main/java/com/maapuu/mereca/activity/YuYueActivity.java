package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.AppointInitBean;
import com.maapuu.mereca.bean.DateBean;
import com.maapuu.mereca.bean.SrvDataBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.event.EventEntity;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class YuYueActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.list_view)
    MyListView listView;

    @BindView(R.id.iv_shop)
    SimpleDraweeView iv_shop;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.txt_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.item_name)
    TextView tvItemName;
    @BindView(R.id.price)
    TextView tvPrice;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_date)
    TextView tvDate;

    private List<SrvDataBean> list;
    private QuickAdapter<SrvDataBean> adapter;
    String name = "";
    String phone = "";
    String oid = "";
    String appoint_time = "";
    String code2d_id = "0";//二维码id，在订单详情里面预约传0，系统默认一个二维码进行预约

    List<DateBean> srv_time;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_yuyue);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("预约");
        list = new ArrayList<>();

        adapter = new QuickAdapter<SrvDataBean>(mContext, R.layout.item_appointment, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, SrvDataBean item) {
                helper.setText(R.id.srv_name, item.getSrv_name());
                helper.setText(R.id.srv_duration, item.getSrv_duration());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getBundle();

        tvName.setText(name);
        tvPhone.setText(phone);

        //code2d_id;//二维码id，在订单详情里面预约传0，系统默认一个二维码进行预约
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.appoint_code2d_init_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), oid, code2d_id, AppConfig.LAT, AppConfig.LNG), true);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            oid = bundle.getString("oid");
        }
    }

    private void setInitUI(AppointInitBean bean) {
        AppointInitBean.OrderDetailBean detail = bean.getOrder_detail();
        if (detail != null) {
            code2d_id = detail.getCode2d_id();
            iv_shop.setImageURI(Uri.parse(detail.getShop_logo()));
            tvShopName.setText(detail.getShop_name());
            tvAddress.setText(detail.getAddress_detail());
            if (TextUtils.isEmpty(detail.getDistance())) {
                tvDistance.setText("距我" + detail.getDistance() + "米");
            }
            tvItemName.setText(detail.getItem_name());
            tvPrice.setText("¥" + detail.getPay_amount());
        }

        List<SrvDataBean> srvData = bean.getSrv_data();
        if (srvData != null && srvData.size() > 0) {
            if (adapter != null) {
                adapter.addAll(srvData);
            }
        }

        srv_time = bean.getSrv_time();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            AppointInitBean bean = FastJsonTools.getPerson(object.optString("result"), AppointInitBean.class);
                            if (bean != null) {
                                setInitUI(bean);
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
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            it = new Intent(mContext, YuYueSuccessActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("code2d_id", code2d_id);
                            bundle.putString("oid", oid);
                            it.putExtras(bundle);
                            startActivity(it);

                            //通知上一界面刷新数据
                            EventBus.getDefault().post(new EventEntity(AppConfig.refresh_in_ConsumeCodeDetailActivity,getInstanceTag()));
                            finish();

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
    @OnClick({R.id.txt_left, R.id.ll_date, R.id.txt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.ll_date:
                it = new Intent(mContext, ChooseYuYueTimeActivity.class);
                it.putExtra("srv_time", (Serializable) srv_time);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;

            case R.id.txt_commit:
                submitAppoint();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case AppConfig.ACTIVITY_REQUESTCODE:
                    if (!TextUtils.isEmpty(data.getStringExtra("time"))) {
                        appoint_time = data.getStringExtra("time") + ":00";
                    }
                    tvDate.setText(data.getStringExtra("time"));

                    break;


            }
        }else if(resultCode == AppConfig.ACTIVITY_RESULTCODE){
            finish();
//            appoint_time = "";
//            tvDate.setText("当面约时间");
        }
    }

    //提交预约
    private void submitAppoint() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.appoint_code2d_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), oid, code2d_id, name, phone, appoint_time), true);
    }
}
