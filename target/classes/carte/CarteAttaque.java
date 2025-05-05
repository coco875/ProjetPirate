package carte;

/**
 * @brief Classe représentant une carte d'attaque (hérite de CarteOffensive)
 */
public class CarteAttaque extends CarteOffensive {
    
    /**
     * @brief Constructeur standard pour une carte d'attaque simple (dégâts seulement)
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degats Points de dégâts infligés
     */
    public CarteAttaque(String nomCarte, String description, int degats) {
        // Correction: Appel du constructeur de CarteOffensive pour ATTAQUE_DIRECTE
        // Dégâts subis mis à 0 par défaut.
        super(nomCarte, description, degats, 0, TypeOffensif.ATTAQUE_DIRECTE); 
    }
    
    /**
     * @brief Constructeur pour une carte d'attaque avec dégâts infligés et subis
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés
     * @param degatsSubis Points de dégâts subis par l'attaquant (anciennement popularitePerdue?)
     *                    Renommé pour clarté, correspond à valeurSecondaire dans Carte.
     */
    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis) {
         // Correction: Appel du constructeur de CarteOffensive pour ATTAQUE_DIRECTE
        super(nomCarte, description, degatsInfliges, degatsSubis, TypeOffensif.ATTAQUE_DIRECTE);
    }
    
    /**
     * @brief Constructeur pour une carte d'attaque avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés
     * @param degatsSubis Points de dégâts subis par l'attaquant
     * @param cout Coût d'achat de la carte
     */
    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis, int cout) {
        // Correction: Appel du constructeur de CarteOffensive pour ATTAQUE_DIRECTE avec coût
        super(nomCarte, description, degatsInfliges, degatsSubis, TypeOffensif.ATTAQUE_DIRECTE, cout);
    }
}
