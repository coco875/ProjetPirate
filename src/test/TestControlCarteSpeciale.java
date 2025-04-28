package test;

import controllers.ControlCarteSpeciale;
import carte.*;

public class TestControlCarteSpeciale {

	public static void main(String[] args) throws Exception {
		System.out.println("Test de la classe ControlCarteSpeciale");
		
		// Création des objets nécessaires pour le test
		ControlCarteSpeciale controlCarteSpeciale = new ControlCarteSpeciale();
		
		// Test des méthodes de base
		// Création d'une carte spéciale - le constructeur prend (cout, nom, description, id)
		CarteCoupSpecial carteSpeciale = new CarteCoupSpecial(3, "Coup Spécial Test", "Description test", 1);
		
		// Test d'activation d'une carte spéciale
		System.out.println("Carte créée: " + carteSpeciale.getNomCarte());
		System.out.println("Coût spécial: " + carteSpeciale.getCoutSpecial());
		
		// Activation de la carte
		controlCarteSpeciale.activerCarteSpeciale(carteSpeciale);
		
		System.out.println("Tests terminés.");
	}
}