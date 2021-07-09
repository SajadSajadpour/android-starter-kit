package com.food.iotsensor.Bluetooth.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.food.iotsensor.Bluetooth.BoxDemoActivity;
import com.food.iotsensor.Bluetooth.common.C;
import com.food.iotsensor.Bluetooth.common.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


public class BtHelper {
    private String uuid = "00001101-0000-1000-8000-00805F9B34FB";

    public BluetoothDevice bluetoothDevice = null;
    public ArrayList<BluetoothDevice> list_device = new ArrayList<>();
    private BluetoothSocket socket = null;
    private InputStream inputStream = null;

    private byte[] Sendbytes;

    private BoxDemoActivity activity;

    public boolean working = false;
    private Thread thread_recv = null;
//    private Handler handler;

    public BtHelper(BoxDemoActivity activity) {
        this.activity = activity;
    }

    public void startReceive() {
        if (thread_recv != null)
            return;

        working = true;
        thread_recv = new Thread(runnable_recv);
        thread_recv.start();
        activity.status("connected");
        activity.is_connected = true;
        activity.btn_open.setText("CONNECTED");
    }

    public void stopReceive() {
        if (thread_recv == null)
            return;

        working = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
        thread_recv.interrupt();
        thread_recv = null;
        activity.is_connected = false;
        activity.btn_open.setText("Connect Scale");
    }

    public void find_bt() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            C.showMsg(activity, "BT FAILED");
            return;
        }
        // 打开蓝牙
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivity(intent);
        }
        C.log("adapter=" + adapter.toString() + ",en=" + adapter.isEnabled());

        Set<BluetoothDevice> set = adapter.getBondedDevices();
        ArrayList<String> list_str_device = new ArrayList<>();
        list_device.clear();
        for (BluetoothDevice dev : set) {
            C.log(dev.getName() + " " + dev.getAddress());
            list_device.add(dev);
            list_str_device.add(dev.getName() + " " + dev.getAddress());
        }

        ArrayAdapter adapter_device = new ArrayAdapter<>(activity,
                android.R.layout.simple_spinner_item, list_str_device);
        adapter_device
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.spinner_dev.setAdapter(adapter_device);

    }

    public void bt_open() {
        int idx = activity.spinner_dev.getSelectedItemPosition();
        C.log("select idx=" + idx);
        if (idx < list_device.size())
            bluetoothDevice = list_device.get(idx);
        if (bluetoothDevice == null) {
            return;
        }
        try {
            connect();
            activity.status("bt connect ok");
        } catch (IOException e) {
            activity.status("bt connect err: " + e.getMessage());
        }
        startReceive();
    }

    public void bt_close() {
        stopReceive();
    }

    public void connect() throws IOException {
        C.log("try connect to " + bluetoothDevice.getName() + " uuid=" + uuid);
        socket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID
                .fromString(uuid));
        socket.connect();

        inputStream = socket.getInputStream();
        send(Data.CMD_QUERY);
    }

    private Runnable runnable_recv = new Runnable() {
        @Override
        public void run() {
            if (socket == null || inputStream == null || !socket.isConnected())
                return;

            byte[] bytes = new byte[64];
            while (working) {
                try {
                    int len = inputStream.read(bytes);
                    if (len > 0) {
                        activity.bt_recv(Arrays.copyOfRange(bytes, 0, len));
                        C.log("len=" + len);
//                        if (handler == null)
//                            break;
//                        Message msg = handler.obtainMessage(0);
//                        msg.obj = Receiveytes;
//                        handler.sendMessage(msg);
                    }

                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void send(String data) {
        byte[] bt = (data).getBytes();
        Sendbytes = Arrays.copyOf(bt, bt.length);
        try {
            OutputStream outStream = socket.getOutputStream();
            outStream.write(Sendbytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
