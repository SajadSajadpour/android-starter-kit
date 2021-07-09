package com.food.iotsensor.Utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class TrackSupport {

    private static String monetary = "USD";

    public TrackSupport() {

    }

    /**
     *
     *
     * @param mMonetary
     */
    public static void setMonetary(String mMonetary) {
        monetary = mMonetary;
    }

    public static void initTrac(Application application, String afKey) {
        AppsFlyerLib.getInstance().startTracking(application, afKey);
    }

    /**
     * 普通应用内事件
     *
     * @param context
     * @param eventName   字符串格式，定义了事件的名称，此名称也可以自定义。事件的命名字符个数需要限制在45个以内
     * @param eventValues 对应MAP格式的事件赋值(即富应用内事件格式), 一个事件可由多个key进行赋值。
     */
    public static void trackEvent(Context context, String eventName, Map<String, Object> eventValues) {

        AppsFlyerLib.getInstance().trackEvent(context.getApplicationContext(), eventName, eventValues);
    }

    /**
     * 收益事件  收益的af_revenue必须为 "AFInAppEventParameterName.REVENUE"
     *
     * @param context
     * @param eventName
     * @param eventValues
     */
    public static void trackRevenueEvent(Context context, String eventName, Map<String, Object> eventValues, double dou) {

        //AppsFlyer
        Map<String, Object> map = new HashMap<>();
        map.put(AFInAppEventParameterName.REVENUE, dou);

        Iterator<Map.Entry<String, Object>> it = eventValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            map.put(entry.getKey(), entry.getValue());
        }

        AppsFlyerLib.getInstance().trackEvent(context.getApplicationContext(), eventName, map);
    }

    /**
     * appsflyer深度追踪
     *
     * @param activity
     */
    public static void reTargeting(Activity activity) {
        AppsFlyerLib.getInstance().sendDeepLinkData(activity);

    }
}


