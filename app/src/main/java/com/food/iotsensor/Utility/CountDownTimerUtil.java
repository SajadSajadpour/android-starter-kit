package com.food.iotsensor.Utility;

import android.app.Activity;
import android.os.CountDownTimer;

import com.food.iotsensor.R;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CountDownTimerUtil {


    public static boolean toGetCode(Activity activity){
        if(getCodeTime==0){
            startCountDownTime();
            return true;
        }else{
            RemindDialogUtil.showEasy(activity,activity.getString(R.string.get_code_time_fail)+" "+getCodeTime+" "+activity.getString(R.string.get_code_time_fail1));
            return false;
        }


    }
    //
    public static int getCodeTime=0;
    private static void startCountDownTime(){
            if(getCodeTime==0) {
                CountDownTimer timer = new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCodeTime = (int)millisUntilFinished/1000;
                        LogUtil.d("时间", getCodeTime + "");
                    }

                    @Override
                    public void onFinish() {
                        getCodeTime = 0;
                    }
                };
                timer.start();
            }
    }
}
