package com.food.iotsensor.Bluetooth.common;

import android.hardware.Camera;
import android.util.Log;

public class CameraHelper {
    public static int iCameraCnt = 0;
    public static int iFontCameraIndex = -1;
    public static int iBackCameraIndex = -1;
    public static boolean isBack = true;

    public static void getCameraInfo() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        iCameraCnt = Camera.getNumberOfCameras();

        for (int i = 0; i < iCameraCnt; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                iFontCameraIndex = i;
                Log.d("fwe_cam", "iFontCameraIndex=" + iFontCameraIndex);
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                iBackCameraIndex = i;
                Log.d("fwe_cam", "iBackCameraIndex=" + iBackCameraIndex);
            }

        }

    }
}
