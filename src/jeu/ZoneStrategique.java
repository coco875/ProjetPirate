package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import carte.CarteStrategique;
import carte.CarteStrategique.TypeStrategique;

/**
 * Zone de jeu pour les cartes stratégiques
 */
public class ZoneStrategique {
    // Liste des cartes stratégiques présentes dans la zone
    private List<CarteStrategique> cartesStrategiques;
    
    /**
     * Constructeur de la zone stratégique
     */
    public ZoneStrategique() {
        this.cartesStrategiques = new ArrayList<>();
    }
    
    /**
     * Ajoute une carte stratégique à la zone
     */
    public void ajouterCarte(CarteStrategique carte) {
        cartesStrategiques.add(carte);
    }
    
    /**
     * Retourne toutes les cartes stratégiques de la zone
     */
    public List<CarteStrategique> getCartesStrategiques() {
        return cartesStrategiques;
    }
    
    /**
     * Vide la zone stratégique
     */
    public void viderZone() {
        cartesStrategiques.clear();
    }
    
    /**
     * Filtre les cartes par type stratégique
     * @param type Le type stratégique à filtrer
     * @return La liste des cartes du type spécifié
     */
    public List<CarteStrategique> getCartesParType(TypeStrategique type) {
        return cartesStrategiques.stream()
                .filter(carte -> carte.getTypeStrategique() == type)
                .collect(Collectors.toList());
    }
}