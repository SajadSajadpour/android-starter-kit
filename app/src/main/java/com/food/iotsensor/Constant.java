package com.food.iotsensor;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */


public class Constant {


    final static public String common_url = BuildConfig.DOMAIN;
    final static public String API_Target_url = common_url + "/json/";//"/Version_3/";


    final static public String success = "success";

    //Save String
    final static public String defaultPreferenceName = "SaveData";
    final static public String userTheme = "ThemeBlue";
    final static public String userLocale = "UserLocale";
    final static public String authToken = "AuthToken";
    final static public String fingerAuthToken = "fingerAuthToken";
    final static public String fingerIdList = "fingerIdList";
    final static public String countryData = "CountryData";
    //
    final static public String toLoginId = "toLoginId";
    final static public String userType = "userType";//merchant, customer
    final static public String userEmail = "userEmail";
    final static public String userId = "userId";

    final static public String Pin_attempts = "Pin_attempts";
    final static public String base_activity = "base_activity";

    final static public String loginAgain = "loginAgain";


    final static public String userIcon = "userIcon";
    final static public String userNickName = "userNickName";
    final static public String userGreeting = "userGreeting";

    final static public String fingerChange = "fingerChange";
    final static public String loginByFinger = "loginByFinger";

    // need to add this when Backend AWS ready
    final static public String md5Key = ""; // old -->  C*mXgi19%dxs1_1^s&$~

    //need to add this when API ready
    final static public String API_key = "";//Ztu7t34Aeda92At

    //need to add this when API ready
    public static final String GREEN_APP_KEY = "";//V1lVDREmtrV9s3BQ2pY8O0JuhlCzS748

    //Global Function
    static public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
