package controllers;

import joueur.*;
import carte.Carte;
import jeu.Jeu;


public class ControlJeu {

	private Jeu jeu;
	
	private ControlJoueur cJ1;
	private ControlJoueur cJ2;
	private ControlPioche cPioche;
	//private ControlMarche cMarche;
	private ControlCartePlateau cCartePlateau;
	
	
	public ControlJeu() {
		
	}
	
	public void initControllers() {
		this.cCartePlateau = new ControlCartePlateau(null, null);
		this.cPioche = new ControlPioche();
		
		
		this.cJ1 = new ControlJoueur(null, cCartePlateau, cPioche);
		this.cJ2 = new ControlJoueur(null, cCartePlateau, cPioche);
		cCartePlateau.setcJ1(cJ1);
		cCartePlateau.setcJ2(cJ2);

		
	}
	

	public void setJoueur1(String nom, Pirate pirate) {
		Joueur j1 = new Joueur(nom, pirate);
		cJ1.setJoueur(j1);
		
	}
	
	public void setJoueur2(String nom, Pirate pirate) {
		Joueur j2 = new Joueur(nom, pirate);
		cJ2.setJoueur(j2);
		
	}
	
	public void initialiserMainsJoueurs() {
		cJ1.initialiserMain();
		cJ2.initialiserMain();
	}
	
	public Carte piocherCarte(int joueurId) {
		switch (joueurId) {
		case 1:
			return cJ1.piocher();
		case 2:
			return cJ2.piocher();
		default:
			throw new IllegalArgumentException("Id de joueur oob");
		}
	}
}
