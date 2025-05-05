package carte;

public class CarteActive extends Carte {
    // Classe représentant une carte active
    
    private boolean estJouee;
    
    public CarteActive(TypeCarte type, String nomCarte, String description, int valeur, int cout) {
        super(type, nomCarte, description, valeur, 0, cout); // valeurSecondaire = 0 par défaut
        this.estJouee = false;
    }
    
    public CarteActive(TypeCarte type, String nomCarte, String description, int valeur) {
        super(type, nomCarte, description, valeur, 0); // valeurSecondaire = 0 par défaut
        this.estJouee = false;
    }
    
    public CarteActive(TypeCarte type, String nomCarte, String description) {
        super(type, nomCarte, description); // Ce constructeur n'a pas besoin de valeur ni de valeurSecondaire
        this.estJouee = false;
    }
    
    public boolean estJouee() {
        return estJouee;
    }
    
    public void setEstJouee(boolean estJouee) {
        this.estJouee = estJouee;
    }
}
