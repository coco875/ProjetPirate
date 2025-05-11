package carte;

/**
 * @brief Classe représentant une carte de popularité
 */
public class CartePopularite extends CarteStrategique {

    private int populariteGagnee;
    private int degatsSubis;
    
    public CartePopularite(String nomCarte, String description, String cheminImage, int cout, int populariteGagnee, int degatsSubis) {
        super(nomCarte, description, TypeStrategique.POPULARITE, cheminImage, cout);
        this.populariteGagnee = populariteGagnee;
        this.degatsSubis = degatsSubis;
    }

    public CartePopularite(String nomCarte, String description, int cout, int populariteGagnee, int degatsSubis) {
        this(nomCarte, description, null, cout, populariteGagnee, degatsSubis);
    }

    public CartePopularite(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
        this(nomCarte, description, null, 10, populariteGagnee, degatsSubis);
    }

    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        effet.populariteGagnee = this.populariteGagnee;
        effet.degatsSubis = this.degatsSubis;
        return effet;
    }
}
