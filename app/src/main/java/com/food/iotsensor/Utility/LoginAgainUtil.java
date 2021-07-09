package com.food.iotsensor.Utility;

import android.app.Activity;

import com.food.iotsensor.Constant;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class LoginAgainUtil {
    private static LoginAgainUtil loginAgainUtil;
    public static LoginAgainUtil getInstance() {
        if (loginAgainUtil == null) {
            loginAgainUtil = new LoginAgainUtil();
        }

        return loginAgainUtil;
    }
    // app
    public boolean isLoginAgain(Activity activity){

        String isLoginAgain =SharedPreferencesUtils.getInstance(activity).getData(Constant.loginAgain);
        if(isLoginAgain.contentEquals("true")){
            return true;
        }else{
            return false;
        }

    }
    //
    public  void loginAgain(Activity activity, boolean isLoginAgain){
        SharedPreferencesUtils.getInstance(activity).setData(Constant.loginAgain, String.valueOf(isLoginAgain));
    }

}
