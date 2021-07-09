package com.food.iotsensor.Utility;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class WindowBrightness {
    private static WindowBrightness mWindowBrightness;

    public static WindowBrightness getInstance() {
        if (mWindowBrightness == null) {
            mWindowBrightness = new WindowBrightness();
        }

        return mWindowBrightness;
    }

    public void setWindowBrightness(Activity activity, float brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }
}
