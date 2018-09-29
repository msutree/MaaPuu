package com.maapuu.mereca.fragment.foundchild;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ShopDetailActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
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

/**
 * Created by dell on 2018/2/27.
 */

public class FoundNearbyShopFragment extends BaseFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private LinearLayoutManager mLayoutManager;
    private List<ShopBean> list;
    private BaseRecyclerAdapter<ShopBean> adapter;
    private int page = 1;

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_found_nearby_shop;
    }

    @Override
    protected void initView(View v) {
        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

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

    private void initData(int pageNum){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.around_shop_list_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"), AppConfig.LAT,AppConfig.LNG,pageNum),true);
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
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<ShopBean>(mContext,list,R.layout.layout_search_shop_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, ShopBean bean, int position, boolean isScrolling) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.getParams(R.id.image);
                    lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-24)*2/5, mContext.getResources().getDisplayMetrics()));
                    holder.setParams(R.id.image,lp);
                    holder.setTypeface(StringUtils.getFont(mContext),R.id.txt_location);

                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getShop_cover()));
                    holder.setText(R.id.txt_shop_name,bean.getShop_name());
                    holder.setScaleRatingBar(R.id.ratingbar,Float.parseFloat(bean.getEvl_level()));
                    holder.setText(R.id.txt_address,bean.getAddress_detail());
                    holder.setVisible(R.id.txt_distance,true);
                    holder.setText(R.id.txt_distance,StringUtils.isEmpty(bean.getDistance())?"":"距您"+bean.getDistance());
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                it = new Intent(mContext, ShopDetailActivity.class);
                it.putExtra("shop_id",list.get(position).getShop_id());
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
