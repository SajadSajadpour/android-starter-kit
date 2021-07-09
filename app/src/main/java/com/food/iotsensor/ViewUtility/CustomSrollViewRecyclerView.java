package com.food.iotsensor.ViewUtility;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CustomSrollViewRecyclerView extends RecyclerView {
    private CallBack callBack;

    public CustomSrollViewRecyclerView(Context context) {
        super(context);
    }

    public CustomSrollViewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSrollViewRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("SrollViewRecyclerView", "onInterceptTouchEvent" + ev.getRawY() + " " + getTop());

        if (callBack != null) {
            callBack.moveEvent();
        }

        return super.onInterceptTouchEvent(ev);
    }

    public interface CallBack {
        void moveEvent();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
