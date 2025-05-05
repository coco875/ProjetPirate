package test;

import controllers.ControlJeu;
import joueur.Pirate;

/**
 * Test de l'initialisation du contrôleur principal de jeu
 */
public class TestControlJeu {

	public static void main(String[] args) {
		// Création du contrôleur de jeu
		ControlJeu controlJeu = new ControlJeu();
		
		// Initialisation des deux pirates
		Pirate pirate1 = new Pirate("Barbe Noire");
		Pirate pirate2 = new Pirate("Anne Bonny");
		
		// Création des joueurs dans le contrôleur
		controlJeu.setJoueur1("Joueur 1", pirate1);
		controlJeu.setJoueur2("Joueur 2", pirate2);
		
		System.out.println("Test initialisation des joueurs dans ControlJeu réussi.");
	}
}