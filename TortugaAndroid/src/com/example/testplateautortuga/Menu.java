package com.example.testplateautortuga;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Menu extends Activity {

	boolean isClickedIA = false;
	boolean isClickedPVP = false;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		String fontPath = "fonts/font.TTF";
		final Button un_joueur = (Button) findViewById(R.id.button1);
		final Button deux_joueurs = (Button) findViewById(R.id.button2);
		final Button jouer = (Button) findViewById(R.id.button3);
		final TextView label_difficulte = (TextView) findViewById(R.id.textView1);
		final RatingBar rating_bar = (RatingBar) findViewById(R.id.ratingBar1);

		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		un_joueur.setTypeface(tf);
		deux_joueurs.setTypeface(tf);
		jouer.setTypeface(tf);
		label_difficulte.setTypeface(tf);

		un_joueur.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isClickedIA == true) {
					un_joueur.setTextColor(Color.WHITE);
					label_difficulte.setVisibility(View.INVISIBLE);
					rating_bar.setVisibility(View.INVISIBLE);
					isClickedIA = false;
				} else {
					un_joueur.setTextColor(Color.YELLOW);
					isClickedIA = true;
					label_difficulte.setVisibility(1);
					rating_bar.setVisibility(1);
					deux_joueurs.setTextColor(Color.WHITE);
					isClickedPVP = false;
				}
			}
		});

		deux_joueurs.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isClickedPVP == true) {
					deux_joueurs.setTextColor(Color.WHITE);
					isClickedPVP = false;
				} else {
					deux_joueurs.setTextColor(Color.YELLOW);
					isClickedPVP = true;
					label_difficulte.setVisibility(View.INVISIBLE);
					rating_bar.setVisibility(View.INVISIBLE);
					un_joueur.setTextColor(Color.WHITE);
					isClickedIA = false;
				}
			}
		});

		String difficult = getString(R.string.oubli_difficulte);
		String mode = getString(R.string.oubli_mode);
		final AlertDialog msg_mode = new AlertDialog.Builder(this).create();
		final AlertDialog msg_difficult = new AlertDialog.Builder(this)
				.create();
		final AlertDialog msg_etoile = new AlertDialog.Builder(this).create();
		msg_difficult.setCancelable(false);
		msg_difficult.setMessage(difficult);

		msg_mode.setCancelable(false);
		msg_mode.setMessage(mode);

		msg_etoile.setCancelable(false);

		jouer.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isClickedIA == false && isClickedPVP == false) {
					msg_mode.setButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					msg_mode.show();
				} else if (isClickedIA == true && rating_bar.getRating() == 0) {
					msg_difficult.setButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					msg_difficult.show();
				} else {
					if (isClickedIA) {
						System.out.println(rating_bar.getRating());
						System.out.println("mode un joueur");
						Intent intent = new Intent(Menu.this,
								PlateauWindows.class);
						intent.putExtra("mode", false);
						intent.putExtra("eclosion", false);
						intent.putExtra("difficulte",
								(int) rating_bar.getRating());
						startActivity(intent);

					} else {
						System.out.println("mode deux joueur");
						Intent intent = new Intent(Menu.this,
								PlateauWindows.class);
						intent.putExtra("mode", true);
						intent.putExtra("eclosion", false);
						intent.putExtra("difficulte", 0);
						startActivity(intent);

					}
				}

			}
		});

	}
}
