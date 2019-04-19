package com.project.application;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements BluetoothSPPConnectionListener {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	private boolean connected = false;
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	protected static final int RESULT_SPEECH = 11;

	public EditText et_msg;
	Button btn_speak;
	private BluetoothSPPConnection mBluetoothSPPConnection;
	private BluetoothAdapter mBluetoothAdapter = null;
	String readMessage = "";

	String temp = "Hi";
	String str_msg;

	TextToSpeech speak;
	TextView txtstatus, txtmsg;
	Button btn_connect;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		Button button = findViewById(R.id.savedDevices);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext() , DeviceListRecyclerView.class);

				startActivity(intent);



			}
		});


		et_msg = (EditText) findViewById(R.id.et_text);
		btn_speak = (Button) findViewById(R.id.btn_speak);
		btn_connect = (Button) findViewById(R.id.btn_connect);
		txtstatus = (TextView) findViewById(R.id.txtstatus);
		txtmsg = (TextView) findViewById(R.id.bluetoothmsg);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		mBluetoothSPPConnection = new BluetoothSPPConnection(this, mHandler); // Registers the


		btn_connect.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!connected) {
					Intent serverIntent = new Intent(v.getContext(), DeviceListActivity.class);
					startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
				} else {
					mBluetoothSPPConnection.close();
				}
			}
		});


		btn_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-UK");
				try {
					startActivityForResult(intent, RESULT_SPEECH);
					et_msg.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Opps! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});
	}

	public String actualdata = "";
	private final Handler mHandler = new Handler() {

		private ContextWrapper context;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case MESSAGE_STATE_CHANGE:
					break;

				case MESSAGE_WRITE:
					byte[] writeBuf = (byte[]) msg.obj;
					// construct a string from the buffer
					String writeMessage = new String(writeBuf);
					// mConversationArrayAdapter.add("Me:  " + writeMessage);
					break;

				case MESSAGE_READ:

					byte[] readBuf = (byte[]) msg.obj;

					readMessage = new String(readBuf, 0, msg.arg1);

		            	/*	 try
		            		  {
		  						Thread.sleep(5000);

		  					  }
		            		  catch (InterruptedException e)
		            		  {
		  						// TODO Auto-generated catch block
		  						e.printStackTrace();

		  					  }
		            		*/
					Toast.makeText(getApplicationContext(), "msg is: " + readMessage, Toast.LENGTH_SHORT).show();

					//comment this
		            		  /*if (readMessage.contains("E"))
		  		  		 	{
		            			  txtmsg.setText("Emergency");
			                	  Voice_output("In Emergency. Please help...",3);

		  		  		 	}*/

					if (readMessage.contains("@")) {
						actualdata = actualdata + readMessage;
						actualdata = actualdata.replace("@", "");
						if (actualdata.contains("V01")) {
							txtmsg.setText("Room 1 Reached");
							Voice_output("Room 1 Reached...", 3);
						}
						if (actualdata.contains("V02")) {
							txtmsg.setText("Room 2 Reached");
							Voice_output("Room 2 Reached...", 3);
						}
						if (actualdata.contains("V03")) {
							txtmsg.setText("Room 3 Reached");
							Voice_output("Room 3 Reached...", 3);
						}
						//     tv_child_activity.setText(actualdata + "-"+ status);
						actualdata = "";


					} else {
						actualdata = actualdata + readMessage;
					}
					Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_SHORT).show();

					break;
				case MESSAGE_DEVICE_NAME:
					break;
				case MESSAGE_TOAST:

					break;
			}
		}
	};


	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		//	Toast.makeText(getApplicationContext(),"requestCode" + RESULT_SPEECH, Toast.LENGTH_LONG).show();
		switch (requestCode) {

			case RESULT_SPEECH:
				//Toast.makeText(getApplicationContext(), "IntoSpeach Result", Toast.LENGTH_LONG).show();
				if (resultCode == RESULT_OK && null != data) {
					try {
						ArrayList text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
						et_msg.setText(text.get(0).toString());
						String usercommand = text.get(0).toString();

						if (usercommand.equalsIgnoreCase("HOD Room") || usercommand.equalsIgnoreCase("1")) {
							HODRoom();
						} else if (usercommand.equalsIgnoreCase("Library") || usercommand.equalsIgnoreCase("2")) {
							Library();
						} else if (usercommand.equalsIgnoreCase("classRoom") || usercommand.equalsIgnoreCase("3")) {
							ClassRoom();
						} else if (usercommand.equalsIgnoreCase("Steps") || usercommand.equalsIgnoreCase("Staircase") || usercommand.equalsIgnoreCase("4")) {
							Steps();
						}
//		    				else if(usercommand.equalsIgnoreCase("fan medium")||usercommand.equalsIgnoreCase("5"))
//		    				{
//		    					medium();
//		    				}
//		    				else if(usercommand.equalsIgnoreCase("fan off")||usercommand.equalsIgnoreCase("7")||usercommand.equalsIgnoreCase("fan of"))
//		    				{
//		    					fanOff();
//		    				}
					} catch (Exception ex) {
						Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
				break;
			case REQUEST_CONNECT_DEVICE:

				if (resultCode == Activity.RESULT_OK) {
					String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
					BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
					mBluetoothSPPConnection.open(device);
				}
				break;
			case REQUEST_ENABLE_BT:
				if (resultCode == Activity.RESULT_OK) {
				}
				break;
		}
	}

	public void bluetoothWrite(int bytes, byte[] buffer) {
		// TODO Auto-generated method stub
		byte[] command = new byte[9];
		command[0] = 'F';
		command[1] = 'A';
		command[2] = 'I';
		command[3] = 'R';
		mBluetoothSPPConnection.write(command);
	}

	public void HODRoom() {
		// TODO Auto-generated method stub
		byte[] command = new byte[4];
		command[0] = 'B';
		command[1] = '0';
		command[2] = '1';
		command[3] = '@';
		mBluetoothSPPConnection.write(command);
	}

	public void Library() {
		// TODO Auto-generated method stub
		byte[] command = new byte[4];
		command[0] = 'B';
		command[1] = '0';
		command[2] = '2';
		command[3] = '@';
		mBluetoothSPPConnection.write(command);
	}

	public void ClassRoom() {
		// TODO Auto-generated method stub
		byte[] command = new byte[4];
		command[0] = 'B';
		command[1] = '0';
		command[2] = '3';
		command[3] = '@';
		mBluetoothSPPConnection.write(command);
	}

	public void Steps() {
		// TODO Auto-generated method stub
		byte[] command = new byte[4];
		command[0] = 'B';
		command[1] = '0';
		command[2] = '4';
		command[3] = '@';
		mBluetoothSPPConnection.write(command);
	}
//			public void medium()
//			{
//				// TODO Auto-generated method stub
//				 byte[] command = new byte[2];
//			     command[0]='F';
//			     command[1]='1';
//			  	mBluetoothSPPConnection.write(command);
//			}

//			public void fanOff()
//			{
//				// TODO Auto-generated method stub
//				 byte[] command = new byte[2];
//			     command[0]='F';
//			     command[1]='0';
//			  	mBluetoothSPPConnection.write(command);
//			}

	public void onConnecting() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "connecting here", Toast.LENGTH_LONG).show();
		TextView connectionView = (TextView) findViewById(R.id.txtstatus);
		connectionView.setText("Connecting...");
	}


	public void onConnected() {
		// TODO Auto-generated method stub
		connected = true;

		// Change the text in the connectionInfo TextView
		TextView connectionView = (TextView) findViewById(R.id.txtstatus);
		connectionView.setText("Connected to " + mBluetoothSPPConnection.getDeviceName());

		// Change the text in the connect button.
		Button bt = (Button) findViewById(R.id.btn_connect);
		bt.setText("Disconnect");

		// Send the 's' character so that the communication can start.
		byte[] command = new byte[1];
		command[0] = 's';
		mBluetoothSPPConnection.write(command);
	}

	public void onConnectionFailed() {
		// TODO Auto-generated method stub
		connected = false;

		// Change the text in the connectionInfo TextView
		TextView connectionView = (TextView) findViewById(R.id.txtstatus);
		connectionView.setText("Connection failed!");

		// Change the text in the connect button.
		Button bt = (Button) findViewById(R.id.btn_connect);
		bt.setText("Connect");
	}

	public void onConnectionLost() {
		// TODO Auto-generated method stub
		connected = false;

		// Change the text in the connectionInfo TextView
		TextView connectionView = (TextView) findViewById(R.id.txtstatus);
		connectionView.setText("Not Connected!");

		// Change the text in the connect button.
		Button bt = (Button) findViewById(R.id.btn_connect);
		bt.setText("Connect");
	}

	public void bluetoothread(int bytes, byte[] buffer) {
		// TODO Auto-generated method stub
				/* byte[] readBuf = (byte[]) msg.obj;
				 String readMessage = new String(readBuf, 0, msg.arg1);
				 mBluetoothSPPConnection.read(command);*/

	}


	public void Voice_output(final String text_speak, final int repeat) {


		//Toast.makeText(getApplicationContext(),"In Method voice" , Toast.LENGTH_SHORT).show();
		speak = new TextToSpeech(this,
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {

						if (status != TextToSpeech.ERROR) {

							speak.setLanguage(Locale.US);

						}

						readMessage = "";


						//comment this
						speak.speak(text_speak, TextToSpeech.QUEUE_ADD, null);



	                      /*  for(int k=0;k<=repeat;k++){
	                        speak.speak(text_speak, TextToSpeech.QUEUE_ADD, null);
	                        }*/

					}
				});

	}


	public void ListentoSpeach() {
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-IN");

		try {
			startActivityForResult(intent, RESULT_SPEECH);
			et_msg.setText("");
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(getApplicationContext(),
					"Opps! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show();
		}

	}


}