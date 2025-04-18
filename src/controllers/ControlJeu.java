package controllers;

import joueur.*;

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
	
	public Joueur setJoueur1(String nomPirate, Pirate pirate) {
		
	}
	
	public Joueur setJoueur2(String nomPirate, Pirate pirate) {
		
	}
}
