package com.example.testplateautortuga;

import android.os.Bundle;


public class PlateauWindows extends MainActivity {

/*	boolean multi;
	boolean modeEclosion;
	
	public PlateauWindows(boolean multi,boolean modeEclosion)
	{
		this.multi = multi;
		this.modeEclosion = modeEclosion;
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(new Plateau(this,false,false));

		
	}
}
