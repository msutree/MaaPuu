package com.maapuu.mereca.background.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.activity.EditWageActivity;
import com.maapuu.mereca.background.shop.activity.JobSetDetailActivity;
import com.maapuu.mereca.background.shop.bean.WageBean;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 模板 工资
 * Created by Jia on 2018/3/15.
 */

public class TemplateWageFragment extends BaseFragment{

    @BindView(R.id.tw_edit_base_wage_ic)
    TextView editBaseWageIc;

    @BindView(R.id.tw_base_wage_tv)
    TextView baseWageTv;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<WageBean> adapter;

    final int request_code_edit_wage = 102;
    String jobName = "";
    String post_id = "";
    String wageBase = "";
    List<WageBean> wage;

    public static TemplateWageFragment newInstance(String jobName,String post_id,String wageBase,List<WageBean> wage){
        TemplateWageFragment fragment = new TemplateWageFragment();
        Bundle args = new Bundle();
        args.putString("jobName",jobName);
        args.putString("post_id",post_id);
        args.putString("wageBase",wageBase);
        args.putSerializable("wage", (Serializable) wage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if(bundle != null){
            jobName = bundle.getString("jobName","");
            post_id = bundle.getString("post_id","");
            wageBase = bundle.getString("wageBase","");
            wage = (List<WageBean>) bundle.getSerializable("wage");
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.shop_fragment_template_wage;
    }

    @Override
    protected void initView(final View v) {
        editBaseWageIc.setTypeface(StringUtils.getFont(mContext));
        if(!TextUtils.isEmpty(wageBase)) baseWageTv.setText(wageBase+"元");

        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new BaseRecyclerAdapter<WageBean>(mContext,R.layout.shop_item_template_wage) {
            @Override
            public void convert(BaseRecyclerHolder holder, final WageBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.tw_title_tv,bean.getSrv_name());
                TextView editIc = holder.getView(R.id.tw_edit_txt);
                editIc.setTypeface(StringUtils.getFont(mContext));

                holder.setOnClickListener(R.id.tw_title_ll,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //编辑工资 （提成设置）
                        Intent intent = new Intent(mContext,EditWageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("post_id",post_id);
                        bundle.putString("jobName",jobName);
                        bundle.putSerializable("WageBean",bean);
                        intent.putExtras(bundle);
                        getActivity().startActivityForResult(intent,request_code_edit_wage);
                    }
                });

                RecyclerView levelRv = holder.getView(R.id.tw_level_rv);
                List<WageBean.WageDetailBean> list = bean.getWage_detail();
                if(list != null && list.size()>0){
                    levelRv.setVisibility(View.VISIBLE);
                    levelRv.setLayoutManager(new LinearLayoutManager(mContext));
                    levelRv.setNestedScrollingEnabled(false);
                    WageLevelAdapter serviceAdapter = new WageLevelAdapter(mContext, bean.getIs_num(),list);
                    levelRv.setAdapter(serviceAdapter);
                } else {
                    levelRv.setVisibility(View.GONE);
                }

            }
        };
        recyclerView.setAdapter(adapter);
        if(wage != null){
           adapter.addList(wage);
        }
    }

    @Override
    protected void initData() {
    }

    private void  setPostBaseWage(String wage_base){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_post_base_wage_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        post_id,wage_base),true);
    }

    @Override
    @OnClick({R.id.tw_edit_base_wage_ic})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tw_edit_base_wage_ic:
                //编辑基本工资
                if(TextUtils.isEmpty(post_id)){
                    ToastUtil.show(mContext,"请添加岗位");
                } else {
                    showEditBaseWageDialog();
                }

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
                        //刷新岗位详情
                        ((JobSetDetailActivity)getActivity()).getPostDetail();

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

    private void showEditBaseWageDialog() {
        NiceDialog ndDialog = NiceDialog.init();
        ndDialog.setLayoutId(R.layout.nd_layout_shop_edit_base_wage)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        final EditText contentEt = holder.getView(R.id.nd_content_et);

                        TextView cancelTv = holder.getView(R.id.nd_cancel);
                        TextView submitTv = holder.getView(R.id.nd_ok);
                        final EditText wageEt = holder.getView(R.id.nd_content_et);

//                        contentEt.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                KeyBoardUtils.openKeybord(contentEt,mContext);
//                            }
//                        });

                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                KeyBoardUtils.closeKeybord(contentEt,mContext);
                                dialog.dismiss();
                            }
                        });

                        submitTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //编辑基本工资
                                String wage_base = wageEt.getText().toString().toString();
                                if(TextUtils.isEmpty(wage_base)){
                                    ToastUtil.show(mContext,"请输入基本工资");
                                    return;
                                }
                                setPostBaseWage(wage_base);
                                KeyBoardUtils.closeKeybord(contentEt,mContext);

                                dialog.dismiss();
                            }
                        });

                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                .show(getActivity().getSupportFragmentManager());
    }

    public void refreshData(String jobName,String post_id,String wageBase,List<WageBean> wage) {
        this.jobName = jobName;
        this.post_id = post_id;
        this.wageBase = wageBase;
        this.wage = wage;
        if(!TextUtils.isEmpty(wageBase))baseWageTv.setText(wageBase+"元");
        adapter.clear();
        adapter.addList(wage);
    }

    //服务套餐列表
    class WageLevelAdapter extends BaseRecyclerAdapter<WageBean.WageDetailBean> {
        //isNum  	计费方式，1按次数计费，2按比例计费
        int isNum;

        public WageLevelAdapter(Context context, int isNum, List list) {
            super(context, list, R.layout.shop_item_template_item);
            this.isNum = isNum;
        }

        @Override
        public void convert(BaseRecyclerHolder holder, WageBean.WageDetailBean item, int position, boolean isScrolling) {
//            if(isNum == 1){
//                holder.setText(R.id.ti_left_tv,item.getCondition()+"次及以内");
//                holder.setText(R.id.ti_right_tv,item.getCommission()+"元/次");
//            } else if(isNum == 0){
//                holder.setText(R.id.ti_left_tv,item.getCondition()+"元");
//                holder.setText(R.id.ti_right_tv,item.getCommission()+"%");
//            }
            holder.setText(R.id.ti_left_tv,item.getCondition_text());
            holder.setText(R.id.ti_right_tv,item.getCommission_text());
        }
    }

}
