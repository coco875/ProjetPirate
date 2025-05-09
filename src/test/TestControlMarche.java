package test;

/*
import controllers.ControlMarche;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import carte.*;
import joueur.Joueur;
import joueur.Pirate;

public class TestControlMarche {

	public static void main(String[] args) throws Exception {
		System.out.println("Test de la classe ControlMarche");
		
		// Création des objets nécessaires pour le test
		ControlPioche controlPioche = new ControlPioche();
		
		// Création des pirates et joueurs
		Pirate pirate1 = new Pirate("Barbe Noire");
		Pirate pirate2 = new Pirate("Jack Sparrow");
		Joueur joueur1 = new Joueur("Joueur1", pirate1);
		Joueur joueur2 = new Joueur("Joueur2", pirate2);
		
		// Création des contrôleurs de joueurs
		ControlJoueur controlJoueur1 = new ControlJoueur(joueur1, null, controlPioche);
		ControlJoueur controlJoueur2 = new ControlJoueur(joueur2, null, controlPioche);
		
		// Création du contrôleur de plateau de cartes et mise à jour des contrôleurs de joueurs
		ControlCartePlateau controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
		controlJoueur1.setControlCartePlateau(controlCartePlateau);
		controlJoueur2.setControlCartePlateau(controlCartePlateau);
		
		// Création du contrôleur de jeu pour le passer au contrôleur de marché
		ControlJeu controlJeu = new ControlJeu();
		controlJeu.setJoueur1("Joueur1", pirate1);
		controlJeu.setJoueur2("Joueur2", pirate2);

		// Création du contrôleur de marché
		ControlMarche controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche, controlJeu); // Ajout de controlJeu
		
		// Test des méthodes de base
		System.out.println("Cartes disponibles au marché :");
		for (Carte carte : controlMarche.getCartesDisponibles()) {
			System.out.println("- " + carte.getNomCarte() + " (Coût: " + carte.getCout() + ")");
		}
		
		// Test d'achat de carte
		System.out.println("\nTest d'achat de carte :");
		int indexCarte = 0; // Première carte dans le marché
		
		// Simuler le tour du joueur 1 pour l'achat
		controlJeu.setJoueurActif(0); // Met le joueur 1 comme joueur actif
		// Assurez-vous que le joueur a assez d'or pour le test
		controlJoueur1.getJoueur().setOr(10); 

		if (controlMarche.acheterCarte(indexCarte)) { // Appel corrigé
			System.out.println("Achat réussi pour le joueur " + (controlJeu.getJoueurActif() + 1));
		} else {
			System.out.println("Échec de l'achat pour le joueur " + (controlJeu.getJoueurActif() + 1));
		}
		
		// Test de rafraîchissement du marché
		System.out.println("\nTest de rafraîchissement du marché :");
		controlMarche.rafraichirMarche();
		System.out.println("Marché rafraîchi");
		
		System.out.println("\nNouvelles cartes disponibles :");
		for (Carte carte : controlMarche.getCartesDisponibles()) {
			System.out.println("- " + carte.getNomCarte() + " (Coût: " + carte.getCout() + ")");
		}
		
		System.out.println("Tests terminés.");
	}
}*/