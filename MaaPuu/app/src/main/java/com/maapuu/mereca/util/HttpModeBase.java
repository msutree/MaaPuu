package com.maapuu.mereca.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.activity.MainActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.AppManager;
import com.maapuu.mereca.constant.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import info.wangchen.simplehud.SimpleHUD;

/**
 * 网络请求封装类利用 Handler回调实现
 *
 * @author Ren
 */
public class HttpModeBase<T> {

    public static final String BASE_URL = "http://beauty.whhxrc.com/";

    public static final String GET_BASE_URL = "api/api.hxrc.php?version=v1&app_id=1&vars=";

    public static final String POST_BASE_URL = "api/api.hxrc.php";// Ctrl+Shift+X小写转大写

    public static final String VERSION = "v1";
    public static final String APP_ID = "1";

    /* 请求ID */
    public static final int HTTP_ERROR = 0x00100;
    public static final int HTTP_REQUESTCODE_1 = 0x00101;
    public static final int HTTP_REQUESTCODE_2 = 0x00102;
    public static final int HTTP_REQUESTCODE_3 = 0x00103;
    public static final int HTTP_REQUESTCODE_4 = 0x00104;
    public static final int HTTP_REQUESTCODE_5 = 0x00105;
    public static final int HTTP_REQUESTCODE_6 = 0x00106;
    public static final int HTTP_REQUESTCODE_7 = 0x00107;
    public static final int HTTP_REQUESTCODE_IMG = 0x01101;
    public static final int HTTP_REQUESTCODE_VIDEO = 0x01102;
    public static final int HTTP_REQUESTCODE_IMG_MUL = 0x01103;

    private Handler mHandler;
    private Context mContext;
    private Message msg;

    public HttpModeBase(Handler mHandler, Context mContext) {
        super();
        this.mHandler = mHandler;
        this.mContext = mContext;
        this.msg = mHandler.obtainMessage();
    }

