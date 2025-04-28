package jeu;

import java.util.ArrayList;
import java.util.List;

import carte.CartePopularite;

public class ZonePop {
    // Liste des cartes de popularité présentes dans la zone
    private List<CartePopularite> cartesPopularite;
    
    /**
     * @brief Constructeur de la zone de popularité
     */
    public ZonePop() {
        this.cartesPopularite = new ArrayList<>();
    }
    
    /**
     * @brief Ajoute une carte de popularité à la zone
     * @param carte La carte à ajouter
     */
    public void ajouterCarte(CartePopularite carte) {
        cartesPopularite.add(carte);
    }
    
    /**
     * @brief Retourne toutes les cartes de popularité de la zone
     * @return Liste des cartes de popularité
     */
    public List<CartePopularite> getCartesPopularite() {
        return cartesPopularite;
    }
    
    /**
     * @brief Vide la zone de popularité
     */
    public void viderZone() {
        cartesPopularite.clear();
    }
}