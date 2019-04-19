package com.project.application;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {


	List<BluetoothDevice> deviceList ;


	public DeviceListAdapter(List<BluetoothDevice> deviceList) {
		this.deviceList = deviceList;
	}


	@NonNull
	@Override
	public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View itemView = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.list_row, viewGroup, false);

		return new DeviceViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull DeviceViewHolder deviceViewHolder, int i) {

		BluetoothDevice device = deviceList.get(i);
		deviceViewHolder.deviceName.setText(device.getName());
		deviceViewHolder.deviceAddress.setText(device.getAddress());

	}

	@Override
	public int getItemCount() {
		return deviceList.size();
	}

	public class DeviceViewHolder extends RecyclerView.ViewHolder{


		public TextView deviceName , deviceAddress ;


		public DeviceViewHolder(@NonNull View itemView) {
			super(itemView);
			deviceAddress = itemView.findViewById(R.id.deviceAddress);
			deviceName = itemView.findViewById(R.id.deviceName);
		}
	}
}
