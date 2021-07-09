/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.food.iotsensor.Bluetooth.ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;


import com.food.iotsensor.Bluetooth.common.C;

import java.util.ArrayList;
import java.util.List;


/**
 * Service for managing connection and data communication with a GATT server
 * hosted on a given Bluetooth LE device.
 */
@SuppressLint("NewApi")
public class BleHelper {
    private final static String TAG = "fweind";

    private BluetoothManager mManager;
    private BluetoothAdapter mAdapter;
    private String mDeviceAddress;
    private BluetoothGatt mGatt;

    private BluetoothGattCharacteristic gattChar_read = null;
    private BluetoothGattCharacteristic gattChar_write = null;

    private Handler mHandler;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private OnConnectListener mOnConnectListener;
    private OnDisconnectListener mOnDisconnectListener;
    private OnServiceDiscoverListener mOnServiceDiscoverListener;
    private OnDataAvailableListener mOnDataAvailableListener;
    private LeScanCallback mLeScanCallback;
    private Activity activity;

    public BleHelper(Activity c) {
        activity = c;
    }

    public boolean checkBleHardwareAvailable() {
        boolean hasBle = activity.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE);
        Log.d(TAG, "[checkBleHardwareAvailable]" + hasBle);
        return hasBle;
    }

    public boolean initBle() {
        if (mManager == null) {
            mManager = (BluetoothManager) activity
                    .getSystemService(Context.BLUETOOTH_SERVICE);
            if (mManager == null) {
                Log.d(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mAdapter = mManager.getAdapter();
        if (mAdapter == null) {
            Log.d(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        findReadGattChar();
        findWriteGattChar();

        Log.d(TAG, "[initBle]ok");
        mHandler = new Handler();

        return mAdapter.enable();
    }

    public void scan(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mAdapter.startLeScan(mLeScanCallback);
        } else {
            mAdapter.stopLeScan(mLeScanCallback);
        }
    }

    public boolean connect(BluetoothDevice device) {
        if (mAdapter == null || device == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }

        scan(false);
        if (mDeviceAddress != null
                && device.getAddress().equals(mDeviceAddress) && mGatt != null) {
            Log.d(TAG, "Trying to use an existing mGatt for connection.");
            C.showMsg(activity, "connect is existed");
            return mGatt.connect();
        }

        mGatt = device.connectGatt(activity, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mDeviceAddress = device.getAddress();
        C.showMsg(activity, "create new connect");
        return true;
    }

    public void disconnect() {
        if (mAdapter == null || mGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mGatt.disconnect();
        mGatt.close();
        mGatt = null;
    }

    public boolean isConnected() {
        return mAdapter != null && mGatt != null && mGatt.connect();
    }

    // 读取指定uuid特征值
    public boolean read() {
        if (!isConnected() || findReadGattChar() == null) {
            return false;
        }

        boolean flag = readCharacteristic(gattChar_read);
        return flag;
    }

    // 写入指定uuid特征值
    public boolean write(final byte[] data) {
        if (!isConnected() || findWriteGattChar() == null || data == null) {
            return false;
        }

        gattChar_write.setValue(data);

        //write操作经常性会失败，要多次写入，最多10次
        int nTry = 0;
        boolean flag = false;
        while (nTry++ < 100 && (flag = writeCharacteristic(gattChar_write)) == false) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public void write(final String data) {
        write(Utils.hexToBytes(Utils.ascToHex(data)));
    }

    public void writeHex(final String data) {
        write(Utils.hexToBytes(data));
    }

    //===================================================================

    public BluetoothGattCharacteristic findReadGattChar() {
        if (gattChar_read == null) {
            gattChar_read = getGattChar(C.UUID_READ);
            if (gattChar_read != null)
                setCharacteristicNotification(gattChar_read, true);
        }
        return gattChar_read;
    }

    public BluetoothGattCharacteristic findWriteGattChar() {
        if (gattChar_write == null) {
            gattChar_write = getGattChar(C.UUID_WRITE);
            if (gattChar_write != null)
                setCharacteristicNotification(gattChar_write, true);
        }
        return gattChar_write;
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (mGatt == null)
            return new ArrayList<>();

        return mGatt.getServices();
    }

    // 返回指定uuid的BluetoothGattCharacteristic
    public BluetoothGattCharacteristic getGattChar(String uuid) {
        List<BluetoothGattService> gattServices = getSupportedGattServices();
        for (BluetoothGattService gattService : gattServices) {
//            Log.d("fwe_debug", "[found]servie=" + gattService.getUuid());
            List<BluetoothGattCharacteristic> gattChars = gattService
                    .getCharacteristics();
            for (BluetoothGattCharacteristic gattChar : gattChars) {
//                Log.d("fwe_debug", "[found]char=" + gattChar.getUuid());
                if (gattChar.getUuid().toString().equals(uuid)) {
                    Log.d("fwe_debug", "servie=" + gattService.getUuid().toString() + ",char=" + gattChar.getUuid().toString());
                    return gattChar;
                }
            }
        }
        return null;
    }

    public String showAllGattChar() {
        String result = "";
        List<BluetoothGattService> gattServices = getSupportedGattServices();
        for (BluetoothGattService gattService : gattServices) {
            List<BluetoothGattCharacteristic> gattChars = gattService
                    .getCharacteristics();
            for (BluetoothGattCharacteristic gattChar : gattChars) {
                result += gattChar.getUuid().toString() + ",";
            }
        }
        return result;
    }

    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mAdapter == null || mGatt == null) {
            return false;
        }
        return mGatt.readCharacteristic(characteristic);
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mAdapter == null || mGatt == null) {
            return false;
        }
        boolean flag = mGatt.writeCharacteristic(characteristic);
//        Log.d(TAG, "[write]" + HexUtil.byteArrToHexStr(characteristic.getValue()) + " , flag=" + flag);
        return flag;
    }

    public boolean setCharacteristicNotification(
            BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mAdapter == null || mGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        return mGatt.setCharacteristicNotification(characteristic, enabled);
    }

    // callback methods for GATT events
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (mOnConnectListener != null)
                    mOnConnectListener.onConnect(gatt);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG,
                        "Attempting to start service discovery:"
                                + mGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                if (mOnDisconnectListener != null)
                    mOnDisconnectListener.onDisconnect(gatt);
                Log.i(TAG, "Disconnected from GATT server.");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS
                    && mOnServiceDiscoverListener != null) {
                mOnServiceDiscoverListener.onServiceDiscover(gatt);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            if (mOnDataAvailableListener != null)
                mOnDataAvailableListener.onCharacteristicRead(gatt,
                        characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, characteristic.getUuid() + " , status=" + status);
//            if (mOnDataAvailableListener != null)
//                mOnDataAvailableListener.onCharacteristicWrite(gatt, characteristic);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            if (mOnDataAvailableListener != null)
                mOnDataAvailableListener.onCharacteristicWrite(gatt,
                        characteristic);
        }
    };


    //=============================================================

    // 初始化：检查硬件支持；是否开启蓝牙；是否开启并获取成功
//	public void initialize() {
//		if (!checkBleHardwareAvailable()) {
//			Toast.makeText(activity, "BLE Hardware is not available!",
//					Toast.LENGTH_LONG).show();
//			activity.finish();
//		}

//		if (!initBle())
//			openBtEnabled();
//
//		if (!initBle()) {
//			Toast.makeText(activity, "BLE init failed!", Toast.LENGTH_LONG)
//					.show();
//			activity.finish();
//		}
//	}

//	public void openBtEnabled() {
//		Log.d(TAG, "[openBtEnabled]");
//		Intent enableBtIntent = new Intent(
//				BluetoothAdapter.ACTION_REQUEST_ENABLE);
//		activity.startActivityForResult(enableBtIntent, 1);
//	}

    public interface OnConnectListener {
        void onConnect(BluetoothGatt gatt);
    }

    public interface OnDisconnectListener {
        void onDisconnect(BluetoothGatt gatt);
    }

    public interface OnServiceDiscoverListener {
        void onServiceDiscover(BluetoothGatt gatt);
    }

    public interface OnDataAvailableListener {
        void onCharacteristicRead(BluetoothGatt gatt,
                                  BluetoothGattCharacteristic characteristic, int status);

        void onCharacteristicWrite(BluetoothGatt gatt,
                                   BluetoothGattCharacteristic characteristic);
    }

    public void setOnConnectListener(OnConnectListener l) {
        mOnConnectListener = l;
    }

    public void setOnDisconnectListener(OnDisconnectListener l) {
        mOnDisconnectListener = l;
    }

    public void setOnServiceDiscoverListener(OnServiceDiscoverListener l) {
        mOnServiceDiscoverListener = l;
    }

    public void setOnDataAvailableListener(OnDataAvailableListener l) {
        mOnDataAvailableListener = l;
    }

    public void setLeScanCallback(LeScanCallback l) {
        mLeScanCallback = l;
    }

}
