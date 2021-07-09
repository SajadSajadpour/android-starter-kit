package com.food.iotsensor.Bluetooth.usb;


import com.food.iotsensor.Bluetooth.usbserial.driver.CdcAcmSerialDriver;
import com.food.iotsensor.Bluetooth.usbserial.driver.ProbeTable;
import com.food.iotsensor.Bluetooth.usbserial.driver.UsbSerialProber;

/**
 * add devices here, that are not known to DefaultProber
 *
 * if the App should auto start for these devices, also
 * add IDs to app/src/main/res/xml/device_filter.xml
 */
public class CustomProber {

    public static UsbSerialProber getCustomProber() {
        ProbeTable customTable = new ProbeTable();
        customTable.addProduct(0x16d0, 0x087e, CdcAcmSerialDriver.class); // e.g. Digispark CDC
        return new UsbSerialProber(customTable);
    }

}
