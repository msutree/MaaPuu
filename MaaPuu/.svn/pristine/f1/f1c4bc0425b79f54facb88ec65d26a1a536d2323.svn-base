package com.maapuu.mereca.fragment.goodschild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.GoodsDetailActivity;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.ProjectBean;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class CampaignDetailFragment extends BaseFragment{
    @BindView(R.id.iv_goods)
    SimpleDraweeView ivGoods;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.txt_goods_price)
    TextView txtGoodsPrice;
    @BindView(R.id.txt_activity_time)
    TextView txtActivityTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static CampaignDetailFragment fragment = null;
    private FullyGridLayoutManager mLayoutManager;
    private List<ProjectBean> list;
    private BaseRecyclerAdapter<ProjectBean> adapter;
    private String result;
    private String shop_id;

    public static CampaignDetailFragment newInstance() {
        if (fragment == null) {
            fragment = new CampaignDetailFragment();
        }
        return fragment;
    }

    @Override
    protected int setContentViewById() {
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            result = bundle.getString("result");
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.fragment_campaign_detail;
    }

    @Override
    protected void initView(View v) {
        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) ivGoods.getLayoutParams();
        ll.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        ivGoods.setLayoutParams(ll);

        list = new ArrayList<>();
        mLayoutManager=new FullyGridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.white)));

    }


    @Override
    protected void initData() {
        try {
            JSONObject resultObj = new JSONObject(result);
            if(resultObj.has("action_data") && !StringUtils.isEmpty(resultObj.optString("action_data"))){
                JSONObject dataObj = resultObj.optJSONObject("action_data");
                if(!StringUtils.isEmpty(dataObj.optString("pack_img"))){
                    ivGoods.setImageURI(Uri.parse(dataObj.optString("pack_img")));
                }
                txtGoodsName.setText(dataObj.optString("pack_name"));
                txtGoodsPrice.setText(dataObj.optString("price"));
                txtActivityTime.setText(dataObj.optString("deadline_begin")+"至"+dataObj.optString("deadline_end"));
            }
            if(resultObj.has("project_list") && !StringUtils.isEmpty(resultObj.optString("project_list")) && resultObj.optJSONArray("project_list").length() > 0){
                list = FastJsonTools.getPersons(resultObj.optString("project_list"),ProjectBean.class);
            }
            adapter = new BaseRecyclerAdapter<ProjectBean>(mContext,list,R.layout.layout_home_project_grid_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, ProjectBean bean, int position, boolean isScrolling) {
                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getItem_img()));
                    holder.setText(R.id.txt_project_name,bean.getItem_name());
                    holder.setText(R.id.txt_project_price,"¥"+bean.getPrice());
                    holder.setText(R.id.txt_project_desc,bean.getItem_desc());
                    LinearLayout ll =holder.getView(R.id.ll);
                    if(position % 2 == 0){
                        if(position == list.size()-2){
                            ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10));
                        }else if(position == list.size()-1){
                            if(list.size() % 2 == 0){
                                ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                            }else {
                                ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10));
                            }
                        } else {
                            ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),0);
                        }
                    }else {
                        if(position == list.size()-1){
                            ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                        }else {
                            ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),0);
                        }
                    }
                    LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) holder.getParams(R.id.ll_1);
                    lp.width=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-34)/2, mContext.getResources().getDisplayMetrics()));
                    holder.setParams(R.id.ll_1,lp);
                    holder.setParams(R.id.txt_project_desc,lp);
                    LinearLayout.LayoutParams lp1 =(LinearLayout.LayoutParams) holder.getParams(R.id.image);
                    lp1.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-34)/2, mContext.getResources().getDisplayMetrics()));
                    lp1.height = lp1.width;
                    holder.setParams(R.id.image,lp1);
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    it = new Intent(mContext,GoodsDetailActivity.class);
                    it.putExtra("type",2);
                    it.putExtra("shop_id",shop_id);
                    it.putExtra("item_id",list.get(position).getItem_id());
                    startActivity(it);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    @OnClick({})
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
