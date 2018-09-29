package com.maapuu.mereca.background.employee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolex.circleprogresslibrary.CircleProgressView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.SrvDetailActivity;
import com.maapuu.mereca.background.employee.bean.MySrvDetailBean;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务详情 工作中 已完成
 * Created by Jia on 2018/3/6.
 */

public class ServiceDetailActivity extends BaseActivity{

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.sd_arrow_ic)
    TextView arrowIc;

    @BindView(R.id.client_icon)
    CircleImgView clientIcon;
    @BindView(R.id.client_name)
    TextView clientNameTv;
    @BindView(R.id.item_name)
    TextView itemNameTv;
    @BindView(R.id.pay_amount)
    TextView payAmountTv;

    @BindView(R.id.order_no)
    TextView orderNoTv;//订单编号
    @BindView(R.id.pay_type)
    TextView payTypeTv;
    @BindView(R.id.create_time_text)
    TextView createTimeTv;

    @BindView(R.id.srv_img)
    CircleImgView srvImgIv;//服务类型
    @BindView(R.id.client_icon2)
    CircleImgView clientIcon2;
    @BindView(R.id.sd_srv_status)
    TextView srvStatusTv;

    @BindView(R.id.sd_working_lt)
    LinearLayout workingLt;
    @BindView(R.id.sw_timer_tv)
    TextView timerTv;
    @BindView(R.id.circleProgressView)
    CircleProgressView circleProgressView;

    @BindView(R.id.sw_srv_duration_tv)
    TextView srvDurationTv;//服务时长
    @BindView(R.id.sw_tc_tv)
    TextView srvTcTv;//提成

    @BindView(R.id.sd_finished_lt)
    LinearLayout finishedLt;
    @BindView(R.id.sd_reward_tv)
    TextView rewardTv;//奖励，提成
    @BindView(R.id.sd_used_time)
    TextView usedTimeTv;

    @BindView(R.id.sd_bottom_lt)
    LinearLayout bottomLt;
    @BindView(R.id.sd_finished_tv)
    TextView finishTv;

    int selectedIndex = 0;

    String appoint_srv_id = "";//预约服务id
    List<String> reasons;//取消原因
    String code2d_id = "";//消费码id

    String date = "";

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.em_activity_service_detail);
    }

    @Override
    public void initView() {
        getBundle();

        txtLeft.setTypeface(StringUtils.getFont(mContext));
        arrowIc.setTypeface(StringUtils.getFont(mContext));
        txtRight.setVisibility(View.VISIBLE);
    }

    private void getBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            selectedIndex = bundle.getInt("selectedIndex",0);
            appoint_srv_id = bundle.getString("appoint_srv_id","");
            if(selectedIndex == 0){
                //工作中
                txtTitle.setText("工作中");
                txtRight.setText("取消服务");
                txtRight.setTextSize(12);
                bottomLt.setVisibility(View.VISIBLE);
                workingLt.setVisibility(View.VISIBLE);
                finishedLt.setVisibility(View.GONE);
            } else {
                //已完成
                txtTitle.setText("已完成");
                srvStatusTv.setText("已完成");
                txtRight.setText("服务单");
                txtRight.setTextSize(12);
                workingLt.setVisibility(View.GONE);
                finishedLt.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initData() {
        getSrvDetail();
    }

    private void getSrvDetail() {
        if(selectedIndex == 0){//工作中服务详情
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                    UrlUtils.srv_work_detail_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), appoint_srv_id), true);

        } else if(selectedIndex == 1){//已完成服务详情
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                    UrlUtils.srv_work_complete_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), appoint_srv_id), true);
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
                            MySrvDetailBean bean = FastJsonTools.getPerson(object.optString("result"), MySrvDetailBean.class);
                            if (bean != null) {
                                setUI(bean);
                            }

                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_REQUESTCODE_2:
                //取消服务
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject js = object.optJSONObject("result");
                        String code2d_id = js.optString("code2d_id");

                        //跳转到服务单详情 界面
                        Intent intent = new Intent(mContext,SrvDetailActivity.class);
                        intent.putExtra("code2d_id",code2d_id);
                        UIUtils.startActivity(mContext,intent);
                        ToastUtil.show(mContext, object.optString("message"));
                        finish();
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case  HttpModeBase.HTTP_REQUESTCODE_3:
                // 完成服务
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        //is_order_complete为0时打开 服务单详情(srv_detail_get) 进入下一个服务；如果为1则表示整个订单的服务都完成了，关闭窗口

                        JSONObject js = object.optJSONObject("result");
                        String code2d_id = js.optString("code2d_id");
                        String is_order_complete = js.optString("is_order_complete");
                        if("0".equals(is_order_complete)){
                            //跳转到服务单详情 界面
                            Intent intent = new Intent(mContext,SrvDetailActivity.class);
                            intent.putExtra("code2d_id",code2d_id);
                            UIUtils.startActivity(mContext,intent);
                            finish();
                        } else if("1".equals(is_order_complete)){
                            Intent intent = new Intent(mContext,MyServiceActivity.class);
                            intent.putExtra("selectedPosition",1);
                            UIUtils.startActivity(mContext,intent);
                            finish();
                            //singleTask 会关闭当前activity上的所有的activity
                        }

                        ToastUtil.show(mContext, object.optString("message"));
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

    private void setUI(MySrvDetailBean bean) {
        MySrvDetailBean.OrderDetailBean detail = bean.getOrder_detail();
        if(detail != null){
            code2d_id = detail.getCode2d_id();
            UIUtils.loadIcon(mContext,detail.getAvatar(),clientIcon);
            clientNameTv.setText(detail.getNick_name());
            itemNameTv.setText(detail.getItem_name());
            payAmountTv.setText("¥"+detail.getPay_amount());

            orderNoTv.setText("订单编号："+detail.getOrder_no());
            payTypeTv.setText("支付方式："+getPayTypeTxt(detail.getPay_type()));
            createTimeTv.setText("下单时间："+detail.getCreate_time_text());
            UIUtils.loadImg(mContext,detail.getAvatar(),clientIcon2);
        }

        MySrvDetailBean.CurSrvBean curSrvBean = bean.getCur_srv();
        if(curSrvBean != null){
            UIUtils.loadImg(mContext,curSrvBean.getSrv_img(),srvImgIv);
            if(!StringUtils.isEmpty(bean.getCur_srv().getSrv_end_time())){
                date = bean.getCur_srv().getSrv_end_time().substring(0,7);
            }

            if(selectedIndex == 0) {
                //工作中
                srvStatusTv.setText("正在为TA"+curSrvBean.getSrv_name());
                srvDurationTv.setText("本次服务不可少于"+curSrvBean.getSrv_duration()+"分钟");

                String tcMoney = "¥"+curSrvBean.getReward();
                String tcMoneyTxt = "服务完成你将获得"+tcMoney+"元的提成";
                srvTcTv.setTextColor(UIUtils.getColor(R.color.text_99));
                SpannableString ss = new SpannableString(tcMoneyTxt);
                // 设置字体大小
                ss.setSpan(new AbsoluteSizeSpan(UIUtils.sp2px(mContext, 15)), tcMoneyTxt.indexOf(tcMoney), 8+tcMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 设置字体颜色
                ss.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.bg_red)), tcMoneyTxt.indexOf(tcMoney), 8+tcMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                srvTcTv.setText(ss);

                //开启计时
                usingTime = curSrvBean.getUsed_time();
                getTimer().schedule(task,0,1000);//延迟0秒执行
            } else {
                //已完成
                srvStatusTv.setText("已完成");
                rewardTv.setText(curSrvBean.getReward()+"");
                if(curSrvBean.getUsed_time()/60 == 0){
                    usedTimeTv.setText("服务已完成，用时："+curSrvBean.getUsed_time()+"秒钟");
                }else {
                    if(curSrvBean.getUsed_time()%60 == 0){
                        usedTimeTv.setText("服务已完成，用时："+curSrvBean.getUsed_time()/60+"分钟");
                    }else {
                        usedTimeTv.setText("服务已完成，用时："+(curSrvBean.getUsed_time()/60+1)+"分钟");
                    }
                }
            }
        }

        //取消原因
        reasons = bean.getReasons();
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.sd_finished_tv,R.id.sd_see_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //取消服务
                if("取消服务".equals(txtRight.getText().toString().trim())){
                    if(reasons != null && reasons.size()>0){
                        showCancelDialog();
                    }
                } else if("服务单".equals(txtRight.getText().toString().trim())){
                    //跳转到服务单详情 界面
                    if(!TextUtils.isEmpty(code2d_id)){
                        Intent intent = new Intent(mContext,SrvDetailActivity.class);
                        intent.putExtra("code2d_id",code2d_id);
                        UIUtils.startActivity(mContext,intent);
                    } else {
                        ToastUtil.show(mContext,"未获取到服务信息");
                    }
                }

                break;

            case R.id.sd_finished_tv:
                showConfirmDialog("完成任务","确定完成任务吗？");

                break;

            case R.id.sd_see_account:
                //查看明细
                it = new Intent(mContext, SalaryDetailActivity.class);
                it.putExtra("uid", LoginUtil.getInfo("uid"));
                it.putExtra("date",date );
                startActivity(it);
