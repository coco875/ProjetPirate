package controllers;

import java.util.List;
import java.util.ArrayList;

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
	
	public void initialiserMain() {
		for (int i=0; i<4; i++) {
			piocher();
		}
	}
	
	public List<Carte> afficherMain() {
		List<Carte> main = joueur.getMain();
		for (int i=0; i<main.size(); i++) {
			System.out.println(main.get(i));
		}
		return main;
	}
	
	public void retirerCarte(Carte carte) {
		joueur.retirerCarte(carte);
	}
	
	public void jouerCarte(Carte carte) {
		
	}
}