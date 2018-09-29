package com.maapuu.mereca.fragment.searchchild;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.GoodsDetailActivity;
import com.maapuu.mereca.activity.SearchResultActivity;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.GoodsBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
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
 * Created by dell on 2018/2/27.
 */

public class SearchGoodsFragment extends BaseFragment {
    @BindView(R.id.ll_has_result)
    LinearLayout llHasResult;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<GoodsBean> list;
    private BaseRecyclerAdapter<GoodsBean> adapter;
    private int page = 1;
    private String keyword = "";//搜索关键字

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        keyword = ((SearchResultActivity)context).getKeyword();
    }

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_search;
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
    protected void initData() {
        initData(page);
    }

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.search_keyword_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                keyword,"4",pageNum),true);
    }

    public void refresh(String result){
        if(!result.equals(keyword)){
            keyword = result;
            page = 1;
            initData(page);
        }
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
                        JSONObject resultObj = object.optJSONObject("result");
                        if(!StringUtils.isEmpty(resultObj.optString("rlt_data")) && resultObj.optJSONArray("rlt_data").length() > 0){
                            llHasResult.setVisibility(View.GONE);
                            List<GoodsBean> lsJson = FastJsonTools.getPersons(resultObj.optString("rlt_data"),GoodsBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<GoodsBean> lsJson = new ArrayList<>();
                            setAdapter(lsJson);
                            if(page != 1){
                                ToastUtil.show(mContext,"暂无更多数据");
                            }else {
                                llHasResult.setVisibility(View.VISIBLE);
                            }
                        }
                        if(resultObj.has("shop_count")){
                            SearchResultActivity.activity.txtShop.setText("门店("+resultObj.optString("shop_count")+")");
                        }
                        if(resultObj.has("staff_count")){
                            SearchResultActivity.activity.txtHairStylist.setText("发型师("+resultObj.optString("staff_count")+")");
                        }
                        if(resultObj.has("project_count")){
                            SearchResultActivity.activity.txtProject.setText("项目("+resultObj.optString("project_count")+")");
                        }
                        if(resultObj.has("commodity_count")){
                            SearchResultActivity.activity.txtGoods.setText("商品("+resultObj.optString("commodity_count")+")");
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
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<GoodsBean>(mContext,list,R.layout.layout_home_goods_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, GoodsBean bean, int position, boolean isScrolling) {
                    holder.setSimpViewImageUri(R.id.iv_goods, Uri.parse(bean.getItem_img()));
                    holder.setText(R.id.txt_goods_name,bean.getItem_name());
                    holder.setText(R.id.txt_price,"¥"+bean.getPrice());
                    holder.setText(R.id.txt_sale_num,"已售"+bean.getSale_num()+"件");
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
                it = new Intent(mContext,GoodsDetailActivity.class);
                it.putExtra("type",1);
                it.putExtra("shop_id",list.get(position).getShop_id());
                it.putExtra("item_id",list.get(position).getItem_id());
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
