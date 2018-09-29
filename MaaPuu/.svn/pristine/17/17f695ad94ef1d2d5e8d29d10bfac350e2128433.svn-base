package com.maapuu.mereca.background.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ImgTextAdapter;
import com.maapuu.mereca.background.shop.bean.GoodsDetailBean;
import com.maapuu.mereca.background.shop.bean.GoodsItemBean;
import com.maapuu.mereca.background.shop.bean.ShopDataBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
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
 * 商品信息
 * Created by Jia on 2018/3/20.
 */

public class GoodsInfoActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;

    @BindView(R.id.gi_img)
    ImageView img;
    @BindView(R.id.gi_item_name_tv)
    TextView itemNameTv;
    @BindView(R.id.gi_gg_tv)
    TextView ggTv;//商品规格
    @BindView(R.id.gi_category_name_tv)
    TextView categoryNameTv;
    @BindView(R.id.gi_transport_tv)
    TextView transportTv;//配送规格
    @BindView(R.id.gi_cost_price_tv)
    TextView costPriceTv;
    @BindView(R.id.gi_price_tv)
    TextView priceTv;
    @BindView(R.id.gi_market_price_tv)
    TextView marketPriceTv;
    @BindView(R.id.gi_promotion_begin_time_tv)
    TextView promotionBeginTimeTv;
    @BindView(R.id.gi_promotion_end_time_tv)
    TextView promotionEndTimeTv;
    @BindView(R.id.gi_promotion_price_tv)
    TextView promotionPriceTv;

    @BindView(R.id.pd_shop_rv)
    NestedRecyclerView shopRv;
    @BindView(R.id.gi_detail_rv)
    NestedRecyclerView ImgTextRv;

    boolean isShowProgress = true;
    String item_id = "";//商品id
    String shop_ids = "";
    String shop_names = "";
    GoodsDetailBean bean;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_goods_info);
    }

    @Override
    public void initView() {
        item_id = getIntent().getStringExtra("item_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("商品信息");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
    }

    @Override
    public void initData() {
        getGoodsDetail();
    }

    private void getGoodsDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_commgr_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),item_id),isShowProgress);
    }

    private void setUI() {
        shop_ids = bean.getShop_ids();
        shop_names = bean.getShop_names();

        //店铺列表
        List<ShopDataBean> shopList = bean.getShop_data();
        if(shopList != null && shopList.size()>0){
            shopRv.setLayoutManager(new LinearLayoutManager(mContext));
            shopRv.setNestedScrollingEnabled(false);
            shopRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                    ,getResources().getColor(R.color.bg_line)));
            ShopAdapter shopAdapter = new ShopAdapter(mContext, shopList);
            shopRv.setAdapter(shopAdapter);
        }

        GoodsItemBean itemBean = bean.getItem_data();
        if(itemBean != null){
            UIUtils.loadImg(mContext,itemBean.getItem_img(),img);
            itemNameTv.setText(itemBean.getItem_name());
            ggTv.setText(itemBean.getItem_specification());
            categoryNameTv.setText(itemBean.getCatalog_name());
            //transportTv.setText("");//配送规格
            costPriceTv.setText("¥"+itemBean.getCost_price()+"元");
            priceTv.setText("¥"+itemBean.getPrice()+"元");
            marketPriceTv.setText("¥"+itemBean.getMarket_price()+"元");
            promotionBeginTimeTv.setText(itemBean.getPromotion_begin_time());
            promotionEndTimeTv.setText(itemBean.getPromotion_end_time());
            promotionPriceTv.setText("¥"+itemBean.getPromotion_price()+"元");

            //图文
            List<ImageTextBean> imgTextList = itemBean.getDetail();
            if(imgTextList != null && imgTextList.size()>0){
                ImgTextRv.setLayoutManager(new LinearLayoutManager(mContext));
                ImgTextRv.setNestedScrollingEnabled(false);
                ImgTextAdapter imgTextAdapter = new ImgTextAdapter(this,imgTextList);
                ImgTextRv.setAdapter(imgTextAdapter);
            }
        }
    }

    private void deleteGoods(String item_id){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_commgr_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_right2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //删除
                showDeleteDialog("你确定删除此商品吗？");

                break;

            case R.id.txt_right2:{
                //编辑 需将已有数据传过去
                Intent intent = new Intent(mContext,AddGoodsActivity.class);
                intent.putExtra("item_id",item_id);
                intent.putExtra("GoodsDetailBean",bean);
                startActivityForResult(intent, AppConfig.ACTIVITY_REQUESTCODE);
            }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case AppConfig.ACTIVITY_REQUESTCODE:
                    //刷新
                    getGoodsDetail();

                    break;
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                isShowProgress = false;
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            bean = FastJsonTools.getPerson(object.optString("result"), GoodsDetailBean.class);
                            if(bean != null){
                                setUI();
                            }
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2://删除店铺
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
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

                                deleteGoods(item_id);
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

    //店铺列表
    class ShopAdapter extends BaseRecyclerAdapter<ShopDataBean> {

        public ShopAdapter(Context context, List list) {
            super(context, list, R.layout.shop_item_shop_detail_service);
        }

        @Override
        public void convert(BaseRecyclerHolder holder, ShopDataBean item, int position, boolean isScrolling) {
            holder.setText(R.id.sds_name_tv,item.getShop_name());
        }
    }

}
