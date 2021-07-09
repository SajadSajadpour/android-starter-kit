package com.food.iotsensor.Utility;

import android.app.Activity;
import android.view.View;

import com.food.iotsensor.R;


/**
 * api json fail
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class ApiFailUtil {
    public static void toGo(final Activity activity){
        RemindDialogUtil.show(activity, activity.getString(R.string.get_data_fail), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SplashActivity.actionStart(activity);
                activity.finish();
            }
        });
    }
}
