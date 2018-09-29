package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺管理
 * Created by Jia on 2018/3/8.
 */

public class ShopManageActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<ShopBean> adapter;
    private List<ShopBean> list;
    private int page = 1;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_shop_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("店铺管理");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69b");//&#xe69b;
        txtRight.setTextSize(17);

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.background)));
        list = new ArrayList<>();

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initData(page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                initData(page);
            }
        });
    }

    @Override
    public void initData() {
        initData(page);
    }

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),pageNum),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<ShopBean> lsJson = FastJsonTools.getPersons(object.optString("result"),ShopBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<ShopBean> lsJson = new ArrayList<>();
                            setAdapter(lsJson);
                            if(page != 1){
                                llHas.setVisibility(View.GONE);
                                ToastUtil.show(mContext,"暂无更多数据");
                            }else {
                                llHas.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter(List<ShopBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if (adapter == null) {
            adapter = new BaseRecyclerAdapter<ShopBean>(mContext, list, R.layout.shop_item_shop_manage) {
                @Override
                public void convert(BaseRecyclerHolder holder, ShopBean bean, final int position, boolean isScrolling) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.getParams(R.id.image);
                    lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-24)*2/5, mContext.getResources().getDisplayMetrics()));
                    holder.setParams(R.id.image,lp);

                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getShop_cover()));
                    holder.setSimpViewImageUri(R.id.iv_shop, Uri.parse(bean.getShop_logo()));
                    holder.setText(R.id.txt_shop_name,bean.getShop_name());
                    holder.setText(R.id.txt_address,bean.getAddress_detail());
                    //店铺装饰
                    holder.getView(R.id.sm_decorate_tv).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            it = new Intent(mContext,ShopDecorateActivity.class);
                            it.putExtra("shop_id",list.get(position).getShop_id());
                            startActivity(it);
                        }
                    });

                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            if (page > 1) {
                adapter.notifyItemRangeInserted(list.size() - lsJson.size(), lsJson.size());
            } else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                //店铺详情
                it = new Intent(mContext,ShopDetailActivity.class);
                it.putExtra("position",position);
                it.putExtra("shop_id",list.get(position).getShop_id());
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_empty_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://新增店铺
            case R.id.txt_empty_btn://新增店铺
                it = new Intent(mContext,AddShopActivity.class);
                it.putExtra("shop_id","");
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE://添加
                page = 1;
                initData(page);
                break;
            case AppConfig.ACTIVITY_RESULTCODE_1://删除
                int pos = data.getIntExtra("position",-1);
                if(pos != -1){
                    list.remove(pos);
                    adapter.notifyDataSetChanged();
                }
                break;
            case AppConfig.ACTIVITY_RESULTCODE_2://编辑
                break;
        }
    }
}
