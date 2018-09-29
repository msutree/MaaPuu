package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.CategoryAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目 选择类别
 * Created by Jia on 2018/3/19.
 */

public class ProjectChooseCategoryActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private CategoryAdapter adapter;
    private List<CatalogBean> list;
    private int pos = -1;

    private String catalog_type;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_choose_category);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择类别");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("\ue69b");
        txtRight.setTextSize(17);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        catalog_type = getIntent().getStringExtra("catalog_type");

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.background)));
        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_catalog_sel_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),catalog_type),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            list = FastJsonTools.getPersons(object.optString("result"), CatalogBean.class);
                            setAdapter();
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
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new CategoryAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new CategoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                it = new Intent();
                it.putExtra("cata_ids",list.get(position).getCatalog_id());
                it.putExtra("cata_names",list.get(position).getCatalog_name());
                setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                finish();
            }

            @Override
            public void onRightItemClick1(View v, int position) {
                it = new Intent(mContext,ProjectAddCategoryActivity.class);
                it.putExtra("catalog_type",catalog_type);
                it.putExtra("category",list.get(position));
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
            }
            @Override
            public void onRightItemClick2(View v, int position) {
                pos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_catalog_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        list.get(position).getCatalog_id()),true);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                it = new Intent(mContext,ProjectAddCategoryActivity.class);
                it.putExtra("catalog_type",catalog_type);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
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

    @Override
    public void onBackPressed() {
        if(adapter != null && adapter.getAlertView() != null && adapter.getAlertView().isShowing()){
            adapter.getAlertView().dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
