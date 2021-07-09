package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class NetWorkChangUtil {
    private static NetWorkChangUtil myReceiverUtil;
    private static NetWorkChangReceiver myReceiver;

    public static NetWorkChangUtil getInstance() {
        if (myReceiverUtil == null) {
            return new NetWorkChangUtil();
        }
        return myReceiverUtil;
    }


    private static List<NetWorkChangReceiver> myReceiverList = new ArrayList<>();

    public void openReceiver(Activity activity) {
        myReceiver = new NetWorkChangReceiver(activity);
        myReceiverList.add(myReceiver);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(myReceiver, filter);

    }

    public void closeReceiver(Activity activity) {
        //
        for (NetWorkChangReceiver tmp : myReceiverList) {
            if (tmp != null) {
                activity.unregisterReceiver(tmp);
            }
        }
        myReceiverList.clear();
    }
}
