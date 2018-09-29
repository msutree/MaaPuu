package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.AddressBean;
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
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class AddressManageActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private AlertView alertView;
    private BaseRecyclerAdapter<AddressBean> adapter;
    private List<AddressBean> list;
    private int page = 1;
    private int delPos = -1; //删除下标
    private int defaultPos = -1; //默认下标
    private boolean isChoose = false;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        isChoose = getIntent().getBooleanExtra("isChoose",false);
        txtTitle.setText(isChoose?"选择地址":"地址管理");
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));
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
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.address_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),pageNum),true);
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
                            List<AddressBean> lsJson = FastJsonTools.getPersons(object.optString("result"),AddressBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<AddressBean> lsJson = new ArrayList<>();
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
                        if(defaultPos != -1){
                            ToastUtil.show(mContext,list.get(defaultPos).getIs_default().equals("1")?"取消默认成功":"设置默认成功");
                            if(list.get(defaultPos).getIs_default().equals("1")){
                                for (int i = 0; i < list.size(); i++){
                                    if(i == defaultPos){
                                        list.get(defaultPos).setIs_default("0");
                                    }
                                }
                            }else {
                                for (int i = 0; i < list.size(); i++){
                                    if(i == defaultPos){
                                        list.get(defaultPos).setIs_default("1");
                                    }else {
                                        list.get(i).setIs_default("0");
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
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
                        ToastUtil.show(mContext,"删除成功");
                        if(delPos != -1){
                            list.remove(delPos);
                            if(list.size() == 0){
                                llHas.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                            delPos = -1;
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

    private void setAdapter(List<AddressBean> lsJson) {
        if(page == 1){
            list.clear();
        }
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<AddressBean>(mContext,list,R.layout.layout_address_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, AddressBean item, final int position, boolean isScrolling) {
                    holder.setText(R.id.txt_name,item.getReceiver());
                    holder.setText(R.id.txt_phone,item.getReceiver_phone());
                    holder.setText(R.id.txt_address,item.getAdress());
                    if(item.getIs_default().equals("1")){
                        holder.setSelected(R.id.txt_default,true);
                        holder.setText(R.id.txt_default,"默认地址");
                    }else {
                        holder.setSelected(R.id.txt_default,false);
                        holder.setText(R.id.txt_default,"设为默认");
                    }
                    holder.setOnClickListener(R.id.txt_default, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            defaultPos = position;
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.address_default_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                    list.get(position).getAddress_id()),true);
                        }
                    });
                    holder.setOnClickListener(R.id.txt_edit, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            it = new Intent(mContext,AddAddressActivity.class);
                            it.putExtra("address_id",list.get(position).getAddress_id());
                            startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                        }
                    });
                    holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isChoose){
                                ToastUtil.show(mContext,"下单时无法删除");
                            }else {
                                alertView = new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int pos) {
                                        if (pos == 0) {
                                            delPos = position;
                                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.address_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                                    list.get(position).getAddress_id()),true);
                                        }
                                    }
                                });
                                alertView.show();
                            }
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
                recyclerView.smoothScrollToPosition(list.size()-lsJson.size()-1);
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if(isChoose){
                    it = new Intent();
                    it.putExtra("addressBean",list.get(position));
                    setResult(AppConfig.ACTIVITY_RESULTCODE_2,it);
                    finish();
                }
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_add_address})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_add_address:
                it = new Intent(mContext,AddAddressActivity.class);
                it.putExtra("address_id","0");
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                page = 1;
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
