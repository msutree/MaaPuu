package com.maapuu.mereca.util;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.maapuu.mereca.callback.ImageLoadCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetBitmapResControl {

    private Activity activity;

    public GetBitmapResControl(Activity activity) {
        this.activity = activity;
    }

    public void doGetBitmap(final String url, final ImageLoadCallback<Bitmap> callBack) {
        new Thread() {
            public void run() {
                byte b[] = null;
                try {
                    HttpEntity _data = getImageData(url);//我这里的测试图片传入的是base64内容格式的.
                    if (_data != null) {
//                        b = Base64Util.decode(_data);
                        b= EntityUtils.toByteArray(_data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = null;
                if (b!=null)
                    bitmap= BitmapFactory.decodeByteArray(b, 0, b.length);
                    final Bitmap finalBTM=bitmap;
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (finalBTM == null) {
                            callBack.onError("获取图片失败");
                        } else {
                            ImageView img = new ImageView(activity);
                            img.setImageBitmap(finalBTM);
                            callBack.onSuccess(finalBTM);
                        }
                    }
                });
            };
        }.start();
    }
    //获取网络中的图片内容
    private  HttpEntity getImageData(String url) throws ClientProtocolException, IOException {
        Log.d("getImageData", "URL:" + url);
        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse httpResponse = client.execute(httpget);
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status == HttpStatus.SC_OK) {
            Log.d("getImageData", "status:" + status);
//            String strResult = EntityUtils.toString(httpResponse.getEntity());
            HttpEntity strResult = httpResponse.getEntity();
            return strResult;
        }
        return null;
    }
}