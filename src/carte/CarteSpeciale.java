package carte;

/**
 * @brief Classe représentant une carte spéciale
 * 
 * Cette classe étend la classe CarteStrategique pour représenter spécifiquement
 * les cartes qui produisent des effets spéciaux uniques (entité uniquement - pas de logique métier).
 */
public class CarteSpeciale extends CarteStrategique {
    /** Description détaillée de l'effet spécial */
    private String effetSpecial;
    /** Indique si la carte peut être réutilisée */
    private boolean estReutilisable;

    /**
     * @brief Constructeur standard pour une carte spéciale
     */
    public CarteSpeciale(String nomCarte, String description, String effetSpecial, int valeur) {
        super(nomCarte, description, effetSpecial, valeur); 
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
    }

    /**
     * @brief Constructeur complet pour une carte spéciale
     */
    public CarteSpeciale(String nomCarte, String description, String effetSpecial, int valeur, int cout) {
        super(nomCarte, description, effetSpecial, valeur, cout);
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
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