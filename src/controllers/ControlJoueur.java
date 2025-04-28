package controllers;

import java.util.List;

import carte.Carte;
import joueur.Joueur;

/**
 * @brief Contrôleur gérant les actions liées au joueur
 * 
 * Ce contrôleur implémente la logique métier liée aux joueurs
 * conformément au modèle ECB.
 */
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
	
	/**
	 * @brief Fait perdre des points de vie au joueur
	 * 
	 * @param points Points de vie à perdre
	 */
	public void perdrePointsDeVie(int points) {
		Integer vie = joueur.getPointsDeVie() - points;
		if (vie < 0) {
			vie = 0;
		}
		joueur.setVie(vie);
	}
	
	/**
	 * @brief Fait gagner des points de popularité au joueur
	 * 
	 * @param points Points de popularité à gagner
	 */
	public void gagnerPopularite(int points) {
		Integer popularite = joueur.getPopularite() + points;
		if (popularite > 5) {
			popularite = 5;
		}
		joueur.setPopularite(popularite);
	}
	
	/**
	 * @brief Applique des effets sur les points de vie et de popularité
	 * 
	 * @param vie Modification des points de vie (positif = gain, négatif = perte)
	 * @param pop Modification des points de popularité (positif = gain, négatif = perte)
	 */
	public void recevoirEffets(int vie, int pop) {
		if (vie < 0) {
			perdrePointsDeVie(-vie);
		} else {
			joueur.setVie(joueur.getPointsDeVie() + vie);
		}
		
		if (pop > 0) {
			gagnerPopularite(pop);
		} else {
			Integer popularite = joueur.getPopularite() + pop;
			if (popularite < 0) {
				popularite = 0;
			}
			joueur.setPopularite(popularite);
		}
	}
	
	public void jouerCarte(Carte carte) {
		if (carte.estCarteAttaque()) {
			cCartePlateau.jouerCarteAttaque(carte, joueur);
		} else if (carte.estCartePopularite()) {
			cCartePlateau.jouerCartePopularite(carte, joueur);
		} else if (carte.estCarteSpeciale()) {
			// Déléguer au contrôleur spécifique pour les cartes spéciales
			cCartePlateau.jouerCarteSpeciale(carte, joueur);
		} else if (carte.estCartePassive()) {
			// Déléguer au contrôleur spécifique pour les cartes passives
			cCartePlateau.jouerCartePassive(carte, joueur);
		}
		retirerCarte(carte);
	}

	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void jouerTour() {
		// Le joueur pioche une carte
		piocher();
		
		// Simuler le joueur qui joue une carte (pour les tests)
		List<Carte> main = joueur.getMain();
		if (!main.isEmpty()) {
			Carte carteAJouer = main.get(0); // Prend la première carte de la main
			jouerCarte(carteAJouer);
		}
		
		// S'assurer que le joueur a maximum 4 cartes en main
		while (joueur.getMain().size() > 4) {
			joueur.retirerCarte(joueur.getMain().get(0));
		}
	}

	public void setControlCartePlateau(ControlCartePlateau controlCartePlateau) {
		this.cCartePlateau = controlCartePlateau;
	}
}