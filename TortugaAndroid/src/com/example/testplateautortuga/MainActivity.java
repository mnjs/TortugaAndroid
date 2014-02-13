package com.example.testplateautortuga;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

@SuppressLint("CutPasteId")
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String fontPath = "fonts/font.TTF";
		Button jouer = (Button) findViewById(R.id.button1);
		Button regles = (Button) findViewById(R.id.button2);

		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		jouer.setTypeface(tf);
		regles.setTypeface(tf);

		jouer.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Animation animButton = AnimationUtils.loadAnimation(arg0.getContext(), R.anim.anim_button);
				arg0.startAnimation(animButton);
				
				Intent i = new Intent(MainActivity.this, Menu.class);
				startActivity(i);
			}
		}

		);
		regles.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Animation animButton = AnimationUtils.loadAnimation(arg0.getContext(), R.anim.anim_button);
				arg0.startAnimation(animButton);
				
				Intent i = new Intent(MainActivity.this, Regles.class);
				startActivity(i);
			}
		});
	}

}
