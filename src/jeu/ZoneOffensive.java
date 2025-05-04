package jeu;

import java.util.ArrayList;
import java.util.List;

import carte.CarteOffensive;

/**
 * @brief Zone où les cartes offensives sont placées
 */
public class ZoneOffensive { // Renamed from ZoneAttaque
    private List<CarteOffensive> cartes;

    /**
     * @brief Constructeur
     */
    public ZoneOffensive() { // Renamed from ZoneAttaque
        cartes = new ArrayList<>();
    }
    
    /**
     * @brief Ajoute une carte offensive à la zone
     * @param carte La carte à ajouter
     */
    public void ajouterCarte(CarteOffensive carte) {
        cartes.add(carte);
    }
    
    /**
     * @brief Retourne toutes les cartes offensives de la zone
     * @return Liste des cartes offensives
     */
    public List<CarteOffensive> getCartesOffensives() {
        return cartes;
    }
    
    /**
     * @brief Vide la zone d'attaque
     */
    public void viderZone() {
        cartes.clear();
    }
}