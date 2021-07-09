package com.food.iotsensor.Utility;

import android.app.Activity;
import android.widget.Toast;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class ToastUtil {

    public static void makeText(Activity activity, String message){

       Toast.makeText(activity,message, Toast.LENGTH_LONG).show();
    }

    public static void makeText(Activity activity, int src){

        Toast.makeText(activity,activity.getText(src), Toast.LENGTH_LONG).show();
    }

    public static void makeShortText(Activity activity, String message){

        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
    }

    public static void makeShortText(Activity activity, int src){

        Toast.makeText(activity,activity.getText(src), Toast.LENGTH_SHORT).show();
    }

}
