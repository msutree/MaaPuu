package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.FuncBean;
import com.maapuu.mereca.background.shop.bean.PostBean;
import com.maapuu.mereca.background.shop.bean.PostSrvBean;
import com.maapuu.mereca.background.shop.bean.PostTempDetailBean;
import com.maapuu.mereca.background.shop.bean.WageBean;
import com.maapuu.mereca.background.shop.fragment.TemplatePermissionFragment;
import com.maapuu.mereca.background.shop.fragment.TemplateServiceFragment;
import com.maapuu.mereca.background.shop.fragment.TemplateWageFragment;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 岗位设置
 * Created by Jia on 2018/3/8.
 */

public class JobSetDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.jsd_job_rv)
    RecyclerView jobRv;

    @BindView(R.id.jsd_add_tv)
    TextView addTv;//添加
    @BindView(R.id.jsd_delete_tv)
    TextView deleteTv;//删除

    @BindView(R.id.txt_tab_1)
    TextView txtTab1;
    @BindView(R.id.txt_tab_2)
    TextView txtTab2;
    @BindView(R.id.txt_tab_3)
    TextView txtTab3;


    int jobPosition = 0;
    String jobName = "";

    private TextView[] tvs;

    private FragmentManager fragmentManager;
    TemplatePermissionFragment permissionFragment;
    TemplateServiceFragment serviceFragment;
    TemplateWageFragment wageFragment;

    private int tabPosition = 0;

    private final static int request_code_edit_permission = 100;
    private final static int request_code_edit_service = 101;
    private final static int request_code_edit_wage = 102;

    BaseRecyclerAdapter<PostBean> jobNameAdapter;

    String post_temp_id = "";//岗位模板id,由上一界面传入
    String post_id = "";//岗位id
    String post_temp_name = "";

    List<FuncBean> func;
    List<PostSrvBean> srv;
    List<WageBean> wage;
    String wageBase = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_job_set_detail);
    }

    @Override
    public void initView() {
        post_temp_id = getIntent().getStringExtra("post_temp_id");
        post_temp_name = getIntent().getStringExtra("title");
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText(post_temp_name);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue627");//&#xe627;
        txtRight.setTextSize(17);

        fragmentManager = getSupportFragmentManager();
        tvs = new TextView[]{txtTab1, txtTab2, txtTab3};

        addFragment(tabPosition);
        setJobRv();
    }

    private void setJobRv() {
        jobNameAdapter = new BaseRecyclerAdapter<PostBean>(mContext, R.layout.shop_item_job_name) {
            @Override
            public void convert(BaseRecyclerHolder holder, final PostBean bean, int position, boolean isScrolling) {
                TextView jobNameTv = holder.getView(R.id.jn_title_tv);
                jobNameTv.setText(bean.getPost_name());

                if(position == jobPosition){
                    jobNameTv.setTextColor(UIUtils.getColor(R.color.main_color));
                    jobNameTv.setTextSize(16);
                    jobNameTv.setBackgroundResource(R.color.white);
                } else {
                    jobNameTv.setTextColor(UIUtils.getColor(R.color.text_33));
                    jobNameTv.setTextSize(14);
                    jobNameTv.setBackgroundResource(R.color.background);
                }
            }
        };
        jobRv.setAdapter(jobNameAdapter);
        jobRv.setNestedScrollingEnabled(false);
        jobRv.setLayoutManager(new LinearLayoutManager(mContext));
        jobNameAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                List<PostBean> jobList = jobNameAdapter.getList();
                 if(jobList != null && jobList.size()>0){
                     post_id = jobList.get(position).getPost_id();
                     jobPosition = position;
                     jobName = jobList.get(position).getPost_name();

                     getPostDetail();
                     //请求接口后再刷新
                     //jobNameAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void initData() {
        getPostTempDetail();
    }

    //岗位模板详情，同时返回第一个岗位的详情
    private void getPostTempDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_post_temp_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id),true);
    }

    //获取岗位列表 （用的还是上一个接口，只取岗位列表的数据）
    private void getPostList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4, UrlUtils.s_post_temp_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id),true);
    }

    //岗位详情
    public void getPostDetail() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_post_detail_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_id),true);
    }

    private void setData(PostTempDetailBean bean) {
        wageBase = bean.getWage_base();
        List<PostBean> postList = bean.getPost();
        if(postList != null && jobNameAdapter != null){
            if(postList.size()>0){
                post_id = postList.get(0).getPost_id();
                jobName = postList.get(0).getPost_name();
            }
            jobNameAdapter.clear();
            jobNameAdapter.addList(postList);
        }

        //把对应数据填充到对应碎片
        func = (ArrayList)bean.getFunc();
        srv = bean.getSrv();
        wage = bean.getWage();

        refreshData();
    }

    private void refreshData() {
       //权限 服务 工资
        if(permissionFragment != null){
            permissionFragment.refreshData(jobName,post_id,wageBase,func);
        } else {
            if(tabPosition==0) addFragment(0);
        }
        if(serviceFragment != null){
            serviceFragment.refreshData(jobName,post_id,wageBase,srv);
        } else {
            if(tabPosition==1) addFragment(1);
        }
        if(wageFragment != null){
            wageFragment.refreshData(jobName,post_id,wageBase,wage);
        } else {
            if(tabPosition==2) addFragment(2);
        }

        if(jobNameAdapter != null) jobNameAdapter.notifyDataSetChanged();
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.jsd_add_tv,R.id.jsd_delete_tv,R.id.txt_tab_1,R.id.txt_tab_2,R.id.txt_tab_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:{
                //适用店铺
                Intent intent = new Intent(mContext,ApplicableShopActivity.class);
                intent.putExtra("post_temp_id",post_temp_id);
                intent.putExtra("post_temp_name",post_temp_name);
                UIUtils.startActivity(mContext,intent);
            }

                break;

            case R.id.jsd_add_tv:
                //添加
                showAddJobDialog("0","");

                break;

            case R.id.jsd_delete_tv:
                //删除
                showDeleteDialog("你确定删除此岗位吗？",post_id);

                break;

            case R.id.txt_tab_1:
                addFragment(0);
                break;
            case R.id.txt_tab_2:
                addFragment(1);
                break;
            case R.id.txt_tab_3:
                addFragment(2);
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
                            PostTempDetailBean bean = FastJsonTools.getPerson(object.optString("result"), PostTempDetailBean.class);
                            setData(bean);
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2://新增岗位
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新 只需更新岗位列表
                        getPostList();
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_3://删除岗位
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新 请求首次进入时的接口
                        post_id = "";//请求接口会重新赋值
                        jobPosition = 0;
                        getPostTempDetail();

                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_4://只取岗位列表的值
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            PostTempDetailBean bean = FastJsonTools.getPerson(object.optString("result"), PostTempDetailBean.class);
                            if(bean != null){
                                List<PostBean> postList = bean.getPost();
                                if(postList != null && jobNameAdapter != null){
                                    jobNameAdapter.clear();
                                    jobNameAdapter.addList(postList);
                                    if(TextUtils.isEmpty(post_id) && postList.size()>0){
                                        post_id = postList.get(0).getPost_id();
                                        jobName = postList.get(0).getPost_name();
                                        //通知刷新数据
                                        refreshData();
                                    }
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

                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }


    private void showAddJobDialog(final String post_id,final String post_name) {
        NiceDialog ndDialog = NiceDialog.init();
        ndDialog.setLayoutId(R.layout.nd_layout_common_edit)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView ndTitleTv = holder.getView(R.id.nd_title_tv);
                        ndTitleTv.setText("岗位名称");

                        final EditText contentEt = holder.getView(R.id.nd_content_et);
                        contentEt.setHint("请输入岗位名称");
                        contentEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        if(!TextUtils.isEmpty(post_name)) contentEt.setText(post_name);

                        TextView cancelTv = holder.getView(R.id.nd_cancel);
                        TextView submitTv = holder.getView(R.id.nd_ok);

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

                                String name = contentEt.getText().toString().toString();
                                if(TextUtils.isEmpty(name)){
                                    ToastUtil.show(mContext,"输入不能为空");
                                    return;
                                }

                                //请求接口后更新数据
                                addPost(post_id,name);

                                KeyBoardUtils.closeKeybord(contentEt,mContext);
                                dialog.dismiss();
                            }
                        });

                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                .show(getSupportFragmentManager());

    }

    private void showDeleteDialog(final String msg,final String post_id) {
        NiceDialog.init()
                .setLayoutId(R.layout.nd_layout_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setText(R.id.title,"删除提示");
                        holder.setText(R.id.message,msg);

                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //消失
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //还需刷新数据
                                deletePost(post_id);

                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(270)//setMargin()和setWidth()选择一个即可
                //.setMargin(60)
                .setOutCancel(true)
                //.setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }

    //增加或修改岗位
    private void  addPost(String post_id,String post_name){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_post_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_id,post_name,post_temp_id),true);
    }

    //删除岗位
    private void  deletePost(String post_id){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,
                UrlUtils.s_post_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_id),true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=  null){
            switch (requestCode){
                case request_code_edit_permission:
                    //刷新数据 且 传递给 TemplatePermissionFragment
                    getPostTempDetail();

                    break;

                case request_code_edit_service:
                    //刷新数据 且 传递给 TemplateServiceFragment
                    getPostTempDetail();

                    break;

                case request_code_edit_wage:
                    //刷新数据 且 传递给 TemplateServiceFragment
//                    getPostTempDetail();
                    getPostDetail();
                    break;
            }

        }
    }

    private void setHead(int postion) {
        for (int i = 0; i < tvs.length; i++){
            if(i == postion){
                tvs[postion].setSelected(true);
            }else {
                tvs[i].setSelected(false);
            }
        }
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (permissionFragment != null) {
            transaction.hide(permissionFragment);
        }
        if (serviceFragment != null) {
            transaction.hide(serviceFragment);
        }
        if (wageFragment != null) {
            transaction.hide(wageFragment);
        }
    }

    private void addFragment(int i) {
        tabPosition = i;
        setHead(i);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (permissionFragment == null) {
                    permissionFragment = TemplatePermissionFragment.newInstance(jobName,post_id,wageBase,func);
                    fragmentTransaction.add(R.id.jsd_container_fl, permissionFragment);
                } else {
                    fragmentTransaction.show(permissionFragment);
                }
                break;
            case 1:
                if (serviceFragment == null) {
                    serviceFragment = TemplateServiceFragment.newInstance(jobName,post_id,wageBase,srv);
                    fragmentTransaction.add(R.id.jsd_container_fl, serviceFragment);
                } else {
                    fragmentTransaction.show(serviceFragment);
                }
                break;
            case 2:
                if (wageFragment == null) {
                    wageFragment = TemplateWageFragment.newInstance(jobName,post_id,wageBase,wage);
                    fragmentTransaction.add(R.id.jsd_container_fl, wageFragment);
                } else {
                    fragmentTransaction.show(wageFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

}
