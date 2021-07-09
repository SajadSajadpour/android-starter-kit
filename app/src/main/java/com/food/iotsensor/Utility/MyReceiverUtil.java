package com.food.iotsensor.Utility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * swipeLayout
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class MyReceiverUtil {
    private static MyReceiverUtil myReceiverUtil;
    private static MyReceiver myReceiver;
    public static String SWIPELAYOUT_CLOSE = "swipeLayout_close";

    public static MyReceiverUtil getInstance() {
        if (myReceiverUtil == null) {
            return new MyReceiverUtil();
        }
        return myReceiverUtil;
    }


    private static List<MyReceiver> myReceiverList=new ArrayList<>();
    public void openReceiver(Activity activity, SwipeRefreshLayout swipeLayout) {
        myReceiver = new MyReceiver(swipeLayout);
        myReceiverList.add(myReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SWIPELAYOUT_CLOSE);
        activity.registerReceiver(myReceiver, intentFilter);
    }

    public void closeReceiver(Activity activity) {
        //方法3
        for(MyReceiver tmp:myReceiverList){
            if(tmp!=null) {
                activity.unregisterReceiver(tmp);
            }
        }
        myReceiverList.clear();
    }

    public class MyReceiver extends BroadcastReceiver {
        SwipeRefreshLayout swipeLayout;

        public MyReceiver(SwipeRefreshLayout swipeLayout) {
            this.swipeLayout = swipeLayout;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(SWIPELAYOUT_CLOSE)) {
//                ToastUtil.makeText(activity, "收到了");
                if (swipeLayout != null && swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }
        }
    }
}
