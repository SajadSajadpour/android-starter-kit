package com.food.iotsensor.Bluetooth.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.food.iotsensor.Bluetooth.BoxDemoActivity;
import com.food.iotsensor.Bluetooth.common.Data;
import com.food.iotsensor.Bluetooth.usbserial.driver.UsbSerialDriver;
import com.food.iotsensor.Bluetooth.usbserial.driver.UsbSerialPort;
import com.food.iotsensor.Bluetooth.usbserial.driver.UsbSerialProber;
import com.food.iotsensor.Bluetooth.usbserial.util.SerialInputOutputManager;
import com.food.iotsensor.BuildConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;


public class UsbHelper {
    private BoxDemoActivity activity;

    private ArrayList<UsbItem> usbItems = new ArrayList<>();
    private UsbItem usbItem = null;

    private enum UsbPermission {Unknown, Requested, Granted, Denied}

    private static final String INTENT_ACTION_GRANT_USB = BuildConfig.APPLICATION_ID + ".GRANT_USB";
    private static final int WRITE_WAIT_MILLIS = 2000;
    private static final int READ_WAIT_MILLIS = 2000;
    private int baudRate = 19200;
    private boolean withIoManager = true;

    private BroadcastReceiver broadcastReceiver;
    private SerialInputOutputManager usbIoManager;
    private UsbSerialPort usbSerialPort;
    private UsbPermission usbPermission = UsbPermission.Unknown;

    public UsbHelper(BoxDemoActivity activity) {
        this.activity = activity;
    }

    public void usb_find() {
        UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();
        UsbSerialProber usbCustomProber = CustomProber.getCustomProber();
        usbItems.clear();
        activity.list_str_device.clear();
        activity.status("found " + usbManager.getDeviceList().size() + " device");
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbDefaultProber.probeDevice(device);
            if (driver == null) {
                driver = usbCustomProber.probeDevice(device);
            }
            if (driver != null) {
                for (int port = 0; port < driver.getPorts().size(); port++) {
                    usbItems.add(new UsbItem(device, port, driver));
                    String name = "<no driver>";
                    if (driver != null) {
                        name = driver.getClass().getSimpleName().replace("SerialDriver", "") + port;
                    }
                    String info = String.format(", V %04X, P %04X", device.getVendorId(), device.getProductId());
                    activity.list_str_device.add(name + info);
                }
            } else {
                usbItems.add(new UsbItem(device, 0, null));
                activity.list_str_device.add("no device");
            }
        }

        ArrayAdapter adapter_device = new ArrayAdapter<>(activity,
                android.R.layout.simple_spinner_item, activity.list_str_device);
        adapter_device
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.spinner_dev.setAdapter(adapter_device);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(INTENT_ACTION_GRANT_USB)) {
                    usbPermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                            ? UsbPermission.Granted : UsbPermission.Denied;

                    if (usbPermission == UsbPermission.Unknown || usbPermission == UsbPermission.Granted)
                        usb_connect();
                }
            }
        };
    }

    public void usb_open() {
        if (usbItems.size() == 0) return;
        usbItem = usbItems.get(activity.spinner_dev.getSelectedItemPosition());

        activity.registerReceiver(broadcastReceiver, new IntentFilter(INTENT_ACTION_GRANT_USB));

        UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        if (!usbManager.hasPermission(usbItem.driver.getDevice())) {
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(activity, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
            usbManager.requestPermission(usbItem.driver.getDevice(), usbPermissionIntent);
        }
        if (usbPermission == UsbPermission.Unknown || usbPermission == UsbPermission.Granted)
            activity.mainLooper.post(this::usb_connect);
//        usbHelper.connect(spinner_dev.getSelectedItemPosition());
    }

    public void usb_connect() {
        activity.status("try open " + usbItem.device.getDeviceName());
        UsbDevice device = null;
        UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        for (UsbDevice v : usbManager.getDeviceList().values())
            if (v.getDeviceId() == usbItem.device.getDeviceId())
                device = v;
        if (device == null) {
            activity.status("connection failed: device not found");
            return;
        }
        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        if (driver == null) {
            driver = CustomProber.getCustomProber().probeDevice(device);
        }
        if (driver == null) {
            activity.status("connection failed: no driver for device");
            return;
        }
        if (driver.getPorts().size() < usbItem.port) {
            activity.status("connection failed: not enough ports at device");
            return;
        }
        usbSerialPort = driver.getPorts().get(usbItem.port);
        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
        if (usbConnection == null && usbPermission == UsbPermission.Unknown && !usbManager.hasPermission(driver.getDevice())) {
            usbPermission = UsbPermission.Requested;
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(activity, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            return;
        }
        if (usbConnection == null) {
            if (!usbManager.hasPermission(driver.getDevice()))
                activity.status("connection failed: permission denied");
            else
                activity.status("connection failed: open failed");
            return;
        }

        activity.status("usbConnection=" + usbConnection.toString());
        try {
            usbSerialPort.open(usbConnection);
            usbSerialPort.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE);
            if (withIoManager) {
                usbIoManager = new SerialInputOutputManager(usbSerialPort, activity);
                Executors.newSingleThreadExecutor().submit(usbIoManager);
            }
            activity.status("connected");
            activity.is_connected = true;
            activity.btn_open.setText("CONNECTED");
            send(Data.CMD_QUERY);
        } catch (Exception e) {
            activity.status("connection failed: " + e.getMessage());
            usb_close();
        }
    }

    public void usb_close() {
        if (broadcastReceiver != null)
            activity.unregisterReceiver(broadcastReceiver);
        if (usbIoManager != null)
            usbIoManager.stop();
        usbIoManager = null;
        try {
            usbSerialPort.close();
        } catch (IOException ignored) {
        }
        usbSerialPort = null;
        activity.is_connected = false;
        activity.btn_open.setText("Connect Scale");
    }

    public void send(byte[] bytes) {
        try {
            activity.status("send:" + new String(bytes));
            usbSerialPort.write(bytes, WRITE_WAIT_MILLIS);
        } catch (Exception e) {
            usb_close();
        }
    }

    public void send(String str) {
        byte[] data = (str).getBytes();
        send(data);
    }

    private void read() {
        if (!activity.is_connected) {
            Toast.makeText(activity, "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] buffer = new byte[8192];
            int len = usbSerialPort.read(buffer, READ_WAIT_MILLIS);
            activity.receive(Arrays.copyOf(buffer, len));
        } catch (IOException e) {
            // when using read with timeout, USB bulkTransfer returns -1 on timeout _and_ errors
            // like connection loss, so there is typically no exception thrown here on error
            activity.status("connection lost: " + e.getMessage());
            usb_close();
        }
    }

}
