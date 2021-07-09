package com.food.iotsensor.Bluetooth.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class C {
    public static final String TAG = "boxdemo";

    public static final String UUID_ = "0000fff0";//"-0000-1000-8000-00805f9b34fb";
    public static final String UUID_WRITE = "0000fff2-0000-1000-8000-00805f9b34fb";//"0000ffe9-0000-1000-8000-00805f9b34fb";
    public static final String UUID_READ = "0000fff1-0000-1000-8000-00805f9b34fb";//"0000ffe4-0000-1000-8000-00805f9b34fb";
    // "0000ffe1-0000-1000-8000-00805f9b34fb";

    public static void ThreadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    public static void log(String msg){
        Log.d(TAG, msg);
    }
}
