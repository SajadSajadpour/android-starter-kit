package com.food.iotsensor.Utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CommonUtils {


    /**
     *   dp  px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getYYYYMMDD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getYYYYMMDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    public static String getYMDHMS(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getMMDD(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getMMDDHHSS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(date);
    }


    public static String getNumberFormat(Number money) {

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((null == money) ? 0 : money);
    }

    public static String getMonthNameByDigit(int digit) {
        String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "Oct0ber", "November", "December"};
        if (digit > 11 && digit < 0)
            return monthArray[0];
        return monthArray[digit];
    }

    public static String getMonthNameByActual(int actual) {
        String[] monthArray = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        if (actual > 12 && actual < 1)
            return monthArray[1];
        return monthArray[actual];
    }

    public static String getMonthNameByActualZH(int actual) {
        String[] monthArray = {"", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        if (actual > 12 && actual < 1)
            return monthArray[1];
        return monthArray[actual];
    }

    public static String getMonthNameByActualMS(int actual) {
        String[] monthArray = {"", "Januari", "Februari", "Mac", "April", "Mungkin", "Jun", "Julai", "Ogos", "September", "Oktober", "November", "Disember"};
        if (actual > 12 && actual < 1)
            return monthArray[1];
        return monthArray[actual];
    }

    /**
     * String转double
     *
     * @param money
     * @return
     */
    public static double getDouble(String money) {
        double price;
        if (money == null || money.equalsIgnoreCase("")) {
            price = 0.0;
        } else {
            try {
                price = Double.valueOf(money);
            } catch (Exception e) {
                price = 0.0;
            }
        }
        return price;
    }


    /**
     *
     *
     * @return
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }
//
}
