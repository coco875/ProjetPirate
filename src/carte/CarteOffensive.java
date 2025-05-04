package carte;

/**
 * @brief Classe représentant une carte offensive
 * 
 * Cette classe représente toutes les cartes qui peuvent être placées dans la zone d'attaque.
 * Elle fusionne les fonctionnalités des anciennes cartes d'attaque, de soin et autres cartes offensives.
 */
public class CarteOffensive extends Carte {
    
    /** Type spécifique de carte offensive */
    private TypeOffensif typeOffensif;
    /** Indique si la carte est active ou a déjà été jouée */
    private boolean estJouee;
    /** Coût spécial pour les cartes de type COUP_SPECIAL */
    private int coutSpecial;
    
    /**
     * @brief Types spécifiques de cartes offensives
     */
    public enum TypeOffensif {
        ATTAQUE_DIRECTE,  // Attaque directe infligeant des dégâts
        COUP_SPECIAL,     // Attaque à effet spécial
        SOIN,             // Soin permettant de récupérer des points de vie
        TRESOR_OFFENSIF   // Carte de trésor à effet offensif (vol d'or)
    }
    
    /**
     * @brief Constructeur standard pour une carte offensive
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés à l'adversaire (valeur principale)
     * @param degatsSubis Points de dégâts subis par l'attaquant (valeur secondaire)
     * @param typeOffensif Type spécifique de carte offensive
     */
    public CarteOffensive(String nomCarte, String description, int degatsInfliges, 
                          int degatsSubis, TypeOffensif typeOffensif) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, degatsInfliges, degatsSubis);
        this.typeOffensif = typeOffensif;
        this.estJouee = false;
        this.coutSpecial = 0;
    }
    
    /**
     * @brief Constructeur complet pour une carte offensive
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param degatsInfliges Points de dégâts infligés à l'adversaire (valeur principale)
     * @param degatsSubis Points de dégâts subis par l'attaquant (valeur secondaire)
     * @param typeOffensif Type spécifique de carte offensive
     * @param cout Coût d'achat de la carte
     */
    public CarteOffensive(String nomCarte, String description, int degatsInfliges, 
                          int degatsSubis, TypeOffensif typeOffensif, int cout) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, degatsInfliges, degatsSubis, cout);
        this.typeOffensif = typeOffensif;
        this.estJouee = false;
        this.coutSpecial = 0;
    }
    
    /**
     * @brief Constructeur pour une carte de soin
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param vieGagne Points de vie gagnés
     */
    public CarteOffensive(String nomCarte, String description, int vieGagne) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, vieGagne, 0);
        this.typeOffensif = TypeOffensif.SOIN;
    }
    
    /**
     * @brief Constructeur pour un coup spécial
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param valeur Valeur de l'effet
     * @param coutSpecial Coût spécial pour utiliser la carte
     */
    public CarteOffensive(String nomCarte, String description, int valeur, int coutSpecial) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, valeur, 0);
        this.typeOffensif = TypeOffensif.COUP_SPECIAL;
        this.coutSpecial = coutSpecial;
    }
    
    /**
     * @brief Constructeur pour une carte de trésor offensive
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orVole Or volé à l'adversaire
     * @param estTresor Indique qu'il s'agit d'une carte trésor (pour disambiguation des constructeurs)
     */
    public CarteOffensive(String nomCarte, String description, int orVole, boolean estTresor) {
        super(TypeCarte.OFFENSIVE, nomCarte, description, orVole, 0);
        this.typeOffensif = TypeOffensif.TRESOR_OFFENSIF;
    }
    
    /**
     * @brief Factory method pour créer une carte offensive d'attaque à partir d'une CarteAttaque
     * @param carteAttaque L'ancienne CarteAttaque à convertir
     * @return Une nouvelle CarteOffensive
     */
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
    
    /**
     * @brief Factory method pour créer une carte offensive de soin à partir d'une CarteSoin
     * @param carteSoin L'ancienne CarteSoin à convertir
     * @return Une nouvelle CarteOffensive
     */
    public static CarteOffensive fromCarteSoin(CarteSoin carteSoin) {
        CarteOffensive carte = new CarteOffensive(
            carteSoin.getNomCarte(),
            carteSoin.getDescription(),
            carteSoin.getVieGagne()
        );
        carte.setCout(carteSoin.getCout());
        return carte;
    }
    
    /**
     * @brief Factory method pour créer une carte offensive de coup spécial à partir d'une CarteCoupSpecial
     * @param carteCoupSpecial L'ancienne CarteCoupSpecial à convertir
     * @return Une nouvelle CarteOffensive
     */
    public static CarteOffensive fromCarteCoupSpecial(CarteCoupSpecial carteCoupSpecial) {
        return new CarteOffensive(
            carteCoupSpecial.getNomCarte(),
            carteCoupSpecial.getDescription(),
            carteCoupSpecial.getValeur(),
            carteCoupSpecial.getCoutSpecial()
        );
    }

    /**
     * @brief Récupère le type spécifique de la carte offensive
     * @return Type spécifique de la carte
     */
    public TypeOffensif getTypeOffensif() {
        return typeOffensif;
    }
    
    /**
     * @brief Définit le type spécifique de la carte offensive
     * @param typeOffensif Nouveau type spécifique
     */
    public void setTypeOffensif(TypeOffensif typeOffensif) {
        this.typeOffensif = typeOffensif;
    }
    
    /**
     * @brief Vérifie si la carte a déjà été jouée
     * @return true si la carte a déjà été jouée, false sinon
     */
    public boolean estJouee() {
        return estJouee;
    }
    
    /**
     * @brief Définit si la carte a été jouée
     * @param estJouee État de la carte (jouée ou non)
     */
    public void setEstJouee(boolean estJouee) {
        this.estJouee = estJouee;
    }
    
    /**
     * @brief Récupère le coût spécial de la carte
     * @return Coût spécial
     */
    public int getCoutSpecial() {
        return coutSpecial;
    }
    
    /**
     * @brief Définit le coût spécial de la carte
     * @param coutSpecial Nouveau coût spécial
     */
    public void setCoutSpecial(int coutSpecial) {
        this.coutSpecial = coutSpecial;
    }
    
    /**
     * @brief Vérifie si la carte est une attaque directe
     * @return true si la carte est une attaque directe, false sinon
     */
    public boolean estAttaqueDirecte() {
        return this.typeOffensif == TypeOffensif.ATTAQUE_DIRECTE;
    }
    
    /**
     * @brief Vérifie si la carte est un coup spécial
     * @return true si la carte est un coup spécial, false sinon
     */
    public boolean estCoupSpecial() {
        return this.typeOffensif == TypeOffensif.COUP_SPECIAL;
    }
    
    /**
     * @brief Vérifie si la carte est un soin
     * @return true si la carte est un soin, false sinon
     */
    public boolean estSoin() {
        return this.typeOffensif == TypeOffensif.SOIN;
    }
    
    /**
     * @brief Vérifie si la carte est un trésor offensif
     * @return true si la carte est un trésor offensif, false sinon
     */
    public boolean estTresorOffensif() {
        return this.typeOffensif == TypeOffensif.TRESOR_OFFENSIF;
    }
    
    /**
     * @brief Récupère les points de dégâts infligés par la carte
     * @return Points de dégâts infligés
     */
    public int getDegatsInfliges() {
        return getValeur();
    }
    
    /**
     * @brief Récupère les points de dégâts subis par l'attaquant
     * @return Points de dégâts subis
     */
    public int getDegatsSubis() {
        return getValeurSecondaire();
    }

    /**
     * @brief Récupère les points de vie gagnés si la carte est de type SOIN
     * @return Points de vie gagnés, ou 0 sinon
     */
    public int getVieGagnee() {
        return (this.typeOffensif == TypeOffensif.SOIN) ? getValeur() : 0;
    }

    /**
     * @brief Récupère l'or volé si la carte est de type TRESOR_OFFENSIF
     * @return Or volé, ou 0 sinon
     */
    public int getOrVole() {
        return (this.typeOffensif == TypeOffensif.TRESOR_OFFENSIF) ? getValeur() : 0;
    }
    
    /**
     * @brief Retourne une représentation textuelle de la carte
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
            case TRESOR_OFFENSIF:
                sb.append("\nOr volé: ").append(getOrVole());
                break;
        }
        
        return sb.toString();
    }
}