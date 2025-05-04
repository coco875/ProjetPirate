package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carte.Carte;

/**
 * Représente la pioche de cartes du jeu
 */
public class Pioche {
    private List<Carte> cartes;
    
    /**
     * Constructeur de la pioche
     */
    public Pioche() {
        cartes = new ArrayList<>();
    }
    
    /**
     * Ajoute une carte à la pioche
     */
    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }
    
    /**
     * Mélange les cartes de la pioche
     */
    public void melanger() {
        Collections.shuffle(cartes);
    }
    
    /**
     * Pioche une carte du dessus de la pioche
     * @return La carte piochée ou null si la pioche est vide
     */
    public Carte piocher() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.remove(0);
    }
    
    /**
     * Vérifie si la pioche est vide
     * @return true si la pioche est vide, false sinon
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }
    
    /**
     * Récupère le nombre de cartes restantes dans la pioche
     */
    public int getNombreCartes() {
        return cartes.size();
    }
}
