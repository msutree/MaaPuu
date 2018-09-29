package com.maapuu.mereca.background.shop.dialog;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyyoona7.lib.BaseCustomPopup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 订单筛选
 * Created by Jia on 2018/3/8.
 */

public class OrderFilterBack extends BaseCustomPopup implements View.OnClickListener {
    View v;
    TagFlowLayout tagFlowLayout;
    TextView resetTv, confirmTv;

    RadioGroup suggestionRg;
    RadioButton rbs1, rbs2;

    RadioGroup refundRg;
    RadioButton rbr1, rbr2,rbr3;

    Context context;
    TagAdapter adapter;

    List<String> dataList;
    ConfirmCall confirmCall;

    //String shop_id = "0"; //店铺id，全部店铺传0
    //String date = ""; //日期过滤，如：2018-04-18
    String status = ""; //订单状态：待使用,待评价,已完成，状态传中文
    String complaint = ""; //投诉：1投诉建议,2已处理
    String refund = ""; //退款：1退款申请,2退款成功,4商家拒绝

    public OrderFilterBack(Context context, List<String> dataList, ConfirmCall confirmCall) {
        super(context);
        this.context = context;
        this.dataList = dataList;
        this.confirmCall = confirmCall;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.pop_layout_order_filter_back, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        //setFocusAndOutsideEnable(true).setBackgroundDimEnable(true).setDimValue(0.2f);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view) {
        v = getView(R.id.view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        resetTv = getView(R.id.pop_reset_tv);
        confirmTv = getView(R.id.pop_confirm_tv);

        suggestionRg = getView(R.id.pop_rg_suggestion);
        rbs1 = getView(R.id.pop_suggestion_rb);
        rbs2 = getView(R.id.pop_suggestion_rb_finished);

        refundRg = getView(R.id.pop_rg_refund);
        rbr1 = getView(R.id.pop_refund_rb_apply);
        rbr2 = getView(R.id.pop_refund_rb_suc);
        rbr3 = getView(R.id.pop_refund_rb_failed);

        tagFlowLayout = getView(R.id.pop_tag);
        adapter = new TagAdapter<String>(dataList) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };
        tagFlowLayout.setAdapter(adapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                status = dataList.get(position);
                return false;
            }
        });

        resetTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);


        suggestionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbs1.isChecked()) {
                    complaint = "1";
                } else if (rbs2.isChecked()) {
                   complaint = "2";
                }
            }
        });

        refundRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbr1.isChecked()) {
                    refund = "1";
                } else if (rbr2.isChecked()) {
                    refund = "2";
                } else if (rbr3.isChecked()) {
                    refund = "4";
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_reset_tv: //重置
                suggestionRg.clearCheck();
                refundRg.clearCheck();
                Set<Integer> set = new HashSet<>();
                adapter.setSelectedList(set);
                status = "";
                complaint = "";
                refund ="";
                if (confirmCall != null) {
                    confirmCall.onCall(status,complaint,refund);
                }
                dismiss();

                break;
            case R.id.pop_confirm_tv:  // 确定 筛选
                if (confirmCall != null) {
                    confirmCall.onCall(status,complaint,refund);
                }
                dismiss();

                break;
        }
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

    public interface ConfirmCall {
        void onCall(String status, String complaint, String refund);
    }

}
