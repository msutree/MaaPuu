package com.maapuu.mereca.fragment.homechild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ShopDetailActivity;
import com.maapuu.mereca.activity.TeamDetailActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.TeamBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.fragment.HomeFragment;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
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
 * 主页——团队
 */

public class HomeTeamFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<TeamBean> adapter;
    private List<TeamBean> list;
    private String shop_id;

    @Override
    protected int setContentViewById() {
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.fragment_home_team;
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
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.shop_team_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                shop_id, AppConfig.LAT,AppConfig.LNG),true);
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
                            list = FastJsonTools.getPersons(object.optString("result"),TeamBean.class);
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
        adapter = new BaseRecyclerAdapter<TeamBean>(mContext,list,R.layout.layout_team_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, TeamBean bean, int position, boolean isScrolling) {
                holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getStaff_avatar()));
                holder.setText(R.id.txt_name,bean.getStaff_name());
                holder.setText(R.id.txt_locate,bean.getCity());
                holder.setText(R.id.txt_distance,"距您"+bean.getDistance());
                holder.setText(R.id.txt_intro,bean.getStaff_intro());
                holder.setScaleRatingBar(R.id.ratingbar,Float.parseFloat(bean.getEvl_level()));
                holder.setText(R.id.txt_appoint_num,bean.getAppoint_num());
                holder.setText(R.id.txt_fans_num,bean.getFans_num());
                holder.setText(R.id.txt_works_num,bean.getWorks_num());
                holder.setText(R.id.txt_evl_num,bean.getEvl_num());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                it = new Intent(mContext, TeamDetailActivity.class);
                it.putExtra("staff_id",list.get(position).getStaff_id());
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