//                UIUtils.startActivity(mContext,SalaryDetailActivity.class);

                break;
        }
    }

    private void showCancelDialog() {
        NiceDialog.init().setLayoutId(R.layout.pop_cancel_reason)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView txtCommit = holder.getView(R.id.txt_commit);

                        RecyclerView rv = holder.getView(R.id.cr_rv);
                        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,reasons,R.layout.pop_item_cancel_reason) {
                            @Override
                            public void convert(BaseRecyclerHolder baseHolder, String item, int position, boolean isScrolling) {
                                baseHolder.setText(R.id.cr_title,item);

                            }
                        };
                        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                                getResources().getColor(R.color.background)));
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        rv.setAdapter(adapter);

                        txtCommit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 取消
                                cancelService();

                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    private void showConfirmDialog(final String key,final String msg) {
        NiceDialog.init()
                .setLayoutId(R.layout.nd_layout_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setText(R.id.title,"提示");
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
                                switch (key){
                                    case "完成任务":
                                        completeService();
                                        break;
                                }

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

    //取消服务
    private void cancelService() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,
                UrlUtils.srv_cancel_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), appoint_srv_id), true);
    }

    //完成服务
    private void completeService() {
        //result code2d_id为消费id；is_order_complete为0时打开服务单详情(srv_detail_get)进入下一个服务；如果为1则表示整个订单的服务都完成了，关闭窗口
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,
                UrlUtils.complete_srv_set(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"), appoint_srv_id), true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    //计时
    Timer timer;
    Handler timerHandler;
    TimerTask task;

    long usingTime;//使用时间  单位：秒
    private Timer getTimer(){
        if(timer == null){
            timer = new Timer();
        }

        if(timerHandler == null){
            timerHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        setTime();
                    }
                    super.handleMessage(msg);
                }
            };
        }

        if(task == null){
            task = new TimerTask() {
                @Override
                public void run() {
                    // 需要做的事:发送消息
                    Message message = new Message();
                    message.what = 1;
                    timerHandler.sendMessage(message);
                }
            };
        }

        return timer;
    }

    private void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer = null;
            timerHandler = null;
            task = null;
        }
    }

    private String formatTime(long time){
        if(time<10){
            return "0"+time;
        }
        return time+"";
    }

    private void setTime(){
        usingTime++;
        if(usingTime < 0){//防止延迟导致计时为负数
            usingTime = 0;
        }

        long hour = usingTime%(24*60*60)/(60*60);
        long minute = usingTime %(24*60*60)%(60*60)/60;
        long second = usingTime %(24*60*60)%(60*60)%60;

        circleProgressView.setProgress(second);
        timerTv.setText(formatTime(hour)+":"+formatTime(minute)+":"+formatTime(second));
    }

    private String getPayTypeTxt(int payType){
        String type = payType+"";
        switch (payType){
            case 1:
                type = "支付宝支付";
                break;
            case 2:
                type = "微信支付";
                break;
            case 3:
                type = "余额支付";
                break;
        }
        return type;
    }
}
