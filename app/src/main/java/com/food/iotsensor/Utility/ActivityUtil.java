package com.food.iotsensor.Utility;

import android.app.Activity;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class ActivityUtil {
    private static Activity mActivity;
    private static Activity mainActivity;

    public static void init(Activity activity) {
        mActivity = activity;
    }

    public static Activity getActivity(){
        return mActivity;
    }

    public static void initMainActivity(Activity activity) {
        mainActivity = activity;
    }

    public static Activity getMainActivity(){
        return mainActivity;
    }

}
