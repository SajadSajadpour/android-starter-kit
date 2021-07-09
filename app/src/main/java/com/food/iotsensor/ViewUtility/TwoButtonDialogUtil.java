package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;


/**
 * dialog
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class TwoButtonDialogUtil {
    private static DialogUtil dialog;

    //这个是需要监听点击确定后处理点击事件的
    public static void show(Activity activity, String connent, final View.OnClickListener listener) {


        if (!isShow) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_redeem_view, null);
            TextView redeem_connent = (TextView) view.findViewById(R.id.redeem_connent);
            redeem_connent.setText(activity.getString(R.string.redeem_dialog_connent) + " " + connent + " " + activity.getString(R.string.redeem_dialog_second_connent));
            ok = (RelativeLayout) view.findViewById(R.id.redeem_ok);
            RelativeLayout cancel = (RelativeLayout) view.findViewById(R.id.redeem_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 cancel();
                }
            });
            isShow = true;
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
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                  cancel();

                }
            });
            dialog.show();
        }

    }

    //这个是需要监听点击确定后处理点击事件的
    public static void show(Activity activity, String connent, String okString, String cancelString,
                            final View.OnClickListener okListener, final View.OnClickListener cancelListener) {


        if (!isShow) {
            final RelativeLayout ok;
            TextView ok_text, cancel_text;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_redeem_view, null);
            TextView redeem_connent = (TextView) view.findViewById(R.id.redeem_connent);
            redeem_connent.setText(connent);
            ok = (RelativeLayout) view.findViewById(R.id.redeem_ok);
            ok_text = (TextView) view.findViewById(R.id.ok_text);
            ok_text.setText(okString);
            cancel_text = (TextView) view.findViewById(R.id.cancel_text);
            cancel_text.setText(cancelString);
            RelativeLayout cancel = (RelativeLayout) view.findViewById(R.id.redeem_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelListener.onClick(v);
              cancel();
                }
            });
            isShow = true;
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
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    okListener.onClick(v);
                  cancel();

                }
            });
            dialog.show();
        }

    }


    public static void cancel() {
        isShow = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    //是否有
    private static boolean isShow = false;


}
