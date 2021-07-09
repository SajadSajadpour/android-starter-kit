package com.food.iotsensor.Bluetooth.common;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.food.iotsensor.Bluetooth.android_serialport_api.SerialPort;
import com.food.iotsensor.Bluetooth.bean.ComBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * 串口工具类
 */
public class SerialHelper {
    private SerialPort mSerialPort = null;
    private OutputStream mOutputStream = null;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private SendThread mSendThread;
    private String sPort = "/dev/ttyMT3";
    private int iBaudRate = 9600;
    private boolean _isOpen = false;
    private byte[] _bLoopData = new byte[]{0x30};
    private int iDelay = 500;
    private Handler handler;

    //----------------------------------------------------
    public SerialHelper(String sPort, int iBaudRate, Handler handler) {
        this.sPort = sPort;
        this.iBaudRate = iBaudRate;
        this.handler = handler;
    }

    //----------------------------------------------------
    public void open() {
        try {
            mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0);
            Log.d("test", "open");
            mOutputStream = mSerialPort.getOutputStream();
            if (handler != null) {
                mInputStream = mSerialPort.getInputStream();
                mReadThread = new ReadThread();
                mReadThread.start();
            }
            mSendThread = new SendThread();
            mSendThread.setSuspendFlag();
            mSendThread.start();
            _isOpen = true;
            return;
        } catch (IOException e) {
        } catch (SecurityException se) {
        } catch (Exception e) {
        }
        mSerialPort = null;
        mOutputStream = null;
        _isOpen = false;
    }

    //----------------------------------------------------
    public void close() {
        if (mReadThread != null)
            mReadThread.interrupt();
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        _isOpen = false;
    }

    //----------------------------------------------------
    public void send(byte[] bOutArray) {
        if (mOutputStream == null || bOutArray == null)
            return;
        try {
            mOutputStream.write(bOutArray);
            Log.d("fwedemo", "send=" + Util.ByteArrToHex(bOutArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------
    public void sendHex(String sHex) {
        Log.d("test", sHex);
        byte[] bOutArray = Util.hexStrToByteArr(sHex);
        send(bOutArray);
    }

    //----------------------------------------------------
    public void sendTxt(String sTxt) {
        byte[] bOutArray = new byte[0];
        try {
            bOutArray = sTxt.getBytes("GB18030");
            send(bOutArray);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (mInputStream == null) {
                        return;
                    }
                    byte[] buffer = new byte[512];
                    int size = mInputStream.read(buffer);
                    //Log.d("demo", "size=" + size);
                    if (size > 0) {
                        ComBean ComRecData = new ComBean(sPort, buffer, size);
                        onDataReceived(ComRecData);
                    }
                    try {
                        Thread.sleep(50);//延时50ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    //----------------------------------------------------
    private class SendThread extends Thread {
        public boolean suspendFlag = true;// 控制线程的执行

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                synchronized (this) {
                    while (suspendFlag) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                send(getbLoopData());
                try {
                    Thread.sleep(iDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //线程暂停
        public void setSuspendFlag() {
            this.suspendFlag = true;
        }

        //唤醒线程
        public synchronized void setResume() {
            this.suspendFlag = false;
            notify();
        }
    }

    //----------------------------------------------------
    public int getBaudRate() {
        return iBaudRate;
    }

    public boolean setBaudRate(int iBaud) {
        if (_isOpen) {
            return false;
        } else {
            iBaudRate = iBaud;
            return true;
        }
    }

    //----------------------------------------------------
    public String getPort() {
        return sPort;
    }

    public boolean setPort(String sPort) {
        if (_isOpen) {
            return false;
        } else {
            this.sPort = sPort;
            return true;
        }
    }

    //----------------------------------------------------
    public boolean isOpen() {
        return _isOpen;
    }

    //----------------------------------------------------
    public byte[] getbLoopData() {
        return _bLoopData;
    }

    //----------------------------------------------------
    public void setbLoopData(byte[] bLoopData) {
        this._bLoopData = bLoopData;
    }

    //----------------------------------------------------
    public void setTxtLoopData(String sTxt) {
        this._bLoopData = sTxt.getBytes();
    }

    //----------------------------------------------------
    public void setHexLoopData(String sHex) {
        this._bLoopData = Util.HexToByteArr(sHex);
    }

    //----------------------------------------------------
    public int getiDelay() {
        return iDelay;
    }

    //----------------------------------------------------
    public void setiDelay(int iDelay) {
        this.iDelay = iDelay;
    }

    //----------------------------------------------------
    public void startSend() {
        if (mSendThread != null) {
            mSendThread.setResume();
        }
    }

    //----------------------------------------------------
    public void stopSend() {
        if (mSendThread != null) {
            mSendThread.setSuspendFlag();
        }
    }

    //----------------------------------------------------
    public void onDataReceived(ComBean data) {
        if (handler == null)
            return;
        Message msg = handler.obtainMessage(0);
        msg.obj = data.bRec;
        handler.sendMessage(msg);
    }
}