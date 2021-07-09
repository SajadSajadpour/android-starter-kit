package com.food.iotsensor.ViewUtility.tree;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


import com.food.iotsensor.DataManager;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.CommonUtils;

import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class YearItemParent extends TreeItemGroup<TransactionYearBean> {

    @Override
    protected int initLayoutId() {
        return R.layout.item_level_year_month;
    }
    boolean isdown=true;
    View image_direction;
    long currentMS;
    float DownX,DownY,moveX,moveY;
    View container;
    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        int pos = viewHolder.getPosition();

        container = viewHolder.getView(R.id.year_month_container);
        image_direction= viewHolder.getView(R.id.image_direction);
        if (pos == 0) {
            container.setBackgroundColor(container.getResources().getColor(R.color.color_4f76bb));
        }
        if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
            viewHolder.setText(R.id.tv_content, CommonUtils.getMonthNameByActualZH(data.getMonthId()).toUpperCase() + "  " + data.getYearId());
        } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
            viewHolder.setText(R.id.tv_content, CommonUtils.getMonthNameByActual(data.getMonthId()).toUpperCase() + "  " + data.getYearId());
        } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
            viewHolder.setText(R.id.tv_content, CommonUtils.getMonthNameByActualMS(data.getMonthId()).toUpperCase() + "  " + data.getYearId());
        }

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DownX = event.getX();//float DownX
                        DownY = event.getY();//float DownY
                        moveX = 0;
                        moveY = 0;
                        currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX += Math.abs(event.getX() - DownX);//X轴距离
                        moveY += Math.abs(event.getY() - DownY);//y轴距离
                        DownX = event.getX();
                        DownY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                        //判断是否继续传递信号
                        if(moveTime<200&&(moveX<20||moveY<20)){
                            if(isdown){
                                isdown=false;
                                image_direction.setBackground(image_direction.getResources().getDrawable(R.mipmap.transaction_records_up));
                            }else{
                                isdown=true;
                                image_direction.setBackground(image_direction.getResources().getDrawable(R.mipmap.transaction_records_down));
                            }
                        }
                        break;
                }
                return false;//继续执行后面的代码

            }
        });
        viewHolder.getView(R.id.tv_year_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SummaryActivity.actionStart(String.valueOf(getData().getMonthId()), String.valueOf(getData().getYearId()), container.getContext());
            }
        });

        //自动打开最上面月份的第一个天数列表
//        if (TransactionActivity.isYearExpend && isCanExpand()) {
//            TransactionActivity.isYearExpend = false;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    isdown=false;
//                    image_direction.setBackground(image_direction.getResources().getDrawable(R.mipmap.transaction_records_up));
//                    onExpand();
//                }
//            }, 50);
//        }
    }

    @Override
    protected List<TreeItem> initChildsList(TransactionYearBean data) {
        return ItemHelperFactory.createTreeItemList(data.getDaysBeanList(), DayItemParent.class, this);
    }
}
