package com.maapuu.mereca.background.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.DecorateHotProjectAdapter;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.HotItemBean;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 店铺装饰 热门项目
 * Created by Jia on 2018/3/13.
 */

public class DecorateHotProjectFragment extends BaseFragment {
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private List<HotItemBean> list;
    private DecorateHotProjectAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private String shop_id;
    private int pos = -1;

    @Override
    protected int setContentViewById() {
        //布局有共用
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.shop_fragment_swipe_rv_one;
    }

    @Override
    protected void initView(View v) {
        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,
                UIUtils.dip2px(mContext,10),
                getResources().getColor(R.color.background)));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
    }

    public void refresh(){
        initData();
    }

    @Override
    protected void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_hot_commodity_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
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
                            list = FastJsonTools.getPersons(object.optString("result"),HotItemBean.class);
                        }else {
                            llHas.setVisibility(View.VISIBLE);
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
                        if(pos != -1){
                            list.remove(pos);
                            adapter.notifyDataSetChanged();
                            pos = -1;
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

    @Override
    public void onClick(View v) {

    }

    private void setAdapter() {
        adapter = new DecorateHotProjectAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DecorateHotProjectAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onRightItemClick(View v, int position) {
                pos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_shop_hot_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        list.get(position).getShop_hot_item_id()),true);
            }
        });
    }

}
