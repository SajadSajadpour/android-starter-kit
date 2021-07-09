package com.food.iotsensor.FingerPrint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;


import com.food.iotsensor.Constant;
import com.food.iotsensor.InterFaces.FingerCallbackLister;
import com.food.iotsensor.R;
import com.food.iotsensor.Utility.ACacheUtil;
import com.food.iotsensor.Utility.PermissionUtils;
import com.food.iotsensor.Utility.RemindDialogUtil;
import com.food.iotsensor.Utility.SharedPreferencesUtils;
import com.food.iotsensor.ViewUtility.FingerPrintDialogUtil;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class FingerPrintUtil {
     public final static int REQUEST_CODE_USE_FINGERPRINT = 1;
    //
    public final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT;

    private static FingerPrintUtil fingerPrintUtil;
    private MyAuthCallback myAuthCallback = null;
    private CancellationSignal cancellationSignal = null;
    private Handler handler = null;

    public static FingerPrintUtil getInstance() {
        if (fingerPrintUtil == null) {
            return new FingerPrintUtil();
        } else {
            return fingerPrintUtil;
        }
    }

    public void toFingerPrintLogin(Activity activity, final FingerCallbackLister listener) {
        String token = ACacheUtil.get(activity).getAsString(Constant.authToken);
        boolean isFingerPrintLogin = fingerPrintIsOpen(activity);
        if (token != null && !token.equals("") && isFingerPrintLogin) {
 //            if (!isFingerprintInfoChange(activity)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(activity, listener);
                } else {
                    RemindDialogUtil.showEasy(activity, activity.getString(R.string.no_sensor_dialog_message));
                }
//            } else {
//                //
//                RemindDialogUtil.showEasy(activity, activity.getString(R.string.finger_change));
//                String id = SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId);
//                SharedPreferencesUtils.getInstance(activity).setData(Constant.loginByFinger + id, String.valueOf(false));
//
//            }
        } else {
        }

    }

    public FingerprintManagerCompat getFingerprintManager(Context context) {
        FingerprintManagerCompat fingerprintManager = null;
        try {
            fingerprintManager = FingerprintManagerCompat.from(context);
        } catch (Throwable e) {
            Log.d("log", "have not class FingerprintManager");
        }
        return fingerprintManager;
    }

    //是否支持指纹识别
    public boolean isSupportFingerPrint(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            RemindDialogUtil.showEasy(activity, activity.getString(R.string.error_sensor_dialog_message));
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (FingerPrintUtil.getInstance().getFingerprintManager(activity).isHardwareDetected() && FingerPrintUtil.getInstance().getFingerprintManager(activity).hasEnrolledFingerprints()) {
                return true;
            } else if (!FingerPrintUtil.getInstance().getFingerprintManager(activity).isHardwareDetected()) {
                RemindDialogUtil.showEasy(activity, activity.getString(R.string.no_sensor_dialog_message));
                return false;
            } else if (!FingerPrintUtil.getInstance().getFingerprintManager(activity).hasEnrolledFingerprints()) {
                RemindDialogUtil.showEasy(activity, activity.getString(R.string.no_fingerprint_enrolled_dialog_message));
                return false;
            }
        }
        return false;


    }

    //true false
    private boolean isFingerprintInfoChange(Activity activity) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                FingerprintManager fingerprintManager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
                Method method = null;
                String id = SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId);
                String jsonString = SharedPreferencesUtils.getInstance(activity).getData(Constant.fingerChange + id);
                JSONObject json = new JSONObject(jsonString);
                Iterator iterator = json.keys();
                method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
                Object obj = method.invoke(fingerprintManager);
                if (obj != null) {
                    Class<?> clazz = Class.forName("android.hardware.fingerprint.Fingerprint");
                    Method getFingerId = clazz.getDeclaredMethod("getFingerId");


                    if (json.length() != ((List) obj).size()) {
                        return true;
                    }
                    for (int i = 0; i < ((List) obj).size(); i++) {
                        Object item = ((List) obj).get(i);
                        if (null == item) {
                            continue;
                        }
                        boolean isHasKey = false;
                        while (iterator.hasNext()) {
                            String key = (String) iterator.next();
                            String value = json.getString(key);
                            if (String.valueOf(getFingerId.invoke(item)).equals(value)) {
                                isHasKey = true;
                                break;
                            }
                        }
                        if (!isHasKey) {
                            return true;
                        }
                    }
                    return false;
                    // SharedPreferencesUtils.getInstance(activity).setJsonList(Constant.fingerIdList,(List) obj);
                } else {
                    return true;
                }
            } else {
                return true;
            }

        } catch (Exception e) {
            System.out.println("inja" + e);
            e.printStackTrace();
        }
        return true;
    }

     private void addFingerprintInfo(Activity activity, String changeId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                FingerprintManager fingerprintManager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
                Method method = null;
                JSONObject json = new JSONObject();
                method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
                Object obj = method.invoke(fingerprintManager);
                if (obj != null) {
                    Class<?> clazz = Class.forName("android.hardware.fingerprint.Fingerprint");
                    Method getFingerId = clazz.getDeclaredMethod("getFingerId");
                    ArrayList<String> list = new ArrayList<String>() {
                    };

                    for (int i = 0; i < ((List) obj).size(); i++) {
                        Object item = ((List) obj).get(i);
                        if (null == item) {
                            continue;
                        }
                        json.put(String.valueOf(i), String.valueOf(getFingerId.invoke(item)));
                    }
                    if (json.length() > 0) {
                        SharedPreferencesUtils.getInstance(activity).setData(changeId, json.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // true false
    public void fingerPrintSwitch(Activity activity, boolean isopen) {
        String id = SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId);
        SharedPreferencesUtils.getInstance(activity).setData(Constant.loginByFinger + id, String.valueOf(isopen));
        if (isopen) {
            addFingerprintInfo(activity, Constant.fingerChange + id);
        } else {
            SharedPreferencesUtils.getInstance(activity).setData(Constant.fingerChange + id, "");
        }
    }

    public boolean fingerPrintIsOpen(Activity activity) {
        String id = SharedPreferencesUtils.getInstance(activity).getData(Constant.toLoginId);
        String open = SharedPreferencesUtils.getInstance(activity).getData(Constant.loginByFinger + id);
        if (open.contentEquals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private void handleErrorCode(Activity activity, int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorCanceled_warning));
                //  setResultInfo(R.string.ErrorCanceled_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorHwUnavailable_warning));
                //    setResultInfo(R.string.ErrorHwUnavailable_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorLockout_warning));
                // setResultInfo(R.string.ErrorLockout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorNoSpace_warning));
                //  setResultInfo(R.string.ErrorNoSpace_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorTimeout_warning));
                // setResultInfo(R.string.ErrorTimeout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                FingerPrintDialogUtil.setText(activity.getString(R.string.ErrorUnableToProcess_warning));
                // setResultInfo(R.string.ErrorUnableToProcess_warning);
                break;
        }
    }

    //
    private void requestPermission(final Activity activity, final FingerCallbackLister listener) {
        PermissionUtils.checkAndRequestPermission(activity, USE_FINGERPRINT, REQUEST_CODE_USE_FINGERPRINT,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        goToFinger(activity, listener);
                        //
                        Log.d("权限", "成功获取权限");
                    }
                });
    }

    public void goToFinger(final Activity activity, final FingerCallbackLister listener) {
        Log.d("权限", "成功获取权限");
        if (FingerPrintUtil.getInstance().isSupportFingerPrint(activity)) {
            try {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);


                        switch (msg.what) {
                            //
                            case MyAuthCallback.MSG_AUTH_SUCCESS:

                                listener.callBack();
                                break;
                            //
                            case MyAuthCallback.MSG_AUTH_FAILED:
                                FingerPrintDialogUtil.setText(activity.getString(R.string.validation_fingerprint_fail));
                                cancellationSignal = null;
                                break;
                            //
                            case MyAuthCallback.MSG_AUTH_ERROR:
                                handleErrorCode(activity, msg.arg1);
                                break;
                            case MyAuthCallback.MSG_AUTH_HELP:
                                // handleHelpCode(msg.arg1);
                                break;
                        }
                    }
                };

                myAuthCallback = new MyAuthCallback(handler);
                CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
                if (cancellationSignal == null) {
                    cancellationSignal = new CancellationSignal();
                }
                FingerPrintUtil.getInstance().getFingerprintManager(activity).authenticate(cryptoObjectHelper.buildCryptoObject(), 0,
                        cancellationSignal, myAuthCallback, null);
                FingerPrintDialogUtil.show(activity, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancellationSignal != null)
                            cancellationSignal.cancel();
                        cancellationSignal = null;
                    }
                });
            } catch (Exception e) {
                RemindDialogUtil.showEasy(activity, activity.getString(R.string.error_sensor_dialog_init_fail));
                e.printStackTrace();
            }
        }

    }

    public void onDestroy() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
    }
}
