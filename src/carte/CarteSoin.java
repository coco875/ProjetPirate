package carte;

/**
 * @brief Classe représentant une carte de soin (hérite de CarteOffensive)
 */
public class CarteSoin extends CarteOffensive {

    private int vieGagne;
    
    public CarteSoin(String nomCarte, String description, int cout, int pointsDeSoin) {
        super(nomCarte, description, TypeOffensif.SOIN, cout);
        this.vieGagne = pointsDeSoin;
    }

    public CarteSoin(String nomCarte, String description, int pointsDeSoin) {
        this(nomCarte, description, 10, pointsDeSoin);
    }
    

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.vieGagne = this.vieGagne;
        return effet;
    }

    @Override
    public String getCheminImage() {
        return "src/ressources/cartes/offensive/soin/" + getNomCarte().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
    }
}
