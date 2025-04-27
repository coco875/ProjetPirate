package carte;

public class CarteProbabilite extends Carte {

    private double probabilite;

    public CarteProbabilite(String nom, double probabilite) {
        super(TypeCarte.POPULAIRE, nom, "Carte de probabilité", 0, 5);
        this.probabilite = probabilite;
    }

    public double getProbabilite() {
        return probabilite;
    }

    public void afficherCarte() {
        System.out.println("Carte Probabilité: " + getNomCarte() + ", Probabilité: " + probabilite);
    }
}