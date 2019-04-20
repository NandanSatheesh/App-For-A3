package com.project.application;

import android.bluetooth.BluetoothDevice;

public class ActiveBluetoothDevice {

	public static BluetoothDevice device ;

	public static BluetoothDevice getDevice() {
		return device;
	}

	public static void setDevice(BluetoothDevice device) {
		ActiveBluetoothDevice.device = device;
	}

	public static String getBluetoothAddress(){
		return device.getAddress();
	}
}
