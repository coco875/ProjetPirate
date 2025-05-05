package carte;

/**
 * @brief Classe représentant une carte coup spécial (hérite de CarteOffensive)
 */
public class CarteCoupSpecial extends CarteOffensive {
    
    /**
     * @brief Constructeur pour une carte coup spécial
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param valeur Valeur de l'effet (peut être dégâts, soin, etc. selon l'implémentation)
     * @param coutSpecial Coût en or pour activer l'effet spécial
     */
    public CarteCoupSpecial(String nomCarte, String description, int valeur, int coutSpecial) {
        // Correction: Appel du constructeur approprié de CarteOffensive pour COUP_SPECIAL
        super(nomCarte, description, valeur, coutSpecial); 
    }

    // Les méthodes get/set spécifiques à CarteCoupSpecial peuvent être ajoutées ici si nécessaire,
    // mais la plupart des fonctionnalités sont héritées de CarteOffensive.
    // Par exemple, getCoutSpecial() est déjà dans CarteOffensive.
}
