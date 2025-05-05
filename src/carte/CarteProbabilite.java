package carte;

/**
 * @brief Classe représentant une carte de probabilité (hérite de CarteStrategique, type POPULARITE)
 */
public class CarteProbabilite extends CarteStrategique {

    /**
     * @brief Constructeur pour une carte de probabilité (affecte la popularité)
     * @param nom Nom de la carte
     * @param populariteGagnee Points de popularité gagnés (valeur principale)
     * @param degatsSubis Dégâts subis en jouant la carte (valeur secondaire)
     */
    public CarteProbabilite(String nom, int populariteGagnee, int degatsSubis) {
        // Correction: Appel du constructeur de CarteStrategique pour POPULARITE
        super(nom, "Carte de probabilité", populariteGagnee, degatsSubis); 
    }
    
    // Pas besoin de constructeur avec coût si non requis par le design.
    // Si un coût est nécessaire, ajouter un constructeur similaire à ceux des autres cartes.
}