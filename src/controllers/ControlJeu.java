package controllers;

import pirate.Pirate;
import jeu.*;

public abstract class ControlJeu {
	private Joueur joueur1, joueur2;
	
	public void setJoueur1(String nom, Pirate pirate) {
		joueur1 = new Joueur(nom, pirate);
	}
	
	public void setJoueur2(String nom, Pirate pirate) {
		joueur2 = new Joueur(nom, pirate);
	}
}
