package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.ActDetailBean;
import com.maapuu.mereca.background.shop.bean.ItemDataBean;
import com.maapuu.mereca.background.shop.bean.RedDataBean;
import com.maapuu.mereca.background.shop.bean.ShopDataBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * （营销活动）红包详情
 * Created by Jia on 2018/3/16.
 */

public class HongBaoDetailActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right_1)
    TextView txtRight1;

    @BindView(R.id.txt_label)
    TextView txtLabel;//店铺和项目
    @BindView(R.id.txt_child_label)
    TextView txtChildLabel; // 项目 或 商品

    @BindView(R.id.hd_red_amount_tv)
    TextView redAmountTv;
    @BindView(R.id.full_cut_amount_tv)
    TextView fullCutAmountTv;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;
    @BindView(R.id.limit_days_tv)
    TextView limitDaysTv;
    @BindView(R.id.delivery_mode_tv)
    TextView deliveryModeTv;//交付方式

    @BindView(R.id.list_view_shop)
    MyListView listViewShop;
    @BindView(R.id.list_view_project)
    MyListView listViewProject;

    private QuickAdapter shopAdapter;
    private QuickAdapter projectAdapter;

    int act_type = 3; //活动类型：1会员卡；2套餐活动；3红包
    String business_id = "";
    String red_type = "";  //红包类型:1项目红包；2商品红包
    ActDetailBean bean;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_hongbao_detail);
    }

    @Override
    public void initView() {
        business_id = getIntent().getStringExtra("business_id");
        red_type = getIntent().getStringExtra("red_type");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight1.setTypeface(StringUtils.getFont(mContext));

        //红包类型:1项目红包；2商品红包
        switch (red_type){
            case "2":
                txtTitle.setText("商品红包详情");
                txtLabel.setText("店铺和商品");
                txtChildLabel.setText("商品");
                break;

            case "1":
                txtTitle.setText("项目红包详情");
                txtLabel.setText("店铺和项目");
                txtChildLabel.setText("项目");
                break;
        }
    }

    @Override
    public void initData() {
        getActDetail();
    }

    private void getActDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_act_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        act_type,
                        business_id),true);
    }

    private void setUI(ActDetailBean bean) {
        RedDataBean red = bean.getRed_data();
        if(red != null){
            redAmountTv.setText(red.getRed_amount()+"元");
            fullCutAmountTv.setText("消费满"+red.getFullcut_amount()+"元");
            limitDaysTv.setText(red.getLimit_days()+"天");

            txtStartTime.setText(red.getDeadline_begin());
            txtEndTime.setText(red.getDeadline_end());
            deliveryModeTv.setText(red.getDelivery_mode_text());

            //店铺
            List<ShopDataBean> shop_data = bean.getShop_data();
            shopAdapter = new QuickAdapter<ShopDataBean>(mContext,R.layout.shop_item_vipcard_detail,shop_data) {
                @Override
                protected void convert(BaseAdapterHelper helper, ShopDataBean item) {
                    helper.setText(R.id.txt_name,item.getShop_name());
                }
            };
            listViewShop.setAdapter(shopAdapter);

            //项目
            List<ItemDataBean> item_data = bean.getItem_data();
            projectAdapter = new QuickAdapter<ItemDataBean>(mContext,R.layout.shop_item_vipcard_detail,item_data) {
                @Override
                protected void convert(BaseAdapterHelper helper, ItemDataBean item) {
                    helper.setVisible(R.id.txt_price,true);
                    helper.setText(R.id.txt_name,item.getItem_name());
                }
            };
            listViewProject.setAdapter(projectAdapter);
        }
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_right_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //删除
                showDeleteDialog("你确定删除吗？");

                break;

            case R.id.txt_right_1:
                it = new Intent(mContext,AddHongBaoActivity.class);
                it.putExtra("red_type",red_type);
                it.putExtra("ActDetailBean",bean);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case AppConfig.ACTIVITY_REQUESTCODE:
                    //关闭界面
                    finish();

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
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            bean = FastJsonTools.getPerson(object.optString("result"), ActDetailBean.class);
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

            case HttpModeBase.HTTP_REQUESTCODE_2://删除
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        setResult(-1,new Intent());
                        finish();
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

    private void showDeleteDialog(final String msg) {
        NiceDialog.init()
                .setLayoutId(R.layout.nd_layout_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setText(R.id.title,"删除提示");
                        holder.setText(R.id.message,msg);

                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //消失
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                deleteGoods();
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                //.setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }

    private void deleteGoods(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_act_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        act_type,business_id),true);
    }


}
