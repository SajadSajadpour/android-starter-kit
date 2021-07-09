package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;


/**
 * dialog
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class RewardsDialogUtil {
    private static DialogUtil dialog;

    //这个是需要监听点击确定后处理点击事件的
    public static void show(Activity activity) {


        if(!isShow) {
            final FontTextView rewards_ok_button;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_rewards_view, null);
            rewards_ok_button= (FontTextView) view.findViewById(R.id.rewards_ok_button);

            rewards_ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RewardsDialogUtil.cancel();
                }
            });
            isShow=true;
            dialog = new DialogUtil(activity, view, true);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        isShow = false;
                        //返回true禁止返回键关掉dialog
                        return false;
                    } else {
                        return false;
                    }
                }

            });
            dialog.show();
        }

    }
    public static void cancel() {
        isShow = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog=null;
        }
    }

    //是否有
    private static boolean isShow = false;



}
