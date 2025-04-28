package jeu;

import java.util.ArrayList;
import java.util.List;

import carte.CarteAttaque;

public class ZoneAttaque {
    // Liste des cartes d'attaque présentes dans la zone
    private List<CarteAttaque> cartesAttaque;
    
    /**
     * @brief Constructeur de la zone d'attaque
     */
    public ZoneAttaque() {
        this.cartesAttaque = new ArrayList<>();
    }
    
    /**
     * @brief Ajoute une carte d'attaque à la zone
     * @param carte La carte à ajouter
     */
    public void ajouterCarte(CarteAttaque carte) {
        cartesAttaque.add(carte);
    }
    
    /**
     * @brief Retourne toutes les cartes d'attaque de la zone
     * @return Liste des cartes d'attaque
     */
    public List<CarteAttaque> getCartesAttaque() {
        return cartesAttaque;
    }
    
    /**
     * @brief Vide la zone d'attaque
     */
    public void viderZone() {
        cartesAttaque.clear();
    }
}