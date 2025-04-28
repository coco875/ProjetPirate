package controllers;

import joueur.*;
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
		this.cCartePlateau = new ControlCartePlateau(cJ1, cJ1);
		this.cPioche = new ControlPioche();
		
		
		this.cJ1 = new ControlJoueur(null, cCartePlateau, cPioche);
		this.cJ2 = new ControlJoueur(null, cCartePlateau, cPioche);

		
	}
	

	public void setJoueur1(String nom, Pirate pirate) {
		Joueur j1 = new Joueur(nom, pirate);
		cJ1.setJoueur(j1);
		
	}
	
	public void setJoueur2(String nom, Pirate pirate) {
		Joueur j2 = new Joueur(nom, pirate);
		cJ2.setJoueur(j2);
		
	}
}
