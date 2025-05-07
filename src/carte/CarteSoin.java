package carte;

/**
 * @brief Classe représentant une carte de soin (hérite de CarteOffensive)
 */
public class CarteSoin extends CarteOffensive {
    
    /**
     * @brief Constructeur standard pour une carte de soin
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param pointsDeSoin Points de santé restaurés (stockés dans vieGagne via le constructeur de CarteOffensive)
     */
    public CarteSoin(String nomCarte, String description, int pointsDeSoin) {
        // Correction: Appel du constructeur de CarteOffensive spécifique au SOIN
        super(nomCarte, description, pointsDeSoin); 
    }
    
    /**
     * @brief Constructeur complet pour une carte de soin avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param pointsDeSoin Points de santé restaurés
     * @param cout Coût d'achat de la carte
     */
    public CarteSoin(String nomCarte, String description, int pointsDeSoin, int cout) {
        // Correction: Appel du constructeur de CarteOffensive spécifique au SOIN
        super(nomCarte, description, pointsDeSoin);
        // Définir le coût séparément car le constructeur de soin ne le prend pas
        setCout(cout); 
    }
    
    /**
     * @brief Récupère les points de soin (utilise directement la valeur)
     * @return Points de soin
     */
    public int getPointsDeSoin() {
        return getValeur(); // On utilise getValeur() au lieu de getVieGagne()
    }
}
