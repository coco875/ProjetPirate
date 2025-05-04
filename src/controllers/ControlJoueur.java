package controllers;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import joueur.Joueur;

/**
 * @brief Contrôleur pour gérer les joueurs
 */
public class ControlJoueur {
    private Joueur joueur;
    private ControlJeu controlJeu;
    private ControlPioche controlPioche;
    private ControlCartePlateau controlCartePlateau;
    private ControlCarteSpeciale controlCarteSpeciale;
    
    /**
     * @brief Constructeur du contrôleur
     * @param joueur Joueur à contrôler
     * @param controlJeu Contrôleur du jeu
     * @param controlPioche Contrôleur de la pioche
     */
    public ControlJoueur(Joueur joueur, ControlJeu controlJeu, ControlPioche controlPioche) {
        this.joueur = joueur;
        this.controlJeu = controlJeu;
        this.controlPioche = controlPioche;
    }
    
    /**
     * @brief Définit le contrôleur de carte plateau associé
     * @param controlCartePlateau Contrôleur de carte plateau
     */
    public void setControlCartePlateau(ControlCartePlateau controlCartePlateau) {
        this.controlCartePlateau = controlCartePlateau;
    }
    
    /**
     * @brief Définit le contrôleur de carte spéciale associé
     * @param controlCarteSpeciale Contrôleur de carte spéciale
     */
    public void setControlCarteSpeciale(ControlCarteSpeciale controlCarteSpeciale) {
        this.controlCarteSpeciale = controlCarteSpeciale;
    }
    
    /**
     * @brief Récupère le joueur contrôlé
     * @return Joueur contrôlé
     */
    public Joueur getJoueur() {
        return joueur;
    }
    
    /**
     * @brief Définit le joueur contrôlé
     * @param joueur Nouveau joueur à contrôler
     */
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    
    /**
     * @brief Initialise la main du joueur en piochant 5 cartes
     */
    public void initialiserMain() {
        for (int i = 0; i < 5; i++) {
            piocher();
        }
    }
    
    /**
     * @brief Pioche une carte pour le joueur
     * @return Carte piochée
     */
    public Carte piocher() {
        Carte carte = controlPioche.piocher();
        if (carte != null) {
            joueur.ajouterCarte(carte);
        }
        return carte;
    }
    
    /**
     * @brief Affiche la main du joueur
     * @return Liste des cartes en main
     */
    public List<Carte> afficherMain() {
        List<Carte> main = joueur.getMain();
        for (int i = 0; i < main.size(); i++) {
            System.out.println((i + 1) + ": " + main.get(i));
        }
        return main;
    }
    
    /**
     * @brief Retire une carte de la main du joueur
     * @param carte Carte à retirer
     * @return true si la carte a été retirée, false sinon
     */
    public boolean retirerCarte(Carte carte) {
        return joueur.retirerCarte(carte);
    }
    
    /**
     * @brief Joue une carte depuis la main du joueur
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
     * @brief Joue une carte spécifique depuis la main du joueur
     * @param carte Carte à jouer
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
     * @brief Défausse une carte de la main du joueur
     * @param indexCarte Index de la carte à défausser
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

    /**
     * @brief Fait perdre des points de vie au joueur
     * @param pointsARetirer Points de vie à retirer
     */
    public void perdrePV(int pointsARetirer) {
        joueur.perdrePV(pointsARetirer);
    }
    
    /**
     * @brief Alias de perdrePV pour compatibilité
     * @param pointsARetirer Points de vie à retirer
     */
    public void perdrePointsDeVie(int pointsARetirer) {
        perdrePV(pointsARetirer);
    }
    
    /**
     * @brief Fait gagner des points de vie au joueur
     * @param pointsAGagner Points de vie à gagner
     */
    public void gagnerPointsDeVie(int pointsAGagner) {
        joueur.gagnerPointsDeVie(pointsAGagner);
    }
    
    /**
     * @brief Fait perdre de la popularité au joueur
     * @param pointsARetirer Points de popularité à retirer
     */
    public void perdrePopularite(int pointsARetirer) {
        joueur.perdrePopularite(pointsARetirer);
    }
    
    /**
     * @brief Fait gagner de la popularité au joueur
     * @param pointsAGagner Points de popularité à gagner
     */
    public void gagnerPopularite(int pointsAGagner) {
        joueur.gagnerPopularite(pointsAGagner);
    }
    
    /**
     * @brief Fait perdre de l'or au joueur
     * @param montant Montant d'or à retirer
     * @return true si l'or a été retiré, false sinon (pas assez d'or)
     */
    public boolean perdreOr(int montant) {
        return joueur.perdreOr(montant);
    }
    
    /**
     * @brief Fait gagner de l'or au joueur
     * @param montant Montant d'or à gagner
     */
    public void gagnerOr(int montant) {
        joueur.gagnerOr(montant);
    }
    
    /**
     * @brief Applique les effets des cartes de l'adversaire sur ce joueur
     * @param cartesAdversaire Liste des cartes de l'adversaire
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
     * @brief Version surchargée de recevoirEffets pour rétrocompatibilité
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
     * @brief Simule un tour complet du joueur
     */
    public void jouerTour() {
        // Piocher une carte
        piocher();
        
        // Jouer une carte (première carte de la main pour simplifier)
        if (!joueur.getMain().isEmpty()) {
            jouerCarte(0);
        }
    }
    
    /**
     * @brief Retourne le nom du joueur
     * @return Nom du joueur
     */
    public String getNom() {
        return joueur.getNom();
    }
    
    /**
     * @brief Retourne les points de vie du joueur
     * @return Points de vie
     */
    public int getPointsDeVie() {
        return joueur.getPointsDeVie();
    }
    
    /**
     * @brief Retourne la popularité du joueur
     * @return Popularité
     */
    public int getPopularite() {
        return joueur.getPopularite();
    }
    
    /**
     * @brief Retourne l'or du joueur
     * @return Montant d'or
     */
    public int getOr() {
        return joueur.getOr();
    }
    
    /**
     * @brief Retourne le pirate du joueur
     * @return Pirate
     */
    public joueur.Pirate getPirate() {
        return joueur.getPirate();
    }
    
    /**
     * @brief Retourne la main du joueur
     * @return Liste des cartes en main
     */
    public List<Carte> getMain() {
        return joueur.getMain();
    }
}