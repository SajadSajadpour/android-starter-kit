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
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class FingerPrintDialogUtil {
    private static DialogUtil dialog;
    public static TextView textView;
    //这个是需要监听点击确定后处理点击事件的
    public static void show(Activity activity, final View.OnClickListener listener) {
        if(activity!=null) {
            final RelativeLayout ok;

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_fingerprint_view, null);
            ok = (RelativeLayout) view.findViewById(R.id.ok_button);
            textView = (TextView) view.findViewById(R.id.connent);

            if (!isShow) {
                isShow = true;
                dialog = new DialogUtil(activity, view, true);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            isShow = false;
                            listener.onClick(ok);
                            //返回true禁止返回键关掉dialog
                            return false;
                        } else {
                            isShow = false;
                            return false;
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShow = false;
                        listener.onClick(v);
                        cancel();

                    }
                });
                dialog.show();
            }
        }
    }

    public static void setText(String connent){
        if(textView!=null){
            textView.setText(connent);
        }
    }


    public static void cancel() {
        try {
            isShow = false;
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
        catch (Exception ex){
            System.out.println("Expectation for finger dialog" + ex);

        }
    }

    //是否有
    private static boolean isShow = false;


}
