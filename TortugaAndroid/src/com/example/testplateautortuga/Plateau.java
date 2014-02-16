package com.example.testplateautortuga;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

//import android.widget.LinearLayout;

/**
 * <b>Classe affichant le plateau de jeu</b>
 * <p>
 * {@link JPanel} affichant le plateau de jeu
 */

@SuppressLint("ViewConstructor")
public class Plateau extends View {

	/**
	 * <b>Mode de Jeu Contre IA</b>
	 * <p>
	 * Constante a Utiliser dans le constructeur Pour choisir de jouer contre
	 * une IA
	 * </p>
	 * 
	 * @see Plateau#Plateau(boolean, boolean)
	 * @see PlateauWindows#PlateauWindows(boolean, boolean)
	 */
	public static final boolean MULTI_IA = false;
	/**
	 * <b>Mode de Jeu deux joueurs</b>
	 * <p>
	 * Constante a Utiliser dans le constructeur Pour choisir de jouer contre un
	 * autre joueur
	 * </p>
	 * 
	 * @see Plateau#Plateau(boolean, boolean)
	 * @see PlateauWindows#PlateauWindows(boolean, boolean)
	 */
	public static final boolean MULTI_DEUX_JOUEURS = true;

	// private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * largeur d'un hexagone
	 * </p>
	 */

	// --------------------------------------------------------------------------------------------
	Bitmap hexagone = BitmapFactory.decodeResource(getResources(),
			R.drawable.hexagone);
	Bitmap image = BitmapFactory.decodeResource(getResources(),
			R.drawable.fondtortuga);
	Bitmap trouge = BitmapFactory.decodeResource(getResources(),
			R.drawable.tortue_rouge);
	Bitmap tvert = BitmapFactory.decodeResource(getResources(),
			R.drawable.tortue_vert);
	Bitmap trougetr = BitmapFactory.decodeResource(getResources(),
			R.drawable.tortue_rouge_tr);
	Bitmap tverttr = BitmapFactory.decodeResource(getResources(),
			R.drawable.tortue_vert_tr);
	Bitmap cercle = BitmapFactory.decodeResource(getResources(),
			R.drawable.cercle);
	Bitmap tretourne = BitmapFactory.decodeResource(getResources(),
			R.drawable.tortue_retourner);
	Bitmap oeuf = BitmapFactory.decodeResource(getResources(), R.drawable.oeuf);
	Bitmap choixrouge = BitmapFactory.decodeResource(getResources(),
			R.drawable.choix_rouge);
	Bitmap choixvert = BitmapFactory.decodeResource(getResources(),
			R.drawable.choix_vert);
	Bitmap choixvertdroite = BitmapFactory.decodeResource(getResources(),
			R.drawable.choix_vert_droite);
	Bitmap winRouge = BitmapFactory.decodeResource(getResources(),
			R.drawable.win_rouge);
	Bitmap winVert = BitmapFactory.decodeResource(getResources(),
			R.drawable.win_vert);

	private Context context;

	private byte choixTortue = 0;
	private boolean choix = false;
	private int difficulte = 0;

	private Canvas canvas;

	private boolean threadD = false;

	private int longueurEcran = this.getHeight();
	private int largeurEcran = this.getWidth();

	private double yO;
	private double xO;

	private float xChoixRouge;
	private float yChoixRouge;
	private float xChoixVerte;
	private float yChoixVerte;

	private int tailleTortueChoix;
 
	private boolean a = false;
	private boolean rejouer = false;  
	
	private ProgressDialog progressDialog;
	
	private MediaPlayer mPlayer = null;
	private PlateauWindows plateau = null;
	// ------------------------------------------------------------------------------------------

