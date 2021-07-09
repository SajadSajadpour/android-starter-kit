package com.food.iotsensor.Utility;

import android.content.Context;

import com.food.iotsensor.Constant;
import com.food.iotsensor.DataManager;
import com.food.iotsensor.Network.NetWorkUtil;
import com.food.iotsensor.Network.gsonutil.model.NetWorkCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class RegisteredChannelsUtil {
    public final static String isReturnChannelKey = "isReturnChannelKey";
    public final static String reportServerSuccess = "reportServerSuccess";


    public static void toReportServer(final Context context) {
        if (true) {
            LogUtil.d("integrate", "freeze --> wMemberRegisterCampaign");
            return;
        }
        String isReturnChanne = SharedPreferencesUtils.getInstance(context).getData(isReturnChannelKey, "false");
        String reportServer=SharedPreferencesUtils.getInstance(context).getData(RegisteredChannelsUtil.reportServerSuccess, "false");
        if( !reportServer.contentEquals("true")) {
           if (isReturnChanne.contentEquals("true")) {
               HashMap<String, String> param = new HashMap<String, String>();
               param.put("campaign", SharedPreferencesUtils.getInstance(context).getData("campaign", "organic"));
               param.put("device_id", Installations.id(context));
               String time = TimeUtil.getTimeMillis();
               String sign = MapSortUtil.getInstance().getNetWorkMd5String(param, time);
               param.put("time", time);
               param.put("sign", sign);
               String url = Constant.API_Target_url + "wMemberRegisterCampaign";
               Map<String, String> header = new HashMap<>();
               header.put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
               header.put("key", Constant.API_key);
               header.put("Auth-Token", DataManager.AuthTokenHolder);
               NetWorkUtil.getInstance().startPostResponse(url, param, header, new NetWorkCallback() {
                   @Override
                   public void onResponse(Object response) {
                       String data = (String) response;
                       try {
                           JSONObject json = new JSONObject(data);
                           if (json.getInt("status") == 200) {
                               SharedPreferencesUtils.getInstance(context).setData(RegisteredChannelsUtil.reportServerSuccess, "true");

                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onFailure(String message) {

                   }
               });

           }
       }
    }
}
