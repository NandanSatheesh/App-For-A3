package com.project.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_activity);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				routeToSpecificActivity();
			}
		},3000);
	}

	private void routeToSpecificActivity(){

		Intent intent = new Intent(getApplicationContext() , FirstPage.class);

		startActivity(intent);

		finish();
	}
}
