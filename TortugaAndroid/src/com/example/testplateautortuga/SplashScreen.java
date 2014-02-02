package com.example.testplateautortuga;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends MainActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		Thread splash_screen = new Thread() {
			public void run() {

				try {
					sleep(200); //3000
					Intent i = new Intent(getBaseContext(), MainActivity.class);
					startActivity(i);
					finish();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		// start thread
		splash_screen.start();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

}