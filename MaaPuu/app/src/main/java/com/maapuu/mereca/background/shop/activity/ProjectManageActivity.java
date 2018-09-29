package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ProjectManageAdapter;
import com.maapuu.mereca.background.shop.bean.ProjectItemBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.background.shop.dialog.ProjectManageFilter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
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
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
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
 * 项目管理
 * Created by Jia on 2018/3/8.
 */

public class ProjectManageActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private List<ShopBean> shopList;
    private List<ProjectItemBean> list;
    private ProjectManageAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private String shop_id;
    private String project_type = "";
    private String other_type = "";
    private String toast_text = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("项目管理");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
        shop_id = getIntent().getStringExtra("shop_id");

        list = new ArrayList<>();
        shopList = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
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

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_projmgr_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id,
                pageNum,project_type,other_type),true);
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
                        if(!StringUtils.isEmpty(resultObj.optString("item_list")) && resultObj.optJSONArray("item_list").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<ProjectItemBean> lsJson = FastJsonTools.getPersons(resultObj.optString("item_list"),ProjectItemBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        } else {
                            List<ProjectItemBean> lsJson = new ArrayList<>();
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
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,toast_text.replace("\n","")+"成功");
                        page = 1;
                        initData(page);
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

    private void setAdapter(List<ProjectItemBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new ProjectManageAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        }else {
//            if(page > 1){
//                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
//                recyclerView.smoothScrollToPosition(list.size()-lsJson.size()-1);
//            }else {
//                adapter.notifyDataSetChanged();
//            }
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new ProjectManageAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                it = new Intent(mContext,ProjectDetailActivity.class);
                it.putExtra("item_id",list.get(position).getItem_id());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
            }

            @Override
            public void onRightItemClick(View v, int position, String tag) {
                toast_text = tag;
                switch (tag){
                    case "上架":
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_shelf_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),list.get(position).getItem_id(),"1"),true);
                        break;
                    case "下架":
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_shelf_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),list.get(position).getItem_id(),"2"),true);
                        break;
                    case "置顶":
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_top_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),list.get(position).getItem_id(),"1"),true);
                        break;
                    case "取消\n置顶":
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_top_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),list.get(position).getItem_id(),"0"),true);
                        break;
                    case "删除":
                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_projmgr_delete_set(LoginUtil.getInfo("token"),
                                LoginUtil.getInfo("uid"),list.get(position).getItem_id()),true);
                        break;
                }
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.ao_choose_shop_lt,R.id.txt_right2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://添加项目
                final List<String> list = new ArrayList<>();
                list.add("单次项目");
                list.add("单项套餐");
                list.add("多项套餐");
                showAddProjectDialog(list);
                break;
            case R.id.txt_right2://筛选
                ProjectManageFilter orderFilter = new ProjectManageFilter(mContext,project_type,other_type, new ProjectManageFilter.ConfirmCall() {
                    @Override
                    public void onCall(String project, String other) {
                        project_type = project;
                        other_type = other;
                        page = 1;
                        initData(page);
                    }
                });
                orderFilter.createPopup();
                orderFilter.showAsDropDown(txtLeft);
                break;
            case R.id.ao_choose_shop_lt:
                ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                    @Override
                    public void onChoose(ShopBean item) {
                        shopTv.setText(item.getShop_name());
                        shop_id = item.getShop_id();
                        page = 1;
                        initData(page);
                    }
                });
                chooseShopFilter.createPopup();
                chooseShopFilter.showAsDropDown(chooseShopLt);
                break;

        }
    }

    private void showAddProjectDialog(final List<String> list) {
        NiceDialog.init().setLayoutId(R.layout.pop_bottom_menu)
                .setConvertListener(new ViewConvertListener() {

                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView cancelTv = holder.getView(R.id.pop_cancel_tv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        RecyclerView rv = holder.getView(R.id.pop_rv);
                        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.pop_item_bottom_menu) {
                            @Override
                            public void convert(BaseRecyclerHolder baseHolder, final String item, int position, boolean isScrolling) {
                                final TextView menuTv = baseHolder.getView(R.id.bm_title);
                                menuTv.setText(item);
                                menuTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (item){
                                            case "单次项目":
                                                it = new Intent(mContext,PmAddProjectActivity.class);
                                                it.putExtra("pack_type","1");
                                                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                                                break;
                                            case "单项套餐":
                                                //隐藏店铺和项目模块
                                                it = new Intent(mContext,PmAddProjectActivity.class);
                                                it.putExtra("pack_type","2");
                                                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                                                break;
                                            case "多项套餐":
                                                // 隐藏店铺 次数 两栏，显示店铺和项目模块
                                                it = new Intent(mContext,PmAddProjectActivity.class);
                                                it.putExtra("pack_type","3");
                                                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                                                break;
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        };
                        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                                getResources().getColor(R.color.background)));
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        rv.setAdapter(adapter);
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                //.setHeight(270)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                page = 1;
                initData();
                break;
        }
    }
}
