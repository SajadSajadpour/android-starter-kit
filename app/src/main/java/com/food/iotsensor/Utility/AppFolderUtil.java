package com.food.iotsensor.Utility;

import android.os.Environment;

import java.io.File;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class AppFolderUtil {
    /**
     *  app sd
     *
     * @return  ， null
     */
    public static File getAppFolder() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File appFolder = new File(Environment.getExternalStorageDirectory(), "iotsensor");
            return createOnNotFound(appFolder);

        } else {
            return null;
        }
    }
    /**
     *
     *
     * @param folder
     * @return  ， null
     */
    private static File createOnNotFound(File folder) {
        if (folder == null) {
            return null;
        }

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (folder.exists()) {
            folder.mkdirs();
            return folder;
        } else {
            return folder;
        }
    }

    /**
     *
     * @param file
     */
    public static void clearFolder(File file){
        File files[] = file.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { //

                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { //

                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
