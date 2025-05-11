package carte;

/**
 * Carte offensive du jeu (attaque, soin, etc.)
 */
public abstract class CarteOffensive extends Carte {
    
    private TypeOffensif typeOffensif;
    
    /**
     * Types spécifiques de cartes offensives
     */
    public enum TypeOffensif {
        ATTAQUE,  // Attaque directe infligeant des dégâts
        SOIN              // Soin permettant de récupérer des points de vie
    }
    
    public CarteOffensive(String nomCarte, String description, TypeOffensif typeOffensif, String cheminImage, int cout) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, cheminImage, cout);
        this.typeOffensif = typeOffensif;
    }

    public CarteOffensive(String nomCarte, String description, TypeOffensif typeOffensif, int cout) {
        this(nomCarte, description, typeOffensif, null, cout);
    }

    public CarteOffensive(String nomCarte, String description, TypeOffensif typeOffensif) {
        this(nomCarte, description, typeOffensif, null, 0);
    }
    
    // Le constructeur pour les cartes de trésor offensives a été supprimé

    // Getters et setters
    
    public TypeOffensif getTypeOffensif() {
        return typeOffensif;
    }
    
    public void setTypeOffensif(TypeOffensif typeOffensif) {
        this.typeOffensif = typeOffensif;
    }
    
    public boolean estTresorOffensif() {
        return false; // Méthode conservée pour la compatibilité mais renvoie toujours false
    }
}