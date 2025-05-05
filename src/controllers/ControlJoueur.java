package controllers;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import joueur.Joueur;

/**
 * Contrôleur pour gérer les joueurs
 */
public class ControlJoueur {
    private Joueur joueur;
    private ControlJeu controlJeu;
    private ControlPioche controlPioche;
    private ControlCartePlateau controlCartePlateau;
    private ControlCarteSpeciale controlCarteSpeciale;
    
    /**
     * Constructeur du contrôleur
     */
    public ControlJoueur(Joueur joueur, ControlJeu controlJeu, ControlPioche controlPioche) {
        this.joueur = joueur;
        this.controlJeu = controlJeu;
        this.controlPioche = controlPioche;
    }
    
    /**
     * Définit le contrôleur de carte plateau associé
     */
    public void setControlCartePlateau(ControlCartePlateau controlCartePlateau) {
        this.controlCartePlateau = controlCartePlateau;
    }
    
    /**
     * Définit le contrôleur de carte spéciale associé
     */
    public void setControlCarteSpeciale(ControlCarteSpeciale controlCarteSpeciale) {
        this.controlCarteSpeciale = controlCarteSpeciale;
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
        
        // Détermine si ce contrôleur gère le joueur 1 ou 2
        boolean estJoueur1 = (this == controlJeu.getJoueur(0));

        if (carte instanceof CarteOffensive) {
            // Jouer une carte offensive
            CarteOffensive carteOff = (CarteOffensive) carte;
            if (estJoueur1) {
                controlCartePlateau.ajouterCarteOffensiveJ1(carteOff);
            } else {
                controlCartePlateau.ajouterCarteOffensiveJ2(carteOff);
            }
            joueur.retirerCarte(carte);
            return true;
        } else if (carte instanceof CarteStrategique) {
            // Jouer une carte stratégique
            CarteStrategique carteStrat = (CarteStrategique) carte;
            if (estJoueur1) {
                controlCartePlateau.ajouterCarteStrategiqueJ1(carteStrat);
            } else {
                controlCartePlateau.ajouterCarteStrategiqueJ2(carteStrat);
            }
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
        
        // Retirer la carte de la main
        boolean retiree = joueur.retirerCarte(carte);
        
        if (retiree && controlCartePlateau != null) {
            // Ajouter la carte à la défausse
            controlCartePlateau.getDefausse().ajouterCarte(carte);
            return true;
        }
        
        return false; // La carte n'a pas pu être retirée ou la défausse n'est pas accessible
    }

    // Méthodes affectant les attributs du joueur
    
    /**
     * Fait perdre des points de vie au joueur
     */
    public void perdrePV(int pointsARetirer) {
        joueur.perdrePV(pointsARetirer);
    }
    
    /**
     * Alias de perdrePV pour compatibilité
     */
    public void perdrePointsDeVie(int pointsARetirer) {
        perdrePV(pointsARetirer);
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
    
    /**
     * Applique les effets des cartes de l'adversaire sur ce joueur
     */
    public void recevoirEffets(List<Carte> cartesAdversaire) {
        for (Carte carte : cartesAdversaire) {
            if (carte instanceof CarteOffensive) {
                CarteOffensive carteOff = (CarteOffensive) carte;
                
                if (carteOff.estAttaqueDirecte()) {
                    // Recevoir des dégâts d'une carte d'attaque
                    perdrePV(carteOff.getDegatsInfliges());
                }
            }
        }
    }
    
    /**
     * Version surchargée de recevoirEffets pour rétrocompatibilité
     * @param degats Dégâts à infliger
     * @param popularite Popularité à modifier (positif = gain, négatif = perte)
     */
    public void recevoirEffets(int degats, int popularite) {
        if (degats > 0) {
            perdrePV(degats);
        } else if (degats < 0) {
            gagnerPointsDeVie(-degats);
        }
        
        if (popularite > 0) {
            gagnerPopularite(popularite);
        } else if (popularite < 0) {
            perdrePopularite(-popularite);
        }
    }
    
    /**
     * Simule un tour complet du joueur
     */
    public void jouerTour() {
        // Piocher une carte
        piocher();
        
        // Jouer une carte (première carte de la main pour simplifier)
        if (!joueur.getMain().isEmpty()) {
            jouerCarte(0);
        }
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