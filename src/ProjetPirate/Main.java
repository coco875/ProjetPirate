package ProjetPirate;

import boundary.BoundaryJeu;
import controllers.ControlJeu;

public class Main {
    public static void main(String[] args) {
        // Initialisation du contrôleur principal
        ControlJeu controlJeu = new ControlJeu();

        // Initialisation de la frontière principale
        BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu);

        // Lancement du jeu via la frontière
        boundaryJeu.lancerJeu();
    }
}
