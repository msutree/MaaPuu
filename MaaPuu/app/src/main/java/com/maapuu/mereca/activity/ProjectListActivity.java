package com.maapuu.mereca.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.bean.ProjectBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.DisplayUtil;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/2/24.
 */

public class ProjectListActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_filter)
    TextView txtFilter;
    @BindView(R.id.recycleview_horizontal)
    RecyclerView recyclerViewH;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyGridLayoutManager mLayoutManager;
    private List<ProjectBean> list;
    private BaseRecyclerAdapter<ProjectBean> adapter;
    private List<CatalogBean> fList;
    private BaseRecyclerAdapter<CatalogBean> flAdapter; //筛选
    private int page = 1;
    private String catalog_id;
    private String shop_id;
    private int pos;
    private String order_type = "1";

    private View popView;
    private PopupWindow popupWindow;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_project_list);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtFilter.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("服务项目");
        shop_id = getIntent().getStringExtra("shop_id");
        catalog_id = getIntent().getStringExtra("catalog_id");
        pos = getIntent().getIntExtra("pos",-1);
        list = new ArrayList<>();
        recyclerViewH.setLayoutManager(new FullyLinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        mLayoutManager=new FullyGridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        fList = (List<CatalogBean>) getIntent().getSerializableExtra("flList");
        if(fList != null){
            for (int i = 0; i < fList.size(); i++){
                if(i == pos){
                    fList.get(pos).setBool(true);
                }else {
                    fList.get(i).setBool(false);
                }
            }
            setFeileiAdapter();
        }

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initData(page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                initData(page);
            }
        });
    }

    @Override
    public void initData() {
        initData(page);
    }

    private void initData(int pageNum) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.project_list_get(LoginUtil.getInfo("token"),shop_id,
                LoginUtil.getInfo("uid"),catalog_id,order_type,pageNum),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            List<ProjectBean> lsJson = FastJsonTools.getPersons(object.optString("result"),ProjectBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<ProjectBean> lsJson = new ArrayList<>();
                            setAdapter(lsJson);
                            if(page != 1){
                                ToastUtil.show(mContext,"暂无更多数据");
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    //分类适配器和全部
    private void setFeileiAdapter(){
        flAdapter = new BaseRecyclerAdapter<CatalogBean>(mContext,fList,R.layout.layout_goods_cate_item) {
            @Override
            public void convert(final BaseRecyclerHolder holder, CatalogBean item, final int position, boolean isScrolling) {
                if(item.isBool()){
                    holder.setSelected(R.id.txt,true);
                    holder.setBackgroundColor(R.id.view1,getResources().getColor(R.color.text_33));
                }else{
                    holder.setSelected(R.id.txt,false);
                    holder.setBackgroundColor(R.id.view1,getResources().getColor(R.color.white));
                }
                holder.setText(R.id.txt,item.getCatalog_name());
                holder.setOnClickListener(R.id.txt, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < fList.size(); i++){
                            if(position == i){
                                fList.get(position).setBool(true);
                                catalog_id = fList.get(position).getCatalog_id();
                            }else {
                                fList.get(i).setBool(false);
                            }
                        }
                        notifyDataSetChanged();
                        page = 1;
                        initData(page);
                    }
                });
            }
        };
        recyclerViewH.setAdapter(flAdapter);
        recyclerViewH.smoothScrollToPosition(pos);
    }

    private void setAdapter(List<ProjectBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<ProjectBean>(mContext,list,R.layout.layout_home_project_grid_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, ProjectBean bean, int position, boolean isScrolling) {
                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getItem_img()));
                    holder.setText(R.id.txt_project_name,bean.getItem_name());
                    holder.setText(R.id.txt_project_price,"¥"+bean.getPrice());
                    holder.setText(R.id.txt_project_desc,bean.getItem_desc());
                    LinearLayout ll =holder.getView(R.id.ll);
                    if(position % 2 == 0){
                        if(position == list.size()-2){
                            ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10));
                        }else if(position == list.size()-1){
                            if(list.size() % 2 == 0){
                                ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                            }else {
                                ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10));
                            }
                        } else {
                            ll.setPadding(DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,5),0);
                        }
                    }else {
                        if(position == list.size()-1){
                            ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),DisplayUtil.dip2px(mContext,10));
                        }else {
                            ll.setPadding(DisplayUtil.dip2px(mContext,5),DisplayUtil.dip2px(mContext,10),DisplayUtil.dip2px(mContext,12),0);
                        }
                    }
                    LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) holder.getParams(R.id.ll_1);
                    lp.width=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-34)/2, mContext.getResources().getDisplayMetrics()));
                    holder.setParams(R.id.ll_1,lp);
                    holder.setParams(R.id.txt_project_desc,lp);
                    LinearLayout.LayoutParams lp1 =(LinearLayout.LayoutParams) holder.getParams(R.id.image);
                    lp1.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (DisplayUtil.getWidthDP(mContext)-34)/2, mContext.getResources().getDisplayMetrics()));
                    lp1.height = lp1.width;
                    holder.setParams(R.id.image,lp1);
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                it = new Intent(mContext,GoodsDetailActivity.class);
                it.putExtra("type",2);
                it.putExtra("shop_id",shop_id);
                it.putExtra("item_id",list.get(position).getItem_id());
                startActivity(it);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_filter})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_filter:
                showPop(view);
                break;
        }
    }

    private void showPop(View v) {
        popView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        TextView txt1 = popView.findViewById(R.id.txt_1);
        TextView txt2 = popView.findViewById(R.id.txt_2);
        TextView txt3 = popView.findViewById(R.id.txt_3);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_type = "1";
                page = 1;
                initData(page);
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_type = "2";
                page = 1;
                initData(page);
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_type = "3";
                page = 1;
                initData(page);
            }
        });
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        popView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
//        ColorDrawable dw = new ColorDrawable(0x75000000);
//        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        int i = popupWindow.getContentView().getMeasuredWidth();

        popupWindow.showAsDropDown(v,  - v.getWidth()*4/3-10, -20);
    }
}
