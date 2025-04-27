package carte;

/**
 * @brief Classe représentant une carte de popularité
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui augmentent la popularité (entité uniquement - pas de logique métier).
 */
public class CartePopularite extends Carte {
    /** Points de popularité accordés par la carte */
    private int popularite;

    /**
     * @brief Constructeur standard pour une carte de popularité
     */
    public CartePopularite(String nomCarte, String description, int popularite, int idCarte) {
        super(TypeCarte.POPULAIRE, nomCarte, description, popularite);
        this.popularite = popularite;
    }
    
    /**
     * @brief Constructeur standard pour une carte de popularité sans ID
     */
    public CartePopularite(String nomCarte, String description, int popularite) {
        super(TypeCarte.POPULAIRE, nomCarte, description, popularite);
        this.popularite = popularite;
    }
    
    /**
     * @brief Constructeur complet pour une carte de popularité
     */
    public CartePopularite(String nomCarte, String description, int popularite, int idCarte, int cout) {
        super(TypeCarte.POPULAIRE, nomCarte, description, popularite, cout);
        this.popularite = popularite;
    }
    
    /**
     * @brief Constructeur complet pour une carte de popularité sans ID
     */
    public CartePopularite(String nomCarte, String description, int popularite, int cout) {
        super(TypeCarte.POPULAIRE, nomCarte, description, popularite, cout);
        this.popularite = popularite;
    }
    
    /**
     * @brief Récupère les points de popularité de la carte
     */
    public int getPopularite() {
        return popularite;
    }
    
    /**
     * @brief Définit les points de popularité de la carte
     */
    public void setPopularite(int popularite) {
        this.popularite = popularite;
        setValeur(popularite); // Synchroniser avec la valeur générique
    }
}
