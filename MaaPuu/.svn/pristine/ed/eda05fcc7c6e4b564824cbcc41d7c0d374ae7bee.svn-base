package com.maapuu.mereca.background.shop.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.activity.AppointmentOrderActivity;
import com.maapuu.mereca.background.shop.activity.CommentAnalysisActivity;
import com.maapuu.mereca.background.shop.activity.EmployeeManageActivity;
import com.maapuu.mereca.background.shop.activity.FinanceManageActivity;
import com.maapuu.mereca.background.shop.activity.GoodsManageActivity;
import com.maapuu.mereca.background.shop.activity.JobSetActivity;
import com.maapuu.mereca.background.shop.activity.MarketingDoingActivity;
import com.maapuu.mereca.background.shop.activity.MealDoingOrderActivity;
import com.maapuu.mereca.background.shop.activity.OrderManageActivity;
import com.maapuu.mereca.background.shop.activity.ProjectManageActivity;
import com.maapuu.mereca.background.shop.activity.RedPacketsHistoryActivity;
import com.maapuu.mereca.background.shop.activity.ReportCountActivity;
import com.maapuu.mereca.background.shop.activity.ServerManageActivity;
import com.maapuu.mereca.background.shop.activity.ShopManageActivity;
import com.maapuu.mereca.background.shop.activity.VipManageActivity;
import com.maapuu.mereca.background.shop.activity.VipOrderActivity;
import com.maapuu.mereca.background.shop.bean.ShopMenuBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.camera.CaptureActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.fragment.HomeFragment;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 我的店面 主页
 * Created by Jia on 2018/2/28.
 */

public class ShopHomeFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener, EasyPermissions.PermissionCallbacks{

    @BindView(R.id.h_shop_menu_rv)
    RecyclerView rv;

    private BaseRecyclerAdapter<ShopMenuBean> adapter;

    @Override
    protected int setContentViewById() {
        return R.layout.shop_fragment_home;
    }

    @Override
    protected void initView(View v) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseRecyclerAdapter<ShopMenuBean>(mContext, R.layout.shop_item_shop_menu) {
            @Override
            public void convert(BaseRecyclerHolder holder, ShopMenuBean bean, int position, boolean isScrolling) {
                View line = holder.getView(R.id.sm_line);
                line.setVisibility(bean.getFlag()==1?View.VISIBLE:View.GONE);
                holder.setText(R.id.sm_menu_tv,bean.getFunc_name());
            }
        };
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_shop_menu_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            List<ShopMenuBean> list = FastJsonTools.getPersons(object.optString("result"), ShopMenuBean.class);
                            adapter.clear();
                            adapter.addList(list);
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

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position) {
        ShopMenuBean bean = adapter.getList().get(position);
        switch (bean.getFunc_code()){
            case "10101"://预约单
                UIUtils.startActivity(getActivity(),AppointmentOrderActivity.class);
                break;
            case "10102"://会员卡录入
                requestLocationPermission();
                break;
            case "10103"://服务管理
                UIUtils.startActivity(getActivity(),ServerManageActivity.class);
                break;
            case "10203"://店铺管理
                UIUtils.startActivity(getActivity(),ShopManageActivity.class);
                break;
            case "10205"://岗位设置
                UIUtils.startActivity(getActivity(),JobSetActivity.class);
                break;
            case "10207"://员工管理
                UIUtils.startActivity(getActivity(),EmployeeManageActivity.class);
                break;
            case "10301"://项目管理
                UIUtils.startActivity(getActivity(),ProjectManageActivity.class);
                break;
            case "10303"://商品管理
                UIUtils.startActivity(getActivity(),GoodsManageActivity.class);
                break;
            case "10305"://营销活动
                UIUtils.startActivity(getActivity(),MarketingDoingActivity.class);
                break;
            case "10501"://会员管理
                UIUtils.startActivity(getActivity(),VipManageActivity.class);
                break;
            case "10503"://订单管理
                UIUtils.startActivity(getActivity(),OrderManageActivity.class);
                break;
            case "10504"://会员订单
                UIUtils.startActivity(getActivity(),VipOrderActivity.class);
                break;
            case "10506"://套餐活动订单
                UIUtils.startActivity(getActivity(),MealDoingOrderActivity.class);
                break;
            case "10508"://红包发放记录
                UIUtils.startActivity(getActivity(),RedPacketsHistoryActivity.class);
                break;
            case "10509"://财务管理
                UIUtils.startActivity(getActivity(),FinanceManageActivity.class);
                break;
            case "10701"://报告统计
                UIUtils.startActivity(getActivity(),ReportCountActivity.class);
                break;
            case "10705"://评价分析
                UIUtils.startActivity(getActivity(),CommentAnalysisActivity.class);
                break;
            case "10805"://自由身
                getActivity().finish();
                break;
        }
    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
            it = new Intent(mContext, CaptureActivity.class);
            it.putExtra("isAddFriends",false);
            it.putExtra("isEntry",true);
            startActivity(it);
        }else {
            EasyPermissions.requestPermissions(this,"您需要开启相机权限", AppConfig.PERMISSIONCODE,
                    Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AlertView(null, "使用扫一扫功能，需打开相机权限？", "取消", null, new String[]{"确定"}, mContext,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    JumpPermissionManagement.GoToSetting(getActivity());
                }
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,ShopHomeFragment.this);
    }
}
