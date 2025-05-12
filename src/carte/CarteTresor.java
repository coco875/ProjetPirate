package carte;

/**
 * Classe représentant une carte de trésor (hérite de CarteStrategique)
 */
public class CarteTresor extends CarteStrategique {

    private int orGagne;
    
    public CarteTresor(String nomCarte, String description, int cout, int orGagne) {
        super(nomCarte, description, TypeStrategique.TRESOR, cout);
        this.orGagne = orGagne;
    }

    public CarteTresor(String nomCarte, String description, int orGagne) {
        this(nomCarte, description, 10, orGagne);
    }
    
    /**
     * Redéfinition de la méthode effetCarte
     */
    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.orGagne = this.orGagne;
        return effet;
    }

    @Override
    public String getCheminImage() {
        return "src/ressources/cartes/strategique/tresor/" + getNomCarte().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
    }
}