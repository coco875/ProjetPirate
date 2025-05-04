package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carte.Carte;

/**
 * @brief Représente la pioche de cartes du jeu
 */
public class Pioche {
    private List<Carte> cartes;
    
    /**
     * @brief Constructeur de la pioche
     */
    public Pioche() {
        cartes = new ArrayList<>();
    }
    
    /**
     * @brief Ajoute une carte à la pioche
     * @param carte Carte à ajouter
     */
    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }
    
    /**
     * @brief Mélange les cartes de la pioche
     */
    public void melanger() {
        Collections.shuffle(cartes);
    }
    
    /**
     * @brief Pioche une carte du dessus de la pioche
     * @return La carte piochée ou null si la pioche est vide
     */
    public Carte piocher() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.remove(0);
    }
    
    /**
     * @brief Vérifie si la pioche est vide
     * @return true si la pioche est vide, false sinon
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }
    
    /**
     * @brief Récupère le nombre de cartes restantes dans la pioche
     * @return Nombre de cartes
     */
    public int getNombreCartes() {
        return cartes.size();
    }
}
