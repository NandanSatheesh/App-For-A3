package com.project.application;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceListRecyclerView extends AppCompatActivity {


	private RecyclerView recyclerView ;
	private DeviceListAdapter adapter ;

	private BluetoothAdapter mBtAdapter;

	private List<BluetoothDevice> deviceList = new ArrayList<>();



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		for(BluetoothDevice device:pairedDevices){
			deviceList.add(device);
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
		getSupportActionBar().hide(); // hide the title bar


		setContentView(R.layout.device_list_recycler_view);

		Toolbar toolbar = findViewById(R.id.toolbar);



//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setDisplayShowHomeEnabled(true);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

//		if(getSupportActionBar()!=null){
//			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//			getSupportActionBar().setDisplayShowHomeEnabled(true);
//		}


		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		adapter = new DeviceListAdapter(deviceList);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(adapter);



	}
}
