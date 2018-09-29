package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.adapter.ImgTextAdapter;
import com.maapuu.mereca.background.shop.bean.ActDetailBean;
import com.maapuu.mereca.background.shop.bean.CardDataBean;
import com.maapuu.mereca.background.shop.bean.ItemDataBean;
import com.maapuu.mereca.background.shop.bean.ShopDataBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.MyListView;
import com.maapuu.mereca.view.NestedRecyclerView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * （营销活动）会员卡详情
 * Created by Jia on 2018/3/16.
 */

public class VipCardDetailActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right_1)
    TextView txtRight1;

    @BindView(R.id.pm_ap_cover_rl)
    RelativeLayout coverRl;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.txt_vipcard_name)
    TextView cardNameTv;

    @BindView(R.id.card_desc_rl)
    RelativeLayout cardDescRl;//会员说明
    @BindView(R.id.card_desc_tv)
    TextView cardDescTv;
    @BindView(R.id.recharge_amount_tv)
    TextView rechargeAmountTv;//充值金额
    @BindView(R.id.limit_months_text_tv)
    TextView limitTv;//使用期限
    @BindView(R.id.txt_discount_type)
    TextView discountTv;

    @BindView(R.id.rl_server_times)
    RelativeLayout rlServerTimes;
    @BindView(R.id.server_times)
    TextView serverTimesTv;//次数

    @BindView(R.id.rl_give_money)
    RelativeLayout rlGiveMoney;//赠送金额
    @BindView(R.id.give_amount_tv)
    TextView giveAmountTv;


    @BindView(R.id.list_view_shop)
    MyListView listViewShop;
    @BindView(R.id.list_view_project)
    MyListView listViewProject;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;
    @BindView(R.id.act_card_detail_rv)
    NestedRecyclerView ImgTextRv;


    private QuickAdapter shopAdapter;
    private QuickAdapter projectAdapter;


    int act_type = 1; //活动类型：1会员卡；2套餐活动；3红包
    String business_id = "";
    int card_type;// card_type 会员类型：1会籍；2项目卡；3充值卡
    ActDetailBean bean;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_vipcard_detail);
    }

    @Override
    public void initView() {
        business_id = getIntent().getStringExtra("business_id");
        card_type = getIntent().getIntExtra("card_type",0);
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight1.setTypeface(StringUtils.getFont(mContext));

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) coverRl.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        coverRl.setLayoutParams(lp);
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
        CardDataBean card = bean.getCard_data();
        if(card != null){
            UIUtils.loadImg(mContext,card.getCard_img(),ivImage);
            cardNameTv.setText(card.getCard_name());
            cardDescTv.setText(card.getCard_desc());
            rechargeAmountTv.setText("¥"+card.getRecharge_amount()+"元");
            limitTv.setText(card.getLimit_months_text());
            if("0.0".equals(card.getDiscount())){
                discountTv.setText("免费");
            } else {
                discountTv.setText(card.getDiscount()+"折");
            }
            serverTimesTv.setText(card.getTimes()+"次");
            giveAmountTv.setText("¥"+card.getGive_amount()+"元");

            card_type = card.getCard_type();
            switch (card_type){//会员类型：1会籍；2项目卡；3充值卡
                case 3:
                    txtTitle.setText("充值卡详情");
                    rlServerTimes.setVisibility(View.GONE);rlGiveMoney.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    txtTitle.setText("项目卡详情");
                    rlServerTimes.setVisibility(View.VISIBLE);rlGiveMoney.setVisibility(View.GONE);
                    break;
                case 1:
                    txtTitle.setText("会籍详情");
                    rlServerTimes.setVisibility(View.GONE);rlGiveMoney.setVisibility(View.GONE);
                    break;
            }

            txtStartTime.setText(card.getDeadline_begin());
            txtEndTime.setText(card.getDeadline_end());

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

            //图文详情
            List<ImageTextBean> imgTextList = card.getDetail();
            if(imgTextList != null && imgTextList.size()>0){
                ImgTextRv.setLayoutManager(new LinearLayoutManager(mContext));
                ImgTextRv.setNestedScrollingEnabled(false);
                ImgTextAdapter imgTextAdapter = new ImgTextAdapter(this,imgTextList);
                ImgTextRv.setAdapter(imgTextAdapter);
            }
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
                it = new Intent(mContext,AddVipCardActivity.class);
                it.putExtra("card_type",card_type);
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
