package com.food.iotsensor.Network.model;

import com.food.iotsensor.Constant;
import com.food.iotsensor.Network.APIManagerUtil;
import com.food.iotsensor.Network.gsonutil.model.NetWorkCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class Logout extends Response {

    private static final String url= Constant.API_Target_url+"user/newlogout";
    //
    public static void getResponse(NetWorkCallback netWorkCallback) {
        Map<String,String> map=new HashMap<>();

        APIManagerUtil.getInstance().greenPostResponse(url,map,netWorkCallback,Logout.class);

    }
    //
    public static void cancelResponse() {
        RequestCall call = OkHttpUtils.get().url(url).build();
        call.cancel();
    }
}
