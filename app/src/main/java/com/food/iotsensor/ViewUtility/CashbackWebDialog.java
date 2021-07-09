package com.food.iotsensor.ViewUtility;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.food.iotsensor.DataManager;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.DialogUtil;

import java.util.Locale;

/**
 * cashback
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class CashbackWebDialog {

    private static CashbackWebDialog cashbackWebDialog;
    private DialogUtil dialog;
    public static CashbackWebDialog getInstance() {
        cashbackWebDialog = new CashbackWebDialog();
        return cashbackWebDialog;
    }
    FontTextView shop_now;
    ImageView iv_delete_web;
    private WebView web;
    String explain = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"/><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"><title></title><style>      ::-moz-selection {        background: #FFFFFF;        text-shadow: none;      }      ::selection {        background: #FFFFFF;        text-shadow: none;      }      html {        padding: 0px;        font-size: 14px;        line-height: 1.4;        color: #737373;        background: #FFFFFF;        -webkit-text-size-adjust: 100%;        -ms-text-size-adjust: 100%;      }      html,      input {        font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;      }      body {               padding: 10px 16px 16px 16px;        border: 0px solid #FFFFFF;        border-radius: 0px;        margin: 0 auto;        box-shadow: 0 1px 16px #FFFFFF, inset 0 " +
            "1px 0 #fff;        background: #FFFFFF;      }      h1 {font-weight: normal;      margin: 0 0px 10px;        font-size: 18px;        text-align: center;   }    p {        margin: 0.3em 0;        }      ul {        padding: 0 0 0 15px;        margin: 1em 0;      }      .container {        max-width: 400px;        margin: 0 auto;      }      button {        padding: 10px 18px;        border: 0px solid #FFFFFF;        border-radius: 0px;        margin-top: 60px;        color: #333;        background-color: #fff;      }      .btn {        text-align: center;      }</style></head><body><div class=\"container\">"

            + "<p>1.这是测试数据，这是测试数据</p>"

            + "</div></body></html>";
    public void show(final Activity activity, String data, final View.OnClickListener chick) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_cashback_web, null);
        dialog = new DialogUtil(activity, view,true);
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        web=(WebView) view.findViewById(R.id.web);
        String languageToLoad = DataManager.UserLocale; // your language
        System.out.println("languageToLoad >" + languageToLoad);
        Locale locale = new Locale(languageToLoad);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
        web.loadData(data,"text/html; charset=UTF-8", null);
        shop_now=(FontTextView)view.findViewById(R.id.shop_now);
        iv_delete_web = (ImageView) view.findViewById(R.id.iv_delete_web);
        iv_delete_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        shop_now.setText(activity.getString(R.string.continue_ok));
        shop_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chick.onClick(v);
                dialog.cancel();
            }
        });
        dialog.show();


    }

}
