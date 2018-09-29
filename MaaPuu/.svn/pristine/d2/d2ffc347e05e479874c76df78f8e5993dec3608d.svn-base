package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.GoodsCommentRecyclerAdapter1;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.EvlBean;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评价分析
 * Created by Jia on 2018/3/12.
 */

public class CommentAnalysisActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.txt_project)
    TextView txtProjectComment;
    @BindView(R.id.txt_goods)
    TextView txtGoodsComment;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private FullyLinearLayoutManager mLayoutManager;
    private List<EvlBean> list;
    private GoodsCommentRecyclerAdapter1 adapter;
    private int list_type = 1;
    private String shop_id = "0";
    private int page = 1;
    private int comPos = -1;
    private int delPos = -1;

    private TextView[] tvs;
    private List<ShopBean> shopList;
    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_comment_analysis);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("评价分析");
        shopList = new ArrayList<>();
        tvs = new TextView[]{txtProjectComment, txtGoodsComment};
        setHead(0);

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                UIUtils.dip2px(mContext,10),
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

    private void setHead(int postion) {
        for (int i = 0; i < tvs.length; i++){
            if(i == postion){
                tvs[postion].setSelected(true);
            }else {
                tvs[i].setSelected(false);
            }
        }
    }

    @Override
    public void initData() {
        initData(page);
    }

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_evalution_list_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),shop_id,list_type,pageNum),true);
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
                        if(resultObj.has("shop_list") && !StringUtils.isEmpty(resultObj.optString("shop_list"))){
                            shopList = FastJsonTools.getPersons(resultObj.optString("shop_list"),ShopBean.class);
                        }
                        if(!StringUtils.isEmpty(resultObj.optString("evl_list")) && resultObj.optJSONArray("evl_list").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<EvlBean> lsJson = FastJsonTools.getPersons(resultObj.optString("evl_list"),EvlBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<EvlBean> lsJson = new ArrayList<>();
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
            case HttpModeBase.HTTP_REQUESTCODE_3:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"回复成功");
                        if(comPos != -1){
                            list.get(comPos).setIs_reply("1");
                            list.get(comPos).setReply_content(object.optJSONObject("result").optString("content"));
                            list.get(comPos).setEvl_reply_id(object.optJSONObject("result").optString("evl_reply_id"));
                            adapter.notifyItemChanged(comPos);
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_4:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"删除成功");
                        if(delPos != -1){
                            list.get(delPos).setIs_reply("0");
                            list.get(delPos).setReply_content("");
                            list.get(delPos).setEvl_reply_id("0");
                            adapter.notifyItemChanged(delPos);
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

    private void setAdapter(List<EvlBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new GoodsCommentRecyclerAdapter1(mContext,list,true);
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new GoodsCommentRecyclerAdapter1.OnRecyclerViewItemClickListener() {
            @Override
            public void onZanClick(int position) {
            }

            @Override
            public void onDelReplyClick(final int position) {
                alertView = new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            delPos = position;
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4,UrlUtils.s_evalution_reply_delete_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),list.get(position).getEvl_reply_id()),true);
                        }
                    }
                });
                alertView.show();
            }

            @Override
            public void onReplyClick(int position,String content) {
                comPos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.s_evalution_reply_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list.get(position).getEvl_id(),content),true);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.ao_choose_shop_lt,R.id.txt_project,R.id.txt_goods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.ao_choose_shop_lt:
                ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                    @Override
                    public void onChoose(ShopBean item) {
                        txtShopName.setText(item.getShop_name());
                        shop_id = item.getShop_id();
                        page = 1;
                        initData(page);
                    }
                });
                chooseShopFilter.createPopup();
                chooseShopFilter.showAsDropDown(chooseShopLt);
                break;
            case R.id.txt_project:
                setHead(0);
                page = 1;
                list_type = 1;
                initData(page);
                break;
            case R.id.txt_goods:
                setHead(1);
                page = 1;
                list_type = 2;
                initData(page);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
