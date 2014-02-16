package com.example.testplateautortuga;

import java.util.ArrayList;
import java.util.HashSet;

//import javax.naming.ConfigurationException;

public class IA {
	
	/**
	 * <b>Classe Impl�mentant les m�thode de L'Intelligence Artificielle</b>
	 * Permettant de faire jouer une IA sur une {@link Configuration} gr�ce a la m�thode {@link IA#meilleurConf(Configuration, byte)}
	 * 
	 * @see Configuration
	 * @see IA#meilleurConf(Configuration, byte)
	 */
	
	private static int[] tabJoueur = {
						10, 0,  500, 3500,
						20, 10, 10, 500, 500,
						30, 20, 20, 10,  10,   0,
						45, 35, 35, 20, 20,  10,  10,
						75, 75, 35, 35, 20,  20,
						100, 120, 75, 35, 30,
						1000000, 100, 75, 45 };	
	private static int[] tabJoueur2={
					45,75,100,1000000,
				30,35,75,120,100,
			 20,20,35,35,75,75,
		  10,10,20,20,35,35,45,
		  0,10,10,20,20,30,
		 500,500,10,10,20,
	    3500,500,0,10};
	public IA() {
		super();
	}

	/**
	 * Calcul la liste des configurations possibles a partir d'une configuration
	 * Pour cela on calcule la liste de coups possibles (triplet) depuis la
	 * configuration puis pour chaque triplet, on cr�e une configuration. Si le
	 * coup joue est un d�placement direct : clone puis coupJoue puis
	 * listeConf.add... Si le coup joue est un saut : appel de sautRecusrif qui
	 * va calculer retourner toutes les autres configurations suivant les saut.
	 * Si le mode �closion est activ�, on ajoute �galement toutes les
	 * Configurations g�ner� par recursifEclo()
	 * 
	 * @param conf
	 *            configuration depuis laquelle on calcule toutes les nouvelles
	 *            configurations
	 * 
	 * @return la liste des nouvelles configurations
	 * @throws CaseNonLibreException
	 */
	private static ArrayList<Configuration> listeFils(Configuration conf)
			throws CaseNonLibreException {
		byte caseDebut;
		byte typeSauteFirst;
		byte caseFin;
		boolean coupObligatoire=true;
		ArrayList<Configuration> listeConf = new ArrayList<Configuration>();
		for (byte[] triplet : conf.verifCoupObligatoire()) { // pour chaque coup
			// de la liste
			// des coups
			// possibles
			caseDebut = triplet[0];
			typeSauteFirst = triplet[1];
			caseFin = triplet[2];
			if (typeSauteFirst == 0) { // si coup = deplacement direct
				Configuration c = conf.clone();
				c.coupJoue(caseDebut, (byte) 0, caseFin);
				listeConf.add(c); // AJOUT de la nouvelle config
				coupObligatoire=false;
			} else if (typeSauteFirst > 0) { // si coup = saut
				listeConf.addAll(sautRecursif(conf, triplet, typeSauteFirst,
						false));
			}
		}
		if (!coupObligatoire && conf.getVarianteEclo()) {
			for (byte i = 1; i <= conf.oeufsDispoEclo(); i++) {
				listeConf.addAll(recursifEclo(conf, i, (byte) 1));
			}
		}
		for (Configuration c : listeConf) { // changement de joueur pour toutes
			// les config fils crees
			c.changePlayer();
		}
		return listeConf;
	}

