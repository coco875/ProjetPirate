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
    
    public CarteOffensive(String nomCarte, String description, TypeOffensif typeOffensif, int cout) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, cout);
        this.typeOffensif = typeOffensif;
    }

    public CarteOffensive(String nomCarte, String description, TypeOffensif typeOffensif) {
        this(nomCarte, description, typeOffensif, 0);
    }
    
    // Le constructeur pour les cartes de trésor offensives a été supprimé

    // Getters et setters
    
    public TypeOffensif getTypeOffensif() {
        return typeOffensif;
    }
}