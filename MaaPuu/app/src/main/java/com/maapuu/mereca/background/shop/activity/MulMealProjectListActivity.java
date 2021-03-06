package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ItemBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
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
 * 多项套餐 项目列表
 * Created by Jia on 2018/3/19.
 */

public class MulMealProjectListActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.mmpl_choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<ItemBean> adapter;
    private List<ItemBean> list;
    private List<ShopBean> shopList;

    private String item_id;
    private String shop_id;
    private String shop_name;
    private String sub_item_ids;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_mul_meal_project_list);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("项目列表");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("保存");
        shopList = new ArrayList<>();
        sub_item_ids = getIntent().getStringExtra("sub_item_ids");
        item_id = getIntent().getStringExtra("item_id");
//        shop_id = getIntent().getStringExtra("shop_id");
//        if(!StringUtils.isEmpty(shop_id)){
//            txtShopName.setText(getIntent().getStringExtra("shop_name"));
//        }

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
        if(!StringUtils.isEmpty(shop_id)){
            initItemData();
        }else {
            initShopData();
        }
    }

    private void initShopData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_select_shop_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),0),true);
    }

    private void initItemData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.s_multi_project_sel_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),item_id,shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            shopList = FastJsonTools.getPersons(object.optString("result"), ShopBean.class);
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
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            list.clear();
                            list.addAll(FastJsonTools.getPersons(object.optString("result"), ItemBean.class));
//                            if(!StringUtils.isEmpty(sub_item_ids)){
//                                for (int i = 0; i < list.size(); i++){
//                                    for (int j = 0; j < sub_item_ids.split(",").length; j++){
//                                        if(list.get(i).getItem_shop_id().equals(sub_item_ids.split(",")[j])){
//                                            list.get(i).setBool(true);
//                                        }
//                                    }
//                                }
//                            }
                            setAdapter();
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
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
            adapter = new BaseRecyclerAdapter<ItemBean>(mContext, list, R.layout.shop_item_mul_meal_project_shop) {
                @Override
                public void convert(BaseRecyclerHolder holder, ItemBean bean, int position, boolean isScrolling) {
                    CheckBox cb = holder.getView(R.id.cr_cb);
                    holder.setText(R.id.txt_name,bean.getItem_name());
                    cb.setChecked(bean.getIs_sel().equals("1"));
                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                list.get(position).setIs_sel(list.get(position).getIs_sel().equals("1")?"0":"1");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.ao_choose_shop_lt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                if(StringUtils.isEmpty(shop_id)){
                    ToastUtil.show(mContext,"请选择店铺");
                    return;
                }
                if(StringUtils.isEmpty(getIds())){
                    ToastUtil.show(mContext,"请选择服务");
                    return;
                }
                it = new Intent();
                it.putExtra("shop_ids",shop_id);
                it.putExtra("shop_name",shop_name);
                it.putExtra("item_ids",getIds());
                it.putExtra("item_names",getName());
                setResult(AppConfig.ACTIVITY_RESULTCODE_3,it);
                finish();

                break;

            case R.id.ao_choose_shop_lt:
                if(shopList.size() == 0){
                    initShopData();
                }else {
                    //选择店铺
//                UIUtils.startActivity(mContext,JobChooseShopActivity.class);
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            txtShopName.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            shop_name = item.getShop_name();
                            initItemData();
                        }
                    });
                    chooseShopFilter.createPopup();
                    chooseShopFilter.showAsDropDown(chooseShopLt);
                }
                break;

        }
    }

    private String getIds(){
        StringBuilder sb = new StringBuilder("");
        for (ItemBean bean:list) {
            if(bean.getIs_sel().equals("1")){
                sb.append(bean.getItem_id()+",");
            }
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    private String getName(){
        StringBuilder sb = new StringBuilder("");
        for (ItemBean bean:list) {
            if(bean.getIs_sel().equals("1")){
                sb.append(bean.getItem_name()+",");
            }
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
