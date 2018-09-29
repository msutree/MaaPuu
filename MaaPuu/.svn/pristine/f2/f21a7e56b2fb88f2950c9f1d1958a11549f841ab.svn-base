package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.SelectItemAdapter;
import com.maapuu.mereca.background.shop.bean.ContainItemsBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/8/6.
 */

public class SelectItemsActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<ContainItemsBean> list;
    private List<ContainItemsBean> templist;
    private SelectItemAdapter adapter;
    private String shop_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_select_items);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择列表");
        txtRight.setVisibility(View.VISIBLE);txtRight.setText("保存");
        shop_id = getIntent().getStringExtra("shop_id");
        list = new ArrayList<>();
        templist = new ArrayList<>();
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext,1,false));
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.background)));
        adapter = new SelectItemAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SelectItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onMinusClick(int position) {
                if(list.get(position).getNum() == 0){
                    return;
                }
                list.get(position).setNum(list.get(position).getNum()-1);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onPlusClick(int position) {
                list.get(position).setNum(list.get(position).getNum()+1);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onTextChange(int position) {

            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_input_item_pack_list_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),shop_id,1),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!(object.opt("result") instanceof Boolean) && object.optJSONArray("result").length() > 0){
                            list.clear();
                            list.addAll(FastJsonTools.getPersons(object.optString("result"),ContainItemsBean.class));
                            adapter.notifyDataSetChanged();
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

    private void addItems(){
        templist.clear();
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getNum() != 0){
                templist.add(list.get(i));
            }
        }
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
                addItems();
                if(templist.size() > 0){
                    it = new Intent();
                    it.putExtra("templist", (Serializable) templist);
                    setResult(AppConfig.ACTIVITY_RESULTCODE,it);
                    finish();
                }
                break;
        }
    }
}
