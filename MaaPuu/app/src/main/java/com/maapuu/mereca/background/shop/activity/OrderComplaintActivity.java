package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.ComplainDetailBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.DividerGridItemDecoration;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单投诉
 * Created by Jia on 2018/4/24.
 */

public class OrderComplaintActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.apply_date_tv)
    TextView applyDateTv;
    @BindView(R.id.rl_contact_phone)
    RelativeLayout rlContactPhone;
    @BindView(R.id.contact_phone_tv)
    TextView contactPhoneTv;
    @BindView(R.id.complaint_desc_tv)
    TextView complaintDescTv;

    @BindView(R.id.txt_label_1)
    TextView txtLabel1;
    @BindView(R.id.txt_label_2)
    TextView txtLabel2;
    @BindView(R.id.txt_btn_1)
    TextView txtBtn1;
    @BindView(R.id.txt_btn_2)
    TextView txtBtn2;
    @BindView(R.id.txt_status)
    TextView txtStatus;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;


    private BaseRecyclerAdapter adapter;
    private AlertView alertView;

    String complaint_id = "";
    //投诉状态：1待处理(投诉建议)；2处理完成(已处理投诉)；
    int complaint_status;
    String phone = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_order_complain);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("投诉订单详情");
    }

    @Override
    public void initData() {
        complaint_id = getIntent().getStringExtra("complaint_id");
        complaint_status = getIntent().getIntExtra("complaint_status",0);
        getComplaint();

        //投诉状态：1待处理(投诉建议)；2处理完成(已处理投诉)；
        if(complaint_status == 1){
            rlBottom.setVisibility(View.VISIBLE);
            txtStatus.setVisibility(View.GONE);
        } else if(complaint_status == 2){
            rlBottom.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
        }
    }


    private void setUI(ComplainDetailBean bean) {
        orderNoTv.setText("订单编号："+bean.getOrder_no());
        nickNameTv.setText(bean.getNick_name());
        applyDateTv.setText(bean.getCreate_time());
        phone = bean.getContact_tel();
        contactPhoneTv.setText(bean.getContact_tel());
        complaintDescTv.setText(bean.getComplaint_desc());
        if(bean.getComplaint_status().equals("1")){
            rlBottom.setVisibility(View.VISIBLE);
            txtStatus.setVisibility(View.GONE);
        } else if(bean.getComplaint_status().equals("2")){
            rlBottom.setVisibility(View.GONE);
            txtStatus.setVisibility(View.VISIBLE);
        }
        List<ComplainDetailBean.DetailBean> imgDetail = bean.getDetail();
        if(imgDetail != null && imgDetail.size()>0){
            setImgRv(imgDetail);
        }
    }

    private void setImgRv(List<ComplainDetailBean.DetailBean> imgDetail) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext,20
                ,mContext.getResources().getColor(R.color.white)));
        adapter = new BaseRecyclerAdapter<ComplainDetailBean.DetailBean>(mContext,imgDetail,R.layout.layout_goods_comment_image_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ComplainDetailBean.DetailBean item, int position, boolean isScrolling) {
                ImageView imgIv = holder.getView(R.id.image);
                if(item.getWidth() != 0 && item.getHeight() != 0){
                    ViewGroup.LayoutParams params = imgIv.getLayoutParams();
                    params.width  = (DisplayUtil.getWidthPX(mContext) - DisplayUtil.dip2px(mContext,30))/3;
                    params.height = params.width;
                    imgIv.setLayoutParams(params);
                }
                holder.setImage(R.id.image,item.getContent(),false);
//                imgIv.setImageURI(Uri.parse(item.getContent()));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void getComplaint() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_complaint_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        complaint_id),true);
    }

    //订单投诉处理，项目商品共用
    private void setComplaint() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_complaint_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        complaint_id),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_btn_1,R.id.txt_btn_2})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_btn_1:
                alertView = new AlertView(null, "您确定此投诉已处理完成吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            setComplaint();
                        }
                    }
                });
                alertView.show();

                break;

            case R.id.txt_btn_2:
                if(TextUtils.isEmpty(phone)){
                    ToastUtil.show(mContext,"未获取到联系电话");
                    return;
                }
                alertView = new AlertView(null, "TEL:"+phone, "取消", null, new String[]{"拨打"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phone));
                            startActivity(intent);
                        }
                    }
                });
                alertView.show();

                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            ComplainDetailBean bean = FastJsonTools.getPerson(object.optString("result"), ComplainDetailBean.class);
                            if(bean != null){
                                setUI(bean);
                            }
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2://订单投诉处理
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    }
                    ToastUtil.show(mContext, object.optString("message"));
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

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        } else {
            super.onBackPressed();
        }
    }

}
