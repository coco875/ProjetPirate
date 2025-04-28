package ProjetPirate;

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
        ControlPioche controlPioche = new ControlPioche();
        
        // Initialisation des contrôleurs de joueurs
        ControlJoueur controlJoueur1 = new ControlJoueur(null, null, controlPioche);
        ControlJoueur controlJoueur2 = new ControlJoueur(null, null, controlPioche);
        
        // Initialisation du contrôleur du marché
        ControlMarche controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche);

        // Initialisation de la frontière principale
        BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu, controlMarche);

        // Lancement du jeu via la frontière
        boundaryJeu.lancerJeu();
    }
}
