package controllers;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import joueur.*;

import carte.Carte;
import jeu.Jeu;

/**
 * @brief Contrôleur principal du jeu
 * 
 * Ce contrôleur coordonne les autres contrôleurs et implémente
 * la logique globale du jeu conformément au modèle ECB.
 */
public class ControlJeu {

	private ControlJoueur cJ1;
	private ControlJoueur cJ2;
	private ControlPioche cPioche;
	private ControlMarche cMarche;
	private ControlCartePlateau cCartePlateau;
	
	
	public ControlJeu() {
		initControllers();
	}
	
	public void initControllers() {

		this.cPioche = new ControlPioche();
		this.cJ1 = new ControlJoueur(null, null, cPioche);
		this.cJ2 = new ControlJoueur(null, null, cPioche);
		this.cCartePlateau = new ControlCartePlateau(cJ1, cJ2);
		this.cMarche = new ControlMarche(cJ1, cJ2, cPioche);
		

		// Maintenant que les contrôleurs sont créés, mettez à jour les références à ControlCartePlateau
		this.cJ1.setControlCartePlateau(cCartePlateau);
		this.cJ2.setControlCartePlateau(cCartePlateau);
	}
	
	/**
	 * @brief Initialise la main d'un joueur avec 4 cartes
	 */
	public void initialiserMainJoueur(int joueurId) {
		if (joueurId == 1) {
			cJ1.initialiserMain();
		} else if (joueurId == 2) {
			cJ2.initialiserMain();
		}
	}
	
	/**
	 * @brief Fait piocher une carte à un joueur
	 */
	public Carte piocherCarte(int joueurId) {
		if (joueurId == 1) {
			return cJ1.piocher();
		} else if (joueurId == 2) {
			return cJ2.piocher();
		}
		throw new IllegalArgumentException("Mauvais id de joueur");
	}
	
	/**
	 * @brief Défausse une carte de la main d'un joueur
	 */
	public void defausserCarte(int joueurId, int indexCarte) {
		if (joueurId == 1 && indexCarte >= 0 && indexCarte < cJ1.getJoueur().getMain().size()) {
			Carte carte = cJ1.getJoueur().getMain().get(indexCarte);
			cJ1.retirerCarte(carte);
		} else if (joueurId == 2 && indexCarte >= 0 && indexCarte < cJ2.getJoueur().getMain().size()) {
			Carte carte = cJ2.getJoueur().getMain().get(indexCarte);
			cJ2.retirerCarte(carte);
		}
	}
	
	/**
	 * @brief Obtient le contrôleur de marché
	 */
	public ControlMarche getControlMarche() {
		return cMarche;
	}


	public void setJoueur1(String nom, Pirate pirate) {
		Joueur j1 = new Joueur(nom, pirate);
		cJ1.setJoueur(j1);
		
	}
	
	public void setJoueur2(String nom, Pirate pirate) {
		Joueur j2 = new Joueur(nom, pirate);
		cJ2.setJoueur(j2);
		
	}
	
	public void initialiserMainsJoueurs() {
		cJ1.initialiserMain();
		cJ2.initialiserMain();
	}
	
			
	public void jouerTour(int joueurId) {
		if (joueurId == 1) {
			cJ1.jouerTour();
		} else if (joueurId == 2) {
			cJ2.jouerTour();
		}
	}

	public boolean verifierVictoire() {
		if (cJ1.getJoueur().getPointsDeVie() <= 0 || cJ2.getJoueur().getPopularite() >= 5) {
			System.out.println(cJ2.getJoueur().getNom() + " a gagné !");
			return true;
		} else if (cJ2.getJoueur().getPointsDeVie() <= 0 || cJ1.getJoueur().getPopularite() >= 5) {
			System.out.println(cJ1.getJoueur().getNom() + " a gagné !");
			return true;
		}
		return false;
	}

	public List<Carte> afficherMain(int joueurId) {
		if (joueurId == 1) {
			return cJ1.afficherMain();
		} else if (joueurId == 2) {
			return cJ2.afficherMain();
		}
		return new ArrayList<>();
	}

	public void jouerCarte(int joueurId, int indexCarte) {
		if (joueurId == 1) {
			Carte carte = cJ1.getJoueur().getMain().get(indexCarte);
			cJ1.jouerCarte(carte);
		} else if (joueurId == 2) {
			Carte carte = cJ2.getJoueur().getMain().get(indexCarte);
			cJ2.jouerCarte(carte);
		}
	}
	
	public Joueur getJoueur(int joueurId) {
		if (joueurId == 1) {
			return cJ1.getJoueur();
		} else {
			return cJ2.getJoueur();
		}
	}
}
