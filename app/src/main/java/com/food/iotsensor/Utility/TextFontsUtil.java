package com.food.iotsensor.Utility;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.TextView;


import com.food.iotsensor.Constant;

import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class TextFontsUtil {

    public static TextView getVagRoundText(Activity activity, TextView textView){
        SharedPreferencesUtils sharedPreferencesUtils= SharedPreferencesUtils.getInstance(activity);
        if(sharedPreferencesUtils.getData(Constant.userLocale).equalsIgnoreCase("zh")) {
            TextPaint tp = textView.getPaint();

            tp.setFakeBoldText(true);
        }else{
            Typeface typeFace = Typeface.createFromAsset(activity.getAssets(),"fonts/vagroundedbt.ttf");
            textView.setTypeface(typeFace);
        }

        return  textView;
    }


    public static void getVagRoundText(Activity activity, List<TextView> textViewList){

        for(int i=0;i<textViewList.size();i++){
            SharedPreferencesUtils sharedPreferencesUtils= SharedPreferencesUtils.getInstance(activity);
            if(sharedPreferencesUtils.getData(Constant.userLocale).equalsIgnoreCase("zh")) {
                TextPaint tp = textViewList.get(i).getPaint();

                tp.setFakeBoldText(true);
            }else{
                Typeface typeFace = Typeface.createFromAsset(activity.getAssets(),"fonts/vagroundedbt.ttf");
                textViewList.get(i).setTypeface(typeFace);
            }
        }

    }
    public static void setTabtext(Activity activity, MenuItem item){
        SpannableString spannableString = new SpannableString(item.getTitle());
        SharedPreferencesUtils sharedPreferencesUtils= SharedPreferencesUtils.getInstance(activity);
        if(sharedPreferencesUtils.getData(Constant.userLocale).equalsIgnoreCase("zh")) {
            StyleSpan BOLD  = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(BOLD, 0, item.getTitle().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }else{
            Typeface typeFace = Typeface.createFromAsset(activity.getAssets(),"fonts/vagroundedbt.ttf");
            spannableString.setSpan(new MyTypefaceSpan(typeFace), 0, item.getTitle().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }

        item.setTitle(spannableString);

    }
}
