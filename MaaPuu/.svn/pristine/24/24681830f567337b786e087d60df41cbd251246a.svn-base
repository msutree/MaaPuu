package com.maapuu.mereca.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.LoginActivity;
import com.maapuu.mereca.activity.MyFriendsActivity;
import com.maapuu.mereca.activity.PublishTrendsActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.fragment.foundchild.FoundFriendsFragment;
import com.maapuu.mereca.fragment.foundchild.FoundNearbyPersonFragment;
import com.maapuu.mereca.fragment.foundchild.FoundNearbyShopFragment;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/1/11.
 */

public class FoundFragment extends BaseFragment {
    @BindView(R.id.txt_nearby_shop)
    TextView txtNearbyShop;
    @BindView(R.id.txt_nearby_person)
    TextView txtNearbyPerson;
    @BindView(R.id.txt_friends)
    TextView txtFriends;
    @BindView(R.id.txt_person)
    TextView txtPerson;
    @BindView(R.id.txt_camera)
    TextView txtCamera;

    private FragmentManager fragmentManager;
    private FoundNearbyShopFragment shopFragment;
    private FoundNearbyPersonFragment personFragment;
    private FoundFriendsFragment friendsFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(getActivity()).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true,0f).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ImmersionBar.with(getActivity()).fitsSystemWindows(true).statusBarDarkFont(true,0f).init();
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_found;
    }

    @Override
    protected void initView(View v) {
        txtPerson.setTypeface(StringUtils.getFont(mContext));
        txtCamera.setTypeface(StringUtils.getFont(mContext));
        fragmentManager = getChildFragmentManager();
        addFragment(0);
    }

    @Override
    protected void initData() {
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
        if (friendsFragment != null) {
            transaction.hide(friendsFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (shopFragment == null) {
                    shopFragment = new FoundNearbyShopFragment();
                    fragmentTransaction.add(R.id.fl_container, shopFragment);
                } else {
                    fragmentTransaction.show(shopFragment);
                }
                break;
            case 1:
                if (personFragment == null) {
                    personFragment = new FoundNearbyPersonFragment();
                    fragmentTransaction.add(R.id.fl_container, personFragment);
                } else {
                    fragmentTransaction.show(personFragment);
                }
                break;
            case 2:
                if(LoginUtil.getLoginState()){
                    if (friendsFragment == null) {
                        friendsFragment = new FoundFriendsFragment();
                        fragmentTransaction.add(R.id.fl_container, friendsFragment);
                    } else {
                        fragmentTransaction.show(friendsFragment);
                    }
                }else {
                    it = new Intent(mContext,LoginActivity.class);
                    startActivity(it);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    @OnClick({R.id.txt_nearby_shop,R.id.txt_nearby_person,R.id.txt_friends,R.id.txt_person,R.id.txt_camera})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_nearby_shop:
                txtNearbyShop.setTextSize(20);txtNearbyShop.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                txtNearbyPerson.setTextSize(15);txtNearbyPerson.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                txtFriends.setTextSize(15);txtFriends.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                addFragment(0);
                break;
            case R.id.txt_nearby_person:
                txtNearbyShop.setTextSize(15);txtNearbyShop.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                txtNearbyPerson.setTextSize(20);txtNearbyPerson.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                txtFriends.setTextSize(15);txtFriends.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                addFragment(1);
                break;
            case R.id.txt_friends:
                txtNearbyShop.setTextSize(15);txtNearbyShop.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                txtNearbyPerson.setTextSize(15);txtNearbyPerson.setTextColor(getActivity().getResources().getColor(R.color.text_b2));
                txtFriends.setTextSize(20);txtFriends.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                addFragment(2);
                break;
            case R.id.txt_person:
                if(LoginUtil.getLoginState()){
                    startActivity(new Intent(mContext, MyFriendsActivity.class));
                }else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                break;
            case R.id.txt_camera:
                if(LoginUtil.getLoginState()){
                    NiceDialog.init().setLayoutId(R.layout.pop_ewm_more)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                    holder.setText(R.id.txt_save_pic,"发布图片动态");
                                    holder.setText(R.id.txt_share,"发布视频动态");
                                    holder.setOnClickListener(R.id.txt_cancel, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    holder.setOnClickListener(R.id.txt_save_pic, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            it = new Intent(mContext, PublishTrendsActivity.class);
                                            it.putExtra("type",0);
                                            startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                                        }
                                    });
                                    holder.setOnClickListener(R.id.txt_share, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            it = new Intent(mContext, PublishTrendsActivity.class);
                                            it.putExtra("type",1);
                                            startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                                        }
                                    });
                                }
                            })
                            .setOutCancel(true).setShowBottom(true).setHeight(155)
                            .show(getChildFragmentManager());
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                if(friendsFragment != null){
                    friendsFragment.refresh();
                }
                break;
        }
    }
}
