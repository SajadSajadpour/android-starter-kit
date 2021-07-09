package com.food.iotsensor.Bluetooth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.food.iotsensor.Bluetooth.ble.BtHelper;
import com.food.iotsensor.Bluetooth.common.Data;
import com.food.iotsensor.Bluetooth.common.HexUtil;
import com.food.iotsensor.Bluetooth.usb.UsbHelper;
import com.food.iotsensor.Bluetooth.usbserial.util.SerialInputOutputManager;
import com.food.iotsensor.R;

import java.util.ArrayList;


public class BoxDemoActivity extends AppCompatActivity implements SerialInputOutputManager.Listener {
    private static final int LOCATION_PERMISSION_CODE = 0x114;
    public Spinner spinner_dev;
    private TextView tv_weight;
    public EditText et_data;
    public Button btn_usb_load, btn_bt_load, btn_open, btn_zero, btn_clean;
    public EditText[] ets;
    public Button[] btns;

    public ArrayList<String> list_str_device = new ArrayList<>();

    public BtHelper btHelper;
    public UsbHelper usbHelper;

    public boolean is_usb = false;
    public boolean is_connected = false;
    private boolean is_query = false;
    public Handler mainLooper;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxdemo);
        /*getSupportActionBar().hide();*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        initUi();
        addListener();

//        usbHelper = new UsbHelper(this);
//        btHelper = new BtHelper(this);

        mainLooper = new Handler(Looper.getMainLooper());

    }

    private void initUi() {
        btn_open = findViewById(R.id.btn_open);
        btn_zero = findViewById(R.id.btn_zero);
        btn_clean = findViewById(R.id.btn_clean);
        btn_bt_load = findViewById(R.id.btn_bt_load);
        btn_usb_load = findViewById(R.id.btn_usb_load);
        et_data = findViewById(R.id.et_data);
        tv_weight = findViewById(R.id.tv_weight);
        spinner_dev = findViewById(R.id.spinner_dev);

        ets = new EditText[7];
        for (int i = 0; i < ets.length; i++) {
            int resID = getResources().getIdentifier("et_" + i, "id", getPackageName());
            ets[i] = findViewById(resID);
        }
        btns = new Button[7];
        for (int i = 0; i < btns.length; i++) {
            int resID = getResources().getIdentifier("btn_set_" + i, "id", getPackageName());
            btns[i] = findViewById(resID);
        }
    }

    //控件事件
    private void addListener() {
        btn_usb_load.setOnClickListener(view -> {
            is_usb = true;
            init_usb();
        });
        btn_bt_load.setOnClickListener(view -> {
            is_usb = false;
            init_bt();
        });
        btn_clean.setOnClickListener(view -> {
            et_data.setText("");
        });
        btn_open.setOnClickListener(view -> {
            if (!is_connected) {
                if (is_usb)
                    usbHelper.usb_open();
                else
                    btHelper.bt_open();
            } else {
                if (is_usb)
                    usbHelper.usb_close();
                else
                    btHelper.bt_close();
            }
        });
        btn_zero.setOnClickListener(view -> {
            sendCmd(Data.CMD_GUILING);
        });
        btns[0].setOnClickListener(view -> {
            sendCmd(Data.CMD_JZ + ets[0].getText().toString().trim());
        });
//        for (int i = 0; i < btns.length; i++) {
//            final int finalI = i;
//            btns[i].setOnClickListener(view -> {
//                    send(Data.getCmdSet(ets[finalI].getText().toString()));
//            });
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (is_connected) {
            if (is_usb)
                usbHelper.usb_close();
            else
                btHelper.bt_close();
        }
    }

    public void receive(byte[] data) {
        SpannableStringBuilder spn = new SpannableStringBuilder();
        if (data.length > 0) {
            spn.append(HexUtil.printBytes(data) + "\n");
            String w = HexUtil.ascBytesToStr(data);
            spn.append("" + w + "\n");
            et_data.append(spn);

            tv_weight.setText("W: " + Data.getWeight(w) + " g");
        }
    }

    public void status(String str) {
        et_data.append(str + '\n');
    }

    public void sendCmd(String cmd) {
        if (!is_connected)
            return;
        if (is_usb)
            usbHelper.send(cmd + "\r\n");
        else
            btHelper.send(cmd + "\r\n");
    }

    //======================== USB ============================
    private void init_usb() {
        usbHelper.usb_find();
    }

    @Override
    public void onNewData(byte[] data) {
        mainLooper.post(() -> {
            receive(data);
        });
    }

    @Override
    public void onRunError(Exception e) {
        mainLooper.post(() -> {
            status("connection lost: " + e.getMessage());
            usbHelper.usb_close();
        });
    }

    //======================== 蓝牙bt =============================
    public void init_bt() {
        btHelper.find_bt();
    }

    public void bt_recv(byte[] data) {
        mainLooper.post(() -> {
            receive(data);
        });
    }
}
