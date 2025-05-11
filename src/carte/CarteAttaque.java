package carte;

/**
 * @brief Classe représentant une carte d'attaque (hérite de CarteOffensive)
 */
public class CarteAttaque extends CarteOffensive {

    private int degatsInfliges;
    private int degatsSubis;
    
    public CarteAttaque(String nomCarte, String description, String cheminImage, int cout, int degatsInfliges, int degatsSubis) {
        super(nomCarte, description, TypeOffensif.ATTAQUE, cheminImage, cout);
        this.degatsInfliges = degatsInfliges;
        this.degatsSubis = degatsSubis;
    }

    public CarteAttaque(String nomCarte, String description, int cout, int degatsInfliges, int degatsSubis) {
        this(nomCarte, description, null, cout, degatsInfliges, degatsSubis);
    }

    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis) {
        this(nomCarte, description, null, 10, degatsInfliges, degatsSubis);
    }

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.degatsInfliges = this.degatsInfliges;
        effet.degatsSubis = this.degatsSubis;
        return effet;
    }
}
