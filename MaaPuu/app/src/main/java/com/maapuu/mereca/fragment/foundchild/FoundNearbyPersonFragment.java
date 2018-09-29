package com.maapuu.mereca.fragment.foundchild;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.PersonalHomepageActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.PersonalBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.EmojiUtil;
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

/**
 * Created by dell on 2018/2/27.
 */

public class FoundNearbyPersonFragment extends BaseFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<PersonalBean> list;
    private BaseRecyclerAdapter<PersonalBean> adapter;
    private int page = 1;
    private int addPos = -1;

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_found_nearby_person;
    }

    @Override
    protected void initView(View v) {
        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

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

    private void initData(int pageNum){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.around_user_list_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"), AppConfig.LAT,AppConfig.LNG,pageNum),true);
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
                            List<PersonalBean> lsJson = FastJsonTools.getPersons(object.optString("result"),PersonalBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<PersonalBean> lsJson = new ArrayList<>();
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
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"发送添加请求成功");
                        if(addPos != -1){
                            list.get(addPos).setIs_add_friend("2");
                            adapter.notifyItemChanged(addPos);
                            addPos = -1;
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

    private void setAdapter(List<PersonalBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new BaseRecyclerAdapter<PersonalBean>(mContext,list,R.layout.layout_nearby_person_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, PersonalBean bean, final int position, boolean isScrolling) {
                    holder.setSimpViewImageUri(R.id.image, Uri.parse(bean.getAvatar()));
                    holder.setText(R.id.txt_name,bean.getNick_name());
                    holder.setText(R.id.txt_distance,"距您"+bean.getDistance());
                    if(bean.getIs_staff().equals("1")){
                        holder.setVisible(R.id.iv_staff,true);
                        holder.setVisible(R.id.txt_post_name,true);
                        holder.setVisible(R.id.txt_shop_name,true);
                        holder.setText(R.id.txt_post_name,bean.getPost_name());
                        holder.setText(R.id.txt_shop_name,bean.getShop_name());
                    }else {
                        holder.setVisible(R.id.iv_staff,false);
                        holder.setVisible(R.id.txt_post_name,false);
                        holder.setVisible(R.id.txt_shop_name,false);
                    }
                    if("1".equals(list.get(position).getIs_add_friend())){
                        holder.setVisible(R.id.txt_is_friends,true);
                        holder.setSelected(R.id.txt_is_friends,true);
                        holder.setText(R.id.txt_is_friends,"+好友");
                    }else if("0".equals(list.get(position).getIs_add_friend())){
                        holder.setVisible(R.id.txt_is_friends,true);
                        holder.setSelected(R.id.txt_is_friends,false);
                        holder.setText(R.id.txt_is_friends,"已添加");
                    }else {
                        holder.setVisible(R.id.txt_is_friends,true);
                        holder.setSelected(R.id.txt_is_friends,false);
                        holder.setText(R.id.txt_is_friends,"待验证");
                    }
                    if(StringUtils.isEmpty(bean.getContent()) && (bean.getLast_moment() == null || bean.getLast_moment().size() == 0)){
                        holder.setVisible(R.id.ll_mo,false);
                    }else {
                        holder.setVisible(R.id.ll_mo,true);
                        if(Integer.parseInt(bean.getMo_num())>99){
                            holder.setText(R.id.txt_mo_num,bean.getMo_num()+"+");
                        }else {
                            holder.setText(R.id.txt_mo_num,bean.getMo_num());
                        }
                        holder.setText(R.id.txt_time,bean.getLast_publish_mo_time());
                        holder.setText(R.id.txt_last_mo, EmojiUtil.utf8ToString(bean.getContent()));
                        if(bean.getLast_moment() != null && bean.getLast_moment().size() > 0){
                            switch (bean.getLast_moment().size()){
                                case 1:
                                    holder.setVisible(R.id.iv_image_1,true);
                                    holder.setVisible(R.id.iv_image_2,false);
                                    holder.setVisible(R.id.iv_image_3,false);
                                    if("2".equals(bean.getLast_moment().get(0).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getFirst_frame()));
                                    }
                                    break;
                                case 2:
                                    holder.setVisible(R.id.iv_image_1,true);
                                    holder.setVisible(R.id.iv_image_2,true);
                                    holder.setVisible(R.id.iv_image_3,false);
                                    if("2".equals(bean.getLast_moment().get(0).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getFirst_frame()));
                                    }
                                    if("2".equals(bean.getLast_moment().get(1).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_2,Uri.parse(bean.getLast_moment().get(1).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_2,Uri.parse(bean.getLast_moment().get(1).getFirst_frame()));
                                    }
                                    break;
                                default:
                                    holder.setVisible(R.id.iv_image_1,true);
                                    holder.setVisible(R.id.iv_image_2,true);
                                    holder.setVisible(R.id.iv_image_3,true);
                                    if("2".equals(bean.getLast_moment().get(0).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_1,Uri.parse(bean.getLast_moment().get(0).getFirst_frame()));
                                    }
                                    if("2".equals(bean.getLast_moment().get(1).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_2,Uri.parse(bean.getLast_moment().get(1).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_2,Uri.parse(bean.getLast_moment().get(1).getFirst_frame()));
                                    }
                                    if("2".equals(bean.getLast_moment().get(2).getContent_type())){
                                        holder.setSimpViewImageUri(R.id.iv_image_3,Uri.parse(bean.getLast_moment().get(2).getContent()));
                                    }else {
                                        holder.setSimpViewImageUri(R.id.iv_image_3,Uri.parse(bean.getLast_moment().get(2).getFirst_frame()));
                                    }
                                    break;
                            }
                        }
                    }
                    holder.setOnClickListener(R.id.txt_is_friends, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if("1".equals(list.get(position).getIs_add_friend())){
                                addPos = position;
                                add(list.get(position).getShare_code());
                            }

                        }
                    });
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
                it = new Intent(mContext,PersonalHomepageActivity.class);
                it.putExtra("userpage_uid",list.get(position).getUid());
                it.putExtra("is_staff",list.get(position).getIs_staff()+"");
                startActivity(it);
            }
        });
    }

    private void add(String share_code) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.friend_add_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),share_code),true);
    }

    @Override
    public void onClick(View view) {

    }
}
