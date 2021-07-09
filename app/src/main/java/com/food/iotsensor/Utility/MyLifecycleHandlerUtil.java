package com.food.iotsensor.Utility;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class MyLifecycleHandlerUtil implements Application.ActivityLifecycleCallbacks {
    public static boolean isAppForeground = false;
    private int mCount;
    public static boolean isDialog = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mCount++;
        if (mCount == 1) {
            LogUtil.d("app状态", "app到前台了");
            if (!LoginAgainUtil.getInstance().isLoginAgain(activity)) {
                isAppForeground = true;
            }
        }


    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

        mCount--;
        // mCount==0，
        if (mCount == 0) {
            isAppForeground = false;
            //
            LogUtil.d("app状态", "到后台了");
        }

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
}
