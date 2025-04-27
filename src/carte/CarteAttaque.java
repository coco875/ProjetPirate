package carte;

/**
 * @brief Classe représentant une carte d'attaque
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes d'attaque (entité uniquement - pas de logique métier).
 */
public class CarteAttaque extends Carte {
    /** Points de dégâts infligés par la carte */
    private int degats;

    /**
     * @brief Constructeur standard pour une carte d'attaque
     */
    public CarteAttaque(String nomCarte, String description, int degats, int idCarte) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degats);
        this.degats = degats;
    }
    
    /**
     * @brief Constructeur standard pour une carte d'attaque sans ID
     */
    public CarteAttaque(String nomCarte, String description, int degats) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degats);
        this.degats = degats;
    }
    
    /**
     * @brief Constructeur complet pour une carte d'attaque
     */
    public CarteAttaque(String nomCarte, String description, int degats, int idCarte, int cout) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degats, cout);
        this.degats = degats;
    }
    
    /**
     * @brief Constructeur complet pour une carte d'attaque sans ID
     */
    public CarteAttaque(String nomCarte, String description, int degats, int cout) {
        super(TypeCarte.ATTAQUE, nomCarte, description, degats, cout);
        this.degats = degats;
    }
    
    /**
     * @brief Récupère les points de dégâts de la carte
     */
    public int getDegats() {
        return degats;
    }
    
    /**
     * @brief Définit les points de dégâts de la carte
     */
    public void setDegats(int degats) {
        this.degats = degats;
        setValeur(degats); // Synchroniser avec la valeur générique
    }
}
