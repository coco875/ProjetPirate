package carte;

/**
 * @brief Classe représentant une carte de trésor
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui affectent l'or du joueur ou de l'adversaire.
 */
public class CarteTresor extends Carte {
    
    /**
     * @brief Constructeur standard pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné par le joueur
     * @param orPerdu Or perdu par le joueur
     * @param orVole Or volé à l'adversaire
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole) {
        super(TypeCarte.TRESOR, nomCarte, description, 0, 0);
        setOrGagne(orGagne);
        setOrPerdu(orPerdu);
        setOrVole(orVole);
    }
    
    /**
     * @brief Constructeur complet pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné par le joueur
     * @param orPerdu Or perdu par le joueur
     * @param orVole Or volé à l'adversaire
     * @param cout Coût d'achat de la carte
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole, int cout) {
        super(TypeCarte.TRESOR, nomCarte, description, 0, 0, cout);
        setOrGagne(orGagne);
        setOrPerdu(orPerdu);
        setOrVole(orVole);
    }
}