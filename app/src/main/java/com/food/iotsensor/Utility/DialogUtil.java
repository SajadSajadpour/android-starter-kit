package com.food.iotsensor.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.food.iotsensor.R;


/**
 * dialog
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class DialogUtil extends Dialog {



    // true dialog
    public DialogUtil(Context context, View view, boolean isCancel) {
        super(context, R.style.Transparent);

        init(view,isCancel);
    }

    //
    public DialogUtil(Context context, View view) {

            super(context, R.style.TransparentNoActionBar);

        init(view,false);
    }

    private void init(View view, boolean isCancel) {

        //Dialog
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
       // setCanceledOnTouchOutside(false);
        //
        getWindow().setBackgroundDrawableResource(R.mipmap.bg_ranslucent);
        if(!isCancel) {
            setCancelable(false);
        }
        setContentView(view);


    }



}