	/**
	 * Fonction recursive qui va calculer les configurations possible a partir
	 * d'un saut par dessus une tortue : quand une tortue saute par dessus une
	 * autre, on regarde si elle peut a nouveau sauter sur d'autre tortue d'ou
	 * l'appel recursif. Ensuite, le contenu de la fonction ressemble (un peu) a
	 * la fonction listeFils. La fonction a un autre appel recursif lorsqu'une
	 * tortue retournee est sautee, il faut creer 2 config, une en retournant la
	 * tortue en la couleur du player (appel recursif), une en la retournant de
	 * la couleur de l'opponent (suite normale de la fonction).
	 * 
	 * @param conf
	 *            Configuration pour laquelle on calcule les nouvelles config
	 * 
	 * @param triplet
	 *            {caseDebut, typeSaute, caseFin} coup effectue apres lequel on
	 *            calcule les nouvelles config
	 * 
	 * @param typeSauteFirst
	 *            = 1 si la premiere tortue saute non retournee etait une rouge
	 *            (1) dans ce tour = 2 si la premiere tortue saute non retournee
	 *            etait une verte (2) dans ce tour = 3 si seulement des tortues
	 *            retournees (3) ont ete sautees depuis le debut du tour
	 * 
	 * @param noirEnPlayer
	 *            boolean qui determinera en couleur couleur se change une
	 *            tortue retournee 2fois true => la tortue retournee se change
	 *            en player (=> nouvel appel recursif) false => la tortue
	 *            retournee se change en opponent
	 * 
	 * @return la liste de toutes les nouvelles configurations
	 * 
	 */

	private static ArrayList<Configuration> sautRecursif(Configuration conf,
			byte[] triplet, byte typeSauteFirst, boolean noirEnPlayer) {

		ArrayList<Configuration> listeConf = new ArrayList<Configuration>();

		byte caseDebut = triplet[0];
		byte typeSaute = triplet[1];
		byte caseFin = triplet[2];
		byte newTurttle = 0;

		if (typeSaute == conf.player)
			newTurttle = conf.player;// player saute opponent2 : pas de
		// changement
		else if (typeSaute == conf.opponent)
			newTurttle = typeSaute; // player saute opponent2 : rouge2 se
		// retourne en noir
		else if (typeSaute == 3) { // player1 saute noir2 : 2 cas, _noir2
			// devient player, on fait un appel recursif
			// pour traiter ce cas.
			// _noir2 devient opponent, on continue la fonction normalement
			if (noirEnPlayer) {
				newTurttle = conf.player;
			} else {
				listeConf.addAll(sautRecursif(conf, triplet, typeSauteFirst,
						true)); // appel recursif
				newTurttle = conf.opponent;
			}
		} else
			System.out
					.println("ERREUR : dans la fct recursive de IA, typeSaute != 1,2 ou 3");

		Configuration c = conf.clone();
		c.coupJoue(caseDebut, newTurttle, caseFin);

		// typeSauteFirst doit contenir le type de la premiere tortue de couleur
		// rouge ou verte sautee dans un tour de jeu
		if (typeSauteFirst == 3) {
			typeSauteFirst = typeSaute;
		}
		ArrayList<byte[]> lMovePossible = c.possibleMove(caseFin,
				typeSauteFirst);
		if (lMovePossible.isEmpty()) { // si pas de saut possible apres le
			// premier coup
			listeConf.add(c); // AJOUT de la nouvelle config
		} else {
			for (byte[] tripletFils : lMovePossible) {
				listeConf.addAll(sautRecursif(c, tripletFils, typeSauteFirst,
						false)); // appel recursif
			}
		}
		return listeConf;
	}

	/**
	 * Fonction recursive qui va calculer les configurations possible lors du
	 * placement d'oeuf(s) : exemple d'appel : recursifEclo(conf, 3, 1) Pour
	 * chaque case libe du plateau, on pose une tortue puis une autre etc...
	 * jusqu'� qu'on ateigne oeufsMaxEclo on commence toujours par appeler la
	 * m�thode avec 1 en troisi�mme param�tre car cela signifie qu'on pose la
	 * pemi�re tortue ensuite un appel r�cursif sera : recursifEclo(c, 3, 2)
	 * avec c comme clone de conf avec un oeufs en plus sur le plateau enfin :
	 * recursifEclo(c, 3, 3) pour placr la 3eme tortue. Ensuite plus d'appel
	 * r�cursif car (3<=3) Cette m�thode cr�e des doublons, d'ou l'utilistion
	 * d'un hashSet et la red�finition de equals() dans Configuation.java
	 * 
	 * @param conf
	 *            Configuration pour laquelle on calcule les nouvelles config
	 * 
	 * @param oeufsMaxEclo
	 *            nombre d'oeufs qui seront pos�s a final
	 * 
	 * @param oeufsEclo
	 *            nombre d'oeufs que l'on �tait entrain de poser
	 * 
	 * @return la liste de toutes les nouvelles configurations
	 * @throws CaseNonLibreException
	 */

