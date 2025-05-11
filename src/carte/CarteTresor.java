package carte;

/**
 * Classe représentant une carte de trésor (hérite de CarteStrategique)
 */
public class CarteTresor extends CarteStrategique {

    private int orGagne;
    
    public CarteTresor(String nomCarte, String description, String cheminImage, int cout, int orGagne) {
        super(nomCarte, description, TypeStrategique.TRESOR, cheminImage, cout);
        this.orGagne = orGagne;
    }

    public CarteTresor(String nomCarte, String description, int cout, int orGagne) {
        this(nomCarte, description, null, cout, orGagne);
    }

    public CarteTresor(String nomCarte, String description, int orGagne) {
        this(nomCarte, description, null, 10, orGagne);
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
}