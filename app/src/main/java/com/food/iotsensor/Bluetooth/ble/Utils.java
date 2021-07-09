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

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class Utils {

	private static HashMap<Integer, String> serviceTypes = new HashMap();
	static {
		// Sample Services.
		serviceTypes.put(BluetoothGattService.SERVICE_TYPE_PRIMARY, "PRIMARY");
		serviceTypes.put(BluetoothGattService.SERVICE_TYPE_SECONDARY,
				"SECONDARY");
	}

	public static String getServiceType(int type) {
		return serviceTypes.get(type);
	}

	// -------------------------------------------
	private static HashMap<Integer, String> charPermissions = new HashMap();
	static {
		charPermissions.put(0, "UNKNOW");
		charPermissions
				.put(BluetoothGattCharacteristic.PERMISSION_READ, "READ");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED,
				"READ_ENCRYPTED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM,
				"READ_ENCRYPTED_MITM");
		charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE,
				"WRITE");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED,
				"WRITE_ENCRYPTED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM,
				"WRITE_ENCRYPTED_MITM");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED,
				"WRITE_SIGNED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM,
				"WRITE_SIGNED_MITM");
	}

	public static String getCharPermission(int permission) {
		return getHashMapValue(charPermissions, permission);
	}

	// -------------------------------------------
	private static HashMap<Integer, String> charProperties = new HashMap();
	static {

		charProperties.put(BluetoothGattCharacteristic.PROPERTY_BROADCAST,
				"BROADCAST");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS,
				"EXTENDED_PROPS");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_INDICATE,
				"INDICATE");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_NOTIFY,
				"NOTIFY");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_READ, "READ");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE,
				"SIGNED_WRITE");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_WRITE, "WRITE");
		charProperties.put(
				BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE,
				"WRITE_NO_RESPONSE");
	}

	public static String getCharPropertie(int property) {
		return getHashMapValue(charProperties, property);
	}

	// --------------------------------------------------------------------------
	private static HashMap<Integer, String> descPermissions = new HashMap();
	static {
		descPermissions.put(0, "UNKNOW");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ, "READ");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED,
				"READ_ENCRYPTED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM,
				"READ_ENCRYPTED_MITM");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE, "WRITE");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED,
				"WRITE_ENCRYPTED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED_MITM,
				"WRITE_ENCRYPTED_MITM");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED,
				"WRITE_SIGNED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM,
				"WRITE_SIGNED_MITM");
	}

	public static String getDescPermission(int property) {
		return getHashMapValue(descPermissions, property);
	}

	private static String getHashMapValue(HashMap<Integer, String> hashMap,
                                          int number) {
		String result = hashMap.get(number);
		if (TextUtils.isEmpty(result)) {
			List<Integer> numbers = getElement(number);
			result = "";
			for (int i = 0; i < numbers.size(); i++) {
				result += hashMap.get(numbers.get(i)) + "|";
			}
		}
		return result;
	}

	/**
	 * 位运算结果的反推函数10 -> 2 | 8;
	 */
	static private List<Integer> getElement(int number) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < 32; i++) {
			int b = 1 << i;
			if ((number & b) > 0)
				result.add(b);
		}

		return result;
	}

	public static String bytesToHex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] hexToBytes(String hex) {
		if (hex == null || hex.equals("")) {
			return null;
		}
		hex = hex.toUpperCase();
		int length = hex.length() / 2;
		char[] hexChars = hex.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String ascToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}

		return hex.toString();
	}

	public static String hexToAsc(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

	public static void main(String[] s){
	}
}
