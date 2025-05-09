package ProjetPirate;

import joueur.Pirate; // Correction de l'import
import boundary.BoundaryJeu;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlMarche;
import controllers.ControlPioche;

public class Main {
    public static void main(String[] args) {
        // Créer les contrôleurs
        ControlJeu controlJeu = new ControlJeu();
        ControlMarche controlMarche = controlJeu.getControlMarche();
        
        // Créer et lancer l'interface console
        BoundaryJeu boundaryJeu = new BoundaryJeu(controlJeu, controlMarche);
        boundaryJeu.lancerJeu();
    }
}
