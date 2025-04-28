package carte;

/**
 * @brief Classe représentant une carte de coup spécial
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui permettent des coups spéciaux avec un coût particulier
 * (entité uniquement - pas de logique métier).
 */
public class CarteCoupSpecial extends CarteActive {
    /** Coût spécifique pour utiliser le coup spécial */
    private int coutSpecial;

    /**
     * @brief Constructeur pour une carte de coup spécial avec type défini
     * 
     * @param coutSpecial Coût d'utilisation du coup spécial
     * @param nomCarte Nom de la carte
     * @param idCarte Identifiant de la carte
     * @param type Type de la carte
     */
    public CarteCoupSpecial(int coutSpecial, String nomCarte, int idCarte, TypeCarte type) {
        super(type, nomCarte, "Coup Spécial: " + nomCarte, coutSpecial);
        this.coutSpecial = coutSpecial;
    }
    
    /**
     * @brief Constructeur pour une carte de coup spécial de type SPECIALE
     * 
     * @param coutSpecial Coût d'utilisation du coup spécial
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param idCarte Identifiant de la carte
     */
    public CarteCoupSpecial(int coutSpecial, String nomCarte, String description, int idCarte) {
        super(TypeCarte.SPECIALE, nomCarte, description, coutSpecial);
        this.coutSpecial = coutSpecial;
    }
    
    /**
     * @brief Récupère le coût du coup spécial
     * @return Coût du coup spécial
     */
    public int getCoutSpecial() {
        return coutSpecial;
    }
    
    /**
     * @brief Définit le coût du coup spécial
     * @param coutSpecial Nouveau coût du coup spécial
     */
    public void setCoutSpecial(int coutSpecial) {
        this.coutSpecial = coutSpecial;
        setValeur(coutSpecial); // Synchroniser avec la valeur générique
    }
    
    /**
     * @brief Récupère le nom de la carte
     * @return Nom de la carte
     */
    public String getNom() {
        return getNomCarte();
    }
}
