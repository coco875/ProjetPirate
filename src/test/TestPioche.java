package test;

import jeu.Pioche;
import joueur.Joueur;
import joueur.Pirate;
import carte.Carte;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;

/**
 * @brief Test des fonctionnalités de pioche et gestion de la main
 * 
 * Cette classe teste les fonctionnalités de pioche et gestion de la main
 * d'un joueur après les modifications pour respecter le modèle ECB.
 */
public class TestPioche {

	public static void main(String[] args) {
		// Initialisation des contrôleurs
		ControlPioche controlPioche = new ControlPioche();
		ControlCartePlateau controlCartePlateau = null; // Sera initialisé plus tard
		
		// Création d'un joueur et de son contrôleur
		Pirate pirate = new Pirate("Barbe Noire");
		Joueur joueur = new Joueur("Capitaine", pirate);
		ControlJoueur controlJoueur = new ControlJoueur(joueur, controlCartePlateau, controlPioche);
		
		// Initialiser le contrôleur de carte plateau maintenant que le contrôleur de joueur est créé
		controlCartePlateau = new ControlCartePlateau(controlJoueur, null);
		controlJoueur.setControlCartePlateau(controlCartePlateau);
		
		System.out.println("=== Test d'initialisation de la main ===");
		controlJoueur.initialiserMain();
		
		// Vérification du nombre de cartes initial
		System.out.println("Nombre de cartes en main: " + joueur.getMain().size());
		System.out.println("Main du joueur après initialisation:");
		controlJoueur.afficherMain();
		
		// Test de pioche supplémentaire
		System.out.println("\n=== Test de pioche supplémentaire ===");
		Carte cartePiochee = controlJoueur.piocher();
		System.out.println("Carte piochée: " + cartePiochee);
		System.out.println("Nombre de cartes en main après pioche: " + joueur.getMain().size());
		
		// Test de retrait de cartes
		System.out.println("\n=== Test de retrait de cartes ===");
		Carte carte1 = joueur.getMain().get(0);
		Carte carte2 = joueur.getMain().get(1);
		
		System.out.println("Cartes à retirer:");
		System.out.println("Carte 1: " + carte1);
		System.out.println("Carte 2: " + carte2);
		
		controlJoueur.retirerCarte(carte1);
		controlJoueur.retirerCarte(carte2);
		
		System.out.println("\nMain après retrait de 2 cartes:");
		controlJoueur.afficherMain();
		System.out.println("Nombre de cartes en main après retrait: " + joueur.getMain().size());
		
		// Test de jeu d'une carte (si la main n'est pas vide)
		if (!joueur.getMain().isEmpty()) {
			System.out.println("\n=== Test de jeu d'une carte ===");
			Carte carteAJouer = joueur.getMain().get(0);
			System.out.println("Carte à jouer: " + carteAJouer);
			
			// Jouer la carte si c'est possible (nécessite que les contrôleurs soient correctement configurés)
			try {
				controlJoueur.jouerCarte(carteAJouer);
				System.out.println("Carte jouée avec succès");
			} catch (Exception e) {
				System.out.println("Impossible de jouer la carte: " + e.getMessage());
			}
			
			System.out.println("Nombre de cartes en main après jeu: " + joueur.getMain().size());
		}
		
		System.out.println("\n=== Fin des tests ===");
	}
}