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
     */
    public CarteTresor(String nomCarte, String description, int orGagne) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor
        super(nomCarte, description, orGagne, true);
    }

    /**
     * Constructeur complet pour une carte de trésor avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné
     * @param cout Coût d'achat de la carte
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int cout) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor avec coût
        super(nomCarte, description, orGagne, true, cout);
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
        
        return sb.toString();
    }
}