	private static HashSet<Configuration> recursifEclo(Configuration conf,
			byte oeufsMaxEclo, byte oeufsEclo) throws CaseNonLibreException {
		HashSet<Configuration> listeConf = new HashSet<Configuration>();

		if (oeufsEclo <= oeufsMaxEclo) {
			for (byte libre : conf.caseLibreEclo()) {
				Configuration c = conf.clone();
				c.coupEclo(libre);
				listeConf.addAll(recursifEclo(c, oeufsMaxEclo,
						(byte) (oeufsEclo + 1)));
			}
		} else {
			listeConf.add(conf);
		}
		return listeConf;
	}

	// --------------------------Fonctions utiles à l'evaluation--------------------------------

	/**
	 * Fonction permettant d'inverser un tableau
	 * @param tab un tableau
	 * @return son inverse
	 */
	public static int[] inverser(int[] tab) {
		int[] tab2 = new int[tab.length];
		for (int i = 0; i < tab.length; i++) {
			tab2[i] = tab[tab.length - i - 1];
		}
		return tab2;
	}
	/**
	 * Fonction indiquant le nombre de tortue que le joueur poss�de
	 * @param jeu tableau de 37 case repr�sentant le plateau
	 * @param joueur joueur
	 * @return le nombre de tortue poss�d� par le joueur
	 */
	public static Byte nb_tortues_possede(Byte jeu[], Byte joueur) {
		Byte nb = 0;
		for (int i = 0; i < jeu.length; i++) {
			if (jeu[i] == joueur) {
				nb++;
			}

		}
		return nb;
	}

	/**
	 *  Calcule le nombre de saut possible de mani�re r�cursive
	 * @param conf une {@link Configuration}
	 * @param joueur le joueur
	 * @param pos la position de la tortue �tant d�part de la cha�ne
	 * @param nbSaut nb de sauts d�j� effectu�
	 * @return le nombre de saut possible pour cette tortue
	 */
	public static byte chaine(Configuration conf, byte joueur, byte pos,
			byte nbSaut) {
		// On donne la valeur a signe selon que le joueur est rouge ou vert
		// Si joueur==1, retourne 1 sinon -1
		byte signe = (byte) (joueur == 1 ? 1 : -1);
		// Opponent est l'opposé de joueur
		byte opp = (byte) (joueur == 1 ? 2 : 1);
		// Meilleurnbcoup augmentera selon qu'une chaine est possible
		//byte meilleurnbCoup = 0;
		// Si il y a une tortue en haut et qu'apres il y en a pas, on peut
		// Bouffer
		if ((conf.tPlateau[pos + (signe * -8)] == 3 || conf.tPlateau[pos
				+ (signe * -8)] == opp)
				&& conf.tPlateau[pos + (signe * -8 * 2)] == 0) {
				chaine(conf, joueur, (byte) (pos + (signe * -8 * 2)),
					(byte) (nbSaut + 1));
		}

		return nbSaut;
	}
	
	//autre methode
	//public static byte chaine_mange(Configuration conf, byte joueur, byte pos){
		
	//}

	
	//-------------------------------Fonctions pour l'evaluation---------------------

