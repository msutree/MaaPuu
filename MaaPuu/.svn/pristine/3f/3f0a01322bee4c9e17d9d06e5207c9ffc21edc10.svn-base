package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.FinanceManageBean;
import com.maapuu.mereca.base.BaseActivity;
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
import butterknife.OnClick;

/**
 * 商铺账户 财务管理
 * Created by Jia on 2018/4/20.
 */

public class FmShopAccountActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.fm_account_title_tv)
    TextView accountTitle_tv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<FinanceManageBean.BalanceLogBean> adapter;
    private int page = 1;
    String shop_id = "0";//店铺id，全部店铺传0
    String date = "";//查询日期，格式：2018-04
    boolean isShowProgress = true;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_fm_shop_account);
    }

    @Override
    public void initView() {
        shop_id = getIntent().getStringExtra("shop_id");
        date = getIntent().getStringExtra("date");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("明细");
        accountTitle_tv.setText(date+"明细");

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new BaseRecyclerAdapter<FinanceManageBean.BalanceLogBean>(mContext, R.layout.item_shop_finance_manage) {
            @Override
            public void convert(BaseRecyclerHolder holder, FinanceManageBean.BalanceLogBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.fm_name,bean.getBusiness_text());
                holder.setText(R.id.fm_time,bean.getCreate_time());
                holder.setText(R.id.fm_amount,bean.getAmount()+"元");
            }
        };
        recyclerView.setAdapter(adapter);

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getAccountDetail();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getAccountDetail();
            }
        });
    }

    @Override
    public void initData() {
        getAccountDetail();
    }

    private void setUI(FinanceManageBean bean) {
        List<FinanceManageBean.BalanceLogBean> balanceList = bean.getBalance_log();
        if(page == 1) adapter.clear();
        adapter.addList(balanceList);
        if(page > 1){
            if((balanceList ==null || balanceList.size()==0)){
                page--;
                ToastUtil.show(mContext,"暂无更多数据");
            }
        }
    }

    private void getAccountDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_account_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        shop_id,
                        date,
                        page),isShowProgress);
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

        }
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
                            FinanceManageBean bean = FastJsonTools.getPerson(object.optString("result"), FinanceManageBean.class);
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
