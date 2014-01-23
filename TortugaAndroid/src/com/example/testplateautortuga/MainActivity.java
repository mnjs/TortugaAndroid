package com.example.testplateautortuga;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) (findViewById(R.id.button1));
		@SuppressWarnings("unused")
		final CheckBox modeEclosion = (CheckBox) (findViewById(R.id.modeEclosion));
		
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, PlateauWindows.class);
			startActivity(intent);
			//PlateauWindows plateau = new PlateauWindows(false,false);
			}
		});
	}

}
