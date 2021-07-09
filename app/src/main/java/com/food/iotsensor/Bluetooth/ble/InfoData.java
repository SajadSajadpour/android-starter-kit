package com.food.iotsensor.Bluetooth.ble;


import com.food.iotsensor.Bluetooth.common.Convert;
import com.food.iotsensor.Bluetooth.common.HexUtil;

public class InfoData {
    // 数据协议 0A 30 20 A3 30 31 35 3E 0D
    public static byte HEAD = 58;// OA
    public static byte HEAD_RET = 9;// OA
    public static byte DEST = 48;// 30
    public static byte SOURCE = 32;// 20
    public static byte CMD_READ = -93;// A3
    public static byte CMD_WRITE = -92;// A4
    public static byte[] bArea, bCheck;
    public static byte END = 13;// OD
    public static int count_area = 16 * 4;

    public static String cardid = "1234567890";

    //归零
    //在工作状态，发送Z\r\n 即可实现称重归零。满量程的±4%。
    public static byte[] GUILING = {90, 13, 10};

    //去皮
    //在工作状态，发送T\r\n即可实现称重去皮。全量程去皮。
    public static byte[] QUPI = {84, 13, 10};

    //重启
    public static byte[] RESTART = {82, 13, 10};

    //关机阀值
    //在工作状态，发送I3\r\n，表示超过3个d，关机计时清零，可取范围为1~20，默认选取3
    public static byte[] SHUTDOWN_VAL = {73, 0, 13, 10};

    //关机时间
    //在工作状态，发送B5\r\n，表示5分钟无操作即关机。设置的时间必须不小于1分钟，最大不超过240分钟。默认关机省电时间是5分钟。
    public static byte[] SHUTDOWN_TIME = {66, 0, 13, 10};

    public static byte[][] UNIT = {{85, 49, 13, 10}, {85, 50, 13, 10},
            {85, 51, 13, 10}, {85, 52, 13, 10}, {85, 53, 13, 10}};

    public static boolean isHead(byte b) {
        return b == HEAD || b == HEAD_RET;
    }

    public static boolean isEnd(byte b) {
        return b == END;
    }

    //获取关机阀值命令
    public static byte[] getCmdShutdownVal(String s) {
        //数值范围1~20
        int val = Convert.toInt(s);
        if (val < 1 || val > 20) val = 3;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 73;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //获取关机时间命令
    public static byte[] getCmdShutdownTime(String s) {
        //数值范围1~240
        int val = Convert.toInt(s);
        if (val < 1 || val > 240) val = 5;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 66;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //零点跟踪
    public static byte[] getCmdldgz(String s) {
        //数值范围0~6
        int val = Convert.toInt(s);
        if (val < 0 || val > 6) val = 2;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 65;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //滤波设置
    public static byte[] getCmdlbsz(String s) {
        //数值范围1~6
        int val = Convert.toInt(s);
        if (val < 1 || val > 6) val = 2;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 70;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //稳定判断
    public static byte[] getCmdwdpd(String s) {
        //数值范围0~6
        int val = Convert.toInt(s);
        if (val < 0 || val > 6) val = 2;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 83;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //量程设置
    public static byte[] getCmdlcsz(String s) {
        //数值范围0~9999
        int val = Convert.toInt(s);
        if (val < 0 || val > 9999) val = 30;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 67;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    //校准设置
    public static byte[] getCmdjzsz(String s) {
        //数值范围，最多4位数
        int val = Convert.toInt(s);
        if (val < 0 || val > 9999) val = 5;

        byte[] bytes = HexUtil.strToAscBytes(val+"");
        byte[] arr = new byte[bytes.length + 3];
        arr[0] = 69;
        System.arraycopy(bytes, 0, arr, 1, bytes.length);
        arr[arr.length - 2] = 13;
        arr[arr.length - 1] = 10;
        return arr;
    }

    public static byte[] getCmdByParam(byte[] arr, byte param) {
        arr[1] = param;
        return arr;
    }

    public static void displayByteArr(byte[] arr) {
        if (arr == null) {
            System.out.println("arr is null!");
            return;
        }
        for (byte b : arr) {
            System.out.print(b + " ");
        }
    }

    public static void main(String[] args) {
    }

}
