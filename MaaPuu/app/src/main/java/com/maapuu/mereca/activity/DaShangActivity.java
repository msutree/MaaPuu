package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.view.MyGridView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class DaShangActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.grid_view)
    MyGridView gridView;

    private List<String> list;
    private QuickAdapter<String> adapter;
    private BaseRecyclerAdapter<String> hAdapter;//横向的

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashan);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("打赏");
        txtRight.setText("全选");txtRight.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        list.add("");list.add("");list.add("");
        list.add("");list.add("");

        adapter = new QuickAdapter<String>(mContext,R.layout.layout_dashan_grid_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
            }
        };
        gridView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_confirm_dashang})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_confirm_dashang:
                popdashang();
                break;
        }
    }

    private void popdashang(){
        NiceDialog.init().setLayoutId(R.layout.pop_dashan)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        ImageView ivClose = holder.getView(R.id.iv_close);
                        TextView txtPay = holder.getView(R.id.txt_pay);
                        RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                        List<String> hList = new ArrayList<String>();
                        hList.add("");hList.add("");hList.add("");hList.add("");hList.add("");hList.add("");
                        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
                        hAdapter = new BaseRecyclerAdapter<String>(mContext,hList,R.layout.layout_pop_dashan_item) {
                            @Override
                            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {
                            }
                        };
                        recyclerView.setAdapter(hAdapter);
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        txtPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                popPay();
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(false).setHeight(300).setWidth(300)
                .show(getSupportFragmentManager());
    }

    private void popPay(){
        NiceDialog.init().setLayoutId(R.layout.pop_pay)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        ImageView ivClose = holder.getView(R.id.iv_close);
                        final TextView txtAlipay = holder.getView(R.id.txt_alipay);
                        final TextView txtWxpay = holder.getView(R.id.txt_wxpay);
                        final TextView txtYue = holder.getView(R.id.txt_yue);
                        TextView txtConfirmPay = holder.getView(R.id.txt_confirm_pay);
                        txtAlipay.setSelected(true);
                        txtAlipay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                txtAlipay.setSelected(true);txtWxpay.setSelected(false);txtYue.setSelected(false);
                            }
                        });
                        txtWxpay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                txtAlipay.setSelected(false);txtWxpay.setSelected(true);txtYue.setSelected(false);
                            }
                        });
                        txtYue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                txtAlipay.setSelected(false);txtWxpay.setSelected(false);txtYue.setSelected(true);
                            }
                        });
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        txtConfirmPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(true).setHeight(300)
                .show(getSupportFragmentManager());
    }
}
