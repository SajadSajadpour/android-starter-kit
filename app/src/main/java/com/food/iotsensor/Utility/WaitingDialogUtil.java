package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.food.iotsensor.Constant;
import com.food.iotsensor.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class WaitingDialogUtil {


    static DialogUtil dialog;

    private static DialogUtil getInstance(Activity activity) {


        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_view, null);
        SharedPreferencesUtils sharedPreferencesUtils= SharedPreferencesUtils.getInstance(activity);
        String color = Constant.userTheme;
        int colorCode = activity.getResources().getIdentifier(color, "color", activity.getPackageName());
        AVLoadingIndicatorView indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        indicatorView.getIndicator().setColor(ContextCompat.getColor(activity, colorCode));
        dialog = new DialogUtil(activity, view,false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {


                   //返回true禁止返回键关掉dialog
                    return true;
                }
                else
                {
                    return false;
                }
            }

        });
        return dialog;
    }
    //
    private static boolean isShow=false;

    public static void show(Activity activity) {
        if(!isShow && activity!=null) {
            isShow=true;
            try {
                getInstance(activity).show();
            }
            catch (Exception ex){
                System.out.println("WaitingDialogUtil Exception" + ex);
            }
//            getInstance(activity).show();
        }
    }

    public static void cancel() {
        try {
            isShow=false;
            if (dialog != null) {
                dialog.cancel();
                dialog=null;
            }
        }
        catch (Exception ex){

            System.out.println("Expectation for Waiting Dialog util" + ex);
        }
    }


    public static void showNoActionBar(Activity activity) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.loading_view, null);
        SharedPreferencesUtils sharedPreferencesUtils= SharedPreferencesUtils.getInstance(activity);
        String color = "ThemeBlue";
        int colorCode = activity.getResources().getIdentifier(color, "color", activity.getPackageName());
        AVLoadingIndicatorView indicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        indicatorView.getIndicator().setColor(ContextCompat.getColor(activity, colorCode));

        if(!isShow && activity!=null) {
            dialog = new DialogUtil(activity, view);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {


                        //true dialog
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            });
            dialog.show();
        }

    }


}