    /**
     * xutilsPost 异步的
     * @param typeID
     * @param json
     * @return
     */
    public void xPost(final int typeID,final JSONObject json, final Boolean isProgress) {
        boolean isConnected = NetWorkUtils.isConnected(mContext);
        if (isConnected) {
            if (isProgress) {
                SimpleHUD.showLoadingMessage(mContext, "加载中...", AppConfig.isCanCancle);
            }
//            try {
//                json.put("token",LoginUtil.getInfo("token"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            RequestParams params = new RequestParams(BASE_URL + POST_BASE_URL);
            if(json.optString("action").equals("mo_comment_set ") || json.optString("action").equals("mo_publish_set") ||
                    json.optString("action").equals("circle_sub_comment_set") || json.optString("action").equals("circle_comment_set") ||
                    json.optString("action").equals("circle_publish_set") || json.optString("action").equals("order_evl_set") ||
                    json.optString("action").equals("order_commodity_evl_set") || json.optString("action").equals("s_evalution_reply_set") ||
                    json.optString("action").equals("s_commgr_set") || !AppConfig.isEncrypt){
                params.addBodyParameter("vars", json.toString());
                params.addBodyParameter("version", VERSION);
                params.addBodyParameter("app_id", APP_ID);
            }else {
                String request ="vars" + "=" + json.toString() + "&" + "version" + "=" + VERSION  + "&" + "app_id" + "=" + APP_ID  ;
                params.setBodyContent(DESEncrypt.encrypt(request.toString()));
            }
//            if(AppConfig.isEncrypt){
//                String request ="vars" + "=" + json.toString() + "&" + "version" + "=" + VERSION  + "&" + "app_id" + "=" + APP_ID  ;
//                params.setBodyContent(DESEncrypt.encrypt(request.toString()));
//            }else {
//                params.addBodyParameter("vars", json.toString());
//                params.addBodyParameter("version", VERSION);
//                params.addBodyParameter("app_id", APP_ID);
//            }
            params.addHeader("Content-Type", "application/json");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String json) {
                    String result = "";
                    if(StringUtils.isEmpty(DESEncrypt.decrypt(json))){
                        result = json;
                    }else {
                        result = AppConfig.isEncrypt ? DESEncrypt.decrypt(json) : json;
                    }
                    if (result.startsWith("\ufeff")) {
                        result = result.substring(1);
                    }
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.optInt("status") == 2) {
                            new AlertView("提示", "登录状态已过期，回到首页", null, new String[]{"确定"}, null, mContext,
                                    AlertView.Style.Alert, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    if (position == 0) {
                                        LoginUtil.clear(mContext);
                                        if(AppConfig.mIMKit != null){
                                            // openIM SDK提供的登录服务
                                            IYWLoginService mLoginService = AppConfig.mIMKit.getLoginService();
                                            mLoginService.logout(new IWxCallback() {
                                                //此时logout已关闭所有基于IMBaseActivity的OpenIM相关Actiivity，s
                                                @Override
                                                public void onSuccess(Object... arg0) {
                                                    LogUtil.e("------退出登录成功");}

                                                @Override
                                                public void onProgress(int arg0) {}

                                                @Override
                                                public void onError(int arg0, String arg1) {}
                                            });
                                        }
                                        Intent it = new Intent(mContext, MainActivity.class);
                                        mContext.startActivity(it);
                                        AppManager.getAppManager().finishAllActivity();
                                    }
                                }
                            }).show();
                        } else {
                            Message msg = mHandler.obtainMessage();
                            msg.what = typeID;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    if (isProgress) {
                        SimpleHUD.dismiss();
                    }
                    Message msg = new Message();
                    msg.what = HTTP_ERROR;
                    msg.obj = "网络请求有误";
                    mHandler.sendMessage(msg);
                    ex.printStackTrace();// 把错误信息打印出轨迹来
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Message msg = new Message();
                    msg.what = HTTP_ERROR;
                    msg.obj = "网络请求已取消";
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onFinished() {
                    if (isProgress) {
                        SimpleHUD.dismiss();
                    }
                }
            });
        }
    }

    /**
     * xutilsPost请求 异步的
     *
     * @param typeID
     * @param filePath
     * @return
     */
    public void uploadImage(final int typeID, String filePath, final Boolean isProgress) {
        boolean isConnected = NetWorkUtils.isConnected(mContext);
        if (isConnected) {
            if (isProgress) {
                SimpleHUD.showLoadingMessage(mContext, "上传中...", AppConfig.isCanCancle);
            }
            RequestParams params = new RequestParams(BASE_URL + POST_BASE_URL);
            params.addHeader("Content-Type", "application/json");
            params.setConnectTimeout(30 * 1000);
            File file = null;
            try {
                file = BitmapUtils.scal(Uri.parse(filePath));
            } catch (Exception e) {
                file = new File(filePath);
                e.printStackTrace();
            }
            params.addBodyParameter("vars", UrlUtils.cmm_image_save_set().toString());
            params.addBodyParameter("version", VERSION);
            params.addBodyParameter("app_id", APP_ID);
            params.setMultipart(true);
            if (file != null && file.exists()) {
                params.addBodyParameter("image", file);
                Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Message msg = mHandler.obtainMessage();
                                msg.what = typeID;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                if (ex instanceof HttpException) { // 网络错误
                                    HttpException httpEx = (HttpException) ex;
                                    int responseCode = httpEx.getCode();
                                    String responseMsg = httpEx.getMessage();
                                    String errorResult = httpEx.getResult();
                                    // ...
                                } else { // 其他错误
                                    // ...
                                }
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "网络请求有误";
                                mHandler.sendMessage(msg);
                                ex.printStackTrace();// 把错误信息打印出轨迹来
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "上传请求已取消";
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onFinished() {
//                            if (isProgress) {
//                                SimpleHUD.dismiss();
//                            }
                            }
                        }
                );
            } else {
                ToastUtil.show(mContext,"图片路径不存在");
            }
        }
    }
    /**
     * xutilsPost请求 异步的
     *
     * @param typeID
     * @param filePath
     * @return
     */
    public void uploadVideo(final int typeID, String filePath, final Boolean isProgress) {
        boolean isConnected = NetWorkUtils.isConnected(mContext);
        if (isConnected) {
            if (isProgress) {
                SimpleHUD.showLoadingMessage(mContext, "上传中...", AppConfig.isCanCancle);
            }
            RequestParams params = new RequestParams(BASE_URL + POST_BASE_URL);
            params.addHeader("Content-Type", "application/json");
            params.setConnectTimeout(3 * 60 * 1000);
            File file = new File(filePath);
//            try {
//                file = BitmapUtils.scal(Uri.parse(filePath));
//            } catch (Exception e) {
//                file = new File(filePath);
//                e.printStackTrace();
//            }
            params.addBodyParameter("vars", UrlUtils.cmm_file_save_set().toString());
            params.addBodyParameter("version", VERSION);
            params.addBodyParameter("app_id", APP_ID);
            params.setMultipart(true);
            if (file != null && file.exists()) {
                params.addBodyParameter("file", file);
                Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Message msg = mHandler.obtainMessage();
                                msg.what = typeID;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                if (ex instanceof HttpException) { // 网络错误
                                    HttpException httpEx = (HttpException) ex;
                                    int responseCode = httpEx.getCode();
                                    String responseMsg = httpEx.getMessage();
                                    String errorResult = httpEx.getResult();
                                    // ...
                                } else { // 其他错误
                                    // ...
                                }
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "网络请求有误";
                                mHandler.sendMessage(msg);
                                ex.printStackTrace();// 把错误信息打印出轨迹来
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "上传请求已取消";
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onFinished() {
//                            if (isProgress) {
//                                SimpleHUD.dismiss();
//                            }
                            }
                        }
                );
            } else {
                ToastUtil.show(mContext,"文件路径不存在");
            }
        }
    }

    /**
     * xutilsPostList 请求异步的
     * @param typeID
     * @param filePath
     * @return
     */
    public void xutilsPostList(final int typeID,String filePath, final int position, final Boolean isProgress) {
        boolean isConnected = NetWorkUtils.isConnected(mContext);
        if (isConnected) {
            if (isProgress) {
                SimpleHUD.showLoadingMessage(mContext, "上传中...", AppConfig.isCanCancle);
            }
            RequestParams params = new RequestParams(BASE_URL + POST_BASE_URL);
            params.addHeader("Content-Type", "application/json");
            params.setConnectTimeout(30 * 1000);
            File file = null;
            try {
                file = BitmapUtils.scal(Uri.parse(filePath));
            } catch (Exception e) {
                file = new File(filePath);
                e.printStackTrace();
            }
            params.addBodyParameter("vars", UrlUtils.cmm_image_save_set().toString());
            params.addBodyParameter("version", VERSION);
            params.addBodyParameter("app_id", APP_ID);
            params.setMultipart(true);
            if (file != null && file.exists()) {
                params.addBodyParameter("image", file);
                Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Message msg = mHandler.obtainMessage();
                                msg.what = typeID;
                                msg.obj = result;
                                msg.arg1 = 1;//1为上传返回
                                msg.arg2 = position;
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                if (ex instanceof HttpException) { // 网络错误
                                    HttpException httpEx = (HttpException) ex;
                                    int responseCode = httpEx.getCode();
                                    String responseMsg = httpEx.getMessage();
                                    String errorResult = httpEx.getResult();
                                    // ...
                                } else { // 其他错误
                                    // ...
                                }
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "网络请求有误";
                                msg.arg1 = 1;//1为上传返回
                                msg.arg2 = position;
                                mHandler.sendMessage(msg);
                                ex.printStackTrace();// 把错误信息打印出轨迹来
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                                Message msg = new Message();
                                msg.what = HTTP_ERROR;
                                msg.obj = "上传请求已取消";
                                msg.arg1 = 1;//1为上传返回
                                msg.arg2 = position;
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onFinished() {
//                            if (isProgress) {
//                                SimpleHUD.dismiss();
//                            }
                            }
                        }
                );
            } else {
                ToastUtil.show(mContext, "图片路径不存在");
            }
        }
    }
}
