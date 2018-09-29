package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.KaBaoServerBean;
import com.maapuu.mereca.bean.MyCardBean;
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
 * Created by dell on 2018/2/24.
 */

public class KaBaoActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private LinearLayoutManager mLayoutManager;
    private List<MyCardBean> list;
    private BaseRecyclerAdapter<MyCardBean> adapter;
    private int page = 1;
    public static KaBaoActivity activity;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_kabao);
        activity = this;
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("卡包");
        txtRight.setText("办会员卡");txtRight.setVisibility(View.VISIBLE);
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

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.card_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),pageNum),true);
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
                            List<MyCardBean> lsJson = FastJsonTools.getPersons(object.optString("result"),MyCardBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<MyCardBean> lsJson = new ArrayList<>();
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
            case 999:
                page = 1;
                initData(page);
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

    private void setAdapter(List<MyCardBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<MyCardBean>(mContext,list,R.layout.layout_kabao_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, MyCardBean bean, final int position, boolean isScrolling) {
                    if(bean.getMember_status().equals("2")){
                        holder.setSelected(R.id.txt_status,true);
                        holder.setText(R.id.txt_status,"已过期");
                    }else {
                        holder.setSelected(R.id.txt_status,false);
                    }
                    holder.setText(R.id.txt_shop_name,bean.getCard_name());
                    switch (bean.getCard_type()){
                        case 1:
                            holder.setImageResource(R.id.iv_card_type,R.mipmap.nianka);
                            holder.setText(R.id.txt_consume,bean.getCost_days()+"天");
                            holder.setText(R.id.txt_rest,"-1".equals(bean.getRemain_days())?"无限期":(bean.getRemain_days()+"天"));
                            break;
                        case 2:
                            holder.setImageResource(R.id.iv_card_type,R.mipmap.xiangmuka);
                            holder.setText(R.id.txt_consume,bean.getCost_times()+"次");
                            holder.setText(R.id.txt_rest,bean.getRemain_times()+"次");
                            break;
                        case 3:
                            holder.setImageResource(R.id.iv_card_type,R.mipmap.chongzhika);
                            holder.setText(R.id.txt_consume,"¥"+bean.getCost_amount());
                            holder.setText(R.id.txt_rest,"¥"+bean.getRemain_amount());
                            break;
                    }
                    if(bean.getCard_type()==1 && "-1".equals(bean.getRemain_days())){
                        holder.setVisible(R.id.txt_label,false);
                    }else {
                        holder.setVisible(R.id.txt_label,true);
                    }
                    if(list.get(position).isBool()){
                        holder.setSelected(R.id.txt_open,true);
                        holder.setText(R.id.txt_open,"收起");
                        if(list.get(position).getLast_order() == null || list.get(position).getLast_order().size() == 0){
                            holder.setVisible(R.id.recycler_view,false);
                        }else {
                            holder.setVisible(R.id.recycler_view,true);
                        }
                    }else {
                        holder.setText(R.id.txt_open,"展开");
                        holder.setSelected(R.id.txt_open,false);
                        holder.setVisible(R.id.recycler_view,false);
                    }
                    if(list.get(position).getLast_order() != null && list.get(position).getLast_order().size() > 0){
                        RecyclerView rvRecord = holder.getView(R.id.recycler_view);
                        rvRecord.setHasFixedSize(true);
                        rvRecord.setLayoutManager(new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
                        rvRecord.setAdapter(new BaseRecyclerAdapter<KaBaoServerBean>(mContext,list.get(position).getLast_order(),R.layout.layout_kabao_serve_record_item) {
                            @Override
                            public void convert(BaseRecyclerHolder holder, KaBaoServerBean item, int pos, boolean isScrolling) {
                                holder.setText(R.id.txt_name,item.getItem_name());
                                holder.setText(R.id.txt_time,item.getCreate_time());
                                holder.setText(R.id.txt_price,"¥"+item.getPrice());
                                if(pos == list.get(position).getLast_order().size() - 1){
                                    holder.setPadding(R.id.ll_item,mContext,10,10,10,10);
                                }else {
                                    holder.setPadding(R.id.ll_item,mContext,10,10,10,0);
                                }
                            }
                        });
                    }
                    holder.setOnClickListener(R.id.txt_open, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < list.size(); i++){
                                if(i == position){
                                    list.get(position).setBool(!list.get(position).isBool());
                                    break;
                                }
                            }
                            notifyItemChanged(position);
                        }
                    });
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
                it = new Intent(mContext,KaBaoDetailActivity.class);
                it.putExtra("member_id",list.get(position).getMember_id());
                startActivity(it);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                startActivity(new Intent(mContext,MembershipCenterActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
