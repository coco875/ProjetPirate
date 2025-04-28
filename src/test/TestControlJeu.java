package test;

import controllers.ControlJeu;
import joueur.Pirate;

public class TestControlJeu {

	public static void main(String[] args) {
		ControlJeu controlJeu = new ControlJeu();
		Pirate pirate1 = new Pirate("Barbe Noire");
		Pirate pirate2 = new Pirate("Anne Bonny");
		controlJeu.setJoueur1("Joueur 1", pirate1);
		controlJeu.setJoueur2("Joueur 2", pirate2);
		System.out.println("Test initialisation des joueurs dans ControlJeu réussi.");
	}
}