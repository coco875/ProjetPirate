package carte;

/**
 * @brief Classe représentant une carte spéciale
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui produisent des effets spéciaux uniques (entité uniquement - pas de logique métier).
 */
public class CarteSpeciale extends Carte {
    /** Description détaillée de l'effet spécial */
    private String effetSpecial;
    /** Indique si la carte peut être réutilisée */
    private boolean estReutilisable;

    /**
     * @brief Constructeur standard pour une carte spéciale
     */
    public CarteSpeciale(String nomCarte, String description, String effetSpecial, int valeur) {
        super(TypeCarte.SPECIALE, nomCarte, description, valeur);
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
    }

    /**
     * @brief Constructeur complet pour une carte spéciale
     */
    public CarteSpeciale(String nomCarte, String description, String effetSpecial, int valeur, int cout) {
        super(TypeCarte.SPECIALE, nomCarte, description, valeur, cout);
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
    }

    /**
     * @brief Récupère la description de l'effet spécial
     */
    public String getEffetSpecial() {
        return effetSpecial;
    }

    /**
     * @brief Définit la description de l'effet spécial
     */
    public void setEffetSpecial(String effetSpecial) {
        this.effetSpecial = effetSpecial;
    }

    /**
     * @brief Vérifie si la carte est réutilisable
     */
    public boolean estReutilisable() {
        return estReutilisable;
    }

    /**
     * @brief Définit si la carte est réutilisable
     */
    public void setReutilisable(boolean estReutilisable) {
        this.estReutilisable = estReutilisable;
    }
}