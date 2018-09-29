package com.maapuu.mereca.background.shop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ProjectOrderDetailBean;
import com.maapuu.mereca.background.shop.bean.ShopDataBean;
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

/** 后台 项目订单详情
 * Created by Jia on 2018/4/23.
 */

public class ShopOrderProjectDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.iv_shop)
    SimpleDraweeView ivShop;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_shop_address)
    TextView txtShopAddress;
    @BindView(R.id.txt_distance)
    TextView txtDistance;

    @BindView(R.id.iv_goods)
    SimpleDraweeView ivGoods;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.txt_goods_spec)
    TextView txtGoodsSpec;
    @BindView(R.id.txt_goods_price)
    TextView txtGoodsPrice;
    @BindView(R.id.txt_goods_num)
    TextView txtGoodsNum;

    //@BindView(R.id.txt_srv_price)
    //TextView txtSrvPrice;
    @BindView(R.id.txt_youhui_price)
    TextView txtYouhuiPrice;
    @BindView(R.id.txt_order_price)
    TextView txtOrderPrice;

    //@BindView(R.id.txt_label)
    //TextView txtLabel;
    @BindView(R.id.txt_pay_amount)
    TextView txtPayAmount;
    @BindView(R.id.txt_order_no)
    TextView txtOrderNo;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.txt_order_time)
    TextView txtOrderTime;
    @BindView(R.id.txt_client_name)
    TextView txtClientName;
    @BindView(R.id.txt_contact)
    TextView txtContact;

    private String oid;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_order_detail_project);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("订单详情");
        oid = getIntent().getStringExtra("oid");
    }

    @Override
    public void initData() {
        getOrderDetail();
    }

    private void getOrderDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_project_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        oid),true);
    }

    private void setUI(ProjectOrderDetailBean bean) {
        ShopDataBean shop = bean.getShop_data();
        if(shop != null){
            ivShop.setImageURI(Uri.parse(shop.getShop_logo()));
            txtShopName.setText(shop.getShop_name());
            txtShopAddress.setText(shop.getAddress_detail());
            //txtDistance.setText("");
        }

        ProjectOrderDetailBean.ItemDetailBean item = bean.getItem_detail();
        if(item != null){
            ivGoods.setImageURI(Uri.parse(item.getItem_img()));
            txtGoodsName.setText(item.getItem_name());
            txtGoodsSpec.setText(item.getItem_desc());
            txtGoodsPrice.setText("¥"+item.getPrice());
            txtGoodsNum.setText("x"+item.getNum());
        }

        ProjectOrderDetailBean.OrderDetailBean order = bean.getOrder_detail();
        if(order != null){
            txtYouhuiPrice.setText("¥"+order.getDis_amount());
            txtOrderPrice.setText("¥"+order.getOrder_amount());
            txtPayAmount.setText("¥"+order.getPay_amount());
            txtOrderNo.setText("订单编号："+order.getOrder_no());
            switch (order.getPay_type()){
                case 1:
                    txtPayType.setText("支付方式："+"支付宝支付");
                    break;
                case 2:
                    txtPayType.setText("支付方式："+"微信支付");
                    break;
                case 3:
                    txtPayType.setText("支付方式："+"余额支付");
                    break;
            }
            txtOrderTime.setText("下单时间："+order.getCreate_time_text());

            txtClientName.setVisibility(StringUtils.isEmpty(order.getNick_name())?View.GONE:View.VISIBLE);
            txtContact.setVisibility(StringUtils.isEmpty(order.getNick_name())?View.GONE:View.VISIBLE);
            txtClientName.setText("账户名称："+order.getNick_name());
            txtContact.setText("联系方式："+order.getPhone());
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
                            ProjectOrderDetailBean bean = FastJsonTools.getPerson(object.optString("result"), ProjectOrderDetailBean.class);
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
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
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

}
