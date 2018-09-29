package com.maapuu.mereca.background.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.background.shop.bean.ItemBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
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
 * 项目详情
 * Created by Jia on 2018/3/20.
 */

public class ProjectDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.txt_goods_spec)
    TextView txtGoodsSpec;
    @BindView(R.id.txt_goods_type)
    TextView txtGoodsType;
    @BindView(R.id.ll_srv_times)
    LinearLayout llSrvTimes;
    @BindView(R.id.txt_srv_times)
    TextView txtSrvTimes;
    @BindView(R.id.txt_cost)
    TextView txtCost;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_market_price)
    TextView txtMarketPrice;
    @BindView(R.id.txt_start_time)
    TextView txtStartTime;
    @BindView(R.id.txt_end_time)
    TextView txtEndTime;
    @BindView(R.id.txt_promotion_price)
    TextView txtPromotionPrice;
    @BindView(R.id.pd_contain_service_rv)
    NestedRecyclerView serviceRv;
    @BindView(R.id.pd_shop_rv)
    NestedRecyclerView shopRv;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    @BindView(R.id.pd_item_rv)
    NestedRecyclerView itemRv;
    @BindView(R.id.list_view)
    MyListView listView;
    @BindView(R.id.txt_project_type)
    TextView txtProjectType;//大小项目

    private String item_id;
    private String result;
    private boolean isEdit = false;//是否编辑过了
    private AlertView alertView;

    private List<SrvBean> srvList;
    private List<ShopBean> shopList;
    private List<ItemBean> itemList;
    private List<ImageTextBean> list;
    private QuickAdapter<ImageTextBean> adapter;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("项目详情");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
        item_id = getIntent().getStringExtra("item_id");

        srvList = new ArrayList<>();
        shopList = new ArrayList<>();
        itemList = new ArrayList<>();
        list = new ArrayList<>();
        serviceRv.setLayoutManager(new LinearLayoutManager(mContext));
        serviceRv.setNestedScrollingEnabled(false);
        serviceRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.bg_line)));
        shopRv.setLayoutManager(new LinearLayoutManager(mContext));
        shopRv.setNestedScrollingEnabled(false);
        shopRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.bg_line)));
        itemRv.setLayoutManager(new LinearLayoutManager(mContext));
        itemRv.setNestedScrollingEnabled(false);
        itemRv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.bg_line)));
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_projmgr_detail_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),item_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        result = object.optString("result");
                        JSONObject itemObj = resultObj.optJSONObject("item_data");
                        UIUtils.loadImg(mContext,itemObj.optString("item_img"),image);
                        txtGoodsName.setText(itemObj.optString("item_name"));
                        txtGoodsSpec.setText(itemObj.optString("item_desc"));
                        txtGoodsType.setText(itemObj.optString("catalog_name"));
                        if(itemObj.optString("pack_type").equals("1")){
                            llSrvTimes.setVisibility(View.GONE);
                            llItem.setVisibility(View.GONE);
                        }else if(itemObj.optString("pack_type").equals("2")){
                            llSrvTimes.setVisibility(View.VISIBLE);
                            txtSrvTimes.setText(itemObj.optString("srv_num")+"次");
                            llItem.setVisibility(View.GONE);
                        }else if(itemObj.optString("pack_type").equals("3")){
                            llSrvTimes.setVisibility(View.GONE);
                            llItem.setVisibility(View.VISIBLE);
                        }
                        txtCost.setText("¥"+itemObj.optString("cost_price")+"元");
                        txtPrice.setText("¥"+itemObj.optString("price")+"元");
                        txtMarketPrice.setText("¥"+itemObj.optString("market_price")+"元");
                        txtStartTime.setText(itemObj.optString("promotion_begin_time"));
                        txtEndTime.setText(itemObj.optString("promotion_end_time"));
                        txtPromotionPrice.setText("¥"+itemObj.optString("promotion_price")+"元");
                        //project_type 项目类型：1小项目；2大项目
                        if("1".equals(itemObj.optString("project_type"))){
                            txtProjectType.setText("小项目");
                        } else if("2".equals(itemObj.optString("project_type"))){
                            txtProjectType.setText("大项目");
                        }
                        if(itemObj.has("detail") && !StringUtils.isEmpty(itemObj.optString("detail")) && itemObj.optJSONArray("detail").length() > 0){
                            list = FastJsonTools.getPersons(itemObj.optString("detail"),ImageTextBean.class);
                        }
                        if(resultObj.has("srv_data") && !StringUtils.isEmpty(resultObj.optString("srv_data")) && resultObj.optJSONArray("srv_data").length() > 0){
                            srvList = FastJsonTools.getPersons(resultObj.optString("srv_data"),SrvBean.class);
                        }
                        if(resultObj.has("shop_data") && !StringUtils.isEmpty(resultObj.optString("shop_data")) && resultObj.optJSONArray("shop_data").length() > 0){
                            shopList = FastJsonTools.getPersons(resultObj.optString("shop_data"),ShopBean.class);
                        }
                        if(resultObj.has("sub_item") && !StringUtils.isEmpty(resultObj.optString("sub_item")) && resultObj.optJSONArray("sub_item").length() > 0){
                            itemList = FastJsonTools.getPersons(resultObj.optString("sub_item"),ItemBean.class);
                        }
                        setAdapter();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"删除成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
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

    private void setAdapter(){
        ServiceAdapter serviceAdapter = new ServiceAdapter(mContext, srvList);
        serviceRv.setAdapter(serviceAdapter);

        ShopAdapter shopAdapter = new ShopAdapter(mContext, shopList);
        shopRv.setAdapter(shopAdapter);

        ItemAdapter itemAdapter = new ItemAdapter(mContext, itemList);
        itemRv.setAdapter(itemAdapter);

        adapter = new QuickAdapter<ImageTextBean>(mContext,R.layout.layout_goods_content_detail_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImageTextBean item) {
                int position = helper.getPosition();
                RelativeLayout ll = helper.getView(R.id.ll);
                if(position == list.size() - 1){
                    ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                }else {
                    ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),0);
                }
                if(list.get(position).getHeight() != 0 && list.get(position).getWidth() != 0){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) helper.getParams(R.id.image);
                    float f = Float.valueOf(list.get(position).getHeight())/list.get(position).getWidth();
                    lp.height = DisplayUtil.getHeight(mContext,f);
                    helper.setLayoutParams(R.id.image,lp);
                }
                if(list.get(position).getContent_type().equals("1")){
                    helper.setVisible(R.id.image,false);
                    helper.setVisible(R.id.txt,true);
                    helper.setText(R.id.txt,item.getContent());
                }else {
                    helper.setVisible(R.id.image,true);
                    helper.setVisible(R.id.txt,false);
                    helper.setSimpViewImageUri(R.id.image,Uri.parse(item.getContent()));
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureSelectUtil.show(ProjectDetailActivity.this,position,list);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_right2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                if(isEdit){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                    finish();
                }
                finish();
                break;
            case R.id.txt_right:
                alertView = new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_delete_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),item_id),true);
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.txt_right2:
                //编辑
                //需将已有数据传过去
                it = new Intent(mContext,PmAddProjectActivity.class);
                it.putExtra("item_id",item_id);
                it.putExtra("result",result);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;

        }
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

                                ToastUtil.show(mContext,"确定");
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

    //服务列表
    class ServiceAdapter extends BaseRecyclerAdapter<SrvBean> {
        public ServiceAdapter(Context context, List list) {
            super(context, list, R.layout.shop_item_shop_detail_service);
        }
        @Override
        public void convert(BaseRecyclerHolder holder, SrvBean item, int position, boolean isScrolling) {
            holder.setText(R.id.sds_name_tv,item.getSrv_name());
        }
    }

    //店铺列表
    class ShopAdapter extends BaseRecyclerAdapter<ShopBean> {
        public ShopAdapter(Context context, List list) {
            super(context, list, R.layout.shop_item_shop_detail_service);
        }

        @Override
        public void convert(BaseRecyclerHolder holder, ShopBean item, int position, boolean isScrolling) {
            holder.setText(R.id.sds_name_tv,item.getShop_name());
        }
    }
    //项目列表
    class ItemAdapter extends BaseRecyclerAdapter<ItemBean> {
        public ItemAdapter(Context context, List list) {
            super(context, list, R.layout.shop_item_shop_detail_service);
        }

        @Override
        public void convert(BaseRecyclerHolder holder, ItemBean item, int position, boolean isScrolling) {
            holder.setText(R.id.sds_name_tv,item.getItem_name());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                isEdit = true;
                initData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            if(isEdit){
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
            }
            super.onBackPressed();
        }
    }
}
