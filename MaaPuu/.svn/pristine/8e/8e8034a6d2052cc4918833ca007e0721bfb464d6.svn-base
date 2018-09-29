package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.OrderCommentDetailBean;
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
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单评论详情
 * Created by Jia on 2018/4/24.
 */

public class OrderCommentDetailActivity extends BaseActivity{
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
    @BindView(R.id.comment_desc_tv)
    TextView commentDescTv;

    @BindView(R.id.rating_bar1)
    ScaleRatingBar bar1;
    @BindView(R.id.rating_bar2)
    ScaleRatingBar bar2;
    @BindView(R.id.rating_bar3)
    ScaleRatingBar bar3;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private BaseRecyclerAdapter adapter;
    String evl_id = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_order_comment_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("订单详情");
    }

    @Override
    public void initData() {
        evl_id = getIntent().getStringExtra("evl_id");
        getCommentDetail();
    }


    private void setUI(OrderCommentDetailBean bean) {
        orderNoTv.setText("订单编号："+bean.getOrder_no());
        nickNameTv.setText(bean.getNick_name());
        applyDateTv.setText(bean.getCreate_time());
        commentDescTv.setText(bean.getEvl_content());

        bar1.setRating(bean.getShop_desc_level());//描述相符
        bar1.setRating(bean.getStaff_logist_level());//物流
        bar1.setRating(bean.getSrv_level());//服务态度

        List<OrderCommentDetailBean.DetailBean> imgDetail = bean.getDetail();
        if(imgDetail != null && imgDetail.size()>0){
            setImgRv(imgDetail);
        }
    }

    private void setImgRv(List<OrderCommentDetailBean.DetailBean> imgDetail) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext,20
                ,mContext.getResources().getColor(R.color.white)));
        adapter = new BaseRecyclerAdapter<OrderCommentDetailBean.DetailBean>(mContext,imgDetail,R.layout.layout_goods_comment_image_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, OrderCommentDetailBean.DetailBean item, int position, boolean isScrolling) {
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

    private void getCommentDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_evl_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        evl_id),true);
    }


    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
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
                            OrderCommentDetailBean bean = FastJsonTools.getPerson(object.optString("result"), OrderCommentDetailBean.class);
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

            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

}
