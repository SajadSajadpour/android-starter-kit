package com.food.iotsensor.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.food.iotsensor.Network.gsonutil.model.NetWorkCallback;
import com.food.iotsensor.Utility.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.squareup.okhttp.Request;


import java.util.Map;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class NetWorkUtil {
    /**
     *（WLAN、3G/2G
     *
     * @param context Context
     * @return true
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    private static NetWorkUtil netWorkUtil;

    public static NetWorkUtil getInstance() {

        if (netWorkUtil == null) {
            netWorkUtil = new NetWorkUtil();
        }
        return netWorkUtil;
    }

    public void startGetResponse(final String url, Map<String, String> params, Map<String, String> header, final NetWorkCallback callback) {
        LogUtil.d("integrate", url);
        LogUtil.d("integrate", params.toString());
        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .headers(header)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtil.d("integrate", url + "\n" + e.getLocalizedMessage());
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtil.d("integrate", url + "\n" + response);
                        callback.onResponse(response);

                    }
                });
    }

    public void startPostResponse(final String url, Map<String, String> params, Map<String, String> header, final NetWorkCallback callback) {
        LogUtil.d("integrate", url);
        LogUtil.d("integrate", params.toString());

        OkHttpUtils
                .post()
                .url(url)
                .params(params)
                .headers(header)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtil.d("integrate", url + "\n" + e.getLocalizedMessage());
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {

                        LogUtil.d("integrate", url + "\n" + response);
                        callback.onResponse(response);
                    }
                });
    }
}
