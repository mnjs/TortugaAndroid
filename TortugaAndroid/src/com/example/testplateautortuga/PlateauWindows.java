package com.example.testplateautortuga;

import android.os.Bundle;
import android.widget.ProgressBar;

public class PlateauWindows extends MainActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		Bundle b = getIntent().getExtras();
		boolean mode = b.getBoolean("mode");
		boolean eclosion = b.getBoolean("eclosion");
		int difficulte = b.getInt("difficulte");
		
		setContentView(new Plateau(this,mode,eclosion,difficulte,this));
	}
	
	public void onConfigurationChanged(
			android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}
