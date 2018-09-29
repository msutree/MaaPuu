package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ApplicableShopAdapter;
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
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 适用店铺
 * Created by Jia on 2018/3/15.
 */

public class ApplicableShopActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.post_temp_name_tv)
    TextView postTempNameTv;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private ApplicableShopAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    String post_temp_id = "";
    String post_temp_name = "";
    boolean isShowProgress = true;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_applicable_shop);
    }

    @Override
    public void initView() {
        post_temp_id = getIntent().getStringExtra("post_temp_id");
        post_temp_name = getIntent().getStringExtra("post_temp_name");
        postTempNameTv.setText(post_temp_name);
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("适用店铺");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69b");//&#xe69b;
        txtRight.setTextSize(17);

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getShopList();
            }
        });

        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,
                UIUtils.dip2px(mContext,1),
                getResources().getColor(R.color.background)));
        adapter = new ApplicableShopAdapter(mContext);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ApplicableShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
            }

            @Override
            public void onRightItemClick(View v, int position) {
                //删除
                showDeleteDialog("删除店铺将清除店铺内所有员工的职位",adapter.getList().get(position).getShop_id());
            }
        });
    }

    @Override
    public void initData() {
        getShopList();
    }

    //用店铺列表
    private void getShopList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_temp_shop_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id),isShowProgress);
    }

    private void showDeleteDialog(final String msg,final String shop_id) {
        NiceDialog.init()
                .setLayoutId(R.layout.nd_layout_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setText(R.id.title,"删除提示");
                        holder.setText(R.id.message,msg);

                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //消失
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除
                                deleteShop(shop_id);

                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                //.setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }

    //删除店铺
    private void deleteShop(String shop_id) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.s_temp_shop_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id,post_temp_id),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //选择店铺
                it = new Intent(mContext,JobChooseShopActivity.class);
                it.putExtra("post_temp_id",post_temp_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == AppConfig.ACTIVITY_REQUESTCODE){
                //刷新数据
                getShopList();
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
                            List<ShopBean> list = FastJsonTools.getPersons(object.optString("result"), ShopBean.class);
                            //没有分页
                            adapter.clear();
                            adapter.addList(list);
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
                        //刷新
                        getShopList();

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
