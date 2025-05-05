package jeu;

import java.util.ArrayList;
import java.util.List;

import carte.CarteOffensive;

/**
 * Zone où les cartes offensives sont placées
 */
public class ZoneOffensive {
    private List<CarteOffensive> cartes;

    /**
     * Constructeur
     */
    public ZoneOffensive() {
        cartes = new ArrayList<>();
    }
    
    /**
     * Ajoute une carte offensive à la zone
     */
    public void ajouterCarte(CarteOffensive carte) {
        cartes.add(carte);
    }
    
    /**
     * Retourne toutes les cartes offensives de la zone
     */
    public List<CarteOffensive> getCartesOffensives() {
        return cartes;
    }
    
    /**
     * Vide la zone d'attaque
     */
    public void viderZone() {
        cartes.clear();
    }
}