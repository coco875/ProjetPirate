package controllers;

import java.util.List;

import carte.Carte;
import carte.Marche;
import joueur.Joueur;

/**
 * Contrôleur gérant les interactions avec le marché
 */
public class ControlMarche {
    private Marche marche;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private ControlPioche controlPioche;
    private ControlJeu controlJeu;

    /**
     * Constructeur de ControlMarche
     */
    public ControlMarche(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2, ControlPioche controlPioche, ControlJeu controlJeu) {
        this.marche = new Marche();
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
        this.controlPioche = controlPioche;
        this.controlJeu = controlJeu;
        remplirMarcheInitial();
    }

    /**
     * Remplit initialement le marché
     */
    private void remplirMarcheInitial() {
        for (int i = 0; i < 3; i++) { // Remplir avec 3 cartes initiales
            Carte carte = controlPioche.piocher();
            if (carte != null) {
                marche.ajouterCarte(carte);
            }
        }
    }

    /**
     * Rafraîchit les cartes disponibles au marché
     */
    public void rafraichirMarche() {
        marche.getCartesDisponibles().clear();
        
        // Ajouter 3 cartes de la pioche au marché
        for (int i = 0; i < 3; i++) {
            Carte carte = controlPioche.piocher();
            marche.ajouterCarte(carte);
        }
    }

    /**
     * Obtient la liste des cartes disponibles au marché
     */
    public List<Carte> getCartesDisponibles() {
        if (marche.getCartesDisponibles().isEmpty()) {
            rafraichirMarche();
        }
        return marche.getCartesDisponibles();
    }

    /**
     * Permet à un joueur d'acheter une carte du marché
     * @param indexCarte L'index de la carte à acheter
     * @return true si l'achat est réussi, false sinon
     */
    public boolean acheterCarte(int indexCarte) {
        // Déterminer l'acheteur en fonction du joueur actif dans ControlJeu
        ControlJoueur acheteur = (controlJeu.getJoueurActif() == 0) ? controlJoueur1 : controlJoueur2;
        
        if (indexCarte < 0 || indexCarte >= marche.getCartesDisponibles().size()) {
            System.out.println("Index de carte invalide.");
            return false;
        }
        
        Carte carteAcheter = marche.getCartesDisponibles().get(indexCarte);
        Joueur joueur = acheteur.getJoueur();
        
        int cout = carteAcheter.getCout();
        
        // Vérifier si le joueur a assez d'or
        if (joueur.getOr() >= cout) {
            // Déduire le coût et ajouter la carte à la main du joueur
            joueur.setOr(joueur.getOr() - cout);
            joueur.ajouterCarte(carteAcheter);
            
            // Retirer la carte des cartes disponibles
            marche.getCartesDisponibles().remove(indexCarte);
            
            // Si le marché est vide, le rafraîchir
            if (marche.getCartesDisponibles().isEmpty()) {
                rafraichirMarche();
            }
            
            return true;
        }
        
        return false;
    }
}