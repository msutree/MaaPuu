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
import com.maapuu.mereca.background.shop.bean.ContainItemsBean;
import com.maapuu.mereca.background.shop.bean.ItemBean;
import com.maapuu.mereca.background.shop.bean.PackActBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.CardBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/2/24.
 */

public class ChooseTaocanActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<ContainItemsBean> list;
    private BaseRecyclerAdapter<ContainItemsBean> adapter;
    private String shop_id = "0";
    private int pack_type = 1;//卡类型：1会籍；2项目卡；3充值卡

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_taocan);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择套餐");
        shop_id = getIntent().getStringExtra("shop_id");
        pack_type = getIntent().getIntExtra("pack_type",2);

        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.background)));
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_input_item_pack_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                shop_id,pack_type),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        list.clear();
                        if(object.has("result") && object.optJSONArray("result").length() > 0){
                            list.addAll(FastJsonTools.getPersons(object.optString("result"),ContainItemsBean.class));
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
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<ContainItemsBean>(mContext,list,R.layout.shop_item_market_meal_doing) {
                @Override
                public void convert(BaseRecyclerHolder holder, ContainItemsBean bean, int position, boolean isScrolling) {
                    holder.setText(R.id.mmd_title_tv,bean.getItem_name());
                    holder.setVisible(R.id.iv_left,false);
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                it = new Intent();
                it.putExtra("item_name",list.get(position).getItem_name());
                it.putExtra("item_id",list.get(position).getItem_id());
                setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                finish();
            }
        });
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
}
