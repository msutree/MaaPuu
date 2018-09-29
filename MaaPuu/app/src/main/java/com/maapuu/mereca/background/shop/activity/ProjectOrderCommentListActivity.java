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
import com.maapuu.mereca.background.shop.bean.OrderCommentBean;
import com.maapuu.mereca.base.BaseActivity;
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
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目订单评论
 * Created by Jia on 2018/4/23.
 */

public class ProjectOrderCommentListActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter adapter;
    //boolean isShowProgress = true;
    String oid = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_order_comment_list);
    }

    @Override
    public void initView() {
        oid = getIntent().getStringExtra("oid");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("项目订单评价");


        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                UIUtils.dip2px(mContext,1),
                getResources().getColor(R.color.background)));

        adapter = new BaseRecyclerAdapter<OrderCommentBean>(mContext, R.layout.shop_item_project_order_comment) {
            @Override
            public void convert(BaseRecyclerHolder holder, final OrderCommentBean bean, final int position, boolean isScrolling) {
                ScaleRatingBar bar= holder.getView(R.id.rating_bar);
                bar.setRating((float) bean.getEvl_level());
                //订单编号
                holder.setText(R.id.txt_name,bean.getNick_name());
                holder.setText(R.id.txt_time,bean.getCreate_time());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                //评价详情
                OrderCommentBean bean = (OrderCommentBean) adapter.getList().get(position);
                it = new Intent(mContext,OrderCommentDetailActivity.class);
                it.putExtra("evl_id",bean.getEvl_id());
                startActivity(it);
            }
        });
    }

    @Override
    public void initData() {
        getCommentList();
    }

    private void setUI(List<OrderCommentBean> list) {
        adapter.clear();
        adapter.addList(list);
    }

    private void getCommentList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_project_evl_list_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        oid),true);
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
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
                            List<OrderCommentBean> list = FastJsonTools.getPersons(object.optString("result"), OrderCommentBean.class);
                            setUI(list);
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
