package com.food.iotsensor.ViewUtility.tree;

import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class TransactionYearBean extends BaseItemData {

    private int yearId;
    private int monthId;
    private boolean isYear;
    private List<TransactionDayBean> daysBeanList;

    public boolean isYear() {
        return isYear;
    }

    public void setYear(boolean year) {
        isYear = year;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

    public List<TransactionDayBean> getDaysBeanList() {
        return daysBeanList;
    }

    public void setDaysBeanList(List<TransactionDayBean> daysBeanList) {
        this.daysBeanList = daysBeanList;
    }

    public static class TransactionDayBean extends BaseItemData {
        private int dayId;
        private int yearId;
        private int monthId;
        private boolean isMonth;

        public boolean isMonth() {
            return isMonth;
        }

        public void setMonth(boolean month) {
            isMonth = month;
        }

        private List<TransactionDetailBean> data;

        public int getDayId() {
            return dayId;
        }

        public void setDayId(int dayId) {
            this.dayId = dayId;
        }

        public int getYearId() {
            return yearId;
        }

        public void setYearId(int yearId) {
            this.yearId = yearId;
        }

        public int getMonthId() {
            return monthId;
        }

        public void setMonthId(int monthId) {
            this.monthId = monthId;
        }

        public List<TransactionDetailBean> getData() {
            return data;
        }

        public void setData(List<TransactionDetailBean> data) {
            this.data = data;
        }
    }

    public static class TransactionDetailBean extends BaseItemData {

        private String date;
        private String status;
        private double amount;
        private String type;
        private int day;
        private String tansfer_type;
        private String tranfer_id;
        private String hid;
        private double total_count;

        public double getTotal_count() {
            return total_count;
        }

        public void setTotal_count(double total_count) {
            this.total_count = total_count;
        }

        public String getHid() {
            return hid;
        }

        public void setHid(String hid) {
            this.hid = hid;
        }

        public String getTranfer_id() {
            return tranfer_id;
        }

        public void setTranfer_id(String tranfer_id) {
            this.tranfer_id = tranfer_id;
        }

        public String getTansfer_type() {
            return tansfer_type;
        }

        public void setTansfer_type(String tansfer_type) {
            this.tansfer_type = tansfer_type;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
