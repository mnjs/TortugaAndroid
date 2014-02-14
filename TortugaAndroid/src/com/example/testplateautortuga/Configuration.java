package com.example.testplateautortuga;


import java.util.ArrayList;


/**
 * <b>Class représentant un plateau de jeu</b>
 * <p>
 * Un plateau est caractérisé par les information suivante :
 * <ul>
 * <li>Un tableau représentant le plateau</li>
 * <li>le numéro du joueur actuel</li>
 * <li>le numéro de l'adversaire</li>
 * <li>la note évalué du plateau</li>
 * <li>Un éventuel gagnant</li>
 * </ul>
 */

public class Configuration implements Comparable<Configuration>{

	/** 
	 * <b>Mode de Jeu Eclosion</b>
	 * <p>
	 * Constante a Utiliser dans le constructeur Pour choisir de jouer en mode éclosion
	 * </p>
	 * @see Configuration#Configuration(boolean)
	 */
	final public static boolean VARIANTE__ECLOSION=true;
	/** 
	 * <b>Mode de Jeu Normal</b>
	 * <p>
	 * Constante a Utiliser dans le constructeur Pour choisir de jouer en mode éclosion
	 * </p>
	 * @see Configuration#Configuration(boolean)
	 */
	final public static boolean VARIANTE_NORMAL=false;
	/** 
	 * <b>Tableau des position possible pour les oeufs en mode éclosion</b>
	 * <p>
	 * Utilisé pour la définition des coups possibles en mode éclosion.
	 * </p>
	 * @see Configuration#possibleMove(byte, byte)
	 * @see Configuration#allPossibleMove()
	 * @see Configuration#verifCoupObligatoire()
	 */
	final private static byte tStartEclo[][] = {null,{37, 46, 55, 56, 65, 66, 67},
													 {13, 14, 15, 24, 25, 34, 43}}; // 7 emplacements d'éclosion du plateau
	/** 
	 * <b>Tableau de conversion de plateau</b>
	 * <p>
	 * Utilisé pour la conversion d'un tableau de 81 cases en tableau de 37 cases
	 * </p>
	 * @see Configuration#convertTabPlateauEn37()
	 * @see Configuration#en37(byte)
	 * @see Configuration#en37(int)
	 */
	final private static byte[] t81convert37={-1,-1,-1,-1,-1,-1,-1,-1,-1,
											  -1,-1,-1,-1, 0, 1, 2, 3,-1,
											  -1,-1,-1, 4, 5, 6, 7, 8,-1,
											  -1,-1, 9,10,11,12,13,14,-1,
											  -1,15,16,17,18,19,20,21,-1,
											  -1,22,23,24,25,26,27,-1,-1,
											  -1,28,29,30,31,32,-1,-1,-1,
											  -1,33,34,35,36,-1,-1,-1,-1,
											  -1,-1,-1,-1,-1,-1,-1,-1,-1};
	/** 
	 * <b>Tableau de conversion de plateau</b>
	 * <p>
	 * Utilisé pour la conversion de coordonnées d'un tableau de 81 cases en tableau de 37 cases
	 * </p>
	 * @see Configuration#en81(byte)
	 * @see Configuration#en81(int)
	 */
	final private static byte[] t37convert81={13,14,15,16,
										   21,22,23,24,25,
										29,30,31,32,33,34,
									 37,38,39,40,41,42,43,
									 46,47,48,49,50,51,
									 55,56,57,58,59,
									 64,65,66,67};
	
	
	
	
	
	/** 
	 * <b>Tableau représentant le plateau de Jeu</b>
	 * <p>
	 * 
	 * remplissage du tableau :
	 * <ul>
	 * <li> -1 = case en dehors du plateau</li>
	 * <li> 0 = case vide du plateau</li>
	 * <li> 1 = tortue du joueur en bas (rouge)</li>
	 * <li> 2 = tortue du joueur en haut (vert)</li>
	 * <li> 3 = tortue retourne</li>
	 * <ul>
	 * </p>
	 */
	public byte[] tPlateau;
	
	/** 
	 * <b>Joueur actuel</b>
	 * <p>
	 * stock le numéro du joueur qui est en cours de jouer
	 * </p>
	 * @see Configuration#getPlayer()
	 */
	public byte player;
	/** 
	 * <b>Joueur adverse</b>
	 * <p>
	 * stock le numéro de l'adversaire du joueur actuel
	 * </p>
	 * @see Configuration#getOpponent()
	 */
	public byte opponent;
	
