package com.food.iotsensor.Utility;

import android.widget.EditText;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
/**
 * 键盘的工具类
 * @author davy
 * @since 2018/4/26
 */
public class KeyboardUtil {
    private static KeyboardUtil keyboardUtil;
    public static KeyboardUtil getInstance() {
        if (keyboardUtil == null) {
            keyboardUtil = new KeyboardUtil();
        }

        return keyboardUtil;
    }
    private boolean setting=false;
    private String inputData = "";
    private boolean isHadNew = false;

    public void getAfterrocessing(String beForString, String afterString, EditText money_ed ) {
        if (!setting) {
            setting = true;
            if (afterString.length() < beForString.length()) {

                money_ed.setText(String.format("%s", "0.00"));
                money_ed.setSelection(money_ed.getText().length());
                setting = false;

                return;
            }

            if (afterString.length() > 8) {
                money_ed.setText(String.format("%s", beForString));
                money_ed.setSelection(money_ed.getText().length());
                setting = false;

                return;
            }


            for (int i = 0; i < beForString.length(); i++) {


                if (!String.valueOf(beForString.charAt(i)).equals(String.valueOf(afterString.charAt(i)))) {
                    inputData = String.valueOf(afterString.charAt(i));
                    isHadNew = true;
                    break;
                }

            }
            if (!isHadNew) {

                inputData = String.valueOf(afterString.charAt(afterString.length() - 1));
            }
            isHadNew = false;
           LogUtil.d("新增的字符", inputData);
            beForString = beForString + inputData;

            StringBuffer tmpData;

            String nextData = String.valueOf(beForString.charAt(beForString.length() - 3));
            beForString = beForString.replace(".", nextData);
            tmpData = new StringBuffer(beForString);
            tmpData = tmpData.replace(beForString.length() - 3, beForString.length() - 2, ".");

            if ( beForString.length() == 5 && String.valueOf(beForString.charAt(1)).equals("0")) {
                if(String.valueOf(beForString.charAt(0)).equals("0")) {
                    tmpData = tmpData.replace(0, 2, "0");
                }
            }
            if ( beForString.length() == 5 && String.valueOf(beForString.charAt(0)).equals("0") && !String.valueOf(beForString.charAt(1)).equals("0")) {
                // &&

                tmpData = tmpData.replace(0, 1, "");
            }
            beForString = tmpData.toString();
            money_ed.setText(String.format("%s", beForString));
            money_ed.setSelection(money_ed.getText().length());
            setting = false;


        }
    }

}
