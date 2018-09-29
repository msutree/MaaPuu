package com.maapuu.mereca.background.shop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.OrderPackDetailBean;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 * 活动订单详情
 */

public class MealDoingOrderDetailActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.avatar)
    SimpleDraweeView avatarIv;
    @BindView(R.id.nick_name)
    TextView nickNameTv;
    @BindView(R.id.pay_time)
    TextView payTimeTv;
    @BindView(R.id.pack_name)
    TextView packNameTv;
    @BindView(R.id.recharge_amount)
    TextView rechargeAmountTv;
    @BindView(R.id.order_no)
    TextView orderNoTv;


    @BindView(R.id.list_view)
    MyListView listView;
    private QuickAdapter<OrderPackDetailBean.ItemsBean> adapter;

    String oid = "";//订单id

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_meal_doing_order_detail);
    }

    @Override
    public void initView() {
        oid = getIntent().getStringExtra("oid");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("活动订单详情");

        adapter = new QuickAdapter<OrderPackDetailBean.ItemsBean>(mContext,R.layout.shop_item_pack_order_detail) {
            @Override
            protected void convert(BaseAdapterHelper helper, OrderPackDetailBean.ItemsBean item) {
                helper.setText(R.id.item_name,item.getItem_name());
                helper.setText(R.id.order_no,"订单编号："+item.getOrder_no());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getOrderPackDetail();
    }

    private void getOrderPackDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_order_pack_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        oid),true);
    }

    private void setUI(OrderPackDetailBean bean) {
        avatarIv.setImageURI(Uri.parse(bean.getAvatar()));
        nickNameTv.setText(bean.getNick_name());
        payTimeTv.setText(bean.getPay_time());
        packNameTv.setText(bean.getPack_name());
        rechargeAmountTv.setText("¥"+bean.getRecharge_amount());
        orderNoTv.setText("订单编号："+bean.getOrder_no());

        List<OrderPackDetailBean.ItemsBean> items = bean.getItems();
        if(items != null && items.size()>0){
            adapter.clear();
            adapter.addAll(items);
        }
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            OrderPackDetailBean bean = FastJsonTools.getPerson(object.optString("result"), OrderPackDetailBean.class);
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

}
