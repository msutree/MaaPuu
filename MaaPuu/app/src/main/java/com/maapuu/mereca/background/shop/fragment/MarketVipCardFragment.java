package com.maapuu.mereca.background.shop.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.activity.MarketingDoingActivity;
import com.maapuu.mereca.background.shop.activity.VipCardDetailActivity;
import com.maapuu.mereca.background.shop.bean.ActBean;
import com.maapuu.mereca.background.shop.bean.CardActBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
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

import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * 营销活动 会员卡
 * Created by Jia on 2018/3/10.
 */

public class MarketVipCardFragment extends BaseFragment implements MarketingDoingActivity.OnShopSelectListener{

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<CardActBean> adapter;

    boolean isShowProgress = true;
    int act_type = 1; //活动类型：1会员卡；2套餐活动；3红包
    String shop_id = "0";//店铺id，0表示全部店铺
    int page = 1;//第几页


    @Override
    protected int setContentViewById() {
        //布局有共用
        return R.layout.shop_fragment_rv_one;
    }

    @Override
    protected void initView(View v) {
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getActList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getActList();
            }
        });
        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,10,
                getResources().getColor(R.color.background)));

        adapter = new BaseRecyclerAdapter<CardActBean>(mContext,R.layout.shop_item_market_vip_card) {
            @Override
            public void convert(BaseRecyclerHolder holder, CardActBean bean, int position, boolean isScrolling) {
                switch (bean.getCard_type()){
                    case 1:
                        holder.setImageResource(R.id.card_img,R.mipmap.nianka);
                        break;
                    case 2:
                        holder.setImageResource(R.id.card_img,R.mipmap.xiangmuka);
                        break;
                    case 3:
                        holder.setImageResource(R.id.card_img,R.mipmap.chongzhika);
                        break;
                }

                holder.setText(R.id.card_name_tv,bean.getCard_name());
                holder.setText(R.id.card_desc_tv,bean.getCard_desc());
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                CardActBean bean = adapter.getList().get(position);
                it = new Intent(mContext, VipCardDetailActivity.class);
                //it.putExtra("act_type",act_type);//活动类型：1会员卡；2套餐活动；3红包
                it.putExtra("business_id",bean.getCard_id());
                it.putExtra("card_type",bean.getCard_type());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
            }
        });

    }

    @Override
    protected void initData() {
        getActList();
    }

    private void getActList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_act_list_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        act_type,
                        shop_id,
                        page),isShowProgress);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = false;
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            ActBean bean = FastJsonTools.getPerson(object.optString("result"), ActBean.class);
                            if(bean != null){
                                if(page == 1 && (bean.getCard_act_list() == null || bean.getCard_act_list().size() ==0)){
                                    llHas.setVisibility(View.VISIBLE);
                                }else {
                                    llHas.setVisibility(View.GONE);
                                }
                                List<CardActBean> list = bean.getCard_act_list();
                                if(page == 1) adapter.clear();
                                adapter.addList(list);
                                if(page > 1){
                                    if((list ==null || list.size()==0)){
                                        page--;
                                        ToastUtil.show(mContext,"暂无更多数据");
                                    }
                                }
                            }
                        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case AppConfig.ACTIVITY_REQUESTCODE:
                    //刷新
                    page = 1;
                    getActList();

                    break;
            }
        }
    }

    @Override
    public void loadData() {//切换fragment时调用
        if(mHttpModeBase != null){
            page = 1;
            //isShowProgress = true;
            getActList();
        }
    }

    @Override
    public void onShopSelect(String shop_id) {//切换店铺时调用
        this.shop_id = shop_id;
        loadData();
    }
}
