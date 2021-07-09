package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class OopsViewDialog {

    private static OopsViewDialog oopsViewDialog;
    private DialogUtil dialog;
    public static OopsViewDialog getInstance() {
        oopsViewDialog = new OopsViewDialog();
        return oopsViewDialog;
    }


    public void show(Activity activity,String tx_connent,String tx_second_content) {
        if(activity!=null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_oops_erro_base, null);
            dialog = new DialogUtil(activity, view, true);
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialog.show();
            RelativeLayout body = (RelativeLayout) view.findViewById(R.id.body);
            body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            TextView content = (TextView) view.findViewById(R.id.oop_content);
            content.setText(tx_connent);
            TextView second_content = (TextView) view.findViewById(R.id.oop_second_content);
            second_content.setText(tx_second_content);
        }
    }

    public void showFinishDialog(final Activity activity,String tx_connent,String tx_second_content) {
        if(activity!=null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_oops_erro_base, null);
            dialog = new DialogUtil(activity, view, true);
            Window dialogWindow = dialog.getWindow();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {
                        dialog.cancel();
                        activity.finish();
                        //返回true禁止返回键关掉dialog
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            });
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialog.show();
            RelativeLayout body = (RelativeLayout) view.findViewById(R.id.body);
            body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    activity.finish();
                }
            });
            TextView content = (TextView) view.findViewById(R.id.oop_content);
            content.setText(tx_connent);
            TextView second_content = (TextView) view.findViewById(R.id.oop_second_content);
            second_content.setText(tx_second_content);
        }
    }


}
