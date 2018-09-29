package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.CutBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DisplayUtil;
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
 * 商铺详情
 * Created by Jia on 2018/3/12.
 */

public class ShopDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.iv_shop)
    SimpleDraweeView ivShop;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_shop_phone)
    TextView txtShopPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_detail)
    TextView txtDetail;
    @BindView(R.id.txt_kefu_account)
    TextView txtKefuAccount;
    @BindView(R.id.txt_admin_account)
    TextView txtAdminAccount;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String shop_id;
    private String shop_name;
    private String shop_logo;
    private String shop_code;
    private String attention_num;
    private int position;
    private String result;
    private boolean isEdit = false;//是否编辑过了
    private AlertView alertView;

    private List<CutBean> list;
    private BaseRecyclerAdapter<CutBean> adapter;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_shop_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("商铺详情");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
        position = getIntent().getIntExtra("position",-1);
        shop_id = getIntent().getStringExtra("shop_id");
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image.getLayoutParams();
        lp.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DisplayUtil.getWidthDP(mContext)*2/5, mContext.getResources().getDisplayMetrics()));
        image.setLayoutParams(lp);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        result = object.optString("result");
                        shop_name = resultObj.optString("shop_name");
                        shop_logo = resultObj.optString("shop_logo");
                        image.setImageURI(Uri.parse(resultObj.optString("shop_cover")));
                        ivShop.setImageURI(Uri.parse(resultObj.optString("shop_logo")));
                        txtShopName.setText(resultObj.optString("shop_name"));
                        txtShopPhone.setText(resultObj.optString("shop_tel"));
                        txtAddress.setText(resultObj.optString("city_name"));
                        txtDetail.setText(resultObj.optString("address_detail"));
                        txtKefuAccount.setText(resultObj.optString("shop_service_phone"));
                        txtAdminAccount.setText(resultObj.optString("shop_admin_phone"));
                        shop_code = resultObj.optString("shop_code");
                        attention_num = resultObj.optString("attention_num");
                        if(resultObj.has("fullcutset") && resultObj.optJSONArray("fullcutset").length() > 0){
                            list = FastJsonTools.getPersons(resultObj.optString("fullcutset"),CutBean.class);
                        }
                        setAdapter();
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
                        ToastUtil.show(mContext,"删除成功");
                        it = new Intent();
                        it.putExtra("position",position);
                        setResult(AppConfig.ACTIVITY_RESULTCODE_1,it);
                        finish();
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
        adapter = new BaseRecyclerAdapter<CutBean>(mContext,list,R.layout.layout_add_shop_youhui_item1) {
            @Override
            public void convert(BaseRecyclerHolder holder, CutBean item, int position, boolean isScrolling) {
                holder.setText(R.id.txt_full,"消费满"+item.getFullcut_amount()+"元");
                holder.setText(R.id.txt_cut,"可减"+item.getCut_amount()+"元");
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_right2,R.id.sd_shop_qr_code_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                if(isEdit){
                    setResult(AppConfig.ACTIVITY_RESULTCODE);
                    finish();
                }
                finish();
                break;
            case R.id.txt_right:
                alertView = new AlertView(null, "确认删除吗？", "取消",null, new String[]{"删除"}, mContext,
                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_shop_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id),true);
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.txt_right2:
                //编辑
                //需将已有数据传过去
                it = new Intent(mContext,AddShopActivity.class);
                it.putExtra("shop_id",shop_id);
                it.putExtra("result",result);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;

            case R.id.sd_shop_qr_code_ll:
                //商铺二维码
                it = new Intent(mContext,ShopQrCodeActivity.class);
                it.putExtra("shop_name",shop_name);
                it.putExtra("shop_logo",shop_logo);
                it.putExtra("shop_code",shop_code);
                it.putExtra("attention_num",attention_num);
                startActivity(it);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                isEdit = true;
                initData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            if(isEdit){
                setResult(AppConfig.ACTIVITY_RESULTCODE);
                finish();
            }
            super.onBackPressed();
        }
    }
}
