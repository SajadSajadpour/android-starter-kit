package com.food.iotsensor.Bluetooth.common;

import java.text.DecimalFormat;

public class Data {
    private static byte[] CMD_BIAODING = {0x02, 0x31, 0x45, 0x20, 0x20, 0x20, 0x31, 0x30, 0x30, 0x03};
    private static byte[] CMD_ZHILING = {0x02, 0x31, 0x5A, 0x20, 0x03};
    private static byte[] CMD_CLGL = {0x02, 0x3F, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x44, 0x20, 0x20, 0x20, 0x20, 0x35, 0x30, 0x03};

    private static byte[] CMD_MODE = {0x02, 0x3F, 0x4D, 0x31, 0x03};

    public static String CMD_QUERY = "S";
    public static String CMD_GUILING = "Z";
    public static String CMD_JZ = "C";

    //是否是重量数据
    public static boolean isWeight(byte[] arr) {
        boolean result = arr != null && arr.length == 27;
        return result;
    }

    //获取重量数据
    public static String getWeight(byte[] arr) {
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(Convert.toDouble(HexUtil.ascBytesToStr(arr, 3, 7)));
        return result;
    }

    public static String getWeight(String data) {
        int n = data.indexOf("N");
        int s = data.indexOf("S");
        if (n < 0) return "0";
        if (s < 0) s = data.indexOf("\r\n");
        if (s < 0 || n + 1 > s) return "0";
        String w = data.substring(n + 1, s);
        return w;
    }

    public static byte[] getCmdBiaoding(int idx, String fm) {
        CMD_BIAODING[1] = HexUtil.strToAscByte(Math.abs(idx) % 8 + "");
        byte[] bytes = HexUtil.strToAscBytes(fm);
        System.arraycopy(bytes, 0, CMD_BIAODING, 3, 6);
        return CMD_BIAODING;
    }

    public static byte[] getCmdZhiling(int idx) {
        CMD_ZHILING[1] = HexUtil.strToAscByte(Math.abs(idx) % 8 + "");
        return CMD_ZHILING;
    }

    public static byte[] getCmdSet(String gl) {
        //补齐6位
        gl = StrUtil.padLeft(Convert.toInt(gl) + "", 6, ' ');
        byte[] b_gl = HexUtil.strToAscBytes(gl);
        System.arraycopy(b_gl, 0, CMD_CLGL, 10, 6);
        return CMD_CLGL;
    }

    //1&4
    public static byte[] getCmdMode(int idx) {
        CMD_MODE[3] = HexUtil.strToAscByte(Math.abs(idx) % 5 + "");
        return CMD_MODE;
    }

    public static void main(String[] s) {
        HexUtil.printBytes(getCmdBiaoding(1, ""));
    }
}
