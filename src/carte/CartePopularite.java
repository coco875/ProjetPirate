package carte;

/**
 * @brief Classe représentant une carte de popularité
 */
public class CartePopularite extends CarteStrategique {

    private int populariteGagnee;
    private int degatsSubis;
    
    public CartePopularite(String nomCarte, String description, int cout, int populariteGagnee, int degatsSubis) {
        super(nomCarte, description, TypeStrategique.POPULARITE, cout);
        this.populariteGagnee = populariteGagnee;
        this.degatsSubis = degatsSubis;
    }

    public CartePopularite(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
        this(nomCarte, description, 10, populariteGagnee, degatsSubis);
    }

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.populariteGagnee = this.populariteGagnee;
        effet.degatsSubis = this.degatsSubis;
        return effet;
    }

    @Override
    public String getCheminImage() {
        return "src/ressources/cartes/strategique/popularite/" + getNomCarte().replaceAll("\\s+", "_").toLowerCase() + ".jpg";
    }
}
