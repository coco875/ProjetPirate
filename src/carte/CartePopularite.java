package carte;

/**
 * @brief Classe représentant une carte de popularité
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui augmentent la popularité. Elle utilise:
 * - La valeur principale (valeur) pour stocker les points de popularité gagnés
 * - La valeur secondaire (valeurSecondaire) pour stocker les points de dégâts subis par le joueur
 */
public class CartePopularite extends Carte {
    
    /**
     * @brief Constructeur standard pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (valeur principale)
     * @param degatsSubis Points de dégâts subis par le joueur (valeur secondaire)
     */
    public CartePopularite(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
        super(TypeCarte.POPULAIRE, nomCarte, description, populariteGagnee, degatsSubis);
    }
    
    /**
     * @brief Constructeur complet pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (valeur principale)
     * @param degatsSubis Points de dégâts subis par le joueur (valeur secondaire)
     * @param cout Coût d'achat de la carte
     */
    public CartePopularite(String nomCarte, String description, int populariteGagnee, int degatsSubis, int cout) {
        super(TypeCarte.POPULAIRE, nomCarte, description, populariteGagnee, degatsSubis, cout);
    }
}
