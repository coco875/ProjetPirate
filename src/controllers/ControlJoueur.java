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
	
	public Joueur getJoueur() {
		return joueur;
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
	
	
	public void perdrePointsDeVie(int vie) {
		int newVie = joueur.getVie() - vie;
		if (newVie <= 0) {
			newVie = 0;
			//mourir? ou alors controlJeu regarde à chaque fin de tour la vie des joueurs
			//joueur.setVie(newVie);
		}
		joueur.setVie(newVie);
	}
	
	public void gagnerPopularité(int pop) {
		int newPop = joueur.getPopularite() + pop;
		if (newPop >= 5) {
			newPop = 5;
			//gagner? ou alors ControlJeu regarde à chaque fin de tour la pop des joueurs
			//joueur.setPopularite(newPop);
		}
		joueur.setPopularite(newPop);
	}
	
	public void perdrePopularité(int pop) {
		int newPop = joueur.getPopularite() - pop;
		if (newPop <= 0) {
			newPop = 0;
		}
		joueur.setPopularite(newPop);
	}
	
	public void recevoirEffets(int vie, int pop) {
		if (vie < 0) {
			perdrePointsDeVie(Math.abs(vie));
		} else {
			//gagnerPointDeVie ?
		}
		
		if (pop < 0) {
			perdrePopularité(Math.abs(pop));
		} else {
			gagnerPopularité(pop);
		}
	}
	
	public void jouerTour() {
		
	}
	
	public Carte defausser(int idCarte) {
		List<Carte> main = joueur.getMain();
		Carte c = main.get(idCarte);
		joueur.retirerCarte(c);
		return c;
	}
	
	public void gagnerOr(int or) {
		int newOr = joueur.getOr() - or;
		joueur.setOr(newOr);
	}
	
	public void perdreOr(int or) {
		int newOr = joueur.getOr() - or;
		if (newOr < 0) {
			joueur.setOr(0);
		} else {
			joueur.setOr(newOr);
		}
	}
}