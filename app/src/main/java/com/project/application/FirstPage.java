package com.project.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FirstPage extends Activity {

	Button loginAction;
	EditText userName, password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);


		Intent intent = new Intent(getApplicationContext() , DeviceListRecyclerView.class);

		startActivity(intent);

		userName = (EditText) findViewById(R.id.userNameField);
		password = (EditText) findViewById(R.id.passwordField);
		loginAction = findViewById(R.id.loginAction);

		loginAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginControl();
			}
		});


	}


	private void loginControl() {

		String userNameExpected = "admin";
		String passwordExpected = "admin";

		if (userNameExpected.equals(userName.getText().toString()) && passwordExpected.equals(password.getText().toString())) {


			Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();

			Intent a1 = new Intent(getApplicationContext(), MainActivity.class);

			startActivity(a1);

			finish();

		} else if(userName.getText().toString().length() == 0) {

			Toast.makeText(getApplicationContext(), "Username Field cannot be empty", Toast.LENGTH_LONG).show();

		}
		else if(password.getText().toString().length() == 0){

			Toast.makeText(getApplicationContext(), "Password Field cannot be empty", Toast.LENGTH_LONG).show();

		}
		else{

			Toast.makeText(getApplicationContext(), "No Such Username or Password", Toast.LENGTH_LONG).show();
		}

	}


}
		

	
	

