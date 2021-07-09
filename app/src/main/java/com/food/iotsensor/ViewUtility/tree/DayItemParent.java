package com.food.iotsensor.ViewUtility.tree;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.TextView;


import com.food.iotsensor.DataManager;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.CommonUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */

public class DayItemParent extends TreeItemGroup<TransactionYearBean.TransactionDayBean> {

    @Override
    protected int initLayoutId() {
        return R.layout.item_level_day;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        TextView textView = viewHolder.getView(R.id.tv_content_day);
        if (year == getData().getYearId() && month == getData().getMonthId() && day == getData().getDayId()) {
            textView.setText(R.string.note_transaction_today);
            textView.setCompoundDrawables(null, null, null, null);
        } else {
            if (isExpand()) {
                if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActualZH(getData().getMonthId()) + " " + getData().getYearId());
                } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActual(getData().getMonthId()) + " " + getData().getYearId());
                } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActualMS(getData().getMonthId()) + " " + getData().getYearId());
                }
                Drawable drawable = textView.getResources().getDrawable(R.mipmap.add_up);
                drawable.setBounds(0, 0, 65, 65);
                textView.setCompoundDrawables(null, null, drawable, null);
            } else {
                if (DataManager.UserLocale.equalsIgnoreCase("zh")) { //中文
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActualZH(getData().getMonthId()));
                } else if (DataManager.UserLocale.equalsIgnoreCase("en")) {
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActual(getData().getMonthId()));
                } else if (DataManager.UserLocale.equalsIgnoreCase("ms")) {
                    textView.setText(getData().getDayId() + " " + CommonUtils.getMonthNameByActualMS(getData().getMonthId()));
                }
                Drawable drawable = textView.getResources().getDrawable(R.mipmap.add_menu);
                drawable.setBounds(0, 0, 60, 75);
                textView.setCompoundDrawables(null, null, drawable, null);
            }


        }
        //进入交易页面，自动打开第一个日期的具体交易列表
//        if (TransactionActivity.isIsDayExpend && isCanExpand()) {
//            TransactionActivity.isIsDayExpend = false;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    onExpand();
//                }
//            }, 50);
//        }

    }

    @Override
    protected List<TreeItem> initChildsList(TransactionYearBean.TransactionDayBean data) {
        return null;/*ItemHelperFactory.createTreeItemList(data.getData(), DetailItem.class, this);*/
    }

}
