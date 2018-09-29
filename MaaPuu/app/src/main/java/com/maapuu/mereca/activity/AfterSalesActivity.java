package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.RefundBean;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/2/24.
 */

public class AfterSalesActivity extends BaseActivity {
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

    private LinearLayoutManager mLayoutManager;
    private List<RefundBean> list;
    private BaseRecyclerAdapter<RefundBean> adapter;

    private int type; //1项目   2商品

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_after_sales);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        type = getIntent().getIntExtra("type",1);
        txtTitle.setText(type == 1 ? "项目售后" : "售后");
        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.refund_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),type),true);
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
                        if(object.has("result") && !StringUtils.isEmpty(object.optString("result"))){
                            list = FastJsonTools.getPersons(object.optString("result"),RefundBean.class);
                        }
                        if(list != null && list.size() > 0){
                            llHas.setVisibility(View.GONE);
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
            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<RefundBean>(mContext,list,R.layout.layout_after_sales_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, RefundBean bean, final int position, boolean isScrolling) {
                    holder.setText(R.id.txt_shop_name,bean.getShop_name());
                    holder.setVisible(R.id.rl_project,type == 1 ? true : false);
                    holder.setVisible(R.id.rl_goods,type == 1 ? false : true);
                    switch (bean.getRefund_status()){
                        case "1":
                            holder.setText(R.id.txt_status,"退款中");
                            break;
                        case "2":
                            holder.setText(R.id.txt_status,"退款成功");
                            break;
                        case "3":
                            holder.setText(R.id.txt_status,"取消退款");
                            break;
                        case "4":
                            holder.setText(R.id.txt_status,"商家拒绝");
                            break;
                    }
                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getItem_img()));
                    if(type == 1){
                        holder.setText(R.id.txt_project_name,bean.getItem_name());
                        holder.setText(R.id.txt_project_shop,bean.getShop_name());
                        holder.setText(R.id.txt_yy_time,"预约时间："+bean.getCreate_time_text());
                        holder.setText(R.id.txt_project_price,"¥"+bean.getPrice());
                    }else {
                        holder.setText(R.id.txt_goods_name,bean.getItem_name());
                        holder.setText(R.id.txt_goods_spec,"规格："+bean.getItem_desc_spec());
                        holder.setText(R.id.txt_goods_num,"×"+bean.getNum());
                        holder.setText(R.id.txt_goods_price,"¥"+bean.getPrice());
                    }
                    holder.setText(R.id.txt_refund_type,bean.getRefund_type());

                    holder.setOnClickListener(R.id.txt_check_detail, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            it = new Intent(mContext,AfterSalesDetailActivity.class);
                            it.putExtra("refund_id",list.get(position).getRefund_id());
                            it.putExtra("type",type);
                            startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                initData();
                break;
        }
    }
}
