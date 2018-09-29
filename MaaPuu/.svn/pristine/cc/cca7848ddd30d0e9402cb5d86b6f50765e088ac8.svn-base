package com.maapuu.mereca.fragment.collectionchild;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.GoodsDetailActivity;
import com.maapuu.mereca.adapter.CollectionGoodsAdapter;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.GoodsBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
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
 * Created by dell on 2018/3/8.
 */

public class CollectionGoodsFragment extends BaseFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.txt_no_content)
    TextView txtNoContent;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private List<GoodsBean> list;
    private CollectionGoodsAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private int pos = -1;

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initView(View v) {
        list = new ArrayList<>();
        txtNoContent.setText("还没有收藏的商品");
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
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
    protected void initData() {
        initData(page);
    }

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.collect_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),"2",pageNum),true);
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
                            List<GoodsBean> lsJson = FastJsonTools.getPersons(object.optString("result"),GoodsBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<GoodsBean> lsJson = new ArrayList<>();
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
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"取消收藏成功");
                        if(pos != -1){
                            list.remove(pos);
                            if(list.size() == 0){
                                llHas.setVisibility(View.VISIBLE);
                            }
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

    private void setAdapter(List<GoodsBean> lsJson) {
        if(page == 1){
            list.clear();
        }
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new CollectionGoodsAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
                recyclerView.smoothScrollToPosition(list.size()-lsJson.size()-1);
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new CollectionGoodsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                it = new Intent(mContext,GoodsDetailActivity.class);
                it.putExtra("type",1);
                it.putExtra("shop_id",list.get(position).getShop_id());
                it.putExtra("item_id",list.get(position).getItem_id());
                startActivity(it);
            }

            @Override
            public void onRightItemClick(View v, int position) {
                pos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.item_collect_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        list.get(position).getShop_id(),list.get(position).getItem_id(),"1"),true);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
