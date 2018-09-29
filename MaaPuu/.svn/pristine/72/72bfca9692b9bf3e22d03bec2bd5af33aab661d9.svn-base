package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
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
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择店铺
 * Created by Jia on 2018/3/8.
 */

public class JobChooseShopActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.confirm_tv)
    TextView confirmTv;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<ShopBean> adapter;

    String post_temp_id = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_job_choose_shop);
    }

    @Override
    public void initView() {
        post_temp_id = getIntent().getStringExtra("post_temp_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择店铺");
        txtRight.setVisibility(View.VISIBLE);

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.background)));
        adapter = new BaseRecyclerAdapter<ShopBean>(mContext, R.layout.shop_item_job_choose_shop) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShopBean bean, int position, boolean isScrolling) {
                CircleImgView icon = holder.getView(R.id.cr_icon);
                CheckBox cb = holder.getView(R.id.cr_cb);
                UIUtils.loadImg(mContext,bean.getShop_logo(),icon);
                holder.setText(R.id.cr_shop_name_tv,bean.getShop_name());
                cb.setChecked(bean.isChecked());
            }
        };

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                ShopBean bean = adapter.getList().get(position);
                bean.setChecked(!bean.isChecked());
                adapter.notifyItemChanged(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getShopList();
    }

    //适用店铺 选择列表
    private void getShopList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_temp_shop_sel_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.confirm_tv:
                getSetShop(getShopIds());

                break;
        }
    }

    //设置适用店铺
    private void getSetShop(String shop_ids) {
        if(TextUtils.isEmpty(shop_ids)){
            ToastUtil.show(mContext,"请选择商铺");
            return;
        }
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2, UrlUtils.s_temp_shop_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id,shop_ids),true);
    }

    private String getShopIds(){
        StringBuilder sb = new StringBuilder("");
        if(adapter != null && adapter.getList() != null && adapter.getList().size()>0){
            for (ShopBean bean:adapter.getList()) {
                if(bean.isChecked()){
                    sb.append(bean.getShop_id()+",");
                }
            }
            if(sb.toString().endsWith(",")){
                sb.deleteCharAt(sb.length()-1);
            }
        }

        return sb.toString();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            List<ShopBean> list = FastJsonTools.getPersons(object.optString("result"), ShopBean.class);
                            //没有分页
                            adapter.clear();
                            adapter.addList(list);
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
                        // 关闭界面，上一界面刷新数据
                        Intent intent = new Intent();
                        setResult(-1,intent);

                        finish();
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


}
