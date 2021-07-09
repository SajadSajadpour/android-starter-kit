package com.food.iotsensor.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.food.iotsensor.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferences
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class SharedPreferencesUtils {

    Context c;
    String preferenceName;
    private static SharedPreferencesUtils sharedPreferencesUtils;

    public static SharedPreferencesUtils getInstance(Context context){
        sharedPreferencesUtils=new SharedPreferencesUtils(context, Constant.defaultPreferenceName);
        return sharedPreferencesUtils;
    }
    public SharedPreferencesUtils(Context c, String preferenceName) {
        this.c = c;
        this.preferenceName = preferenceName;
    }

     public void setData(String key, String data) {
        SharedPreferences.Editor editor = c.getSharedPreferences(preferenceName, c.MODE_PRIVATE).edit();
        editor.putString(key, data);
        editor.commit();

    }

     public String getData(String key){

        SharedPreferences prefs = c.getSharedPreferences(preferenceName, c.MODE_PRIVATE);
        String restoredText = prefs.getString(key, "");
        return restoredText;
    }

    //key
    public String getData(String key, String value){

        SharedPreferences prefs = c.getSharedPreferences(preferenceName, c.MODE_PRIVATE);
        String restoredText = prefs.getString(key, value);
        return restoredText;
    }
    /**
     *  JsonList
     * @param tag
     * @param datalist
     */
    public <T> void setJsonList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        // json
        String strJson = gson.toJson(datalist);
        SharedPreferences.Editor editor = c.getSharedPreferences(preferenceName, c.MODE_PRIVATE).edit();
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     *  JsonList
     * @param tag
     * @return
     */
    public <T> List<T> getJsonList(String tag) {
        List<T> datalist=new ArrayList<T>();
        SharedPreferences prefs = c.getSharedPreferences(preferenceName, c.MODE_PRIVATE);
        String strJson = prefs.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }





}
