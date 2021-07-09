package com.food.iotsensor.Bluetooth.usb;

import android.hardware.usb.UsbDevice;

import com.food.iotsensor.Bluetooth.usbserial.driver.UsbSerialDriver;


public class UsbItem {
    UsbDevice device;
    int port;
    UsbSerialDriver driver;

    UsbItem(UsbDevice device, int port, UsbSerialDriver driver) {
        this.device = device;
        this.port = port;
        this.driver = driver;
    }
}
