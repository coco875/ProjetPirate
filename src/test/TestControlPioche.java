package test;

import controllers.ControlPioche;
import carte.*;

public class TestControlPioche {

	public static void main(String[] args) throws Exception {
		System.out.println("Test de la classe ControlPioche");
		
		// Création des objets nécessaires pour le test
		ControlPioche controlPioche = new ControlPioche();
		
		// Test de la méthode piocher
		System.out.println("Test de pioche de carte :");
		Carte cartePiochee = controlPioche.piocher();
		
		if (cartePiochee != null) {
			System.out.println("Carte piochée : " + cartePiochee.getNomCarte());
			System.out.println("Type : " + cartePiochee.getType());
			System.out.println("Description : " + cartePiochee.getDescription());
		} else {
			System.out.println("Aucune carte n'a été piochée (pioche vide)");
		}
		
		// Test de pioche multiple
		System.out.println("\nTest de pioches multiples :");
		for (int i = 0; i < 3; i++) {
			Carte carte = controlPioche.piocher();
			if (carte != null) {
				System.out.println("Carte " + (i+1) + " : " + carte.getNomCarte());
			} else {
				System.out.println("Plus de cartes disponibles");
				break;
			}
		}
		
		System.out.println("Tests terminés.");
	}
}