package com.food.iotsensor.Utility;

import java.text.DateFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class RegexUtils {

    /**
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     *
     * @param month
     * @return
     */
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

}
