package com.food.iotsensor.Bluetooth.common;

import java.sql.Timestamp;
import java.text.DecimalFormat;

public class Convert {
    public static String toStr(Object object, String def) {
        try {
            if (object != null)
                return String.valueOf(object);
        } catch (Exception e) {
        }
        return def;
    }

    public static String toStr(Object object) {
        return toStr(object, "");
    }

    // 是否要判断正整数
    public static int toInt(Object object, int def, boolean z) {
        try {
            String s = object.toString().trim().toLowerCase();
            if (s.equals("true") || s.equals("checked") || s.equals("是")
                    || s.equals("真") || s.equals("1") || s.equals("yes"))
                return 1;

            int n = Double.valueOf(s).intValue();
            if (z && n < 1)
                n = 1;
            return n;
        } catch (Exception e) {
        }
        return def;
    }

    public static double toDouble(Object object) {
        try {
            return Double.valueOf(object.toString().trim());
        } catch (Exception e) {
        }
        return 0;
    }

    public static int toInt(Object object, int def) {
        return toInt(object, def, false);
    }

    public static int toInt(Object object, boolean z) {
        return toInt(object, 0, z);
    }

    public static int toInt(Object object) {
        return toInt(object, 0, false);
    }

    public static float toFloat(Object object) {
        try {
            return Float.valueOf(object.toString()).floatValue();
        } catch (Exception e) {
        }
        return 0;
    }

    public static boolean toBool(Object object) {
        try {
            String o = object.toString().trim().toLowerCase();
            if (o.equals("是") || o.equals("1") || o.equals("yes")
                    || o.equals("true"))
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static String toBoolCn(Object object) {
        try {
            return toBool(object) ? "是" : "否";
        } catch (Exception e) {
        }
        return "否";
    }

    public static String toBoolEn(Object object) {
        try {
            return toBool(object) ? "Yes" : "No";
        } catch (Exception e) {
        }
        return "No";
    }

    public static long toLong(Object object) {
        try {
            return Long.valueOf(object.toString()).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean empty(Object object) {
        return object == null || object.toString().trim().length() == 0 ? true
                : false;
    }

    public static Timestamp toTimestamp(Object object) {
        try {
            String s = object.toString();
            if (s.length() == 10)
                s += "000";
            return new Timestamp(Long.parseLong(s));
        } catch (Exception e) {
            try {
                return Timestamp.valueOf("1990-1-1");
            } catch (Exception e2) {
            }
        }
        return Timestamp.valueOf("1990-1-1");
    }

    public static float toByteM(long l) {
        return l / 1024 / 1024;
    }

    public static float toByteK(long l) {
        return l / 1024;
    }

    public static float toByteG(long l) {
        return l / 1024 / 1024 / 1024;
    }

    public static String toByteCn(long l) {
        try {
            String result = "";
            long b = l % 1024;
            l = l / 1024;
            long k = l % 1024;
            if (k > 0)
                result = k + "K" + result;
            l = l / 1024;
            long m = l % 1024;
            if (k > 0)
                result = m + "M" + result;
            l = l / 1024;
            long g = l % 1024;
            if (k > 0)
                result = g + "G" + result;

            if (g > 0)
                result = g + "." + Math.round(m * 100 / 1024) + "G";
            else if (m > 0)
                result = m + "." + Math.round(k * 100 / 1024) + "M";
            else if (k > 0)
                result = k + "." + Math.round(b * 100 / 1024) + "K";
            else if (b > 0)
                result = b + "B";
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取百分比值
    public static String getPer(Object num1, Object num2) {
        double val1 = Double.valueOf(num1.toString());
        double val2 = Double.valueOf(num2.toString());
        if (val2 == 0) {
            return "0.0%";
        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(val1 / val2 * 100) + "%";
        }
    }

    public static String padLeft(String source, int fillLength, char fillChar) {
        return stringFill(source, fillLength, fillChar, true);
    }

    public static String padRight(String source, int fillLength, char fillChar) {
        return stringFill(source, fillLength, fillChar, false);
    }

    public static String stringFill(String source, int fillLength,
                                    char fillChar, boolean isLeftFill) {
        if (source == null || source.length() >= fillLength)
            return source;

        StringBuilder result = new StringBuilder(fillLength);
        int len = fillLength - source.length();
        if (isLeftFill) {
            for (; len > 0; len--) {
                result.append(fillChar);
            }
            result.append(source);
        } else {
            result.append(source);
            for (; len > 0; len--) {
                result.append(fillChar);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(toInt("5.0"));
    }

}
