package com.project.application;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {


	private List<BluetoothDevice> deviceList;
	private final ClickListener listener;

	public static String EXTRA_DEVICE_ADDRESS = "device_address";


	public DeviceListAdapter(List<BluetoothDevice> deviceList, ClickListener listener) {
		this.deviceList = deviceList;
		this.listener = listener;
	}


	@NonNull
	@Override
	public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View itemView = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.item_card_device, viewGroup, false);

		return new DeviceViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull DeviceViewHolder deviceViewHolder, int i) {

		BluetoothDevice device = deviceList.get(i);
		deviceViewHolder.bind(device, listener);


	}

	@Override
	public int getItemCount() {
		return deviceList.size();
	}

	public class DeviceViewHolder extends RecyclerView.ViewHolder {


		public TextView deviceName, deviceAddress;


		public DeviceViewHolder(@NonNull View itemView) {


			super(itemView);
			deviceAddress = itemView.findViewById(R.id.deviceAddress);
			deviceName = itemView.findViewById(R.id.deviceName);
		}

		public void bind(final BluetoothDevice device, final ClickListener listener) {
			deviceAddress.setText(device.getAddress());
			deviceName.setText(device.getName());

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(device);
				}
			});
		}

	}

	public interface ClickListener {
		void onItemClick(BluetoothDevice device);
	}

}
