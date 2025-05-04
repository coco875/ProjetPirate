package jeu;

import java.util.ArrayList;
import java.util.List;

import carte.CarteStrategique;

/**
 * @brief Zone de jeu pour les cartes stratégiques
 */
public class ZoneStrategique {
    // Liste des cartes stratégiques présentes dans la zone
    private List<CarteStrategique> cartesStrategiques;
    
    /**
     * @brief Constructeur de la zone stratégique
     */
    public ZoneStrategique() {
        this.cartesStrategiques = new ArrayList<>();
    }
    
    /**
     * @brief Ajoute une carte stratégique à la zone
     * @param carte La carte à ajouter
     */
    public void ajouterCarte(CarteStrategique carte) {
        cartesStrategiques.add(carte);
    }
    
    /**
     * @brief Retourne toutes les cartes stratégiques de la zone
     * @return Liste des cartes stratégiques
     */
    public List<CarteStrategique> getCartesStrategiques() {
        return cartesStrategiques;
    }
    
    /**
     * @brief Vide la zone stratégique
     */
    public void viderZone() {
        cartesStrategiques.clear();
    }
}