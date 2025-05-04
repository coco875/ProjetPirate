package carte;

/**
 * @brief Classe représentant une carte passive
 * 
 * Cette classe étend la classe CarteStrategique pour représenter spécifiquement
 * les cartes qui ont des effets passifs sur plusieurs tours (entité uniquement - pas de logique métier).
 */
public class CartePassive extends CarteStrategique {

    /**
     * Constructeur pour une carte passive.
     * @param nomCarte Nom de la carte.
     * @param description Description de la carte.
     * @param valeur Valeur de l'effet passif.
     * @param duree Durée de l'effet en nombre de tours.
     * @param typeEffet Description du type d'effet passif.
     */
    public CartePassive(String nomCarte, String description, int valeur, int duree, String typeEffet) {
        // Appel au constructeur de CarteStrategique pour les cartes passives
        super(nomCarte, description, valeur, duree, typeEffet); 
    }

    /**
     * Constructeur pour une carte passive avec coût.
     * @param nomCarte Nom de la carte.
     * @param description Description de la carte.
     * @param valeur Valeur de l'effet passif.
     * @param duree Durée de l'effet en nombre de tours.
     * @param typeEffet Description du type d'effet passif.
     * @param cout Coût d'achat de la carte.
     */
    public CartePassive(String nomCarte, String description, int valeur, int duree, String typeEffet, int cout) {
         // Appel au constructeur de CarteStrategique pour les cartes passives avec coût
        super(nomCarte, description, valeur, duree, typeEffet, cout);
    }

    /**
     * @brief Diminue la durée de l'effet passif
     * @return Vrai si l'effet est toujours actif, faux sinon
     */
    public boolean reduireDuree() {
        int duree = getDuree();
        duree--;
        setDuree(duree);
        return duree > 0;
    }
}