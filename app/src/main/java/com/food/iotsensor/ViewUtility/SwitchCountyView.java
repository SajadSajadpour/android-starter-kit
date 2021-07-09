package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class SwitchCountyView {

    private static SwitchCountyView switchCountyView;
    //马来西亚，中国，越南，菲律宾，泰国，日本，韩国
    private RelativeLayout malaysia, china, vietnam, philippines, thailand, japan, south_korea,cancel;
    private SwitchCountyCallBackListener listener;
    private DialogUtil dialog;

    public static SwitchCountyView getInstance() {
        switchCountyView = new SwitchCountyView();
        return switchCountyView;
    }

    public interface SwitchCountyCallBackListener {
        void returnData(String data);
    }

    public void show(Activity activity, SwitchCountyCallBackListener listener) {
        this.listener = listener;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_switch_county, null);
        dialog = new DialogUtil(activity, view,true);
        malaysia = (RelativeLayout) view.findViewById(R.id.malaysia);
        malaysia.setOnClickListener(chickListener);
        china = (RelativeLayout) view.findViewById(R.id.china);
        china.setOnClickListener(chickListener);
        vietnam = (RelativeLayout) view.findViewById(R.id.vietnam);
        vietnam.setOnClickListener(chickListener);
        philippines = (RelativeLayout) view.findViewById(R.id.philippines);
        philippines.setOnClickListener(chickListener);
        thailand = (RelativeLayout) view.findViewById(R.id.thailand);
        thailand.setOnClickListener(chickListener);
        japan = (RelativeLayout) view.findViewById(R.id.japan);
        japan.setOnClickListener(chickListener);
        south_korea = (RelativeLayout) view.findViewById(R.id.south_korea);
        south_korea.setOnClickListener(chickListener);
        cancel = (RelativeLayout) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(chickListener);
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialog.show();


    }

    View.OnClickListener chickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.malaysia:
                    listener.returnData("Malaysia");
                    dialog.cancel();
                    break;
                case R.id.china:
                    listener.returnData("China");
                    dialog.cancel();
                    break;
                case R.id.vietnam:
                    listener.returnData("Vietnam");
                    dialog.cancel();
                    break;
                case R.id.philippines:
                    listener.returnData("Philippines");
                    dialog.cancel();
                    break;
                case R.id.thailand:
                    listener.returnData("Thailand");
                    dialog.cancel();
                    break;
                case R.id.japan:
                    listener.returnData("Japan");
                    dialog.cancel();
                    break;
                case R.id.south_korea:
                    listener.returnData("South Korea");
                    dialog.cancel();
                    break;
                default:
                    dialog.cancel();
                    break;
            }
        }
    };
}
