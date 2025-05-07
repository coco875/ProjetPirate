package carte;

/**
 * Carte offensive du jeu (attaque, soin, etc.)
 */
public class CarteOffensive extends Carte {
    
    private TypeOffensif typeOffensif;
    private boolean estJouee;        // Indique si la carte est active ou a déjà été jouée
    private int coutSpecial;         // Coût spécial pour les cartes de type COUP_SPECIAL
    
    /**
     * Types spécifiques de cartes offensives
     */
    public enum TypeOffensif {
        ATTAQUE_DIRECTE,  // Attaque directe infligeant des dégâts
        COUP_SPECIAL,     // Attaque à effet spécial
        SOIN              // Soin permettant de récupérer des points de vie
        // TRESOR_OFFENSIF a été supprimé (fonctionnalité de vol d'or retirée)
    }
    
    /**
     * Constructeur standard
     */
    public CarteOffensive(String nomCarte, String description, int degatsInfliges, 
                          int degatsSubis, TypeOffensif typeOffensif) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, degatsInfliges, degatsSubis);
        this.typeOffensif = typeOffensif;
        this.estJouee = false;
        this.coutSpecial = 0;
    }
    
    /**
     * Constructeur avec coût
     */
    public CarteOffensive(String nomCarte, String description, int degatsInfliges, 
                          int degatsSubis, TypeOffensif typeOffensif, int cout) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, degatsInfliges, degatsSubis, cout);
        this.typeOffensif = typeOffensif;
        this.estJouee = false;
        this.coutSpecial = 0;
    }
    
    /**
     * Constructeur pour carte de soin
     */
    public CarteOffensive(String nomCarte, String description, int vieGagne) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, vieGagne, 0);
        this.typeOffensif = TypeOffensif.SOIN;
    }
    
    /**
     * Constructeur pour coup spécial
     */
    public CarteOffensive(String nomCarte, String description, int valeur, int coutSpecial) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, valeur, 0);
        this.typeOffensif = TypeOffensif.COUP_SPECIAL;
        this.coutSpecial = coutSpecial;
    }
    
    // Le constructeur pour les cartes de trésor offensives a été supprimé
    
    // Méthodes de conversion depuis les anciens types
    
    public static CarteOffensive fromCarteAttaque(CarteAttaque carteAttaque) {
        return new CarteOffensive(
            carteAttaque.getNomCarte(),
            carteAttaque.getDescription(),
            carteAttaque.getDegatsInfliges(),
            carteAttaque.getDegatsSubis(),
            TypeOffensif.ATTAQUE_DIRECTE,
            carteAttaque.getCout()
        );
    }
    
    public static CarteOffensive fromCarteSoin(CarteSoin carteSoin) {
        CarteOffensive carte = new CarteOffensive(
            carteSoin.getNomCarte(),
            carteSoin.getDescription(),
            carteSoin.getPointsDeSoin()
        );
        carte.setCout(carteSoin.getCout());
        return carte;
    }
    
    public static CarteOffensive fromCarteCoupSpecial(CarteCoupSpecial carteCoupSpecial) {
        return new CarteOffensive(
            carteCoupSpecial.getNomCarte(),
            carteCoupSpecial.getDescription(),
            carteCoupSpecial.getValeur(),
            carteCoupSpecial.getCoutSpecial()
        );
    }

    // Getters et setters
    
    public TypeOffensif getTypeOffensif() {
        return typeOffensif;
    }
    
    public void setTypeOffensif(TypeOffensif typeOffensif) {
        this.typeOffensif = typeOffensif;
    }
    
    public boolean estJouee() {
        return estJouee;
    }
    
    public void setEstJouee(boolean estJouee) {
        this.estJouee = estJouee;
    }
    
    public int getCoutSpecial() {
        return coutSpecial;
    }
    
    public void setCoutSpecial(int coutSpecial) {
        this.coutSpecial = coutSpecial;
    }
    
    // Méthodes de vérification du type
    
    public boolean estAttaqueDirecte() {
        return this.typeOffensif == TypeOffensif.ATTAQUE_DIRECTE;
    }
    
    public boolean estCoupSpecial() {
        return this.typeOffensif == TypeOffensif.COUP_SPECIAL;
    }
    
    public boolean estSoin() {
        return this.typeOffensif == TypeOffensif.SOIN;
    }
    
    public boolean estTresorOffensif() {
        return false; // Méthode conservée pour la compatibilité mais renvoie toujours false
    }
    
    // Accesseurs spécifiques selon le type
    
    public int getDegatsInfliges() {
        return getValeur();
    }
    
    public int getDegatsSubis() {
        return getValeurSecondaire();
    }

    public int getVieGagnee() {
        return (this.typeOffensif == TypeOffensif.SOIN) ? getValeur() : 0;
    }

    public int getOrVole() {
        return 0; // Méthode conservée pour la compatibilité mais renvoie toujours 0
    }

    public int getOrGagne() {
        return 0; // Par défaut, les cartes offensives ne font pas gagner d'or
    }
    
    public int getOrPerdu() {
        return 0; // Par défaut, les cartes offensives ne font pas perdre d'or
    }
    
    /**
     * Redéfinition de la méthode effetCarte pour les cartes offensives
     */
    @Override
    public EffetCarte effetCarte() {
        EffetCarte effet = new EffetCarte();
        
        switch (typeOffensif) {
            case ATTAQUE_DIRECTE:
                effet.degatsInfliges = getValeur();
                effet.degatsSubis = getValeurSecondaire();
                effet.estAttaque = true;
                break;
            case COUP_SPECIAL:
                effet.degatsInfliges = getValeur();
                effet.effetSpecial = "Coup spécial";
                effet.estSpeciale = true;
                effet.estAttaque = true;
                break;
            case SOIN:
                effet.vieGagnee = getValeur();
                effet.estSoin = true;
                break;
        }
        
        // Ajout des autres effets potentiels
        effet.orGagne = getOrGagne();
        effet.orPerdu = getOrPerdu();
        effet.orVole = getOrVole();
        
        return effet;
    }
    
    /**
     * Représentation textuelle de la carte
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNomCarte()).append("\n").append(getDescription());
        
        switch (typeOffensif) {
            case ATTAQUE_DIRECTE:
                sb.append("\nDégâts infligés: ").append(getDegatsInfliges());
                sb.append("\nDégâts subis: ").append(getDegatsSubis());
                break;
            case COUP_SPECIAL:
                sb.append("\nValeur: ").append(getValeur());
                sb.append("\nCoût spécial: ").append(getCoutSpecial());
                break;
            case SOIN:
                sb.append("\nVie gagnée: ").append(getVieGagnee());
                break;
            // Le cas TRESOR_OFFENSIF a été supprimé
        }
        
        return sb.toString();
    }
}