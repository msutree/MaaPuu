package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 配置信息展示
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.webview)
    WebView webView;

    private boolean isHtml=false;
    private String content;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        webView.loadDataWithBaseURL(HttpModeBase.BASE_URL, object.optString("result"), "text/html", "utf-8", null);
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

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText(getIntent().getStringExtra("title"));
        isHtml = getIntent().getBooleanExtra("isHtml",false);
        content= getIntent().getStringExtra("content");

        if(isHtml){
            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    if(!TextUtils.isEmpty(title) && !"about:blank".equals(title)){
//                        txtTitle.setText(title);
                        txtTitle.setText("详情");
                    }
                }
            };

            // 设置setWebChromeClient对象
            webView.setWebChromeClient(wvcc);
        }
    }

    @Override
    public void initData() {
        if(!isHtml){
            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.service_agreement_get(),true);
        }else {
            webView.loadUrl(content);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
//            webView.loadDataWithBaseURL(HttpModeBase.BASE_URL, content, "text/html", "utf-8", null);
        }
    }


    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                finish();
                break;
        }
    }
}
