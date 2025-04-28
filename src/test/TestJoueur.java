package test;

import joueur.Joueur;
import joueur.Pirate;

public class TestJoueur {

	public static void main(String[] args) {
		Pirate pirate = new Pirate("Jack Sparrow");
		Joueur joueur = new Joueur("Joueur 1", pirate);
		System.out.println("Nom du joueur : " + joueur.getNom());
		System.out.println("Pirate du joueur : " + joueur.getPirate().getNom());
		// Ajoutez des tests pour les cartes et les actions du joueur
	}
}