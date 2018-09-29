package com.maapuu.mereca.background.shop.dialog;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.UIUtils;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.zyyoona7.lib.BaseCustomPopup;

import java.util.List;


/**
 * 选择商铺
 * Created by Jia on 2018/3/8.
 */

public class ChooseShopFilter extends BaseCustomPopup {

    RecyclerView rv;
    View v;

    Context mContext;
    List<ShopBean> dataList;
    ChooseCall chooseCall;

    public ChooseShopFilter(Context context, List<ShopBean> dataList, ChooseCall chooseCall) {
        super(context);
        this.mContext = context;
        this.dataList = dataList;
        this.chooseCall = chooseCall;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.pop_layout_choose_shop, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view) {
        rv = getView(R.id.pop_rv);
        final ViewGroup.LayoutParams lp = rv.getLayoutParams();
        if (dataList.size() > 5) {
            lp.height = DisplayUtil.dip2px(mContext,40 * 5);
        } else {
            lp.height = DisplayUtil.dip2px(mContext,40 * dataList.size());
        }
        rv.setLayoutParams(lp);
        v = getView(R.id.view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        BaseRecyclerAdapter<ShopBean> adapter = new BaseRecyclerAdapter<ShopBean>(mContext,dataList,R.layout.pop_item_shop) {
            @Override
            public void convert(BaseRecyclerHolder baseHolder, ShopBean item, int position, boolean isScrolling) {
                baseHolder.setText(R.id.cr_title,item.getShop_name());

            }
        };
        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                UIUtils.getColor(R.color.background)));
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {

                chooseCall.onChoose(dataList.get(position));
                dismiss();
            }
        });

    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, a[1] + anchor.getHeight() + 0);
        } else {
            super.showAsDropDown(anchor);
        }
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor, xoff, yoff);
    }

    public interface ChooseCall{
        void onChoose(ShopBean item);
    }

}
