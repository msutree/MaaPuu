package com.maapuu.mereca.background.shop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ImageTextBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员详情
 * Created by Jia on 2018/4/26.
 */

public class VipDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.txt_card_name)
    TextView txtCardName;
    @BindView(R.id.txt_discount)
    TextView txtDiscount;
    @BindView(R.id.txt_cost)
    TextView txtCost;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_include_project)
    TextView txtIncludeProject;
    @BindView(R.id.list_view)
    MyListView listView;

    private List<ImageTextBean> list = new ArrayList<>();
    private QuickAdapter<ImageTextBean> adapter;

    String member_id = "";//卡号

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_vip_detail);
    }

    @Override
    public void initView() {
        member_id = getIntent().getStringExtra("member_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("会员详情");

        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) image.getLayoutParams();
        ll.height = DisplayUtil.getHeight(mContext,2f/5);
        image.setLayoutParams(ll);
    }

    @Override
    public void initData() {
        getVipDetail();
    }

    private void getVipDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_order_member_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        member_id),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        image.setImageURI(Uri.parse(resultObj.optString("card_img")));
                        txtCardName.setText(resultObj.optString("card_name"));
//                        card_type = resultObj.optInt("card_type");
                        if(StringUtils.isEmpty(resultObj.optString("card_desc"))){
                            txtDiscount.setVisibility(View.GONE);
                        }else {
                            txtDiscount.setVisibility(View.VISIBLE);
                            txtDiscount.setText(resultObj.optString("card_desc"));
                        }
//                        price = resultObj.optString("recharge_amount");
                        txtCost.setText(resultObj.optString("recharge_amount")+"元");
                        txtTime.setText(resultObj.optString("valid_date"));
                        txtIncludeProject.setText(resultObj.optString("item_str"));
                        if(resultObj.has("detail") && !StringUtils.isEmpty(resultObj.optString("detail")) && resultObj.optJSONArray("detail").length() > 0){
                            list = FastJsonTools.getPersons(resultObj.optString("detail"),ImageTextBean.class);
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

    private void setAdapter(){
        adapter = new QuickAdapter<ImageTextBean>(mContext,R.layout.layout_goods_content_detail_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImageTextBean item) {
                int position = helper.getPosition();
                RelativeLayout ll = helper.getView(R.id.ll);
                if(position == list.size() - 1){
                    ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                }else {
                    ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),0);
                }
                if(list.get(position).getHeight() != 0 && list.get(position).getWidth() != 0){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) helper.getParams(R.id.image);
                    float f = Float.valueOf(list.get(position).getHeight())/list.get(position).getWidth();
                    lp.height = DisplayUtil.getHeight(mContext,f);
                    helper.setLayoutParams(R.id.image,lp);
                }
                if(list.get(position).getContent_type().equals("1")){
                    helper.setVisible(R.id.image,false);
                    helper.setVisible(R.id.txt,true);
                    helper.setText(R.id.txt,item.getContent());
                }else {
                    helper.setVisible(R.id.image,true);
                    helper.setVisible(R.id.txt,false);
                    helper.setSimpViewImageUri(R.id.image,Uri.parse(item.getContent()));
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureSelectUtil.show(VipDetailActivity.this,position,list);
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
