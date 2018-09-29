package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.LeftListViewAdapter;
import com.maapuu.mereca.background.shop.bean.CutBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.bean.GoodsBean;
import com.maapuu.mereca.callback.LeftCallBack;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.fragment.HomeFragment;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FloatingItemDecoration;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.DisplayUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/8/4.
 * 选择项目
 */

public class ChooseItemsActivity extends BaseActivity implements LeftCallBack {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycler_view_left)
    RecyclerView listViewLeft;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_cut)
    TextView txtCut;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;

    private List<CatalogBean> list;
    private List<GoodsBean> goods;
    private List<Integer> catenums;//每个分类的个数
    private LeftListViewAdapter leftAdapter;
    private BaseRecyclerAdapter<GoodsBean> adapter;
    private BaseRecyclerAdapter<GoodsBean> bottomAdapter;
    private FloatingItemDecoration floatingItemDecoration;
    private Map<Integer,String> keys=new HashMap<>();//存放所有key的位置和内容

    private String shop_id;
    private LinearLayoutManager mLinearLayoutManager;

    private View popView;
    private PopupWindow popupWindow;
    private List<GoodsBean> beans = new ArrayList<>();
    private List<CutBean> cuts = new ArrayList<>();
    private double cut_amount = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_items);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择项目");
        list = new ArrayList<>();
        catenums = new ArrayList<>();
        goods = new ArrayList<>();
        shop_id = getIntent().getStringExtra("shop_id");

        listViewLeft.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        mLinearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        floatingItemDecoration = new FloatingItemDecoration(mContext,mContext.getResources().getColor(R.color.white),0.5f,0.5f);
        floatingItemDecoration.setHeadBackground(mContext.getResources().getColor(R.color.white));
        floatingItemDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics()));
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.project_list_new_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id,1),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.optJSONArray("result").length() > 0){
                            list = FastJsonTools.getPersons(object.optString("result"),CatalogBean.class);
                        }
                        if(object.has("fullcutset") && object.optJSONArray("fullcutset").length() > 0){
                            cuts = FastJsonTools.getPersons(object.optString("fullcutset"),CutBean.class);
                            txtCut.setVisibility(View.VISIBLE);
                            setCut();
                        }else {
                            txtCut.setVisibility(View.GONE);
                        }
                        setAdapter();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        goods.clear();
        catenums.clear();
        keys.clear();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++){
            keys.put(goods.size(),list.get(i).getCatalog_name());
            catenums.add(list.get(i).getItems().size());
            goods.addAll(list.get(i).getItems());
            if(i == 0){
                map.put(i, 1);
            }else {
                map.put(i, 0);
            }
        }
        leftAdapter = new LeftListViewAdapter(mContext, list, this);
        leftAdapter.setMap(map);
        listViewLeft.setAdapter(leftAdapter);
        if(adapter == null){
            floatingItemDecoration.setKeys(keys);
            recyclerView.addItemDecoration(floatingItemDecoration);
            adapter = new BaseRecyclerAdapter<GoodsBean>(mContext,goods,R.layout.layout_choose_items_item) {
                @Override
                public void convert(final BaseRecyclerHolder holder, GoodsBean bean, final int position, boolean isScrolling) {
                    holder.setImage(R.id.iv_goods,bean.getItem_img(),false);
                    holder.setText(R.id.txt_goods_name,bean.getItem_name());
                    holder.setText(R.id.txt_price,"¥"+bean.getPrice());
                    holder.setText(R.id.txt_sale_num,"已售"+bean.getSale_num()+"件");
                    holder.setText(R.id.txt_market_price,"门市价  ¥"+bean.getMarket_price());
                    holder.setTextFlags(R.id.txt_market_price, Paint.STRIKE_THRU_TEXT_FLAG);
                    if(bean.getPrice().equals(bean.getMarket_price()) || Double.parseDouble(bean.getPrice()) == Double.parseDouble(bean.getMarket_price())){
                        holder.setVisible(R.id.txt_market_price,false);
                    }else {
                        holder.setVisible(R.id.txt_market_price,true);
                    }
                    holder.setText(R.id.txt_goods_num,""+bean.getNum());
                    if(bean.getNum() == 0){
                        holder.setVisible(R.id.iv_minus,false);
                        holder.setVisible(R.id.txt_goods_num,false);
                    }else {
                        holder.setVisible(R.id.iv_minus,true);
                        holder.setVisible(R.id.txt_goods_num,true);
                    }

                    holder.setOnClickListener(R.id.iv_minus, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goods.get(position).setNum(goods.get(position).getNum()-1);
                            notifyItemChanged(position);
                            setCut();
                            txtTotalAmount.setText("¥"+StringUtils.formatDouble(getTotalAmount()));
                        }
                    });
                    holder.setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goods.get(position).setNum(goods.get(position).getNum()+1);
                            notifyItemChanged(position);
                            setCut();
                            txtTotalAmount.setText("¥"+StringUtils.formatDouble(getTotalAmount()));
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        }else {
            mLinearLayoutManager.scrollToPositionWithOffset(0, 0);
            adapter.notifyDataSetChanged();
        }
