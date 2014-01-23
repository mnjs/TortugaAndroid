package com.example.testplateautortuga;

import android.os.Bundle;


public class PlateauWindows extends MainActivity {

	String save = null;
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
	
	protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        save = "coucou";
    }
	
	public Object onRetainNonConfigurationInstance() {
	    System.out.println(save);
	    return null;
	}
}
