package com.maapuu.mereca.background.shop.activity;

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
import com.maapuu.mereca.background.shop.bean.RefundDetailBean;
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
 * 订单 退款
 * Created by Jia on 2018/4/24.
 */

public class OrderRefundActivity extends BaseActivity {
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
    //@BindView(R.id.rl_contact_phone)
    //RelativeLayout rlContactPhone;
    //@BindView(R.id.contact_phone_tv)
    //TextView contactPhoneTv;
    @BindView(R.id.refund_type_tv)
    TextView refundTypeTv;
    @BindView(R.id.refund_reason_tv)
    TextView refundReasonTv;
    @BindView(R.id.refund_desc_tv)
    TextView refundDescTv;
    @BindView(R.id.refund_money_tv)
    TextView refundMoneyTv;

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

    String refund_id = "";
    //refund_status 退款状态：1退款中(退款申请)；2退款成功；3用户取消退款；4商家拒绝）
    int refund_status;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_order_refund);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("退款订单详情");
    }

    @Override
    public void initData() {
        refund_id = getIntent().getStringExtra("refund_id");
        refund_status = getIntent().getIntExtra("refund_status",0);
        getRefund();

        //refund_status 退款状态：1退款中(退款申请)；2退款成功；3用户取消退款；4商家拒绝）
        switch (refund_status){
            case 1:
                rlBottom.setVisibility(View.VISIBLE);
                txtStatus.setVisibility(View.GONE);
                break;

            case 2:
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("退款成功");
                rlBottom.setVisibility(View.GONE);

                break;

            case 3:
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("用户取消退款");
                rlBottom.setVisibility(View.GONE);
                break;

            case 4:
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("退款审核不通过");
                rlBottom.setVisibility(View.GONE);
                break;
        }
    }


    private void setUI(RefundDetailBean bean) {
        orderNoTv.setText("订单编号："+bean.getOrder_no());
        nickNameTv.setText(bean.getNick_name());
        applyDateTv.setText(bean.getApply_time());
        //phone = bean.getContact_tel();
        refundTypeTv.setText(bean.getRefund_type());
        refundReasonTv.setText(bean.getRefund_reason());
        refundDescTv.setVisibility(StringUtils.isEmpty(bean.getRefund_desc())?View.GONE:View.VISIBLE);
        refundDescTv.setText(bean.getRefund_desc());
        refundMoneyTv.setText("¥"+bean.getRefund_amount());

        List<RefundDetailBean.DetailBean> imgDetail = bean.getDetail();
        if(imgDetail != null && imgDetail.size()>0){
            setImgRv(imgDetail);
        }

    }

    private void setImgRv(List<RefundDetailBean.DetailBean> imgDetail) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext,20
                ,mContext.getResources().getColor(R.color.white)));
        adapter = new BaseRecyclerAdapter<RefundDetailBean.DetailBean>(mContext,imgDetail,R.layout.layout_goods_comment_image_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, RefundDetailBean.DetailBean item, int position, boolean isScrolling) {
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

    private void getRefund() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_refund_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        refund_id),true);
    }

    //订单投诉处理，项目商品共用
    private void setRefund(int refund_status) {//2退款成功；4商家拒绝退款
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_refund_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        refund_id,refund_status+""),true);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_btn_1,R.id.txt_btn_2})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_btn_1:
                alertView = new AlertView(null, "您确定拒绝此退款申请吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            setRefund(4);//2退款成功；4商家拒绝退款
                        }
                    }
                });
                alertView.show();

                break;

            case R.id.txt_btn_2:
                alertView = new AlertView(null, "您确定同意此退款申请吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            setRefund(2);//2退款成功；4商家拒绝退款
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
                            RefundDetailBean bean = FastJsonTools.getPerson(object.optString("result"), RefundDetailBean.class);
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

            case HttpModeBase.HTTP_REQUESTCODE_2://订单退款处理
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext, object.optString("message"));
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
                    }else {
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

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
