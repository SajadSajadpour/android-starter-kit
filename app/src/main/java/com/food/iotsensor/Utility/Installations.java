package com.food.iotsensor.Utility;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 *  一id 一id
 */
public class Installations {

    private static String Gv_UUID = null;
    private static final String INSTALLATION = "INSTALLATION-" + UUID.nameUUIDFromBytes("androidkit".getBytes());

    public synchronized static String id(Context context) {
        if (Gv_UUID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) {
                    writeInstallationFile(context, installation);
                }
                Gv_UUID = readInstallationFile(installation);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Gv_UUID;
        }
        return Gv_UUID;
    }

    /**

     * @param installation 保存唯一标识符的File对象。
     * @return 唯一标识符。
     * @throws IOException IO异常。
     */

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(installation, "r");
        byte[] bs = new byte[(int) accessFile.length()];
        accessFile.readFully(bs);
        accessFile.close();
        return new String(bs);
    }

    /**
     * 读出保存在程序文件系统中的表示该设备在此程序上的唯一标识符。
     * @param context      Context对象。
     * @param installation 保存唯一标识符的File对象。
     */
    private static void writeInstallationFile(Context context, File installation)
            throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String uuid = UUID.nameUUIDFromBytes(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).getBytes()).toString();
        Log.i("cfuture09-androidkit", uuid);
        out.write(uuid.getBytes());
        out.close();
    }
}