	/**
	 * �value une plateau en fonction de la positions de tortue pr�sente dessu pour un joueur donn�
	 * @param jeu tableau de 37 case repr�sentant le plateau
	 * @param player le joueur
	 * @return note �valu�
	 */
	public static int position(Byte[] jeu, byte player) {
		
		//Initialisation de la valeur de position
		int val = 0;
		//Definition des valeurs du tableau correspondants aux joueurs et opposant
		byte opponent = 0;
		if (player == 1)
			opponent = 2;
		else if (player == 2)
			opponent = 1;
		else
			System.out.println("erreur IA");
		
		// Evaluation coté vert
		if (player != 1) {
			for (byte i = 0; i < 37; i++) {
				if (jeu[i] == opponent) {
					val -= tabJoueur2[i];
				} else if (jeu[i] == player) {
					val += tabJoueur[i];
				}
			}
		}
		// Evaluation coté rouge
		if (player == 1) {
			val = 0;
			for (byte i = 0; i < 37; i++) {
				if (jeu[i] == opponent) {
					val -= tabJoueur[i];
				} else if (jeu[i] == player) {
					val += tabJoueur2[i];
				}
			}
		}
		return val;

	}
	/**
	 * �value une plateau en fonction du nombre de tortue de chaque joueur pour un joueur donn�
	 * @param jeu tableau de 37 case repr�sentant le plateau
	 * @param player le joueur
	 * @return note �valu�
	 */
	public static int valeur_nombre_tortue(Byte[] jeu, byte player) {

		int val = 0;
		byte opponent = 0;
		//Byte empty = 0;
		if (player == 1)
			opponent = 2;
		else if (player == 2)
			opponent = 1;
		else
			System.out.println("erreur IA");

		Byte a = nb_tortues_possede(jeu, player);
		Byte b = nb_tortues_possede(jeu, opponent);
		val += (a - b) * 400;

		return val;
	}
	/**
	 * �value une plateau en fonction des situation offensive ou defensive pour un joueur donn�
	 * @param jeu tableau de 37 case repr�sentant le plateau
	 * @param player le joueur
	 * @return note �valu�
	 */
	public static int avantage(Byte[] jeu, byte player) {

		int val = 0;
		byte opponent = 0;
		Byte empty = 0;
		if (player == 1)
			opponent = 2;
		else if (player == 2)
			opponent = 1;
		else
			System.out.println("erreur IA");

		// Evaluation coté vert
		if (player != 1) {
			if (jeu[33] == empty) {
				val += 500;
			}
			// Si la tour de defense adverse est absente (4 tortues defensives)
			//Exactement 1
			if (jeu[28] != opponent || jeu[29] != opponent
					|| jeu[34] != opponent) {
				val += 40;
				//Exactement 2
				if ((jeu[28] != opponent && jeu[29] != opponent)
						|| (jeu[28] != opponent && jeu[34] != opponent)
						|| (jeu[29] != opponent && jeu[34] != opponent)) {
					val += 60;
					//Exactement 3
					if (jeu[28] != opponent && jeu[29] != opponent
							&& jeu[34] != opponent) {
						val += 120;
					}
				}

			}
			
			// Si il a une ouverture dans la defense adverse (case 33 vide et au moins 1
			// defensive absente
			if ((jeu[33] == empty)
					&& (jeu[28] != player || jeu[29] != player || jeu[34] != player)) {
				val += 1500;
			}
			// Valeur defensive joueur
			if (jeu[2] == player || jeu[7] == player || jeu[8] == player) {
				// On cherche lequelles sont présentes
				Byte[] tab=new Byte[]{jeu[2],jeu[7],jeu[8]};
				// la valeur est incrementée proportionnellement au nombre de tortues
				// defensives
				val += (nb_tortues_possede(tab, player)) * 420;
				// On rajoute un bonus si il y a la totalité des tortues defensives présentes
				if (nb_tortues_possede(tab, player) == 4) {
					val += 100;
				}

			}
		}
		//Evaluation coté rouge
		else{
			if (jeu[3] == empty) {
				val += 500;
			}
			// si tour de la defense absent
			if (jeu[8] != opponent || jeu[7] != opponent
					|| jeu[2] != opponent) {
				val += 40;
				if ((jeu[8] != opponent && jeu[7] != opponent)
						|| (jeu[8] != opponent && jeu[2] != opponent)
						|| (jeu[7] != opponent && jeu[2] != opponent)) {
					val += 60;
					if (jeu[8] != opponent && jeu[7] != opponent
							&& jeu[2] != opponent) {
						val += 120;
					}
				}

			}
			
			if ((jeu[3] == empty)
					&& (jeu[8] != player || jeu[7] != player || jeu[2] != player)) {
				val += 1500;
			}
			
			if (jeu[34] == player || jeu[29] == player || jeu[28] == player) {
				Byte[] tab=new Byte[]{jeu[34],jeu[29],jeu[28]};
				val += (nb_tortues_possede(tab, player)) * 420;
				if (nb_tortues_possede(tab, player) == 4) {
					val += 250;
				}

			}
		}

		return val;
	}

