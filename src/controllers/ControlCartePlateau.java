package controllers;

import carte.Carte;
import carte.CarteAttaque;
import carte.CartePassive;
import carte.CarteSpeciale;
import joueur.Joueur;

/**
 * @brief Contrôleur gérant les interactions des cartes sur le plateau
 * 
 * Ce contrôleur implémente la logique métier liée aux interactions des cartes
 * conformément au modèle ECB.
 */
public class ControlCartePlateau {
	private ControlJoueur cJ1;
	private ControlJoueur cJ2;
	
	public ControlCartePlateau(ControlJoueur cJ1, ControlJoueur cJ2) {
		this.cJ1 = cJ1;
		this.cJ2 = cJ2;
	}
	
	/**
	 * @brief Gère le jeu d'une carte d'attaque
	 */
	public void jouerCarteAttaque(Carte carte, Joueur joueurActif) {
		// Trouver l'adversaire
		Joueur adversaire = (cJ1.getJoueur() == joueurActif) ? cJ2.getJoueur() : cJ1.getJoueur();
		ControlJoueur adversaireControl = (cJ1.getJoueur() == joueurActif) ? cJ2 : cJ1;
		
		// Appliquer les dégâts
		int degats = carte.getValeur();
		if (carte instanceof CarteAttaque) {
			degats = ((CarteAttaque) carte).getDegats();
		}
		
		adversaireControl.perdrePointsDeVie(degats);
		System.out.println(joueurActif.getNom() + " attaque " + adversaire.getNom() + " avec " + carte);
		System.out.println(adversaire.getNom() + " perd " + degats + " points de vie.");
	}

	/**
	 * @brief Gère le jeu d'une carte de popularité
	 */
	public void jouerCartePopularite(Carte carte, Joueur joueurActif) {
		ControlJoueur joueurControl = (cJ1.getJoueur() == joueurActif) ? cJ1 : cJ2;
		joueurControl.gagnerPopularite(carte.getValeur());
		System.out.println(joueurActif.getNom() + " gagne " + carte.getValeur() + " points de popularité avec " + carte);
	}
	
	/**
	 * @brief Gère le jeu d'une carte spéciale
	 */
	public void jouerCarteSpeciale(Carte carte, Joueur joueurActif) {
		ControlJoueur joueurControl = (cJ1.getJoueur() == joueurActif) ? cJ1 : cJ2;
		
		System.out.println(joueurActif.getNom() + " utilise la carte spéciale " + carte.getNomCarte() + "!");
		
		if (carte instanceof CarteSpeciale) {
			CarteSpeciale carteSpeciale = (CarteSpeciale) carte;
			System.out.println("Effet spécial: " + carteSpeciale.getEffetSpecial());
			
			// Exemple d'effet spécial : gain de popularité et d'or
			joueurControl.gagnerPopularite(carte.getValeur());
			joueurActif.setOr(joueurActif.getOr() + carte.getValeur());
			
			System.out.println(joueurActif.getNom() + " gagne " + carte.getValeur() + 
					" popularité et " + carte.getValeur() + " or.");
		}
	}
	
	/**
	 * @brief Gère le jeu d'une carte passive
	 */
	public void jouerCartePassive(Carte carte, Joueur joueurActif) {
		ControlJoueur joueurControl = (cJ1.getJoueur() == joueurActif) ? cJ1 : cJ2;
		
		if (carte instanceof CartePassive) {
			CartePassive cartePassive = (CartePassive) carte;
			String typeEffet = cartePassive.getTypeEffet();
			
			System.out.println(joueurActif.getNom() + " utilise la carte passive " + 
					carte.getNomCarte() + " (" + typeEffet + ")!");
			System.out.println("Effet actif pendant " + cartePassive.getDuree() + " tours.");
			
			// Appliquer un effet selon le type
			switch (typeEffet.toLowerCase()) {
				case "protection":
					System.out.println(joueurActif.getNom() + " est protégé contre les prochaines attaques.");
					// La logique de protection serait implémentée ailleurs
					break;
				case "bonus":
					System.out.println(joueurActif.getNom() + " reçoit un bonus de " + 
							carte.getValeur() + " à toutes ses actions.");
					// La logique de bonus serait implémentée ailleurs
					break;
				case "revenu":
					joueurActif.setOr(joueurActif.getOr() + carte.getValeur());
					System.out.println(joueurActif.getNom() + " gagne " + 
							carte.getValeur() + " or passif.");
					break;
				default:
					System.out.println("Effet passif appliqué.");
					break;
			}
		}
	}
}
