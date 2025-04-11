package controllers;

import entities.Carte;
import entities.Joueur;

public class ControlJoueur {
	private Joueur joueur;
	private ControlCartePlateau cCartePlateau;
	private ControlMain cMain;

	
	public ControlJoueur(Joueur joueur, ControlCartePlateau cCartePlateau, ControlMain cMain) {
		super();
		this.joueur = joueur;
		this.cCartePlateau = cCartePlateau;
		this.cMain = cMain;
	}

	public Carte piocher() {
		Carte c = cMain.piocher();
		joueur.ajouterCarte(c);
		return c;
	}
	
	public void jouerCarte(Carte carte) {
		
	}
}
