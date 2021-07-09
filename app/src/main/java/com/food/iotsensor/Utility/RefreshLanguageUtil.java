package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


import com.food.iotsensor.Constant;
import com.food.iotsensor.DataManager;

import java.util.Locale;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class RefreshLanguageUtil {
    private static RefreshLanguageUtil refreshLanguageUtil;
    public static RefreshLanguageUtil getInstance() {
        if (refreshLanguageUtil == null) {
            refreshLanguageUtil = new RefreshLanguageUtil();
        }

        return refreshLanguageUtil;
    }

    public void toRefresh(Activity activity, String language){
        if(!(null==language)&&!language.equals("")) {
            SharedPreferencesUtils.getInstance(activity).setData(Constant.userLocale, language);
            DataManager.UserLocale = language;
            Locale locale = new Locale(language);
            Resources resources = activity.getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }
}
