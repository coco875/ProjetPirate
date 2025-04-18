package controllers;

import joueur.*;

public class ControlJeu {

import pirate.Pirate;

public class ControlJeu {

	//private Jeu jeu;
	
	private ControlJoueur cJ1;
	private ControlJoueur cJ2;
	private ControlPioche cPioche;
	//private ControlMarche cMarche;
	private ControlCartePlateau cCartePlateau;
	
	
	public ControlJeu() {
		
	}
	
	public void initControllers() {
		this.cCartePlateau = new ControlCartePlateau();
		this.cPioche = new ControlPioche();
		
		
		this.cJ1 = new ControlJoueur(null, cCartePlateau, cPioche);
		this.cJ2 = new ControlJoueur(null, cCartePlateau, cPioche);

		
	}
	
}
	public void setJoueur1(String nom, Pirate pirate) {
		
	}
	
	public void setJoueur2(String nom, Pirate pirate) {

		
	}
}
