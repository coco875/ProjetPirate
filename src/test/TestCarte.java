package test;

import carte.*;

/**
 * Test des fonctionnalités de base des cartes
 */
public class TestCarte {

	public static void main(String[] args) throws Exception {
		// Test de création et manipulation d'une carte d'attaque
		Carte c = new CarteAttaque("d", "vd", 2, 3);
		c.setDescription("a");
		c.setNomCarte("b");
		c.getNomCarte();
		c.getType();
		c.getDescription();
		System.out.println(c.toString());
		
		// Test d'une carte popularité
		Carte d = new CartePopularite("titre", "de", 0, 1);
		System.out.println(d.toString());
		
		// Test de chargement d'une carte depuis un fichier
		Carte e = ParserCarte.lireCarte("src/ressources/cartes/templateAttaque.txt");
		System.out.println(e.toString());
	}
}