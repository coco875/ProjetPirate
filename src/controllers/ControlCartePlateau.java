package controllers;

import

public class ControlCartePlateau {
	private Jeu jeu;
	private ControlJoueur cJ1;
	private ControlJoueur cJ2;
	
	
	public ControlCartePlateau(Jeu jeu, ControlJoueur cJ1, ControlJoueur cJ2) {
		this.jeu = jeu;
		this.cJ1 = cJ1;
		this.cJ2 = cJ2;
		
		
	}
}