	private int l;
	/**
	 * tableau de 37 case contenant le plateau � afficher
	 */
	private Byte[] confTab;
	/**
	 * configuration du jeu actuel
	 */
	private static Configuration conf;
	/**
	 * Tortue sur les quelles on a cliqu�
	 */
	private byte clTor1, clTor2, saute;
	/**
	 * images du plateau et des tortues
	 */
	/**
	 * tableau contenant les coordonn�es des polygones du plateaux
	 */
	Polygon[] tPoly;
	/**
	 * tableau contenant le point central de chaques case du plateau
	 */
	Point[] tPoint;
	/**
	 * liste des tortues pouvant �tre jou�es
	 * 
	 * @see Plateau#affPossibleMove()
	 */
	ArrayList<Byte> tcercle;
	/**
	 * liste des mouvement possible de la tortue s�lectionn�e
	 */
	ArrayList<byte[]> transTortue;
	/**
	 * indique si un joueur a gagn�
	 */
	boolean win;
	/**
	 * indique si il faut afficher le bouton terminer
	 */
	@SuppressWarnings("unused")
	private boolean affTerminee;
	/**
	 * indique si il faut afficher l'oeuf en haut a gauche
	 */
	@SuppressWarnings("unused")
	private boolean affOeuf;
	/**
	 * indique si le joueur est en mode placement d'oeuf
	 */
	private boolean oeufT;
	/**
	 * indique si le joueur a cliqu� sur l'oeuf mais n'a pas encore pos� l'oeuf
	 */
	private boolean oeufC = false;
	/**
	 * indique si le Mode de Jeu multijoueur ou non
	 * 
	 * @see Plateau#MULTI_DEUX_JOUEURS
	 * @see Plateau#MULTI_IA
	 */
	private boolean multi;
	/**
	 * indique la variante de jeu actuelle
	 * 
	 * @see Configuration#VARIANTE_NORMAL
	 * @see Configuration#VARIANTE__ECLOSION
	 */
	private boolean variante;

	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * cr�e un nouveau {@link Plateau} en variante normal contre une
	 * Intelligence Artificielle.
	 * </p>
	 */
	/*
	 * public Plateau() { this(getContext(),false, false); }
	 */
	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * cr�e un nouveau {@link Plateau} avec la variante et le mode de jeu
	 * indiqu� en param�tre.
	 * 
	 * @param multi
	 *            {@link Plateau#MULTI_IA} ou {@link Plateau#MULTI_DEUX_JOUEURS}
	 *            </p>
	 * @param bar2
	 * @param plateauWindows
	 * @param application
	 */
	public Plateau(Context context, boolean multi, boolean variante,
			int difficulte, PlateauWindows plateau) {
		super(context);
		this.context = context;
		this.plateau = plateau;
		this.multi = multi;
		this.variante = variante;
		this.difficulte = difficulte;
		
		this.progressDialog = new ProgressDialog(context);

		tPoly = new Polygon[37];
		tPoint = new Point[37];
		tcercle = new ArrayList<Byte>();
		transTortue = new ArrayList<byte[]>();
		clTor1 = -1;
		clTor2 = -1;
		saute = -1;
		win = false;

		oeufT = false;
		conf = new Configuration(variante);
		conf.changePlayer();
		confTab = conf.convertTabPlateauEn37();
		affPossibleMove();
		affOeuf = false;
		affTerminee = false;
	}

	/**
	 * stock les tortue pouvant �tre jouer afin de les entourer d'un cercle
	 */
	private void affPossibleMove() {
		tcercle.clear();
		if (!oeufT) {
			for (byte[] coup : conf.verifCoupObligatoire()) {
				tcercle.add((byte) Configuration.en37(coup[0]));
			}
			if (tcercle.isEmpty()) {
				for (byte[] coup : conf.allPossibleMove()) {
					tcercle.add(Configuration.en37(coup[0]));
				}
			}
		} else {
			for (byte numCase : conf.caseLibreEclo()) {
				tcercle.add(Configuration.en37(numCase));
			}
		}
	}

	/**
	 * M�thode de dessin du {@link Plateau}
	 * 
	 * @param g
	 */
	public void drawImage(Bitmap nom, float x, float y, int l, int h) { // Coordonnées x+y et longueur+hauteur
		Bitmap resizedImage = Bitmap.createScaledBitmap(nom, l, h, true);
		canvas.drawBitmap(resizedImage, x, y, null);
	}

	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;

		longueurEcran = this.getHeight();
		largeurEcran = this.getWidth();
		int orientationEcran = this.getResources().getConfiguration().orientation;

		drawImage(image, 0, 0, largeurEcran, longueurEcran);

		if (orientationEcran == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
			l = (int) ((longueurEcran / 2 - longueurEcran / 30) / (5.5 * Math
					.sqrt(3)));
			if (choix) {
				choixTortuePortrait();
			}
		} else {
			l = (int) ((longueurEcran - longueurEcran / 4) / (5.5 * Math
					.sqrt(3)));
			if (choix) {
				choixTortueLandscape();
			}
		}

		creatTPoly();
		// -----------------------Draw Plateau---------------------------//

		if (choix == false) {
			xO = (largeurEcran / 2. - (11 * l) / 2.);
			yO = (longueurEcran / 2. - (7 * l * Math.sqrt(3)) / 2.);
		}

		dessinerPlateauTortue();
		dessinerTortuePossibles();

		for (Byte b : tcercle) {
			int L3 = 2 * l + 2;
			drawImage(cercle, tPoint[b].x - (L3 * 7 / 8) / 2,
					(int) (tPoint[b].y - ((L3 * 7 / 8)) / 2), L3 * 7 / 8,
					(int) ((L3 * 7 / 8)));
		}

		// --------------------------------------------------------------------------//

		if (conf.isEclo() && (conf.player == 1 || multi)) {
			affOeuf = true;
			/*
			 * g.drawImage(oeuf, 10, 10, 60, 80, null); g.setColor(Color.BLACK);
			 * g.drawString( "oeuf restant : " + conf.oeufsDispoEclo() + "(" +
			 * conf.getStockPlayerEclo() + ")", 10, 80 + 10 + 10);
			 */
		} else {
			affOeuf = false;
		}
		if (oeufT && (conf.player == 1 || multi)) {
			affTerminee = true;
			/*
			 * g.setColor(new Color(150, 150, 150)); g.fill3DRect(10, 80 + 10 +
			 * 10 + 10, 100, 20, true); g.setColor(Color.BLACK);
			 * g.drawString("Terminer le tour", 10 + 5, 80 + 10 + 10 + 10 + 10 +
			 * 5);
			 */
		} else {
			affTerminee = false;
		}

		if (win == true) {
			afficherVainqueur();
			rejouer = true;
		}

	}

