package com.food.iotsensor.ViewUtility;

/**
 * map view , final class
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**

 */
public class BaiduMapContainer extends LinearLayout {
    private ScrollView scrollView;
    private VerticalSwipeRefreshLayout swipeRefreshLayout;

    public BaiduMapContainer(Context context) {
        super(context);
    }

    public BaiduMapContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollView(ScrollView scrollView, VerticalSwipeRefreshLayout swipeRefreshLayout) {
        this.scrollView = scrollView;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (scrollView != null)
                scrollView.requestDisallowInterceptTouchEvent(false);
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.requestDisallowInterceptTouchEvent(false);
        } else {
            if (scrollView != null)
                scrollView.requestDisallowInterceptTouchEvent(true);
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}