package com.food.iotsensor.ViewUtility;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.food.iotsensor.Constant;
import com.food.iotsensor.Utility.SharedPreferencesUtils;


public class FontTextView extends TextView {
    private Context mContext;
    private String TypefaceName = "";

    public String getTypefaceName() {
        return TypefaceName;
    }

    public void setTypefaceName(String typefaceName) {
        TypefaceName = typefaceName;
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + TypefaceName + ".ttf");
        this.setTypeface(typeface);
        System.gc();
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        int resouceId = attrs.getAttributeResourceValue(null, "typefaceName", 0);
        if (resouceId != 0) {
            TypefaceName = context.getResources().getString(resouceId);
        } else {
            TypefaceName = attrs.getAttributeValue(null, "typefaceName");
        }
        if (TypefaceName != null && !"".equals(TypefaceName)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts" +
                    "/" + TypefaceName + ".ttf");
            this.setTypeface(typeface);
        }
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        SharedPreferencesUtils sharedPreferencesUtils=SharedPreferencesUtils.getInstance(context);

        // 先判断是否配置的资源文件
        int resouceId = attrs.getAttributeResourceValue(null, "typefaceName", 0);
        if (resouceId != 0) {
            TypefaceName = context.getResources().getString(resouceId);
        } else {
            TypefaceName = attrs.getAttributeValue(null, "typefaceName");
        }
        if (TypefaceName != null && !"".equals(TypefaceName)) {
            if(sharedPreferencesUtils.getData(Constant.userLocale).equalsIgnoreCase("zh")) {
                TextPaint tp = this.getPaint();

                tp.setFakeBoldText(true);

            }else{
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TypefaceName + ".ttf");
                this.setTypeface(typeface);
            }
        }
    }
    public FontTextView(Context context) {
        super(context);
        this.mContext = context;
        SharedPreferencesUtils sharedPreferencesUtils=SharedPreferencesUtils.getInstance(context);

        // TypefaceName = attrs.getAttributeValue(null, "TypefaceName");
        if (TypefaceName != null && !"".equals(TypefaceName)) {
            if(sharedPreferencesUtils.getData(Constant.userLocale).equalsIgnoreCase("zh")) {
                TextPaint tp = this.getPaint();

                tp.setFakeBoldText(true);

            }else{
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TypefaceName + ".ttf");
                this.setTypeface(typeface);
            }
        }
    }
}