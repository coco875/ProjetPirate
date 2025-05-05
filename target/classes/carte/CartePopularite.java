package carte;

/**
 * @brief Classe représentant une carte de popularité
 */
public class CartePopularite extends CarteStrategique {
    
    /**
     * @brief Constructeur standard pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (stockés dans valeurPrincipale)
     * @param degatsSubis Points de dégâts subis par le joueur (stockés dans valeurSecondaire)
     */
    public CartePopularite(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
        super(nomCarte, description, populariteGagnee, degatsSubis);
        setTypeStrategique(TypeStrategique.POPULARITE);
    }
    
    /**
     * @brief Constructeur complet pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (stockés dans valeurPrincipale)
     * @param degatsSubis Points de dégâts subis par le joueur (stockés dans valeurSecondaire)
     * @param cout Coût d'achat de la carte
     */
    public CartePopularite(String nomCarte, String description, int populariteGagnee, 
                          int degatsSubis, int cout) {
        super(nomCarte, description, populariteGagnee, degatsSubis, cout);
        setTypeStrategique(TypeStrategique.POPULARITE);
    }
}