	/** 
	 * <b>Evaluation du plateau actuel</b>
	 * <p>
	 * Evaluation du plateau faite par l'intelligence artificielle
	 * </p>
	 * @see IA#evaluation(Configuration)
	 */
	public int eval;
	/** 
	 * <b>Joueur ayant gagné</b>
	 * <p>
	 * stock le numéro du joueur qui a gagné ou est a 0 si la partie est en cours
	 * </p>
	 * @see Configuration#whoWin()
	 */
	public byte winner; // 0 si partie en cour ; 1 si rouge(bas) gagne ; 2 si vert(haut) gagne
	/** 
	 * <b>Type de partie</b>
	 * <p>
	 * défini le type de partie
	 * </p>
	 * @see Configuration#VARIANTE__ECLOSION
	 * @see Configuration#VARIANTE_NORMAL
	 * @see Configuration#getVarianteEclo()
	 */
	private boolean varianteEclo = false; // change : enlever le true par d�faut
	/** 
	 * <b>Stock d'oeuf pour le joueur actuel</b>
	 * <p>
	 * stock le nombre d'oeufs que le joueur peux placer
	 * </p>
	 * @see Configuration#getStockPlayerEclo()
	 */
	private byte stockPlayerEclo = 8;
	/** 
	 * <b>Stock d'oeuf pour l'adversaire</b>
	 * <p>
	 * stock le nombre d'oeufs que l'adversaire peux placer
	 * </p>
	 * @see Configuration#getStockOpponentEclo()
	 */
	private byte stockOpponentEclo = 8;
	
	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * crée une nouvelle {@link Configuration} en variante normal.
	 * </p>
	 */
	public Configuration(){
		this(false);
	}
	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * crée une nouvelle partie
	 * 
	 * </p>
	 * @param varianteEclo Type de partie ({@link Configuration#VARIANTE__ECLOSION} ou {@link Configuration#VARIANTE_NORMAL})
	 * 
	 * @see Configuration#VARIANTE__ECLOSION
	 * @see Configuration#VARIANTE_NORMAL
	 */
	public Configuration(boolean varianteEclo) {
		tPlateau = new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,
							    -1,-1,-1,-1, 0, 0, 0, 0,-1,	//		 ______
							    -1,-1,-1, 0, 0, 0, 0, 0,-1,	//		/	   |
							    -1,-1, 0, 0, 0, 0, 0, 0,-1,	//	   /	V  |
							    -1, 0, 0, 0, 0, 0, 0, 0,-1,	//	  |		   |
							    -1, 0, 0, 0, 0, 0, 0,-1,-1,	//	  |	      /
							    -1, 0, 0, 0, 0, 0,-1,-1,-1,	//	  |	R    /
							    -1, 0, 0, 0, 0,-1,-1,-1,-1,	//	  |_____/
							    -1,-1,-1,-1,-1,-1,-1,-1,-1};
		
		eval = 0;
		player = 2;
		opponent = 1;
		winner=0;
		this.varianteEclo = varianteEclo;
		if ( varianteEclo ) {
			// rajouter ce qu'il faut pour la variante éclosion
		} else { 
			placementDefautTortue();
		}
	}
	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * permet de créer un {@link Configuration} en renseignant toute les attribut de celle-ci
	 * </p>
	 * @param tab tableau représentant le plateau (81 cases)
	 * @param player Joueur actuel
	 * @param opponent joueur opposant
	 * @param eval evaluation du plateau
	 * @param varianteEclo Type de partie ({@link Configuration#VARIANTE__ECLOSION} ou {@link Configuration#VARIANTE_NORMAL})
	 * @param stockPlayerEclo stock de tortue de joueur actuel
	 * @param stockOpponentEclo stock de tortue de l'opposant
	 * 
	 * @see Configuration#VARIANTE__ECLOSION
	 * @see Configuration#VARIANTE_NORMAL
	 */
	public Configuration(byte[] tab,byte player, byte opponent, int eval, boolean varianteEclo, byte stockPlayerEclo, byte stockOpponentEclo){
		tPlateau=new byte[81];
		System.arraycopy(tab, 0, tPlateau, 0, tab.length);
		this.player = player;
		this.opponent = opponent;
		this.eval = eval;
		this.varianteEclo = varianteEclo; 
		this.stockPlayerEclo = stockPlayerEclo;
		this.stockOpponentEclo = stockOpponentEclo;
		
	}
	/**
	 * <b>Constructeur de classe</b>
	 * <p>
	 * permet de créer un {@link Configuration} a partir d'un tableau
	 * </p>
	 * @param tab37 tableau représentant le plateau (37 cases)
	 * @param varianteEclo Type de partie ({@link Configuration#VARIANTE__ECLOSION} ou {@link Configuration#VARIANTE_NORMAL})
	 * 
	 * @see Configuration#VARIANTE__ECLOSION
	 * @see Configuration#VARIANTE_NORMAL
	 */
	public Configuration(byte[] tab37, boolean varianteEclo){
		this(varianteEclo);
		 for (int i = 0; i < tab37.length; i++) {
			tPlateau[Configuration.en81(i)]=tab37[i];
		}
	}
	/**
	 * Retourne un clone de l'objet
	 * @return clone de l'objet
	 */
	public Configuration clone() {
		return new Configuration(tPlateau, player, opponent, eval, varianteEclo, stockPlayerEclo, stockOpponentEclo);
	}
	/**
	 * Retourne <code>true</code> si on est en mode éclosion
	 * @return <code>true</code> si on est en mode éclosion
	 */
	public boolean isEclo() {
		return varianteEclo;
	}
	
	public byte getPlayer(){
		return player;
	}
	/**
	 * Retourne le mode de jeu
	 * @return {@link Configuration#VARIANTE_NORMAL} ou {@link Configuration#VARIANTE__ECLOSION}
	 */
	public boolean getVarianteEclo() {
		return varianteEclo;
	}
	
	/**
	 * Change le mode de jeu
	 * @change {@link Configuration#VARIANTE_NORMAL} ou {@link Configuration#VARIANTE__ECLOSION}
	 */
	public void setVariante(boolean var) {
		varianteEclo=var;
	}
	
	/**
	 * Retourne le stock de d'oeufs du joueur
	 * @return le nombre d'oeufs que le joueur à en stock
	 */
	public byte getStockPlayerEclo() {
		return stockPlayerEclo;
	}
	/**
	 * Retourne le stock de d'oeufs de l'opposant
	 * @return le nombre d'oeufs que l'opposant à en stock
	 */
	public byte getStockOpponentEclo() {
		return stockOpponentEclo;
	}
	/**
	 * Retourne l'evaluation du plateau
	 * @return l'evaluation du plateau
	 */
	public int getEval() {
		return eval;
	}
	/**
	 * Met à jour l'évaluation du plateau
	 * @param i nouvelle valeur de l'évaluation
	 */
	public void setEval(int i) {
		this.eval = i;
	}	
	
	/**
	 * remplit le tableau pour le début de partie
	 */
	private void placementDefautTortue(){
		// d�finition du plateau avec les tortues (hors mode éclosion)
		for (int i=0 ; i<4 ; i++){
			tPlateau[i+13]=2;
			tPlateau[16+9*i]=2;
		}tPlateau[24]=2;
		for (int i=0 ; i<4 ; i++){
			tPlateau[i+64]=1;
			tPlateau[37+9*i]=1;
		}tPlateau[56]=1;
	}
	
	/**
	 * Retourne un tableau de 37x37 byte représentant le plateau de jeu<br>
	 * les tortues sont représenté de la façon suivante:<br>
	 *-1 = case en dehors du plateau<br>
	 * 0 = case vide du plateau<br>
	 * 1 = tortue du joueur en bas<br>
	 * 2 = tortue du joueur en haut<br>
	 * 3 = tortue retourne<br>

	 * @return le plateau sus forme de tableau
	 */
	public Byte[] convertTabPlateauEn37(){ // m�thode à utiliser avant d'envoyer tPlateau37 à la partie graphique
		int ii = 0;
		Byte[] tPlateau37 = new Byte[37];
		for (int i=0; i<tPlateau.length ; i++){
			if (tPlateau[i] != -1){
				tPlateau37[ii]=tPlateau[i];
				ii ++;
			}
		}
		return tPlateau37;
	}
	
	/**
	 * convertit un indice du tableau de 81 cases en indice du tableau de 37 cases correspondant 
	 * @param numCase indice de dans le tableau de 81 cases
	 * @return indice de dans le tableau de 37 cases
	 */
	public static byte en37(byte numCase){ // ex : en37(21) retourne 4, en37(64) retourne 33
		if (numCase >= 0 && numCase < t81convert37.length)
			return t81convert37[numCase];
		else
			System.out.println("config.en37() : bug, parametre en dehors du tableau");
		return -1;
	}
	/**
	 * convertit un indice du tableau de 37 cases en indice du tableau de 81 cases correspondant 
	 * @param numCase indice de dans le tableau de 37 cases
	 * @return indice de dans le tableau de 81 cases
	 */
	public  static byte en81(byte numCase){ // (réciproque de en37()) ex : en37(4) retourne 21, en37(33) retourne 64
		if (numCase >= 0 && numCase < t37convert81.length)
			return t37convert81[numCase];
		else
			System.out.println("config.en81() : bug, parametre en dehors du tableau");
		return -1;
	}
	/**
	 * convertit un indice du tableau de 81 cases en indice du tableau de 37 cases correspondant 
	 * @param numCase indice de dans le tableau de 81 cases
	 * @return indice de dans le tableau de 37 cases
	 */
	public static byte en37(int numCase){ // ex : en37(21) retourne 4, en37(64) retourne 33
		if (numCase >= 0 && numCase < t81convert37.length)
			return t81convert37[numCase];
		else
			System.out.println("config.en37() : bug, parametre en dehors du tableau");
		return -1;
	}
	/**
	 * convertit un indice du tableau de 37 cases en indice du tableau de 81 cases correspondant 
	 * @param numCase indice de dans le tableau de 37 cases
	 * @return indice de dans le tableau de 81 cases
	 */
	public  static byte en81(int numCase){ // (réciproque de en37()) ex : en37(4) retourne 21, en37(33) retourne 64
		if (numCase >= 0 && numCase < t37convert81.length)
			return t37convert81[numCase];
		else
			System.out.println("config.en81() : bug, parametre en dehors du tableau");
		return -1;
	}
	/**
	 * inverse le joueur actuel et l'opposant
	 */
	public void changePlayer(){
		byte tmp = player;
		player = opponent;
		opponent = tmp;
		if (varianteEclo){
			tmp = stockOpponentEclo;
			stockOpponentEclo = stockPlayerEclo;
			stockPlayerEclo = tmp;
		}
	}
	/**
	 * Retourne le joueur gagnant la partie
	 * @return le joueur gagnant la partie ou 0 si la partie est en cours 
	 */
	public byte whoWin(){
		if 		(tPlateau[16] == 1) return 1;
		else if (tPlateau[64] == 2) return 2;
		else if ( isBlocked() )		return opponent;
		else return 0;
	}
	
	/**
	 * basée sur la méthode possibleMove()
	 * Teste si des déplacements ou sauts sont possible, si c'est le cas, la fonction
	 * s'arrête prématurément en renvoyant false. Sinon, si toutes les tortues sont testées
	 * et qu'aucun déplacement n'est possible, on renvoie true.
	 * Dans le cas où le joueur n'a plus de tortue, la méthode renvoie true
	 * 
	 * @return true si configuration blocké (plus de coups possible), false sinon
	 */
	public boolean isBlocked(){
		byte signe=0, tCaseAutour[]={-8,-9, 1};
		if (player == 1) signe = 1;
		else if (player == 2) signe = -1;
		
		for (byte numCase=0 ; numCase<tPlateau.length ; numCase++) {
			if (tPlateau[numCase] == player){ //pour chaque tortue du joueur
				byte typeCaseSuiv;
				for (byte dir : tCaseAutour){
					typeCaseSuiv = tPlateau[numCase+signe*dir];
					if (typeCaseSuiv == 0){
						return false; // DEPLACEMENT possible => non bloqué
					}else if ((typeCaseSuiv > 0 ) && (tPlateau[numCase+signe*2*dir] == 0)){ // SAUT possible
						return false; // SAUT possible => non bloqué
					}
				}
			}
		}
		return true;
	}	
	/**
	 * Compare la configuration avec un objet et retourne si elle est identique a celui ci
	 * @return <code>true</code> si les obje son égaux et <code>false</code> au contraire
	 * @param conf l'objet qui doit etre comparé a la {@link Configuration}
	 */
	public boolean equals(Object conf){
		
		if ((((Configuration)conf).player != this.player) || (((Configuration)conf).opponent != this.opponent))
			return false;
		for (byte b = 0 ; b<81 ; b++){
			if (this.tPlateau[b] != ((Configuration) conf).tPlateau[b]) {
				return false;
			}
		}
		return true;
	}

	/**On surcharge cette méthode définit dans Object, ainsi, quand la collection {@link HashSet} (qui n'autorise pas les doublons)
	 * compare 2 configurations pour enlever les doublons, elle appelle directement la méthode equals (aussi redéfinit)
	 * 
	 * cf http://www.developpez.net/forums/d44105/java/general-java/apis/java-util/hashset-element-non-unique/
	 */
    public int hashCode() {
        return 1;//getPath().hashCode();
    }
    /**
     * Retourne la valeur absolue d'un entier
     * @param val un entier
     * @return sa valeur absolue
     */
	 private static int abs(int val){
		 if (val < 0)	return (byte) -val;
		 else 			return val;
	 }
	/**
	 * Retourne le nombre d'oeuf que le joueur actuel peux poser
	 * @return nombre d'oeuf que le joueur actuel peux poser
	 */
	public byte oeufsDispoEclo(){
		byte eclosionMaxEclo;
		if (abs(stockPlayerEclo - stockOpponentEclo) > 2)
			System.out.println("Erreur mode eclo : plus de 2 tortue d'écart entre les 2 joueurs");
		eclosionMaxEclo = (byte)(stockPlayerEclo - stockOpponentEclo + 2);
		if (eclosionMaxEclo > stockPlayerEclo)
			eclosionMaxEclo = stockPlayerEclo;
		return eclosionMaxEclo;
	}
	/**
	 * Retourne la liste des case libre pour accueillir un oeuf
	 * @return liste des case libre pour accueillir un oeuf
	 */
	public ArrayList<Byte> caseLibreEclo(){

		byte[] tCaseDepart = {};
		tCaseDepart = tStartEclo[player];
		
		ArrayList<Byte> lCaseLibreEclo = new ArrayList<Byte>();
		for( byte c : tCaseDepart){
			if (tPlateau[c] == 0){
				lCaseLibreEclo.add(c);
			}
		}
		return lCaseLibreEclo;
	}
	/**
	 * Met un oeuf su le plateau et décrémente le nombre d'oeuf disponible
	 * @param caseEclo la case ou l'on veux poser l'oeuf
	 * @throws CaseNonLibreException si la cases n'est pas libre
	 */
	public void coupEclo(byte caseEclo) throws CaseNonLibreException{
		if (tPlateau[caseEclo] > 0){
			throw new CaseNonLibreException();
		}
		stockPlayerEclo--;
		tPlateau[caseEclo] = player;
	}
	/**
	 * Retourne la liste des coup possible
	 * @return liste des coup possible
	 * @see Configuration#possibleMove(byte, byte)
	 */
	public ArrayList<byte[]> allPossibleMove(){
		
		ArrayList<byte[]> lMovePossible=new ArrayList<byte[]>();
		for (byte i=0 ; i<tPlateau.length ; i++) {
			if (tPlateau[i] == player){
				lMovePossible.addAll(possibleMove(i, (byte)0));
			}
		}
		return lMovePossible;
	}

	/**
	 * retourne un liste de tout les coups possibles pour une tortue donné
	 * où chaque coup est représenté par un tableau de byte.
	 * le premier élément du tableau est la case de départ de la tortue qui peux bouger<br>
	 * le deuxième element et le type de saut : <br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 0 - pas de saut<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 1|2 - saut par dessus une tortue de type 1|2<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 3 - saut par dessus une tortue retournée<br>
	 * le dernier élément du tableau est la case de destination

	 @param  numCase  Numéro de la case où se trouve la tortue dont on veux les mouvement possible 
	 
	 @param tortueSautee  Nombre de saut que la tortue a déjà effectué.
	 
	 @return La liste des coups possibles 
	 */
	public ArrayList<byte[]> possibleMove(byte numCase, byte tortueSautee){
		
		byte signe=0, tCaseAutour[]={-8,-9, 1};
		ArrayList<byte[]> lMovePossible = new ArrayList<byte[]>();
		if (player == 1) signe = 1;
		else if (player == 2) signe = -1;
		else System.err.println("Erreur, Configuration, methode possibleMove, pas de tortue sur tPlateau["+numCase+"]");
		byte typeCaseSuiv;
		for (byte dir : tCaseAutour){
			typeCaseSuiv = tPlateau[numCase+signe*dir];
			if (tortueSautee == 0){			// PREMIER DEPLACEMENT OU SAUT
				if (typeCaseSuiv == 0){					// DEPLACEMENT possible
					byte[] tab3={numCase, 0, (byte)(numCase+signe*dir)};
					lMovePossible.add(tab3);
				}else if ((typeCaseSuiv > 0 ) && (tPlateau[numCase+signe*2*dir] == 0)){ // SAUT possible
					byte[] tab3={numCase, typeCaseSuiv, (byte)(numCase+signe*2*dir)};
					lMovePossible.add(tab3);
				}
			}else if (tortueSautee > 0){	// APRES UN SAUT
				if ((typeCaseSuiv > 0 ) &&(tPlateau[numCase+signe*2*dir] == 0)) { // SAUT possible en théorie
					if ((typeCaseSuiv==3) || (typeCaseSuiv==tortueSautee) || tortueSautee==3) {		// SAUT possible selon règles du jeu
						byte[] tab3={numCase, typeCaseSuiv, (byte)(numCase+signe*2*dir)};
						lMovePossible.add(tab3);
					}
				}
			}
		}
		return lMovePossible;
	}
	/**
	 * retourne la liste des coup obligatoire ou si il n'y en a pas celle des coup possible
	 * @return liste des coup obligatoire ou si il n'y en a pas celle des coup possible
	 * 
	 * @see Configuration#allPossibleMove()
	 * @see Configuration#possibleMove(byte, byte)
	 */
	public ArrayList<byte[]> verifCoupObligatoire(){
		ArrayList<byte []> lMoveObligatoire = new ArrayList<byte []>();
		ArrayList<byte[]> lMovePossible = new ArrayList<byte[]>();
		lMovePossible=allPossibleMove();
		for (byte i=0; i<lMovePossible.size() ; i++){
			if (lMovePossible.get(i)[1] == opponent) {
				lMoveObligatoire.add(lMovePossible.get(i));
			}
		}if (lMoveObligatoire.isEmpty()){
			if (lMovePossible.isEmpty()) // si le joueur ne peut pas jouer, son adversaire gagne la partie
				winner = opponent;
			return lMovePossible;
		}else{ 
			return lMoveObligatoire;
		}
	}
	
	public ArrayList<byte[]> retourneCoupObligatoire() {
		ArrayList<byte[]> lMoveObligatoire = new ArrayList<byte[]>();
		ArrayList<byte[]> lMovePossible = new ArrayList<byte[]>();
		lMovePossible = allPossibleMove();
		
		for (byte i = 0; i < lMovePossible.size(); i++) {
			if (lMovePossible.get(i)[1] == opponent || lMovePossible.get(i)[1] == 3) {
				lMoveObligatoire.add(lMovePossible.get(i));
			}
		}
		return lMoveObligatoire;
	}
	
	/**
	 * Déplace une tortue en changeant des valeurs du tableau tPlateau[]

	 * @param caseDebut numéro de la case d'ou la tortue1 saute
	 * @param typeSaute type de la tortue2 APRES que la tortue1 aie sautée par dessus
	 * @param caseFin 	numéro de la case ou la tortue1 attérit 
	 */
	public void coupJoue(byte caseDebut,byte typeSaute,byte caseFin){ // la tortue se déplace
		tPlateau[caseFin]=tPlateau[caseDebut];
		tPlateau[caseDebut]=0;
		if(typeSaute>0){
			byte caseSaute=(byte)((caseDebut+caseFin) / 2);
			if(tPlateau[caseSaute]==3){
				tPlateau[caseSaute]=(byte) typeSaute;
			}
			else if(typeSaute==opponent){
				tPlateau[caseSaute]=3;
			}
		}
		winner = whoWin();
	}
	public byte getOpponent() {
		return opponent;
	}

	public void setStockPlayer(byte s) {
		stockPlayerEclo = s;
	}
	public void setStockOpponent(byte s) {
		stockOpponentEclo = s;
	}
	
	
	
	/**  	  ___
	 * Affiche sur la console le plateau dessiné en ACSII Art
	 */		
	public void printHexRV(){
		String[] rv =new String[]{" ","R","V","N"};
		System.out.println("___________________________________");
		System.out.println("|               ___               |");
		System.out.println("|           ___/ "+ rv[tPlateau[en81(3)]] + " \\___           |");
		System.out.println("|       ___/ "+ rv[tPlateau[en81(2)]] + " \\___/ " + rv[tPlateau[en81(8)]] + " \\___       |");
		System.out.println("|   ___/ "+ rv[tPlateau[en81(1)]] + " \\___/ " + rv[tPlateau[en81(7)]] + " \\___/ " + rv[tPlateau[en81(14)]] + " \\___   |");
		System.out.println("|  / "+ rv[tPlateau[en81(0)]] + " \\___/ " + rv[tPlateau[en81(6)]] + " \\___/ " + rv[tPlateau[en81(13)]] + " \\___/ "+ rv[tPlateau[en81(21)]] + " \\  |");
		System.out.println("|  \\___/ "+ rv[tPlateau[en81(5)]] + " \\___/ " + rv[tPlateau[en81(12)]] + " \\___/ " + rv[tPlateau[en81(20)]] + " \\___/  |");
		System.out.println("|  / "+ rv[tPlateau[en81(4)]] + " \\___/ " + rv[tPlateau[en81(11)]] + " \\___/ " + rv[tPlateau[en81(19)]] + " \\___/ " + rv[tPlateau[en81(27)]] + " \\  |");
		System.out.println("|  \\___/ "+ rv[tPlateau[en81(10)]] + " \\___/ " + rv[tPlateau[en81(18)]] + " \\___/ " + rv[tPlateau[en81(26)]] + " \\___/  |");
		System.out.println("|  / "+ rv[tPlateau[en81(9)]] + " \\___/ " + rv[tPlateau[en81(17)]] + " \\___/ " + rv[tPlateau[en81(25)]] + " \\___/ " + rv[tPlateau[en81(32)]] + " \\  |");
		System.out.println("|  \\___/ "+ rv[tPlateau[en81(16)]] + " \\___/ " + rv[tPlateau[en81(24)]] + " \\___/ " + rv[tPlateau[en81(31)]] + " \\___/  |");
		System.out.println("|  / "+ rv[tPlateau[en81(15)]] + " \\___/ " + rv[tPlateau[en81(23)]] + " \\___/ " + rv[tPlateau[en81(30)]] + " \\___/ " + rv[tPlateau[en81(36)]] + " \\  |");
		System.out.println("|  \\___/ "+ rv[tPlateau[en81(22)]] + " \\___/ " + rv[tPlateau[en81(29)]] + " \\___/ " + rv[tPlateau[en81(35)]] + " \\___/  |");
		System.out.println("|      \\___/ "+ rv[tPlateau[en81(28)]] + " \\___/ " + rv[tPlateau[en81(34)]] + " \\___/      |");
		System.out.println("|          \\___/ "+ rv[tPlateau[en81(33)]] + " \\___/          |");
		System.out.println("|              \\___/              |");
		System.out.println("|_________________________________|");
	}
	/**
	 * affiche sur la console tout les coups possibles
	 */
	public void printAllCoupPossible(){
		ArrayList<byte[]> lMovePossible=allPossibleMove();
		for (int i=0; i<lMovePossible.size() ; i++){  // <=> print mvt possible
			System.out.print("case init : " + lMovePossible.get(i)[0]);
			System.out.print("; type sautee : " + lMovePossible.get(i)[1]);
			System.out.println("; case finale : " + lMovePossible.get(i)[2]);
		}		
	}
	/**
	 * compare deux Configuration 
	 * @return le nombre de point de la {@link Configuration} courante moins ceux de la {@link Configuration} en paramètre
	 */
	@Override
	public int compareTo(Configuration a) {
			return -(a.eval - eval);
           
    }
}


