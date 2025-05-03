package carte;

/**
 * @brief Classe représentant une carte d'attaque
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes d'attaque. Elle utilise:
 * - La valeur principale (valeur) pour stocker les points de dégâts infligés à l'adversaire
 * - La valeur secondaire (valeurSecondaire) pour stocker les points de dégâts subis par l'attaquant
 */
public class CarteAttaque extends Carte {
    
    /**
     * @brief Constructeur standard pour une carte d'attaque
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés à l'adversaire (valeur principale)
     * @param degatsSubis Points de dégâts subis par l'attaquant (valeur secondaire)
     */
    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degatsInfliges, degatsSubis);
    }
    
    /**
     * @brief Constructeur complet pour une carte d'attaque
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés à l'adversaire (valeur principale)
     * @param degatsSubis Points de dégâts subis par l'attaquant (valeur secondaire)
     * @param cout Coût d'achat de la carte
     */
    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis, int cout) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degatsInfliges, degatsSubis, cout);
    }
}
