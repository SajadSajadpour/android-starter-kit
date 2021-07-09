package com.food.iotsensor.ViewUtility;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class CustomGridLayoutRecyclerView extends RecyclerView {
    StaggeredGridLayoutManager layoutManager;
    public CustomGridLayoutRecyclerView(Context context) {
        super(context);
    }

    public CustomGridLayoutRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridLayoutRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /*
        heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int defaultsize=measureHight(Integer.MAX_VALUE >> 2, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(defaultsize, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
        */

        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
       // int height= MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
        int height= MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

}
