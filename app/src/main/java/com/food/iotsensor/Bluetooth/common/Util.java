package com.food.iotsensor.Bluetooth.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 数据工具
 */
public class Util {

    public static void printByteArr(byte[] bytes) {
        System.out.print("[");
        for (byte b : bytes) {
            System.out.print(Integer.toHexString(b & 0xFF) + " ");
        }
        System.out.println("]");
    }

    public static byte[] strToByteArr(String str) {
        try {
            return str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static byte[] strToByteArr2(String str) {
        char[] ch = str.toCharArray();
        byte[] result = new byte[ch.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) ch[i];
        }
        return result;
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static byte[] appendByteArr(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
//        System.out.println("len=" + result.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    //-------------------------------------------------------
    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    static public int isOdd(int num) {
        return num & 0x1;
    }

    //-------------------------------------------------------
    static public int HexToInt(String inHex)//Hex字符串转int
    {
        return Integer.parseInt(inHex, 16);
    }

    //-------------------------------------------------------
    static public byte HexToByte(String inHex)//Hex字符串转byte
    {
        return (byte) Integer.parseInt(inHex, 16);
    }

    //-------------------------------------------------------
    static public String Byte2Hex(Byte inByte)//1字节转2个Hex字符
    {
        return String.format("%02x", inByte).toUpperCase();
    }

    //-------------------------------------------------------
    static public String ByteArrToHex(byte[] inBytArr)//字节数组转转hex字符串
    {
        StringBuilder strBuilder = new StringBuilder();
        int j = inBytArr.length;
        for (int i = 0; i < j; i++) {
            strBuilder.append(Byte2Hex(inBytArr[i]));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    //-------------------------------------------------------
    static public String ByteArrToHex(byte[] inBytArr, int offset, int byteCount)//字节数组转转hex字符串，可选长度
    {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            strBuilder.append(Byte2Hex(inBytArr[i]));
        }
        return strBuilder.toString();
    }

    //-------------------------------------------------------
    //转hex字符串转字节数组
    static public byte[] HexToByteArr(String inHex)//hex字符串转字节数组
    {
        int hexlen = inHex.length();
        byte[] result;
        if (isOdd(hexlen) == 1) {//奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {//偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    //ASCII字节数组转字符串
    public static String ascByteArrToStr(byte[] data) {
        String result = "";
        if (data == null)
            return result;
        for (byte b : data) {
            result += (char) b;
        }
        return result;
    }

    //ASC字节数组转字符串
    public static String ascByteArrToStr(byte[] data, int idx, int len) {
        String result = "";
        if (data == null)
            return result;
        for (int i = idx; i < idx + len; i++) {
            result += (char) data[i];
        }
        return result;
    }

    public static byte[] hexStrToByteArr(String hexStr) {
        byte[] result = {};
        if (hexStr == null || hexStr.length() < 2)
            return result;
        String[] arr = hexStr.split(" ");
        result = new byte[arr.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Integer.parseInt(arr[i], 16);
        }
        return result;
    }

    public static int byte2int(byte h, byte l) {
        return h * 16 + l;
    }

    //返回字符串
    public static String toStr(Object o) {
        String ret = "";
        try {
            ret = o.toString();
        } catch (Exception e) {
        }
        return ret;
    }

    //返回整型
    public static int toInt(Object o, int def) {
        try {
            return (int) Double.parseDouble(o.toString());
        } catch (Exception e) {
        }
        return def;
    }

    public static int toInt(Object o) {
        return toInt(o, 0);
    }

    //返回double
    public static double toDouble(Object o) {
        try {
            return Double.parseDouble(o.toString());
        } catch (Exception e) {
        }
        return 0;
    }

    //根据位数得到10,100,1000...
    public static double getTenByDigit(int digit) {
        double d = 1.0;
        while (digit-- > 0) {
            d *= 10.0;
        }
        return d;
    }

    //返回整型
    public static boolean toBoolean(Object o) {
        try {
            return Boolean.parseBoolean(o.toString());
        } catch (Exception e) {
        }
        return false;
    }

    public static String getArrVal(String[] arr, int idx) {
        if (arr == null)
            return "";
        if (idx < 0 || idx >= arr.length)
            return arr[0];
        return arr[idx];
    }

    /**
     * @作者 尧
     * @功能 String左对齐
     */
    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * @作者 尧
     * @功能 String右对齐
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    public static void main(String arg[]) {
        printByteArr(strToByteArr("QRCodeTest"));
    }
}