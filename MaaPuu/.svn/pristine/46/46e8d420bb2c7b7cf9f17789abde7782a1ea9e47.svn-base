package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.adapter.ImgTextAdapter;
import com.maapuu.mereca.background.shop.bean.ActDetailBean;
import com.maapuu.mereca.background.shop.bean.ItemDataBean;
import com.maapuu.mereca.background.shop.bean.PackDataBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * （营销活动）套餐活动详情
 * Created by Jia on 2018/3/16.
 */

public class TaoCanDetailActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right_1)
    TextView txtRight1;

    @BindView(R.id.iv_image)
    ImageView ivImage;//套餐活动

    @BindView(R.id.td_pack_name_tv)
    TextView packNameTv;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;

    @BindView(R.id.td_price_tv)
    TextView priceTv;
    @BindView(R.id.td_market_price_tv)
    TextView marketPriceTv;

    @BindView(R.id.list_view_shop)
    MyListView listViewShop;
    @BindView(R.id.list_view_project)
    MyListView listViewProject;
    @BindView(R.id.act_pack_detail_rv)
    NestedRecyclerView ImgTextRv;

    private List<String> shops;
    private List<String> projects;
    private List<String> details;
    private QuickAdapter shopAdapter;
    private QuickAdapter projectAdapter;

    int act_type = 2; //活动类型：1会员卡；2套餐活动；3红包
    String business_id = "";
    ActDetailBean bean;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_taocan_detail);
    }

    @Override
    public void initView() {
        business_id = getIntent().getStringExtra("business_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight1.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("套餐活动详情");

        LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) ivImage.getLayoutParams();
        rl.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, getResources().getDisplayMetrics());
        ivImage.setLayoutParams(rl);

        shops = new ArrayList<>();projects = new ArrayList<>();details = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            shops.add("");projects.add("");details.add("");
        }
        shopAdapter = new QuickAdapter<String>(mContext,R.layout.shop_item_vipcard_detail,shops) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
            }
        };
        listViewShop.setAdapter(shopAdapter);
        projectAdapter = new QuickAdapter<String>(mContext,R.layout.shop_item_vipcard_detail,projects) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setVisible(R.id.txt_price,true);
                helper.setText(R.id.txt_name,"宇宙第一洗护套餐");
            }
        };
        listViewProject.setAdapter(projectAdapter);
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
        PackDataBean pack = bean.getPack_data();
        if(pack != null){
            packNameTv.setText(pack.getPack_name());
            UIUtils.loadImg(mContext,pack.getPack_img(),ivImage);
            txtStartTime.setText(pack.getDeadline_begin());
            txtEndTime.setText(pack.getDeadline_end());
            priceTv.setText("¥"+pack.getPrice()+"元");
            marketPriceTv.setText("¥"+pack.getMarket_price()+"元");

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
            List<ImageTextBean> imgTextList = pack.getDetail();
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
                it = new Intent(mContext,AddTaoCanActivity.class);
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
