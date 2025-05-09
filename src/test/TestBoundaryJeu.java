package test;

/*
import boundary.BoundaryJeu;
import controllers.ControlJeu;
import controllers.ControlMarche;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;
import joueur.Joueur;
import joueur.Pirate;

public class TestBoundaryJeu {

	public static void main(String[] args) throws Exception {
		System.out.println("Test de la classe BoundaryJeu");
		
		// Création des objets nécessaires pour le test
		ControlJeu controlJeu = new ControlJeu();
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
		
		// Création du contrôleur de marché
		ControlMarche controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche, controlJeu); // Ajout de controlJeu
		
		// Création de la frontière (interface utilisateur)
		BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu, controlMarche);
		
		// Test d'affichage des règles
		System.out.println("\nTest d'affichage des règles:");
		boundaryJeu.afficherRegles();
		
		// Nous ne pouvons pas tester interactivement la configuration des joueurs,
		// donc on se contentera des tests d'affichage
		System.out.println("\nLes tests complets nécessiteraient une saisie utilisateur.");
		System.out.println("Ce test vérifie uniquement que les méthodes d'affichage fonctionnent correctement.");
		
		System.out.println("Tests terminés.");
	}
}*/