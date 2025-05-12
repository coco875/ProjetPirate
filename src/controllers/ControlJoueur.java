package controllers;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import carte.TypeCarte;
import carte.Carte.EffetCarte;
import joueur.Joueur;

/**
 * Contrôleur pour gérer les joueurs
 */
public class ControlJoueur {
    private Joueur joueur;
    private ControlPioche controlPioche;
    private ControlZoneJoueur controlZoneJoueur;
    
    /**
     * Constructeur du contrôleur
     */
    public ControlJoueur(Joueur joueur, ControlPioche controlPioche, ControlZoneJoueur controlZoneJoueur) {
        this.joueur = joueur;
        this.controlPioche = controlPioche;
        this.controlZoneJoueur = controlZoneJoueur;
    }
    
    /**
     * Récupère le joueur contrôlé
     */
    public Joueur getJoueur() {
        return joueur;
    }
    
    /**
     * Définit le joueur contrôlé
     */
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    
    /**
     * Initialise la main du joueur en piochant 5 cartes
     */
    public void initialiserMain() {
        Carte carte = joueur.getCarteSpecial();
        joueur.ajouterCarte(carte);
        for (int i = 0; i < 5; i++) {
            piocher();
        }
    }
    
    /**
     * Pioche une carte pour le joueur
     */
    public Carte piocher() {
        Carte carte = controlPioche.piocher();
        if (carte != null) {
            joueur.ajouterCarte(carte);
        }
        return carte;
    }
    
    /**
     * Affiche la main du joueur
     */
    public List<Carte> afficherMain() {
        List<Carte> main = joueur.getMain();
        for (int i = 0; i < main.size(); i++) {
            System.out.println((i + 1) + ": " + main.get(i));
        }
        return main;
    }
    
    /**
     * Retire une carte de la main du joueur
     * @return true si la carte a été retirée, false sinon
     */
    public boolean retirerCarte(Carte carte) {
        return joueur.retirerCarte(carte);
    }
    
    /**
     * Joue une carte depuis la main du joueur
     * @param indexCarte Index de la carte à jouer
     * @return true si la carte a été jouée, false sinon
     */
    public boolean jouerCarte(int indexCarte) {
        if (indexCarte < 0 || indexCarte >= joueur.getMain().size()) {
            return false;
        }
        
        Carte carte = joueur.getMain().get(indexCarte);

        // Correction dans la méthode jouerCarte pour s'assurer que les cartes sont bien ajoutées aux zones
        if (carte.getType() == TypeCarte.OFFENSIVE) {
            controlZoneJoueur.ajouterCarteOffensive((CarteOffensive) carte);
            joueur.retirerCarte(carte);
            return true;
        } else if (carte.getType() == TypeCarte.STRATEGIQUE) {
            controlZoneJoueur.ajouterCarteStrategique((CarteStrategique) carte);
            joueur.retirerCarte(carte);
            return true;
        } else {
            // Type de carte non reconnu
            return false;
        }
    }
    
    /**
     * Joue une carte spécifique depuis la main du joueur
     * @return true si la carte a été jouée, false sinon
     */
    public boolean jouerCarte(Carte carte) {
        int index = joueur.getMain().indexOf(carte);
        if (index == -1) {
            return false;
        }
        return jouerCarte(index);
    }
    
    /**
     * Défausse une carte de la main du joueur
     * @return true si la carte a été défaussée, false sinon
     */
    public boolean defausserCarte(int indexCarte) {
        if (indexCarte < 0 || indexCarte >= joueur.getMain().size()) {
            return false;
        }
        
        Carte carte = joueur.getMain().get(indexCarte);
        
        return joueur.retirerCarte(carte);
    }

    // Méthodes affectant les attributs du joueur
    
    /**
     * Fait perdre des points de vie au joueur
     */
    public void perdrePointsDeVie(int pointsARetirer) {
        joueur.perdrePointsDeVie(pointsARetirer);
    }
    
    /**
     * Fait gagner des points de vie au joueur
     */
    public void gagnerPointsDeVie(int pointsAGagner) {
        joueur.gagnerPointsDeVie(pointsAGagner);
    }
    
    /**
     * Fait perdre de la popularité au joueur
     */
    public void perdrePopularite(int pointsARetirer) {
        joueur.perdrePopularite(pointsARetirer);
    }
    
    /**
     * Fait gagner de la popularité au joueur
     */
    public void gagnerPopularite(int pointsAGagner) {
        joueur.gagnerPopularite(pointsAGagner);
    }
    
    /**
     * Fait perdre de l'or au joueur
     * @return true si l'or a été retiré, false sinon (pas assez d'or)
     */
    public boolean perdreOr(int montant) {
        return joueur.perdreOr(montant);
    }
    
    /**
     * Fait gagner de l'or au joueur
     */
    public void gagnerOr(int montant) {
        joueur.gagnerOr(montant);
    }
    
    // Getters
    
    public String getNom() {
        return joueur.getNom();
    }
    
    public int getPointsDeVie() {
        return joueur.getPointsDeVie();
    }
    
    public int getPopularite() {
        return joueur.getPopularite();
    }
    
    public int getOr() {
        return joueur.getOr();
    }
    
    public joueur.Pirate getPirate() {
        return joueur.getPirate();
    }
    
    public List<Carte> getMain() {
        return joueur.getMain();
    }
}