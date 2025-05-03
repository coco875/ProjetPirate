package carte;

/**
 * @brief Classe représentant une carte de soin
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui permettent au joueur de regagner des points de vie.
 */
public class CarteSoin extends Carte {
    
    /**
     * @brief Constructeur standard pour une carte de soin
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param vieGagne Points de vie gagnés
     */
    public CarteSoin(String nomCarte, String description, int vieGagne) {
        super(TypeCarte.SOIN, nomCarte, description, 0, 0);
        setVieGagne(vieGagne);
    }
    
    /**
     * @brief Constructeur complet pour une carte de soin
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param vieGagne Points de vie gagnés
     * @param cout Coût d'achat de la carte
     */
    public CarteSoin(String nomCarte, String description, int vieGagne, int cout) {
        super(TypeCarte.SOIN, nomCarte, description, 0, 0, cout);
        setVieGagne(vieGagne);
    }
}
