package com.food.iotsensor.Utility;

import android.app.Activity;

import com.food.iotsensor.Constant;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class ApiCacheUtil {

    //保存
    public static void putApiCache(Activity activity, String key, String value) {
        ACacheUtil.get(activity).put(SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId) + key, value);
    }

    public static String getApiCache(Activity activity, String key) {
        String cache = ACacheUtil.get(activity).getAsString(SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId) + key);
        return cache;
    }
}
