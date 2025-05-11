package carte;

/**
 * @brief Classe représentant une carte de soin (hérite de CarteOffensive)
 */
public class CarteSoin extends CarteOffensive {

    private int vieGagne;
    
    public CarteSoin(String nomCarte, String description, String cheminImage, int cout, int pointsDeSoin) {
        super(nomCarte, description, TypeOffensif.SOIN, cheminImage, cout);
        this.vieGagne = pointsDeSoin;
    }

    public CarteSoin(String nomCarte, String description, int cout, int pointsDeSoin) {
        this(nomCarte, description, null, cout, pointsDeSoin);
    }

    public CarteSoin(String nomCarte, String description, int pointsDeSoin) {
        this(nomCarte, description, null, 10, pointsDeSoin);
    }
    

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.vieGagne = this.vieGagne;
        return effet;
    }
}
