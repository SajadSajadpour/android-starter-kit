package com.food.iotsensor.Bluetooth.common;

public class HexUtil {
    public static String ascBytesToStr(byte[] bytes) {
        String result = "";
        if (bytes == null)
            return result;
        for (byte b : bytes) {
            result += (char) b;
        }
        return result;
    }

    //ASCII字节数组转字符串
    public static String ascBytesToStr(byte[] bytes, int idx, int len) {
        String result = "";
        if (bytes == null)
            return result;
        for (int i = idx; i < idx + len; i++) {
            result += (char) bytes[i];
        }
        return result;
    }

    public static byte strToAscByte(String str) {
        char c = str.toCharArray()[0];
        return (byte) Integer.valueOf(c).intValue();
    }

    public static byte[] strToAscBytes(String str) {
        char[] chars = str.toCharArray();
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) Integer.valueOf(chars[i]).intValue();
        }
        return bytes;
    }

    public static String printBytes(byte[] bytes) {
        System.out.print("[");
        String result = "";
        for (byte b : bytes) {
            result += StrUtil.padLeft(Integer.toHexString(b & 0xFF), 2, '0') + " ";
        }
        System.out.print(result.trim());
        System.out.println("]");
        return result;
    }

    public static void main(String[] s) {
        printBytes(strToAscBytes("1"));
    }
}
