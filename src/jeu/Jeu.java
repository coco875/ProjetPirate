package jeu;

import joueur.Joueur;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;

/**
 * Classe principale représentant une partie du jeu des Pirates
 */
public class Jeu {
	private Pioche pioche = new Pioche();
	private Joueur joueur1;
	private Joueur joueur2;
	private ZoneOffensive zoneOffensiveJ1 = new ZoneOffensive();
	private ZoneStrategique zoneStrategiqueJ1 = new ZoneStrategique();
	private ZoneOffensive zoneOffensiveJ2 = new ZoneOffensive();
	private ZoneStrategique zoneStrategiqueJ2 = new ZoneStrategique();
	
	
	/**
	 * Initialise une partie avec deux joueurs
	 */
	public void initialiserJeu(Joueur joueur1, Joueur joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
	}
}
