package com.food.iotsensor.Utility;

 import android.os.CountDownTimer;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CheckUserTimerUtil {


    public static boolean toCheckUserLogin(){
        if(getCodeTime==0){
            cancelCountDownTime();
            return true;
        }else{
            cancelCountDownTime();
            return false;
        }


    }
    private final static int waitTime=600000;
    //
    private static int getCodeTime=1000000;
    private static CountDownTimer timer;
    public static void startCountDownTime(){
                 timer = new CountDownTimer(waitTime, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCodeTime = (int)millisUntilFinished/1000;
                       LogUtil.d("需要验证的剩余时间", getCodeTime + "");
                    }

                    @Override
                    public void onFinish() {
                        getCodeTime = 0;
                    }
                };
                timer.start();

    }
    public static void cancelCountDownTime(){
        if(timer!=null){
            timer.cancel();
            getCodeTime=1000000;
        }
    }
}
