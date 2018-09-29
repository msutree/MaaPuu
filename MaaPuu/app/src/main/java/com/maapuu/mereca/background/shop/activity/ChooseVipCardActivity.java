package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.MyCardBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员卡切换
 * Created by dell on 2018/3/5.
 */

public class ChooseVipCardActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private BaseRecyclerAdapter<MyCardBean> adapter;

    String member_id = "";
    int page = 1;
    boolean isShowProgress = true;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_switch_vip_card);
    }

    @Override
    public void initView() {
        member_id = getIntent().getStringExtra("member_id");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("会员卡切换");
        //txtRight.setVisibility(View.VISIBLE);
        //txtRight.setText("确定");

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getMemberChange();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getMemberChange();
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseRecyclerAdapter<MyCardBean>(mContext,R.layout.layout_my_membership_card_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, MyCardBean bean, int position, boolean isScrolling) {
                holder.setSelected(R.id.iv_choose,bean.isBool());
                switch (bean.getCard_type()){
                    case 1:
                        holder.setImageResource(R.id.iv_card_type,R.mipmap.nianka);
                        break;
                    case 2:
                        holder.setImageResource(R.id.iv_card_type,R.mipmap.xiangmuka);
                        break;
                    case 3:
                        holder.setImageResource(R.id.iv_card_type,R.mipmap.chongzhika);
                        break;
                }
                holder.setText(R.id.txt_card_name,bean.getCard_name());
                holder.setText(R.id.txt_card_desc,bean.getCard_desc());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                MyCardBean bean = adapter.getList().get(position);
                it = new Intent();
                it.putExtra("member_id",bean.getMember_id());
                setResult(-1,it);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        getMemberChange();
    }

    private void getMemberChange() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_member_change_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        member_id,
                        page),isShowProgress);
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

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = false;
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            List<MyCardBean> list = FastJsonTools.getPersons(object.optString("result"),MyCardBean.class);
                            if(page == 1) adapter.clear();
                            adapter.addList(list);
                            if(page > 1){
                                if((list ==null || list.size()==0)){
                                    page--;
                                    ToastUtil.show(mContext,"暂无更多数据");
                                }
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
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }


}
