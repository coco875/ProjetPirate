package carte;

/**
 * Classe représentant une carte de trésor (hérite de CarteStrategique)
 */
public class CarteTresor extends CarteStrategique {

    /**
     * Constructeur standard pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné en jouant la carte
     * @param orPerdu Or perdu en jouant la carte
     * @param orVole Paramètre ignoré (conservé pour compatibilité avec le code existant)
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor
        super(nomCarte, description, orGagne, orPerdu, true);
        // orVole est ignoré
    }

    /**
     * Constructeur complet pour une carte de trésor avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné
     * @param orPerdu Or perdu
     * @param orVole Paramètre ignoré (conservé pour compatibilité avec le code existant)
     * @param cout Coût d'achat de la carte
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole, int cout) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor avec coût
        super(nomCarte, description, orGagne, orPerdu, true, cout);
        // orVole est ignoré
    }
    
    /**
     * Redéfinition de la méthode effetCarte
     */
    @Override
    public EffetCarte effetCarte() {
        return super.effetCarte();
    }
    
    /**
     * Représentation textuelle de la carte
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNomCarte()).append("\n").append(getDescription());
        
        if (getOrGagne() > 0) {
            sb.append("\nOr gagné: ").append(getOrGagne());
        }
        if (getOrPerdu() > 0) {
            sb.append("\nOr perdu: ").append(getOrPerdu());
        }
        
        return sb.toString();
    }
}