//        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position) {
//                it = new Intent(mContext,GoodsDetailActivity.class);
//                it.putExtra("type",2);
//                it.putExtra("shop_id",shop_id);
//                it.putExtra("item_id",goods.get(position).getItem_id());
//                startActivity(it);
//            }
//        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                for (int i = 0; i < catenums.size(); i++) {
                    int total = 0;
                    for (int j = 0; j < i + 1; j++) {
                        total += catenums.get(j);
                    }
                    if (total >= (firstVisibleItem + 1)) {
                        Map<Integer, Integer> map = leftAdapter.getMap();
                        for (int z = 0; z < map.size(); z++) {
                            map.put(z, 0);
                        }
                        map.put(i, 1);
                        leftAdapter.notifyDataSetChanged();
                        moveToCenter(i);
                        break;
                    }
                }
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.iv_cart,R.id.txt_settlement})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.iv_cart:
                showPop(view);
                break;
            case R.id.txt_settlement:
                if(!hasGoods()){
                    ToastUtil.show(mContext,"请选择商品");
                    return;
                }
                if(LoginUtil.getLoginState()){
                    it = new Intent(mContext,ConfirmProjectOrderActivityV2.class);
                    it.putExtra("is_box_buy",1);
                    it.putExtra("is_cart",0);
                    it.putExtra("item_shop_data",itemDatas());
                    startActivityForResult(it, AppConfig.ACTIVITY_RESULTCODE);
                }else {
                    it = new Intent(mContext,LoginActivity.class);
                    startActivity(it);
                }
                break;
        }
    }

    @Override
    public void changeLayout(int position) {
        int total = 0;
        for (int i = 0; i < position; i++) {
            total += catenums.get(i);
        }
        mLinearLayoutManager.scrollToPositionWithOffset(total, 0);
    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = listViewLeft.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - listViewLeft.getHeight() / 2);
            listViewLeft.smoothScrollBy(0, y);
        }
    }

    private double getTotalAmount(){
        double d = 0;
        for (int i = 0; i < goods.size(); i++){
            if(goods.get(i).getNum() != 0){
                d += Double.parseDouble(goods.get(i).getPrice())*goods.get(i).getNum();
            }
        }
        return d;
    }

    private boolean hasGoods(){
        boolean bool = false;
        for (int i = 0; i < goods.size(); i++){
            if(goods.get(i).getNum() != 0){
                bool = true;
                break;
            }
        }
        return bool;
    }

    private String itemDatas(){
        JSONArray array = new JSONArray();
        int pos = 0;
        try {
            for (int i = 0; i < goods.size(); i++){
                if(goods.get(i).getNum() != 0){
                    JSONObject object = new JSONObject();
                    object.put("item_shop_id",goods.get(i).getItem_shop_id());
                    object.put("num",goods.get(i).getNum());
                    array.put(pos,object);
                    pos++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    private void setCut(){
        if(cuts == null || cuts.size() == 0){
            return;
        }
        if(Double.parseDouble(cuts.get(0).getFullcut_amount()) > getTotalAmount()){
            txtCut.setText("满"+cuts.get(0).getFullcut_amount()+"可立减"+cuts.get(0).getCut_amount()
                    +"元,还差"+StringUtils.formatDouble(Double.parseDouble(cuts.get(0).getFullcut_amount())-getTotalAmount())+"元");
            return;
        }
        if(Double.parseDouble(cuts.get(cuts.size()-1).getFullcut_amount()) <= getTotalAmount()){
            cut_amount = Double.parseDouble(cuts.get(cuts.size()-1).getFullcut_amount());
            txtCut.setText("已满"+cuts.get(cuts.size()-1).getFullcut_amount()+"可立减"+cuts.get(cuts.size()-1).getCut_amount() +"元");
            return;
        }
        for (int i = 0; i < cuts.size()-1; i++){
            if(Double.parseDouble(cuts.get(i).getFullcut_amount()) < getTotalAmount() &&
                    Double.parseDouble(cuts.get(i+1).getFullcut_amount()) >= getTotalAmount()){
                cut_amount = Double.parseDouble(cuts.get(i).getFullcut_amount());
                txtCut.setText("满"+cuts.get(i+1).getFullcut_amount()+"可立减"+cuts.get(i+1).getCut_amount()
                        +"元,还差"+StringUtils.formatDouble(Double.parseDouble(cuts.get(i+1).getFullcut_amount())-getTotalAmount())+"元");
//                txtCut.setText("已满"+cuts.get(i).getFullcut_amount()+"可立减"+cuts.get(i).getCut_amount() +"元");
            }
        }
    }

    private void showPop(View v) {
        popView = LayoutInflater.from(mContext).inflate(R.layout.pop_layout_choose_items, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        View view = popView.findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popView.findViewById(R.id.txt_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasGoods()){
                    ToastUtil.show(mContext,"请选择商品");
                    return;
                }
                it = new Intent(mContext,ConfirmProjectOrderActivityV2.class);
                it.putExtra("is_box_buy",1);//原值为0  update 0814 by L
                it.putExtra("item_shop_data",itemDatas());
                startActivityForResult(it, AppConfig.ACTIVITY_RESULTCODE);
            }
        });
        final RecyclerView popRv = popView.findViewById(R.id.pop_rv);
        popRv.setLayoutManager(new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        beans.clear();
        for (int i = 0; i < goods.size(); i++){
            if(goods.get(i).getNum() != 0){
                beans.add(goods.get(i));
                beans.get(beans.size()-1).setPos(i);
            }
        }
        final ViewGroup.LayoutParams lp = popRv.getLayoutParams();
        if (beans.size() > 5) {
            lp.height = DisplayUtil.dip2px(mContext,40 * 5);
        } else {
            lp.height = DisplayUtil.dip2px(mContext,40 * beans.size());
        }
        popRv.setLayoutParams(lp);
        bottomAdapter = new BaseRecyclerAdapter<GoodsBean>(mContext,beans,R.layout.layout_pop_choose_items_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, final GoodsBean item, final int position, boolean isScrolling) {
                holder.setText(R.id.txt_goods_name,item.getItem_name());
                holder.setText(R.id.txt_price,"¥"+item.getPrice());
                holder.setText(R.id.txt_goods_num,""+item.getNum());
                holder.setOnClickListener(R.id.iv_minus, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(beans.get(position).getNum() == 1){
                            beans.remove(position);
                            notifyDataSetChanged();
                            goods.get(item.getPos()).setNum(goods.get(item.getPos()).getNum()-1);
                            if (beans.size() > 5) {
                                lp.height = DisplayUtil.dip2px(mContext,40 * 5);
                            } else {
                                lp.height = DisplayUtil.dip2px(mContext,40 * beans.size());
                            }
                            popRv.setLayoutParams(lp);
                        }else {
                            beans.get(position).setNum(beans.get(position).getNum()-1);
                            notifyItemChanged(position);
                        }
                        adapter.notifyItemChanged(item.getPos());
                        setCut();
                        if(beans.size() == 0){
                            popupWindow.dismiss();
                        }
                        txtTotalAmount.setText("¥"+StringUtils.formatDouble(getTotalAmount()));
                    }
                });
                holder.setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        beans.get(position).setNum(beans.get(position).getNum()+1);
                        notifyItemChanged(position);
//                        goods.get(item.getPos()).setNum(goods.get(item.getPos()).getNum()+1);
                        adapter.notifyItemChanged(item.getPos());
                        setCut();
                        txtTotalAmount.setText("¥"+StringUtils.formatDouble(getTotalAmount()));
                    }
                });
            }
        };
        popRv.setAdapter(bottomAdapter);
        popView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
                    popupWindow.dismiss();
                }
                return false;
            }
        });

        // 设置好参数之后再show
//        popView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        popupWindow.showAtLocation(v, Gravity.TOP, 0,0);
    }
}
