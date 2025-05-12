package carte;

/**
 * @brief Classe représentant une carte d'attaque (hérite de CarteOffensive)
 */
public class CarteAttaque extends CarteOffensive {

    private int degatsInfliges;
    private int degatsSubis;
    
    public CarteAttaque(String nomCarte, String description, int cout, int degatsInfliges, int degatsSubis) {
        super(nomCarte, description, TypeOffensif.ATTAQUE, cout);
        this.degatsInfliges = degatsInfliges;
        this.degatsSubis = degatsSubis;
    }

    public CarteAttaque(String nomCarte, String description, int degatsInfliges, int degatsSubis) {
        this(nomCarte, description, 10, degatsInfliges, degatsSubis);
    }

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.degatsInfliges = this.degatsInfliges;
        effet.degatsSubis = this.degatsSubis;
        return effet;
    }

    @Override
    public String getCheminImage() {
        return "src/ressources/cartes/offensive/attaque/" + getNomCarte().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
    }
}