	//Fonction qui differencie les fins afin de choisir le meilleur coup pour finir
	
	
	
	// -------------------------------------------------------------------------------------
	/**
	 * �value une {@link Configuration}
	 * @param conf la {@link Configuration} � �valuer
	 * @return note �valu�
	 */
	public static int evaluation(Configuration conf) {
		Byte[] jeu = conf.convertTabPlateauEn37(); // retourne un tableau de 37
		// cases
		// jeu <=> Byte[37] : plateau du jeu avec 37 cases

		// ------------calcule de la valeur
		// d'evaluation--------------------------
		int val = position(jeu, conf.player);
		val += valeur_nombre_tortue(jeu, conf.player);
		val += avantage(jeu, conf.player);
		/*
		 * conf.printHexRV(); System.out.println(val);
		 */
		return val;
	}

	/**
	 * Algorithme r�cursif permettant de noter un {@link Configuration} en fonction de ces {@link Configuration} pr�d�cesseuses
	 * gr�ce � l'algorithme Min Max et plus particuli�rement l'�lagage Alpha Beta.
	 * <p>
	 * http://fr.wikipedia.org/wiki/�lagage_alpha-beta
	 * </p>
	 * @param conf {@link Configuration} a �valuer
	 * @param alpha
	 * @param beta
	 * @param depth profondeur actuel
	 * @param max profondeur maximum
	 * @return valeur estim� de la configuration
	 * @throws CaseNonLibreException en cas de tentative par L'IA de pos� une tortue sur une case utilis�
	 */
	private static int alphaBeta(Configuration conf, int alpha, int beta,
			byte depth, byte max) throws CaseNonLibreException {
		{
			Byte[] jeu = conf.convertTabPlateauEn37();
			Byte a = nb_tortues_possede(jeu, conf.player);
			Byte b = nb_tortues_possede(jeu, conf.opponent);
			if(a==0) {
				//System.out.println("1000000");
				//conf.printHexRV();
				//a priori -100000
				return -10000000;
			}
			if(b==0) {
				//System.out.println("-1000000");
				//conf.printHexRV();
			
				return 10000000;
			}
			
		}
		if ((conf.whoWin() != 0) || (depth >= max)) {
			return evaluation(conf);
		} else {
			int meilleur = Integer.MIN_VALUE;
			for (Configuration fconf : listeFils(conf)) {
				int suiv = -alphaBeta(fconf, -beta, -alpha, (byte) (depth + 1),
						max);
				if (suiv > meilleur) {
					meilleur = suiv;
					if (meilleur > alpha) {
						alpha = meilleur;
						if (alpha >= beta) {
							return meilleur;
						}
					}
				}
			}
			return meilleur;
		}
	}
	/**
	 * fait jouer l'IA et retourne la nouvelle configuration
	 * @param conf {@link Configuration} o� l'IA doit jouer
	 * @param max profondeur maximum de pr�vision pour l'IA
	 * @return la {@link Configuration} apr�s que l'IA est jou�
	 * @throws CaseNonLibreException en cas de tentative par L'IA de pos� une tortue sur une case utilis�
	 */
	public static Configuration meilleurConf(Configuration conf, byte max)
			throws CaseNonLibreException {
		Configuration meilleurConf = null;
		int valMeilleur = Integer.MIN_VALUE;
		int val;
		boolean prems = true;
		@SuppressWarnings("unused")
		int i=1;
		@SuppressWarnings("unused")
		int size=listeFils(conf).size();
		for (Configuration fconf : listeFils(conf)) {
			//System.out.println(i+++"/"+size);
			val = -alphaBeta(fconf, -10000000, 10000000, (byte) 1, (byte) (max));
			//			
			// System.out.println("---------------------------------------------------------------------");
			// fconf.printHexRV();
			// System.out.println("valeur = "+val+ "meilleur = " + valMeilleur);

			if (val > valMeilleur || prems) {
				valMeilleur = val;
				meilleurConf = fconf;
				prems = false;
			}
		}
		//System.out.println("choisi = " + valMeilleur);
		
		
		return meilleurConf;
	}

}
