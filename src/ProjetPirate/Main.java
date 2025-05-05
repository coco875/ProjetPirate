package ProjetPirate;

import joueur.Pirate; // Correction de l'import
import boundary.BoundaryJeu;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlMarche;
import controllers.ControlPioche;

public class Main {
    public static void main(String[] args) {
        // Initialisation du contrôleur principal
        ControlJeu controlJeu = new ControlJeu();
        
        // Initialisation du contrôleur de pioche
        ControlPioche controlPioche = controlJeu.getControlPioche();

        // Créer les contrôleurs de joueurs (exemple)
        controlJeu.setJoueur1("Joueur 1", new Pirate("Barbe Noire"));
        controlJeu.setJoueur2("Joueur 2", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);

        // Créer le contrôleur du marché en passant controlJeu
        ControlMarche controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche, controlJeu);

        // Initialisation de la frontière principale
        BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu, controlMarche);

        // Lancement du jeu via la frontière
        boundaryJeu.lancerJeu();
    }
}
