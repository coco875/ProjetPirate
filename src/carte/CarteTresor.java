package carte;

/**
 * Classe représentant une carte de trésor (hérite de CarteStrategique)
 */
public class CarteTresor extends CarteStrategique {

    private int orVole; // Or volé à l'adversaire (non utilisé dans la version actuelle)

    /**
     * Constructeur standard pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné en jouant la carte
     * @param orPerdu Or perdu en jouant la carte
     * @param orVole Or volé à l'adversaire (non utilisé dans la version actuelle)
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor
        super(nomCarte, description, orGagne, orPerdu, true);
        this.orVole = orVole; // Stockage local pour l'orVole
        
        // Afficher un avertissement si orVole est utilisé
        if (orVole > 0) {
            System.out.println("Avertissement: L'attribut 'or_vole' est ignoré car la fonctionnalité de vol d'or a été supprimée. Carte: " + nomCarte);
        }
    }

    /**
     * Constructeur complet pour une carte de trésor avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné
     * @param orPerdu Or perdu
     * @param orVole Or volé (non utilisé dans la version actuelle)
     * @param cout Coût d'achat de la carte
     */
    public CarteTresor(String nomCarte, String description, int orGagne, int orPerdu, int orVole, int cout) {
        // Utilisation du constructeur de base de CarteStrategique pour carte trésor avec coût
        super(nomCarte, description, orGagne, orPerdu, true, cout);
        this.orVole = orVole; // Stockage local pour l'orVole
        
        // Afficher un avertissement si orVole est utilisé
        if (orVole > 0) {
            System.out.println("Avertissement: L'attribut 'or_vole' est ignoré car la fonctionnalité de vol d'or a été supprimée. Carte: " + nomCarte);
        }
    }
    
    /**
     * @return La valeur d'or volé (pour compatibilité avec le code existant)
     */
    public int getOrVole() {
        return orVole;
    }
    
    /**
     * Redéfinition de la méthode effetCarte pour inclure l'or volé
     */
    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = super.effetCarte();
        effet.orVole = this.orVole;
        return effet;
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
        if (this.orVole > 0) {
            sb.append("\nOr volé: ").append(this.orVole);
        }
        
        return sb.toString();
    }
}