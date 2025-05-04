package carte;

/**
 * @brief Classe représentant une carte de trésor (hérite de CarteStrategique)
 */
public class CarteTresor extends CarteStrategique {

    /**
     * @brief Constructeur standard pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné en jouant la carte
     * @param orPerdu Or perdu en jouant la carte
     * @param orVole Or volé à l'adversaire
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole) {
        // Correction: Appel du constructeur approprié de CarteStrategique pour TRESOR
        super(nomCarte, description, orGagne, orPerdu, orVole); 
    }

    /**
     * @brief Constructeur complet pour une carte de trésor avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné
     * @param orPerdu Or perdu
     * @param orVole Or volé
     * @param cout Coût d'achat de la carte
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole, int cout) {
         // Correction: Appel du constructeur approprié de CarteStrategique pour TRESOR
        super(nomCarte, description, orGagne, orPerdu, orVole);
        // Définir le coût séparément
        setCout(cout);
    }
    
    // Les méthodes getOrGagne, getOrPerdu, getOrVole sont héritées de Carte.
}