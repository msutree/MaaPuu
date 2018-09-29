package com.maapuu.mereca.background.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.activity.EditPermissionActivity;
import com.maapuu.mereca.background.shop.bean.FuncBean;
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
 * 模板 权限
 * Created by Jia on 2018/3/14.
 */

public class TemplatePermissionFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tp_edit_permission_tv)
    TextView editPermissionTv;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<FuncBean> adapter;

    int request_code_edit_permission = 100;

    String jobName = "";
    String post_id = "";//岗位id
    String wageBase = "";
    List<FuncBean> func;

    public static TemplatePermissionFragment newInstance(String jobName,String post_id,String wageBase,List<FuncBean> func){
        TemplatePermissionFragment fragment = new TemplatePermissionFragment();
        Bundle args = new Bundle();
        args.putString("jobName",jobName);
        args.putString("post_id",post_id);
        args.putString("wageBase",wageBase);
        args.putSerializable("func", (Serializable)func);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        if(bundle != null){
            jobName = bundle.getString("jobName","");
            post_id = bundle.getString("post_id","");
            wageBase = bundle.getString("wageBase","");
            func = (List<FuncBean>) bundle.getSerializable("func");
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.shop_fragment_template_permission;
    }

    @Override
    protected void initView(View v) {

        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext,LinearLayoutManager.HORIZONTAL,2,
                getResources().getColor(R.color.background)));
        adapter = new BaseRecyclerAdapter<FuncBean>(mContext,R.layout.shop_item_template_permission) {
            @Override
            public void convert(BaseRecyclerHolder holder, FuncBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.tp_title_tv,bean.getFunc_name());
            }
        };
        recyclerView.setAdapter(adapter);
        if(func != null){
            adapter.addList(func);
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    @OnClick({R.id.tp_edit_permission_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tp_edit_permission_tv:
                //编辑权限
                if(TextUtils.isEmpty(post_id)){
                    ToastUtil.show(mContext,"请添加岗位");
                } else {
                    Intent intent = new Intent(mContext,EditPermissionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",jobName+"权限");
                    bundle.putString("post_id",post_id);
                    intent.putExtras(bundle);
                    getActivity().startActivityForResult(intent,request_code_edit_permission);
                }

                break;
        }
    }

    public void refreshData(String jobName,String post_id,String wageBase,List<FuncBean> func) {
        this.jobName = jobName;
        this.post_id = post_id;
        this.wageBase = wageBase;
        this.func = func;
        adapter.clear();
        adapter.addList(func);
    }
}
