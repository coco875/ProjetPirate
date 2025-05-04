package jeu;

import joueur.Joueur;
import jeu.ZoneOffensive; // Renamed from ZoneAttaque
import jeu.ZoneStrategique;

public class Jeu {
	private Pioche pioche = new Pioche(); // Corrected constructor call
	private Joueur joueur1;
	private Joueur joueur2;
	private ZoneOffensive zoneOffensiveJ1 = new ZoneOffensive(); // Renamed from ZoneAttaque
	private ZoneStrategique zoneStrategiqueJ1 = new ZoneStrategique();
	private ZoneOffensive zoneOffensiveJ2 = new ZoneOffensive(); // Renamed from ZoneAttaque
	private ZoneStrategique zoneStrategiqueJ2 = new ZoneStrategique();
	
	
	public void initialiserJeu(Joueur joueur1, Joueur joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		
	}
}
