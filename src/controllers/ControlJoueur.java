package controllers;

import carte.Carte;
import joueur.Joueur;

public class ControlJoueur {
	private Joueur joueur;
	private ControlCartePlateau cCartePlateau;
	private ControlPioche cPioche;

	

	public ControlJoueur(Joueur joueur, ControlCartePlateau cCartePlateau, ControlPioche cPioche) {
	super();
	this.joueur = joueur;
	this.cCartePlateau = cCartePlateau;
	this.cPioche = cPioche;
}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Carte piocher() {
		Carte c = cPioche.piocher();
		joueur.ajouterCarte(c);
		return c;
	}
	
	public void jouerCarte(Carte carte) {
		
	}
}