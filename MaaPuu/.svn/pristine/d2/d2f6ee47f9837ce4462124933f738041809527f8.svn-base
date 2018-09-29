package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.ApplyJoinShopAdapter;
import com.maapuu.mereca.background.shop.bean.ApplyJoinBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 员工审核
 * Created by Jia on 2018/3/16.
 */

public class EmployeeCheckActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private ApplyJoinShopAdapter adapter;
    private List<ApplyJoinBean> list;
    private String shop_id;
    private int pos = -1;
    private boolean isRefresh = false;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_employee_check);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("员工审核");
        shop_id = getIntent().getStringExtra("shop_id");

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                UIUtils.dip2px(mContext,10),
                getResources().getColor(R.color.background)));
        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_staff_apply_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.has("result") && !StringUtils.isEmpty(object.optString("result"))){
                            llHas.setVisibility(View.GONE);
                            List<ApplyJoinBean> tempList = FastJsonTools.getPersons(object.optString("result"),ApplyJoinBean.class);
                            setAdapter(tempList);
                        }else {
                            llHas.setVisibility(View.VISIBLE);
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
                        ToastUtil.show(mContext,"已拒绝该申请");
                        initData();
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
                        if(pos != -1){
                            list.remove(pos);
                            adapter.notifyDataSetChanged();
                            pos = -1;
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter(List<ApplyJoinBean> tempList) {
        list.clear();
        list.addAll(tempList);
        if (adapter == null) {
            adapter = new ApplyJoinShopAdapter(mContext, list) ;
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new ApplyJoinShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {}

            @Override
            public void onRightItemClick(View v, int position) {
                pos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.s_staff_apply_delete_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"), list.get(position).getStaff_apply_id()),true);
            }

            @Override
            public void onAgreeClick(int position) {
                it = new Intent(mContext,AddEmployeeActivity.class);
                it.putExtra("staff_apply_id",list.get(position).getStaff_apply_id());
                it.putExtra("shop_id",list.get(position).getShop_id());
                it.putExtra("shop_name",list.get(position).getShop_name());
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
            }

            @Override
            public void onRefuseItemClick(int position) {
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_staff_reject_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"), list.get(position).getStaff_apply_id()),true);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                if(isRefresh){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                }
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                isRefresh = true;
                initData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isRefresh){
            setResult(AppConfig.ACTIVITY_RESULTCODE);
            finish();
        }
        super.onBackPressed();
    }
}
