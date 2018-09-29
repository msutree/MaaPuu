package com.maapuu.mereca.fragment.homechild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.CampaignDetailActivity;
import com.maapuu.mereca.activity.ShopDetailActivity;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.CampaignBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.fragment.HomeFragment;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.ToastUtil;
import com.luck.picture.lib.decoration.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2018/2/22.
 * 主页——活动
 */

public class HomeCampaignsFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<CampaignBean> adapter;
    private List<CampaignBean> list;
    private String shop_id;

    @Override
    protected int setContentViewById() {
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.fragment_home_campaigns;
    }

    public void refresh(String shop_id){
        this.shop_id = shop_id;
        initData();
    }

    @Override
    protected void initView(View v) {
        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,20,
                getResources().getColor(R.color.background)));
        list = new ArrayList<>();
    }

    @Override
    protected void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.action_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                if(ShopDetailActivity.activity != null){
                    ShopDetailActivity.activity.mHandler.sendEmptyMessage(888);
                }else {
                    if(HomeFragment.homeFragment != null){
                        HomeFragment.homeFragment.mHandler.sendEmptyMessage(888);
                    }
                }
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.optJSONArray("result").length() > 0){
                            list = FastJsonTools.getPersons(object.optString("result"),CampaignBean.class);
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
                if(HomeFragment.homeFragment != null){
                    HomeFragment.homeFragment.mHandler.sendEmptyMessage(888);
                }
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        adapter = new BaseRecyclerAdapter<CampaignBean>(mContext,list,R.layout.layout_home_campaign_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, CampaignBean bean, int position, boolean isScrolling) {
                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) holder.getParams(R.id.image);
                ll.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-20)*2/5, mContext.getResources().getDisplayMetrics()));
                holder.setParams(R.id.image,ll);

                holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getPack_img()));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                it = new Intent(mContext, CampaignDetailActivity.class);
                it.putExtra("shop_id",shop_id);
                it.putExtra("pack_id",list.get(position).getPack_id());
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
