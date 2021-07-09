package com.food.iotsensor.Bluetooth.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DataTool {
    public static SerialHelper serialHelper = null;
    public static SerialHelper serialHelper_print = null;
    public static SerialHelper serialHelper_pay = null;

    public static byte START = (byte) 0xFA;
    public static byte END = (byte) 0xFB;
    public static String[] STATE_STD = {"稳定", ""};
    public static String[] WEIGHT_STATE = {"N", "G", "T", "Z"};
    public static String[] STATE_QUPI = {"去皮", ""};//去皮
    public static String[] STATE_LING = {"零点", ""};//零点
    public static byte[] CMD_GUILING = {(byte) 0xFA, (byte) 0x02, (byte) 0x4B, (byte) 0x03, (byte) 0x48, (byte) 0xFB};
    public static byte[] CMD_QUPI = {(byte) 0xFA, (byte) 0x02, (byte) 0x4B, (byte) 0x02, (byte) 0x49, (byte) 0xFB};
    public static byte[] CMD_PRINT = {(byte) 0xFA, (byte) 0x02, (byte) 0x4A, (byte) 0x22, (byte) 0x68, (byte) 0xFB};
    public static byte[] CMD_KEY_REPLAY = {(byte) 0xFA, (byte) 0x02, (byte) 0x4A, (byte) 0x31, (byte) 0x7B, (byte) 0xFB};
    public static byte[] CMD_PRICE = {(byte) 0xFA, (byte) 0x07, (byte) 0x52, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x00, (byte) 0xFB};

    //FF FF 06 10 00 00 00 0A 00 00 00 04 09 05 00 00 00 04 09 05 2E
    public static byte[] CMD_PAY = {(byte) 0xFF, (byte) 0xFF, (byte) 0x06, (byte) 0x10};
    //FF FF 07 10 00 00 00 0A 00 00 00 04 09 05 00 00 00 04 09 05 2E
    public static byte[] CMD_PAY_BACK = {(byte) 0xFF, (byte) 0xFF, (byte) 0x07, (byte) 0x10};
    //当前
    public static double now_weight = 0, now_price = 0, now_total = 0.00;
    public static String now_pinlei = "苹果";

    public static byte[] CMD_JIAOZHUN = {(byte) 0xFA, (byte) 0x02, (byte) 0x4C, (byte) 0x20, (byte) 0x6C, (byte) 0xFB};
    public static byte[] CMD_JIAOZHUN2 = {(byte) 0xFA, (byte) 0x02, (byte) 0x46, (byte) 0x20, (byte) 0x66, (byte) 0xFB};
    public static byte[] CMD_JIAOZHUN_OK = {(byte) 0xFA, (byte) 0x02, (byte) 0x47, (byte) 0x20, (byte) 0x67, (byte) 0xFB};

    //滤波参数 零点范围 最大秤量 分度值 校正砝码
    public static final String[] list_lbcs = {"1", "2", "3", "4", "5"};
    public static final String[] list_ldfw = {"0", "1", "2", "3", "4", "5", "6", "7"};
    public static final String[] list_zdcl = {"60kg", "100kg", "150kg", "200kg", "300kg", "500kg", "600kg"};
    public static final String[] list_fdz = {"1g", "2g", "5g", "10g", "20g", "50g", "100g", "200g", "500g"};
    public static final String[] list_jzfm = {"10kg", "25kg", "50kg", "60kg", "75kg", "100kg", "150kg", "200kg", "225kg", "300kg", "500kg", "600kg"};

    public static byte[][] BD_lbcs = {
            {(byte) 0xFA, (byte) 0x02, (byte) 0x41, (byte) 0x01, (byte) 0x40, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x41, (byte) 0x02, (byte) 0x43, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x41, (byte) 0x03, (byte) 0x42, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x41, (byte) 0x04, (byte) 0x45, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x41, (byte) 0x05, (byte) 0x44, (byte) 0xFB}};
    public static byte[][] BD_ldfw = {
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x00, (byte) 0x42, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x01, (byte) 0x43, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x02, (byte) 0x40, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x03, (byte) 0x41, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x04, (byte) 0x46, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x05, (byte) 0x47, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x06, (byte) 0x44, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x02, (byte) 0x42, (byte) 0x07, (byte) 0x45, (byte) 0xFB}};
    public static byte[][] BD_zdcl = {
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x36, (byte) 0x30, (byte) 0x30, (byte) 0x75, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x72, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x35, (byte) 0x30, (byte) 0x30, (byte) 0x77, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x71, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x33, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x70, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x76, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x43, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x36, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x75, (byte) 0xFB}};
    public static byte[][] BD_fdz = {
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x33, (byte) 0x76, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x33, (byte) 0x75, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x33, (byte) 0x72, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x77, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x32, (byte) 0x74, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x32, (byte) 0x73, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x31, (byte) 0x74, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x31, (byte) 0x77, (byte) 0xFB},
            {(byte) 0xFA, (byte) 0x08, (byte) 0x44, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x31, (byte) 0x70, (byte) 0xFB}};
    public static byte[][] BD_jzfm = {
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x74, (byte) 0xFB},//10
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x35, (byte) 0x30, (byte) 0x72, (byte) 0xFB},//25
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x30, (byte) 0x70, (byte) 0xFB},//50
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x36, (byte) 0x30, (byte) 0x30, (byte) 0x73, (byte) 0xFB},    //E60
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x37, (byte) 0x35, (byte) 0x30, (byte) 0x77, (byte) 0xFB},//75
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x74, (byte) 0xFB},//100
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x35, (byte) 0x30, (byte) 0x30, (byte) 0x71, (byte) 0xFB},    //E150
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x77, (byte) 0xFB},    //E200
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x32, (byte) 0x35, (byte) 0x30, (byte) 0x70, (byte) 0xFB},    //E225
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x33, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x76, (byte) 0xFB},    //E300
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x70, (byte) 0xFB},    //E500
            {(byte) 0xFA, (byte) 0x08, (byte) 0x45, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x36, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x73, (byte) 0xFB},    //E600
    };

    /**
     * 拆分和清洗数据，按FA开头,FB结尾
     *
     * @param arr
     * @return
     */
    public static ArrayList<byte[]> splitBytes(byte[] arr) {
        int start = -1, end = -1;
        ArrayList<byte[]> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
//            Log.d("fwe", i + ":" + arr[i]);
            if (arr[i] == START) {
                start = i;
            }
            if (arr[i] == END) {
                end = i;
                if (start > 0 && end > start) {
                    byte[] b = Arrays.copyOfRange(arr, start, end + 1);
//                    Log.d("fwe", "copy:" + start + "-" + end);
                    list.add(b);
                    start = -1;
                }
            }
        }
        return list;
    }