	private void dessinerTexte(int couleur, int taille, int texte, int x, int y) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		String fontPath = "fonts/comic.ttf";
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
		paint.setTypeface(tf);
		paint.setColor(couleur);
		paint.setTextSize(taille);
		canvas.drawText(context.getResources().getString(texte), x, y, paint);
	}

	private void dessinerTortuePossibles() {
		// TODO Auto-generated method stub
		for (byte[] trt : transTortue) {
			int noTortue = trt[0];
			int type = confTab[trt[1]];
			int L2 = 2 * l + 2;

			if (type == 1) {
				drawImage(
						trougetr,
						tPoint[noTortue].x - (L2 * 2 / 3) / 2,
						(int) (tPoint[noTortue].y - ((L2 * 2 / 3) * 0.905) / 2),
						L2 * 2 / 3, (int) ((L2 * 2 / 3) * 0.905));
			} else if (type == 2) {
				drawImage(
						tverttr,
						tPoint[noTortue].x - (L2 * 2 / 3) / 2,
						(int) (tPoint[noTortue].y - ((L2 * 2 / 3) * 0.905) / 2),
						L2 * 2 / 3, (int) ((L2 * 2 / 3) * 0.905));
			}

		}
	}

	private void dessinerPlateauTortue() {
		// TODO Auto-generated method stub
		confTab = conf.convertTabPlateauEn37();
		int L = 2 * l + 2;
		int H = (int) (l * Math.sqrt(3) + 2);
		int k = 4, alignD = 1;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < k; j++) {
				if (i > 3) {
					alignD = 3;
				} else {
					alignD = i;
				}
				int xA = (int) (3. / 2 * l * 3 + xO - j * (3. / 2. * l) + alignD
						* (3. / 2. * l));
				int yA = (int) (j * (l * Math.sqrt(3)) / 2. + i
						* (l * Math.sqrt(3)) / 2. + alignD / 3 * (i - 3)
						* (l * Math.sqrt(3)) / 2. + yO);
				drawImage(hexagone, xA, yA, L, H);
			}
			if (i < 3) {
				k++;
			} else {
				k--;
			}
		}

		k = 4;
		int num = 0;
		for (int i = 0; i < 7; i++) {
			if (i > 3) {
				alignD = i - 3;
			} else {
				alignD = 0;
			}
			for (int j = 0; j < k; j++) {
				int xA = (int) ((j * 3. / 2. * l) + l + alignD * 3. / 2. * l + xO);
				int yA = (int) (3 * (l * Math.sqrt(3)) / 2.
						+ (l * Math.sqrt(3)) / 2. - j * (l * Math.sqrt(3)) / 2.
						+ i * (l * Math.sqrt(3)) - alignD * (l * Math.sqrt(3))
						/ 2. + yO);

				if (confTab[num] == 1) {
					drawImage(trouge, xA - (L * 2 / 3) / 2,
							(int) (yA - ((L * 2 / 3) * 0.905) / 2), L * 2 / 3,
							(int) ((L * 2 / 3) * 0.905));
				} else if (confTab[num] == 2) {
					drawImage(tvert, xA - (L * 2 / 3) / 2,
							(int) (yA - ((L * 2 / 3) * 0.905) / 2), L * 2 / 3,
							(int) ((L * 2 / 3) * 0.905));
				} else if (confTab[num] == 3) {
					drawImage(tretourne, xA - (L * 2 / 3) / 2,
							(int) (yA - ((L * 2 / 3) * 0.905) / 2), L * 2 / 3,
							(int) ((L * 2 / 3) * 0.905));
				}
				num++;
			}
			if (i < 3) {
				k++;
			} else {
				k--;
			}
		}
	}

	private void choixTortueLandscape() {
		// TODO Auto-generated method stub
		tailleTortueChoix = (int) (longueurEcran / 3);
		xChoixRouge = (float) (longueurEcran / 0.99);
		yChoixRouge = (float) (largeurEcran / 3);
		xChoixVerte = (float) (longueurEcran / 0.73);
		yChoixVerte = (float) (largeurEcran / 3);

		dessinerTexte(Color.WHITE, largeurEcran / 20, R.string.choixTortue,
				(int) (largeurEcran / 1.8), (int) (longueurEcran / 3));
		
		drawImage(trouge, xChoixRouge, yChoixRouge, tailleTortueChoix,
				tailleTortueChoix);
		drawImage(choixvertdroite, xChoixVerte, yChoixVerte, tailleTortueChoix,
				tailleTortueChoix);
		xO = (largeurEcran / 3.5 - (11 * l) / 2.);
		yO = (longueurEcran / 2 - (7 * l * Math.sqrt(3)) / 2.);
	}

	private void choixTortuePortrait() {
		// TODO Auto-generated method stub
		tailleTortueChoix = longueurEcran / 6;
		xChoixRouge = largeurEcran / 6;
		yChoixRouge = longueurEcran / 6;
		xChoixVerte = (float) (largeurEcran / 1.2) - tailleTortueChoix;
		yChoixVerte = longueurEcran / 6;

		dessinerTexte(Color.WHITE, largeurEcran / 10, R.string.choixTortue,
				(int) (largeurEcran / 10), (int) (longueurEcran / 8));
		drawImage(choixrouge, xChoixRouge, yChoixRouge, tailleTortueChoix,
				tailleTortueChoix);
		drawImage(choixvert, xChoixVerte, yChoixVerte, tailleTortueChoix,
				tailleTortueChoix);
		xO = (largeurEcran / 2 - (11 * l) / 2.);
		yO = (longueurEcran / 1.5 - (7 * l * Math.sqrt(3)) / 2.);
	}

	private void afficherVainqueur() {
		// TODO Auto-generated method stub
		int orientationEcran = this.getResources().getConfiguration().orientation;

		if (conf.whoWin() == 1) {
			if (orientationEcran == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
				dessinerTexte(Color.RED, largeurEcran / 10,
						R.string.victoireRouge, largeurEcran / 13,
						(int) (longueurEcran / 1.1));
				drawImage(winRouge, 0, 0, largeurEcran,
						(int) (longueurEcran / 1.5));
			} else {
				dessinerTexte(Color.RED, largeurEcran / 20,
						R.string.victoireRouge, largeurEcran / 16,
						(int) (longueurEcran / 2));
				drawImage(winRouge, (int) (largeurEcran / 1.8), 0,
						largeurEcran / 2, (int) (longueurEcran));
			}
		}
		if (conf.whoWin() == 2) {
			if (orientationEcran == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
				dessinerTexte(Color.GREEN, largeurEcran / 10,
						R.string.victoireVert, largeurEcran / 13,
						(int) (longueurEcran / 1.1));
				drawImage(winVert, 0, 0, largeurEcran,
						(int) (longueurEcran / 1.5));
			} else {
				dessinerTexte(Color.GREEN, largeurEcran / 20,
						R.string.victoireVert, largeurEcran / 16,
						(int) (longueurEcran / 2));
				drawImage(winVert, (int) (largeurEcran / 1.8), 0,
						largeurEcran / 2, (int) (longueurEcran));
			}
		}
	}

	/**
	 * Initialize les Polygones et les Point centraux des Cases
	 * 
	 * @see Plateau#tPoly
	 * @see Plateau#tPoint
	 */
	private void creatTPoly() {

		if (choix == false) {
			xO = (largeurEcran / 2. - (11 * l) / 2.);
			yO = (longueurEcran / 2. - (7 * l * Math.sqrt(3)) / 2.);
		}

		int k = 4, alignD = 1;
		int num = 0;
		for (int i = 0; i < 7; i++) {
			if (i > 3) {
				alignD = i - 3;
			} else {
				alignD = 0;
			}
			for (int j = 0; j < k; j++) {
				int xA = (int) ((j * 3. / 2. * l) + l + alignD * 3. / 2. * l + xO);
				int yA = (int) (3 * (l * Math.sqrt(3)) / 2.
						+ (l * Math.sqrt(3)) / 2. - j * (l * Math.sqrt(3)) / 2.
						+ i * (l * Math.sqrt(3)) - alignD * (l * Math.sqrt(3))
						/ 2. + yO);

				tPoint[num] = new Point(xA, yA);

				num++;
			}
			if (i < 3) {
				k++;
			} else {
				k--;
			}
		}
		for (int i = 0; i < 37; i++) {

			if (tPoint[i] != null) {
				int x = tPoint[i].x;
				int y = tPoint[i].y;
				Polygon poly = new Polygon(new int[] { -l + x, -l / 2 + x,
						l / 2 + x, l + x, l / 2 + x, x - l / 2 }, new int[] {
						y + 0, y + (int) (l * Math.sqrt(3) / 2),
						y + (int) (l * Math.sqrt(3) / 2), y + 0,
						y - (int) (l * Math.sqrt(3) / 2),
						y - (int) (l * Math.sqrt(3) / 2) }, 6);
				tPoly[i] = poly;
			} else {
				tPoly[i] = null;
			}
		}
	}

	/**
	 * dessine une tortue dons le num�ro et la couleur a �t� renseign�
	 * 
	 * @param g
	 * @param noTortue
	 *            num�ro de la case o� se trouve la tortue a dessiner
	 * @param type
	 *            couleur de la tortue a dessin�
	 */
	/**
	 * dessine un cercle sur la case sp�cifi� en param�tre
	 * 
	 * @param g
	 * @param b
	 *            case sur laquelle on dessine le cercle
	 */
	/**
	 * retourne le num�ro de la case o� se situe la tortue sur le quel
	 * l'utilisateur a cliqu�
	 * 
	 * @param x
	 *            abscisse du clique
	 * @param y
	 *            ordonn� du clique
	 * @return le num�ro de la case o� se situ la tortue cliqu� ou -1 si il n'y
	 *         a pas de case a cette endroit
	 */
	private byte tortueClique(int x, int y) {
		for (byte i = 0; i < tPoly.length; i++) {
			if (tPoly[i] != null && tPoly[i].contains(x, y)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * retourne si il est possible de cliquer sur la tortue indiqu� en param�tre
	 * tortue
	 * 
	 * @param numTor
	 *            num�ro de la tortue
	 * @return si il est possible de cliquer sur cette tortue
	 */
	private boolean cliquePossible(byte numTor) {
		if (tcercle.contains(numTor)) {
			return true;
		}
		for (byte[] tortue : transTortue) {
			if (tortue[0] == numTor) {
				return true;
			}
		}
		return false;
	}

	/**
	 * remet le plateau a z�ro
	 */
	private void reset() {
		final AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getResources().getString(R.string.reset));

		alertDialog.setMessage(context.getResources().getString(
				R.string.rejouer));

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context
				.getResources().getString(R.string.oui),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (conf.whoWin() == 1) { // Si les rouges gagnent
							conf = new Configuration(variante);
							conf.changePlayer();
						} else {
							conf = new Configuration(variante);
						}
						win = false;
						invalidate();
						rejouer = false;
						affPossibleMove();
					}
				});
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context
				.getResources().getString(R.string.non),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						plateau.finish();
					}
				});
		alertDialog.show();
	}

	/**
	 * affiche un dialogue et remet le plateau a z�ro si quelqu'un gagne
	 */

	private void verifWin() {
		if ((conf.whoWin() == 1) || (conf.whoWin() == 2)) {

			if (conf.whoWin() == 1) {
				win = true;
				if (!multi) {
					playSound(R.raw.win);
				}
				invalidate();
				System.out.println("Les tortues Rouges gagnent");
			}
			if (conf.whoWin() == 2) {
				win = true;
				if (!multi) {
					playSound(R.raw.defaite);
				}
				invalidate();
				System.out.println("Les tortues Vertes gagnent");
			}
		}
	}

	/**
	 * retourne si l'utilisateur a cliqu� sur l'oeuf
	 * 
	 * @param x
	 *            abscisse du clique
	 * @param y
	 *            ordonn� du clique
	 * @return si l'utilisateur a cliqu� sur l'oeuf
	 */
	@SuppressWarnings("unused")
	private boolean isOeufClick(int x, int y) {
		return new Rect(10, 10, 60, 80).contains(x, y);
	}

	/**
	 * retourne si l'utilisateur a cliqu� sur le bouton termin�
	 * 
	 * @param x
	 *            abscisse du clique
	 * @param y
	 *            ordonn� du clique
	 * @return si l'utilisateur a cliqu� sur le bouton termin�
	 */

	private byte isChoixClick(int x, int y) {
		Rect recTortueRouge = new Rect((int) (xChoixRouge),
				(int) (yChoixRouge), (int) (xChoixRouge) + tailleTortueChoix,
				(int) (yChoixRouge) + tailleTortueChoix);
		Rect recTortueVerte = new Rect((int) (xChoixVerte),
				(int) (yChoixVerte), (int) (xChoixVerte) + tailleTortueChoix,
				(int) (yChoixVerte) + tailleTortueChoix);

		if (recTortueRouge.contains(x, y)) {
			choixTortue = 1;
			choix = false;
			invalidate();
			return choixTortue;
		}
		if (recTortueVerte.contains(x, y)) {
			choixTortue = 2;
			choix = false;
			invalidate();
			return choixTortue;
		}
		return choixTortue;
	}

	public boolean isTerminerClick(int x, int y) {
		return new Rect(10, 80 + 10 + 10 + 10, 30, 20).contains(x, y);
	}

	public class Swingworker extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() { 
			if(difficulte > 3){
				progressDialog.getWindow().setGravity(Gravity.TOP);
				progressDialog.show();
				progressDialog.setContentView(R.layout.chargement);	
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				// changer d'IA
				conf = IA.meilleurConf(conf, (byte) difficulte);

			} catch (CaseNonLibreException e) {
				e.printStackTrace();
			}
			affPossibleMove();
			postInvalidate();
			threadD = false;
			return null;
		}

		protected void onPostExecute(Void unused) {
			if(difficulte > 3){
				progressDialog.dismiss();				
			}
			verifWin();
		}

	}

	public boolean onTouchEvent(MotionEvent evt) {

		if (evt.getAction() == MotionEvent.ACTION_DOWN && rejouer) {
			reset();
			rejouer = false;
		}

		a = false;

		if (choix) {
			if (evt.getAction() == MotionEvent.ACTION_DOWN ) {
				int x = (int) evt.getX();
				int y = (int) evt.getY();

				choixTortue = isChoixClick(x, y); // Attend l'appui sur l'une
													// des deux tortues
				if (choixTortue != 0) {
					choix = false; // si le choix a �t� fait, on remet �
									// z�ro
					/* Choix de la tortue */
					conf.coupJoue(Configuration.en81(clTor1), choixTortue,
							Configuration.en81(clTor2));
					choixTortue = 0;
					tcercle.clear();
					if (saute == -1)
						saute = 3;
					clTor1 = clTor2;
					clTor2 = -1;
					a = false;
					/* ----- */

					// Reprise du code
					suiteAction();
					appelThreadIa();
				}
			}

		}

		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			byte numTor = tortueClique((int) evt.getX(), (int) evt.getY()); // on
																			// récupère
																			// la
																			// tortue
																			// cliquée
			boolean isTouchee = false;
			boolean cercleTouche = false;
			
			ArrayList<Byte>listeTortuesCercles =  tcercle;
			ArrayList<byte[]>listeTortuesJouables =  transTortue;
			
			for(byte cercleTortue : listeTortuesCercles){
				if(cercleTortue == numTor){
					cercleTouche = true;
				}
			}
			
			if(!cercleTouche){
				if (transTortue.isEmpty() == false) {
					for (byte[] tortue : listeTortuesJouables) {
						if (conf.retourneCoupObligatoire().isEmpty() == false) {
							if (tortue[0] == numTor) {
								isTouchee = true;
							}
						}
					}
					if (isTouchee == false
							&& conf.retourneCoupObligatoire().isEmpty() == false) {
						return false;
					}
				}
			}

			tcercle.clear();
			invalidate();
			if ((conf.player != 2 || multi) && !oeufT || oeufC) {
				affPossibleMove();
			}

			if ((numTor != -1 && !oeufT && !oeufC) && cliquePossible(numTor)) {
				if (clTor1 == -1 && confTab[numTor] == conf.getPlayer()) {
					clTor1 = numTor;
					tcercle.clear();
					tcercle.add(numTor);
					for (byte[] poss : conf.verifCoupObligatoire()) {
						if (Configuration.en37(poss[0]) == numTor) {
							transTortue.add(new byte[] {
									Configuration.en37(poss[2]),
									Configuration.en37(poss[0]), poss[1] });
						}
					}
					if (transTortue.isEmpty()) {
						for (byte[] poss : conf.possibleMove(
								Configuration.en81(numTor), (byte) 0)) {
							transTortue.add(new byte[] {
									Configuration.en37(poss[2]),
									Configuration.en37(poss[0]), poss[1] });
						}
					}
					invalidate();
				} else if (clTor1 != -1) {
					if (clTor2 == -1) {
						clTor2 = numTor;
						confTab[clTor2] = confTab[clTor1];
						confTab[clTor1] = 0;
						for (byte[] coup : transTortue) {
							if (clTor1 == coup[1] && clTor2 == coup[0]) {
								if (coup[2] == 3) { // Si l'on doit
													// retourner une
													// tortue
									if (choixTortue == 0) {
										choix = true;
										invalidate();
										return false;
									}

								} else if (coup[2] != 0) {
									conf.coupJoue(Configuration.en81(clTor1),
											coup[2], Configuration.en81(clTor2));
									tcercle.clear();
									playSound(R.raw.flip);
									saute = coup[2];
									clTor1 = clTor2;
									clTor2 = -1;
									a = true;
								} else {
									conf.coupJoue(Configuration.en81(clTor1),
											(byte) 0,
											Configuration.en81(clTor2));
									saute = 0;
									a = true;
								}
							}
						}
						suiteAction();

					}

				}

			}
			appelThreadIa();
		}
		return true;

	}

	private void appelThreadIa() {
		// TODO Auto-generated method stub
		if (!multi && conf.getPlayer() == 2 && !win && !threadD) {
			// conf.printHexRV();
			threadD = true;
			Swingworker sw = new Swingworker();
			sw.execute();
		}
	}

	private void suiteAction() {
		// TODO Auto-generated method stub
		transTortue.clear();

		if (saute > 0) {
			for (byte[] poss : conf.possibleMove(Configuration.en81(clTor1),
					saute)) {
				transTortue.add(new byte[] { Configuration.en37(poss[2]),
						Configuration.en37(poss[0]), poss[1] });
				tcercle.clear();
			}
			if (transTortue.isEmpty()) {
				saute = -1;
				clTor1 = -1;
				clTor2 = -1;
				conf.changePlayer();
				affPossibleMove();
			}
		} else if (!a) {
			clTor1 = -1;
			clTor2 = -1;
			affPossibleMove();
		} else {
			clTor1 = -1;
			clTor2 = -1;
			conf.changePlayer();
			tcercle.clear();
			if (multi) {
				affPossibleMove();
			}
			saute = -1;

		}
		verifWin();
	}

	private void playSound(int resId) {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
		}
		mPlayer = MediaPlayer.create(context, resId);
		mPlayer.start();
	}
}