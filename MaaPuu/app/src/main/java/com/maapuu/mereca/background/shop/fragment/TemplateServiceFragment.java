package com.maapuu.mereca.background.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.activity.EditSrvActivity;
import com.maapuu.mereca.background.shop.bean.PostSrvBean;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.ToastUtil;
import com.luck.picture.lib.decoration.RecycleViewDivider;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 模板 服务
 * Created by Jia on 2018/3/15.
 */

public class TemplateServiceFragment extends BaseFragment{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.ts_edit_service_tv)
    TextView editServiceTv;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<PostSrvBean> adapter;

    int request_code_edit_service = 101;

    String jobName = "";
    String post_id = "";//岗位id
    String wageBase = "";
    List<PostSrvBean> srv;

    public static TemplateServiceFragment newInstance(String jobName,String post_id,String wageBase,List<PostSrvBean> srv){
        TemplateServiceFragment fragment = new TemplateServiceFragment();
        Bundle args = new Bundle();
        args.putString("jobName",jobName);
        args.putString("post_id",post_id);
        args.putString("wageBase",wageBase);
        args.putSerializable("srv", (Serializable) srv);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if(bundle != null){
            jobName = bundle.getString("jobName","");
            post_id = bundle.getString("post_id","");
            wageBase = bundle.getString("wageBase","");
            srv = (List<PostSrvBean>) bundle.getSerializable("srv");
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.shop_fragment_template_service;
    }

    @Override
    protected void initView(View v) {

        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,2,
                getResources().getColor(R.color.background)));

        //注意：这里与权限共用了布局
        adapter = new BaseRecyclerAdapter<PostSrvBean>(mContext,R.layout.shop_item_template_permission) {
            @Override
            public void convert(BaseRecyclerHolder holder, PostSrvBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.tp_title_tv,bean.getSrv_name());
            }
        };
        recyclerView.setAdapter(adapter);
        if(srv !=null){
            adapter.addList(srv);
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    @OnClick({R.id.ts_edit_service_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ts_edit_service_tv:
                //编辑服务
                if(TextUtils.isEmpty(post_id)){
                    ToastUtil.show(mContext,"请添加岗位");
                } else {
                    Intent intent = new Intent(mContext,EditSrvActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",jobName+"服务");
                    bundle.putString("post_id",post_id);
                    intent.putExtras(bundle);
                    getActivity().startActivityForResult(intent,request_code_edit_service);
                }

                break;
        }
    }

    public void refreshData(String jobName,String post_id,String wageBase,List<PostSrvBean> srv) {
        this.jobName = jobName;
        this.post_id = post_id;
        this.wageBase = wageBase;
        this.srv = srv;
        adapter.clear();
        adapter.addList(srv);
    }
}
