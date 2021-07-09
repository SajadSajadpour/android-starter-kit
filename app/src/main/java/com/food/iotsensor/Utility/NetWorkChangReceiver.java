package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.food.iotsensor.Network.NetWorkUtil;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class NetWorkChangReceiver extends BroadcastReceiver {
    Activity activity;

    public NetWorkChangReceiver() {
    }

    public NetWorkChangReceiver(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            OopsNetWorkDialog.show(activity);
        } else {
            OopsNetWorkDialog.cancel();
        }
//            }
//        }
    }

}
