package carte;

public class Ressource {
    private String nom;
    private int valeur;

    public Ressource(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public int getValeur() {
        return valeur;
    }
}