package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.JobSetAdapter;
import com.maapuu.mereca.background.shop.bean.PostTempBean;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 岗位设置
 * Created by Jia on 2018/3/8.
 */

public class JobSetActivity extends BaseActivity implements JobSetAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private JobSetAdapter adapter;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_job_set);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("岗位设置");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69b");//&#xe69b;
        txtRight.setTextSize(17);

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.background)));

        adapter = new JobSetAdapter(mContext);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getPostTempList();
    }

    private void getPostTempList(){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_post_temp_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            List<PostTempBean> list = FastJsonTools.getPersons(object.optString("result"), PostTempBean.class);
                            if(list == null && list.size() > 0){
                                llHas.setVisibility(View.VISIBLE);
                            }else {
                                llHas.setVisibility(View.GONE);
                            }
                            adapter.clear();
                            adapter.addList(list);
                        }else {
                            llHas.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //刷新数据
                        getPostTempList();
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

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //新增岗位设置
                showAddModelDialog("0","");

                break;
        }
    }

    private void showAddModelDialog(final String post_temp_id,final String name) {
        NiceDialog ndDialog = NiceDialog.init();
        ndDialog.setLayoutId(R.layout.nd_layout_common_edit)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView ndTitleTv = holder.getView(R.id.nd_title_tv);
                        ndTitleTv.setText("模板名称");

                        final EditText contentEt = holder.getView(R.id.nd_content_et);
                        contentEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        contentEt.setHint("请输入模板名称");
                        if(!TextUtils.isEmpty(name)) contentEt.setText(name);

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
                                String post_temp_name = contentEt.getText().toString().toString();
                                if(TextUtils.isEmpty(post_temp_name)){
                                    ToastUtil.show(mContext,"请输入模板名称");
                                    return;
                                }

                                //新增
                                addPostTemp(post_temp_id,post_temp_name);
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

    private void  addPostTemp(String post_temp_id,String post_temp_name){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_post_temp_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                post_temp_id,post_temp_name),true);
    }

    private void  deletePostTemp(String post_temp_id){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.s_post_temp_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),post_temp_id),true);
    }

    @Override
    public void onItemClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        PostTempBean bean = adapter.getList().get(position);
        //岗位设置详情
        Intent intent = new Intent(mContext,JobSetDetailActivity.class);
        intent.putExtra("title",bean.getPost_temp_name());
        intent.putExtra("post_temp_id",bean.getPost_temp_id());
        UIUtils.startActivity(mContext,intent);
    }

    @Override
    public void onRightItemClick(View v, int position, String tag) {
        PostTempBean bean = adapter.getList().get(position);

        switch (tag){
            case "编辑":
                showAddModelDialog(bean.getPost_temp_id(),bean.getPost_temp_name());

                break;

            case "删除":
                showDeleteDialog("你确定删除此模板吗？",bean.getPost_temp_id());
                break;
        }
    }

    private void showDeleteDialog(final String msg,final String post_temp_id) {
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
                                 //刷新数据
                                deletePostTemp(post_temp_id);
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

}