//    public String utility_class(byte[] bytes, byte[] wei) {
//        try {
//            String weight = new String(bytes, "US-ASCII");//asccii数据
//            String digit = new String(wei, "US-ASCII");//小数点数据
//            int digit2 = Util.toInt(digit);
//            StringBuilder stringBuilder = new StringBuilder(weight).reverse();//反转字符串插入小数点
//            stringBuilder.insert(digit2, ".");
//            weight = stringBuilder.toString();
//            StringBuilder stringBuilder1 = new StringBuilder(weight).reverse();//插入完小数点反转回来return反回数据
//            return stringBuilder1.toString();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "null";
//    }

    //处理byte数据判断小数点在第几位并且解析重量与金额数据
//    public static String getAsciiDouble(byte[] bytes, byte[] wei) {
//        switch (bytes.length) {
//            case 6:
//                return new DataTool().utility_class(bytes, wei);
//            case 7:
//                return new DataTool().utility_class(bytes, wei);
//        }
//        return "0.0";
//    }

    /**
     * 换算重量值
     *
     * @param bytes
     * @return
     */
    public static String[] getWeight(byte[] bytes) {
        String[] result = new String[6];
        byte[] weight = {bytes[5], bytes[6], bytes[7], bytes[8], bytes[9], bytes[10]};//截取byte数组当中的重量数据
        byte digit = bytes[11];//截取byte数组当中的小数点数据
        byte[] unit_price = {bytes[13], bytes[14], bytes[15], bytes[16], bytes[17], bytes[18]};//截取byte数组当中的单价数据
        byte[] total = {bytes[19], bytes[20], bytes[21], bytes[22], bytes[23], bytes[24], bytes[25], bytes[26]};//截取byte数组当中的总价数据
        Double weight_data = getFuhao(bytes[4]) * Util.toDouble(Util.ascByteArrToStr(weight)) / Util.getTenByDigit(Util.toInt((char) digit));
        Double unit_price_data = Util.toDouble(Util.ascByteArrToStr(unit_price)) / 100.0d;
        Double total_data = Util.toDouble(Util.ascByteArrToStr(total)) / 100.0d;
        DecimalFormat format = new DecimalFormat("0.00");
        result[0] = format.format(weight_data);
        result[1] = format.format(unit_price_data);
        result[2] = format.format(total_data);
        result[3] = Util.getArrVal(STATE_STD, getStdInt(bytes[2]));
        result[4] = Util.getArrVal(STATE_QUPI, getQupi(bytes[3]));
        result[5] = Util.getArrVal(STATE_LING, getLing(bytes[3]));

        now_weight = weight_data;
        now_price = unit_price_data;
        now_total = total_data;
        return result;
    }

    //接收协议进行处理
    public void getAscii16() {
//        MainActivity.SetText();
    }

    //获取稳定值int
    public static int getStdInt(byte b) {
        return b == (byte) 0x30 ? 0 : 1;
    }

    //是否显示去皮
    public static int getQupi(byte b) {
        return b == (byte) 0x4E || b == (byte) 0x54 ? 0 : 1;
    }

    //是否显示归零
    public static int getLing(byte b) {
        return b == (byte) 0x47 || b == (byte) 0x5A ? 0 : 1;
    }

    //是否符号+-
    public static int getFuhao(byte b) {
        return b == (byte) 0x2d ? -1 : 1;
    }

    /**
     * FA 02 4A 24 6E FB
     * 1、收按键指令 归零：FA 02 4A (24) (6E) FB
     * 0x21	确认
     * 0x22	打印
     * 0x23	去皮
     * 0x24	归零
     * 0x25	箭头左
     * 0x26	箭头右
     * 0x27	箭头上
     * 0x28	箭头下
     */
    public static boolean isKeyCtrl(byte[] bytes) {
        return bytes.length == 6 && bytes[0] == (byte) 0xFA && bytes[1] == (byte) 0x02 && bytes[2] == (byte) 0x4A;
    }

    public static boolean isCtrl1Ok(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x21;
    }

    public static boolean isCtrl2Print(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x22;
    }

    public static boolean isCtrl3Qupi(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x23;
    }

    public static boolean isCtrl4Guiling(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x24;
    }

    public static boolean isCtrl5Left(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x25;
    }

    public static boolean isCtrl6Right(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x26;
    }

    public static boolean isCtrl7Up(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x27;
    }

    public static boolean isCtrl8Down(byte[] bytes) {
        return isKeyCtrl(bytes) && bytes[3] == (byte) 0x28;
    }

    public static byte[] getCmdPrice(double d) {
        byte[] result = CMD_PRICE;
        int price = (int) Math.round(d * 100d % 1000000d);//限定为0000.00到9999.99之间
        for (int i = 5; i >= 0; i--) {
            result[3 + i] = (byte) (price % 10 + 48);
            price /= 10;
        }
        result[result.length - 2] = getXor(result, 2, result.length - 3);
        return result;
    }

    //异或校验
    public static byte getXor(byte[] datas, int start, int end) {
        if (start < 0 || end < 0 || start >= datas.length || end >= datas.length || start > end)
            return 0;
        byte temp = datas[start];
        for (int i = start + 1; i <= end; i++) {
            temp ^= datas[i];
//            System.out.println(temp+","+datas[i]);
        }
        return temp;
    }

    public static byte[] getPrintData(double weight, double price, double total) {
        byte[] result = {};
        byte[] wrap = {(byte) 0x0A, (byte) 0x0D};
        result = Util.appendByteArr(result, Util.strToByteArr(DateUtils.formatDateTime(new Date())));
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, Util.strToByteArr("Weight:" + weight));
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, Util.strToByteArr("Price:" + price));
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, Util.strToByteArr("Total:" + total));
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, wrap);
        result = Util.appendByteArr(result, wrap);
        return result;
    }

    public static ArrayList<byte[]> getPrintQr(String content) {
        ArrayList<byte[]> list = new ArrayList<>();
        byte[] b1 = {0x0A};
        byte[] b2 = {0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x43, 0x04};
        byte[] b3 = {0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x45, 0x30};
        byte[] b4 = {0x1D, 0x28, 0x6B, 0x0D, 0x00, 0x31, 0x50, 0x30};
        byte[] b5 = {0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x51, 0x30};
        byte[] b6 = {0x0A, 0x0A, 0x0A};
        list.add(b1);
        list.add(b2);
        list.add(b3);

        //计算数据长度
        byte[] data = Util.strToByteArr(content);
        int len = data.length + 3;
        b4[3] = (byte) (len % 0xFF);//pL
        b4[4] = (byte) (len / 0xFF);//pH
        list.add(Util.appendByteArr(b4, data));
        list.add(b5);
        list.add(b6);
        return list;
    }

    public static byte[] getCmdPay(double money) {
        byte[] result = {0x00, 0x00, 0x00, 0x0A};
        byte[] bytes_pay = getPayBytes(money);
        result = Util.appendByteArr(result, bytes_pay);
        result = Util.appendByteArr(result, bytes_pay);
        result = Util.appendByteArr(result, getCheckSum(result));
        result = Util.appendByteArr(CMD_PAY, result);
        return result;
    }

    public static byte[] getCmdPayBack(double money) {
        byte[] result = {0x00, 0x00, 0x00, 0x0A};
        byte[] bytes_pay = getPayBytes(money);
        result = Util.appendByteArr(result, bytes_pay);
        result = Util.appendByteArr(result, bytes_pay);
        result = Util.appendByteArr(result, getCheckSum(result));
        result = Util.appendByteArr(CMD_PAY_BACK, result);
        return result;
    }

    public static byte[] getPayBytes(double money) {
        int n = (int) (Math.abs(money % 10000) * 100) + 1000000;
        byte[] result = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        for (int i = 5; i >= 0; i--) {
            result[i] = (byte) (n % 10);
            n /= 10;
        }
        return result;
    }

    //累加校验
    public static byte[] getCheckSum(byte[] arr) {
        int check = 0;
        for (byte b : arr) {
            check += b;
        }
        check %= 0xFF;
        byte[] result = {(byte) check};
        return result;
    }

    public static void main(String[] arg) {
//        double d = 0.10;
//        byte[] arr = getCmdPay(0.01);
//        byte[] arr=Util.strToByteArr(new Date().toLocaleString());
//        byte[] arr1 = Util.strToByteArr("12");
//        byte[] arr2 = Util.strToByteArr("34");
//        Util.printByteArr(arr);

        String content = "\r\n"+DateUtils.formatDateTime(new Date());
        content += "\r\n订单号：123456789ABCDEFGHIJKLMNOPQ";
        content += "\r\n商品名称：苹果梨子香蕉西瓜西红柿橘子";
        content += "\r\nhttp://www.xxx.com/xxx.do?abcdefghijklmnopq";
        ArrayList<byte[]> list = getPrintQr(content);
        for (byte[] arr : list) {
            System.out.println(String.valueOf(Util.getChars(arr)));
//            Util.printByteArr(arr);
        }
    }
}
