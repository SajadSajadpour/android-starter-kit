package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.food.iotsensor.R;


/**
 * oops dialog
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class OopsNetWorkDialog {
    private static DialogUtil mOopsNetWorkDialog;

    //
    public static void show(Activity activity) {
        if (activity != null && mOopsNetWorkDialog == null) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_oops_network_view, null);
            mOopsNetWorkDialog = new DialogUtil(activity, view, true);
            mOopsNetWorkDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //dialog
                        return true;
                    } else {
                        return false;
                    }
                }

            });
            mOopsNetWorkDialog.show();
        }

    }

    public static void cancel() {
        try {
            if (mOopsNetWorkDialog != null && mOopsNetWorkDialog.isShowing()) {
                mOopsNetWorkDialog.dismiss();
                mOopsNetWorkDialog = null;
            }
        }
        catch (Exception ex){

            System.out.println("Expectation for oops network dialog"+ ex);
        }
    }

}
