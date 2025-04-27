package carte;

/**
 * @brief Classe représentant une carte passive
 * 
 * Cette classe étend la classe Carte pour représenter spécifiquement
 * les cartes qui ont des effets passifs sur plusieurs tours (entité uniquement - pas de logique métier).
 */
public class CartePassive extends Carte {
    /** Durée d'effet en nombre de tours */
    private int duree;
    /** Type d'effet passif (protection, bonus, revenu, etc.) */
    private String typeEffet;

    /**
     * @brief Constructeur standard pour une carte passive
     */
    public CartePassive(String nomCarte, String description, int valeur, int duree, String typeEffet) {
        super(TypeCarte.PASSIVE, nomCarte, description, valeur);
        this.duree = duree;
        this.typeEffet = typeEffet;
    }

    /**
     * @brief Constructeur complet pour une carte passive
     */
    public CartePassive(String nomCarte, String description, int valeur, int duree, String typeEffet, int cout) {
        super(TypeCarte.PASSIVE, nomCarte, description, valeur, cout);
        this.duree = duree;
        this.typeEffet = typeEffet;
    }

    /**
     * @brief Récupère la durée de l'effet
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @brief Définit la durée de l'effet
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * @brief Récupère le type d'effet passif
     */
    public String getTypeEffet() {
        return typeEffet;
    }

    /**
     * @brief Définit le type d'effet passif
     */
    public void setTypeEffet(String typeEffet) {
        this.typeEffet = typeEffet;
    }

    /**
     * @brief Diminue la durée de l'effet passif
     * @return Vrai si l'effet est toujours actif, faux sinon
     */
    public boolean reduireDuree() {
        duree--;
        return duree > 0;
    }
}