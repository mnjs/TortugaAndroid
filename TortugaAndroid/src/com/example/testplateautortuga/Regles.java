package com.example.testplateautortuga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Regles extends MainActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regles);
		
		String path = null;
		if(Locale.getDefault().getLanguage().equals("en")){
			path="regles-EN.txt";			
		}
		if(Locale.getDefault().getLanguage().equals("fr")){
			path="regles-FR.txt";			
		}
		
		try {
			BufferedReader fic = new BufferedReader(new InputStreamReader(this
					.getAssets().open(path)));

			String regles = "";
			String ligne = null;

			while ((ligne = fic.readLine()) != null) {
				regles += ligne+"\n\n";
			}

			TextView texteView = (TextView) findViewById(R.id.textView1);
			Typeface font = Typeface.createFromAsset(getAssets(), "fonts/comic.ttf");
			texteView.setTypeface(font);
			
			((TextView) findViewById(R.id.textView1)).setText(regles);
			fic.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("erreur ouverture fichier");
		}
	}
}
