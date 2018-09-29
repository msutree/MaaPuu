package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.GoodsManageAdapter;
import com.maapuu.mereca.background.shop.bean.GoodsBean;
import com.maapuu.mereca.background.shop.bean.GoodsInitBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.background.shop.dialog.GoodsFilter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品管理
 * Created by Jia on 2018/3/8.
 */

public class GoodsManageActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;

    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private GoodsManageAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;

    String shop_id = "0";//店铺id，0表示全部店铺
    String commodity_type = "";//商品类型，多个逗号分隔(1促销项目；2普通商品)
    String other_type = "";//其他类型(1上架；2下架；3置顶项目；4非置顶项目)

    boolean isShowProgress = true;
    List<ShopBean> shopList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_goods_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("商品管理");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getGoods();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getGoods();
            }
        });
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.background)));
        adapter = new GoodsManageAdapter(mContext);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GoodsManageAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                //商品信息
                int pos = recyclerView.getChildAdapterPosition(view);
                Intent intent = new Intent(mContext,GoodsInfoActivity.class);
                intent.putExtra("item_id",adapter.getList().get(pos).getItem_id());
                UIUtils.startActivity(mContext,intent);
            }

            @Override
            public void onRightItemClick(View v, int position, String tag) {
                GoodsBean bean = adapter.getList().get(position);
                switch (tag){
                    case "上架":
                        setShelf(bean.getItem_id(),1);//1上架项目；2下架项目
                        break;

                    case "下架":
                        setShelf(bean.getItem_id(),2);//1上架项目；2下架项目
                        break;

                    case "置顶":
                        setTop(bean.getItem_id(),1);//0取消置顶；1置顶
                        break;

                    case "取消置顶":
                        setTop(bean.getItem_id(),0);//0取消置顶；1置顶
                        break;

                    case "删除":
                        deleteGoods(bean.getItem_id());

                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        page = 1;
        getGoods();
    }

    private void getGoods() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_commgr_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        shop_id,
                        commodity_type,
                        other_type,
                        page),isShowProgress);
    }

    private void setUI(GoodsInitBean bean) {
        if(bean.getShop_list() != null && bean.getShop_list().size()>0) shopList = bean.getShop_list();
        if(page == 1 && (bean.getItem_list() == null || bean.getItem_list().size() ==0)){
            llHas.setVisibility(View.VISIBLE);
        }else {
            llHas.setVisibility(View.GONE);
        }
        List<GoodsBean> itemList = bean.getItem_list();
        if(page == 1) adapter.clear();
        adapter.addList(itemList);
        if(page > 1){
            if((itemList ==null || itemList.size()==0)){
                page--;
                ToastUtil.show(mContext,"暂无更多数据");
            } else {
                recyclerView.smoothScrollToPosition(adapter.getList().size()-itemList.size()-1);
            }
        }
    }

    private void setTop(String item_id,int is_top){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_commgr_top_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,
                        is_top),true);
    }

    private void setShelf(String item_id,int shelf_status){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_commgr_shelf_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id,
                        shelf_status),true);
    }

    private void deleteGoods(String item_id){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_commgr_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        item_id),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.ao_choose_shop_lt,R.id.txt_right2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:{
                //添加项目
                Intent intent = new Intent(mContext,AddGoodsActivity.class);
                startActivityForResult(intent, AppConfig.ACTIVITY_REQUESTCODE);
            }


                break;

            case R.id.txt_right2:
                //筛选
                GoodsFilter goodsFilter = new GoodsFilter(mContext, new GoodsFilter.ConfirmCall() {

                    @Override
                    public void onCall(String commodity, String other) {
                        commodity_type = commodity;
                        other_type = other;
                        page = 1;
                        getGoods();
                    }
                });
                goodsFilter.createPopup();
                goodsFilter.showAsDropDown(txtLeft);

                break;

            case R.id.ao_choose_shop_lt:
                //选择商铺
                if(shopList != null && shopList.size()>0){
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            shopTv.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            page = 1;
                            getGoods();
                        }
                    });
                    chooseShopFilter.createPopup();
                    chooseShopFilter.showAsDropDown(chooseShopLt);
                } else {
                    ToastUtil.show(mContext,"没有可供选择的商铺");
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
                    page = 1;
                    getGoods();

                    break;
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                isShowProgress = false;
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            GoodsInitBean bean = FastJsonTools.getPerson(object.optString("result"), GoodsInitBean.class);
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
                        //刷新数据
                        getGoods();

                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

}
