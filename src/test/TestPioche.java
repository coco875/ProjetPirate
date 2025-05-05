package test;

import jeu.Pioche;
import joueur.Joueur;
import joueur.Pirate;
import carte.Carte;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;

/**
 * Test des fonctionnalités de pioche et gestion de la main
 */
public class TestPioche {

	public static void main(String[] args) {
		// Initialisation du contrôleur principal du jeu
		ControlJeu controlJeu = new ControlJeu();
		controlJeu.initialiserJeu();
		
		// Création des joueurs via le contrôleur de jeu
		controlJeu.setJoueur1("Capitaine", new Pirate("Barbe Noire"));
		controlJeu.setJoueur2("Second", new Pirate("Anne Bonny"));
		
		// Récupération du contrôleur de joueur 1
		ControlJoueur controlJoueur = controlJeu.getJoueur(0);
		
		System.out.println("=== Test d'initialisation de la main ===");
		// Utilisation de piocher au lieu de initialiserMain
		for (int i = 0; i < 3; i++) {
			controlJoueur.piocher();
		}
		
		// Récupération du joueur
		Joueur joueur = controlJoueur.getJoueur();
		
		System.out.println("Nombre de cartes en main: " + joueur.getMain().size());
		System.out.println("Main du joueur après initialisation:");
		controlJoueur.afficherMain();
		
		System.out.println("\n=== Test de pioche supplémentaire ===");
		Carte cartePiochee = controlJoueur.piocher();
		System.out.println("Carte piochée: " + cartePiochee);
		System.out.println("Nombre de cartes en main après pioche: " + joueur.getMain().size());
		
		// Test de retrait de cartes
		System.out.println("\n=== Test de retrait de cartes ===");
		if (joueur.getMain().size() >= 2) {
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
		} else {
			System.out.println("Pas assez de cartes en main pour effectuer le test de retrait.");
		}
		
		// Test de jeu d'une carte (si la main n'est pas vide)
		if (!joueur.getMain().isEmpty()) {
			System.out.println("\n=== Test de jeu d'une carte ===");
			Carte carteAJouer = joueur.getMain().get(0);
			System.out.println("Carte à jouer: " + carteAJouer);
			
			// Jouer la carte si c'est possible
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