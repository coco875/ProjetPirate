package carte;

/**
 * @brief Classe représentant une carte stratégique
 * 
 * Cette classe représente toutes les cartes qui peuvent être placées dans la zone stratégique.
 * Elle fusionne les fonctionnalités des anciennes cartes de popularité, passive, spéciale et trésor.
 */
public class CarteStrategique extends Carte {
    
    /** Type spécifique de carte stratégique */
    private TypeStrategique typeStrategique;
    /** Durée d'effet en nombre de tours pour les cartes passives */
    private int duree;
    /** Description de l'effet spécial pour les cartes spéciales */
    private String effetSpecial;
    /** Indique si la carte peut être réutilisée (pour les cartes spéciales) */
    private boolean estReutilisable;
    
    /**
     * @brief Types spécifiques de cartes stratégiques
     */
    public enum TypeStrategique {
        POPULARITE,     // Cartes augmentant la popularité
        PASSIVE,        // Cartes à effet passif sur plusieurs tours
        SPECIALE,       // Cartes à effets spéciaux uniques
        TRESOR          // Cartes de trésor (or gagné/perdu)
    }
    
    /**
     * @brief Constructeur standard pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (valeur principale)
     * @param degatsSubis Points de dégâts subis par le joueur (valeur secondaire)
     */
    public CarteStrategique(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, populariteGagnee, degatsSubis);
        this.typeStrategique = TypeStrategique.POPULARITE;
        this.duree = 1; // Par défaut, effet sur 1 tour
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur complet pour une carte de popularité
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param populariteGagnee Points de popularité gagnés (valeur principale)
     * @param degatsSubis Points de dégâts subis par le joueur (valeur secondaire)
     * @param cout Coût d'achat de la carte
     */
    public CarteStrategique(String nomCarte, String description, int populariteGagnee, 
                            int degatsSubis, int cout) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, populariteGagnee, degatsSubis, cout);
        this.typeStrategique = TypeStrategique.POPULARITE;
        this.duree = 1; // Par défaut, effet sur 1 tour
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur pour une carte passive
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param valeur Valeur de l'effet passif
     * @param duree Durée de l'effet en nombre de tours
     * @param typeEffet Description du type d'effet passif
     */
    public CarteStrategique(String nomCarte, String description, int valeur, 
                            int duree, String typeEffet) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0);
        this.typeStrategique = TypeStrategique.PASSIVE;
        this.duree = duree;
        this.effetSpecial = typeEffet;
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur pour une carte passive avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param valeur Valeur de l'effet passif
     * @param duree Durée de l'effet en nombre de tours
     * @param typeEffet Description du type d'effet passif
     * @param cout Coût d'achat de la carte
     */
    public CarteStrategique(String nomCarte, String description, int valeur, 
                            int duree, String typeEffet, int cout) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0, cout);
        this.typeStrategique = TypeStrategique.PASSIVE;
        this.duree = duree;
        this.effetSpecial = typeEffet;
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur pour une carte spéciale
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param effetSpecial Description de l'effet spécial
     * @param valeur Valeur de l'effet spécial
     */
    public CarteStrategique(String nomCarte, String description, String effetSpecial, int valeur) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0);
        this.typeStrategique = TypeStrategique.SPECIALE;
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur pour une carte spéciale avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param effetSpecial Description de l'effet spécial
     * @param valeur Valeur de l'effet spécial
     * @param cout Coût d'achat de la carte
     */
    public CarteStrategique(String nomCarte, String description, String effetSpecial, 
                            int valeur, int cout) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0, cout);
        this.typeStrategique = TypeStrategique.SPECIALE;
        this.effetSpecial = effetSpecial;
        this.estReutilisable = false;
    }
    
    /**
     * @brief Constructeur pour une carte de trésor
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné par le joueur
     * @param orPerdu Or perdu par le joueur
     * @param estTresor Indique qu'il s'agit d'une carte trésor (pour disambiguation des constructeurs)
     */
    public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, boolean estTresor) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0);
        this.typeStrategique = TypeStrategique.TRESOR;
        setOrGagne(orGagne);
        setOrPerdu(orPerdu);
    }
    
    /**
     * @brief Constructeur pour une carte de trésor avec coût
     * @param nomCarte Nom de la carte
     * @param description Description de la carte
     * @param orGagne Or gagné par le joueur
     * @param orPerdu Or perdu par le joueur
     * @param estTresor Indique qu'il s'agit d'une carte trésor (pour disambiguation des constructeurs)
     * @param cout Coût d'achat de la carte
     */
    public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, 
                            boolean estTresor, int cout) {
        super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0, cout);
        this.typeStrategique = TypeStrategique.TRESOR;
        setOrGagne(orGagne);
        setOrPerdu(orPerdu);
    }
    
    /**
     * @brief Factory method pour créer une carte stratégique de popularité à partir d'une CartePopularite
     * @param cartePopularite L'ancienne CartePopularite à convertir
     * @return Une nouvelle CarteStrategique
     */
    public static CarteStrategique fromCartePopularite(CartePopularite cartePopularite) {
        return new CarteStrategique(
            cartePopularite.getNomCarte(),
            cartePopularite.getDescription(),
            cartePopularite.getPopularite(),
            cartePopularite.getDegatsSubisPopularite(),
            cartePopularite.getCout()
        );
    }
    
    /**
     * @brief Factory method pour créer une carte stratégique passive à partir d'une CartePassive
     * @param cartePassive L'ancienne CartePassive à convertir
     * @return Une nouvelle CarteStrategique
     */
    public static CarteStrategique fromCartePassive(CartePassive cartePassive) {
        return new CarteStrategique(
            cartePassive.getNomCarte(),
            cartePassive.getDescription(),
            cartePassive.getValeur(),
            cartePassive.getDuree(),
            cartePassive.getTypeEffet(),
            cartePassive.getCout()
        );
    }
    
    /**
     * @brief Factory method pour créer une carte stratégique spéciale à partir d'une CarteSpeciale
     * @param carteSpeciale L'ancienne CarteSpeciale à convertir
     * @return Une nouvelle CarteStrategique
     */
    public static CarteStrategique fromCarteSpeciale(CarteSpeciale carteSpeciale) {
        return new CarteStrategique(
            carteSpeciale.getNomCarte(),
            carteSpeciale.getDescription(),
            carteSpeciale.getEffetSpecial(),
            carteSpeciale.getValeur(),
            carteSpeciale.getCout()
        );
    }
    
    /**
     * @brief Factory method pour créer une carte stratégique de trésor à partir d'une CarteTresor
     * @param carteTresor L'ancienne CarteTresor à convertir
     * @return Une nouvelle CarteStrategique
     */
    public static CarteStrategique fromCarteTresor(CarteTresor carteTresor) {
        CarteStrategique carte = new CarteStrategique(
            carteTresor.getNomCarte(),
            carteTresor.getDescription(),
            carteTresor.getOrGagne(),
            carteTresor.getOrPerdu(),
            true
        );
        carte.setCout(carteTresor.getCout());
        return carte;
    }

    /**
     * @brief Récupère le type spécifique de la carte stratégique
     * @return Type spécifique de la carte
     */
    public TypeStrategique getTypeStrategique() {
        return typeStrategique;
    }
    
    /**
     * @brief Définit le type spécifique de la carte stratégique
     * @param typeStrategique Nouveau type spécifique
     */
    public void setTypeStrategique(TypeStrategique typeStrategique) {
        this.typeStrategique = typeStrategique;
    }
    
    /**
     * @brief Récupère la durée de l'effet
     * @return Durée en nombre de tours
     */
    public int getDuree() {
        return duree;
    }
    
    /**
     * @brief Définit la durée de l'effet
     * @param duree Nouvelle durée en nombre de tours
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }
    
    /**
     * @brief Récupère la description de l'effet spécial
     * @return Description de l'effet spécial
     */
    public String getEffetSpecial() {
        return effetSpecial;
    }
    
    /**
     * @brief Définit la description de l'effet spécial
     * @param effetSpecial Nouvelle description de l'effet spécial
     */
    public void setEffetSpecial(String effetSpecial) {
        this.effetSpecial = effetSpecial;
    }
    
    /**
     * @brief Vérifie si la carte est réutilisable
     * @return true si la carte est réutilisable, false sinon
     */
    public boolean estReutilisable() {
        return estReutilisable;
    }
    
    /**
     * @brief Définit si la carte est réutilisable
     * @param estReutilisable État de réutilisabilité de la carte
     */
    public void setReutilisable(boolean estReutilisable) {
        this.estReutilisable = estReutilisable;
    }
    
    /**
     * @brief Diminue la durée de l'effet passif
     * @return Vrai si l'effet est toujours actif, faux sinon
     */
    public boolean reduireDuree() {
        duree--;
        return duree > 0;
    }
    
    /**
     * @brief Vérifie si la carte est une carte de popularité
     * @return true si c'est une carte de popularité, false sinon
     */
    public boolean estPopularite() {
        return this.typeStrategique == TypeStrategique.POPULARITE;
    }
    
    /**
     * @brief Vérifie si la carte est une carte passive
     * @return true si c'est une carte passive, false sinon
     */
    public boolean estPassive() {
        return this.typeStrategique == TypeStrategique.PASSIVE;
    }
    
    /**
     * @brief Vérifie si la carte est une carte spéciale
     * @return true si c'est une carte spéciale, false sinon
     */
    public boolean estSpeciale() {
        return this.typeStrategique == TypeStrategique.SPECIALE;
    }
    
    /**
     * @brief Vérifie si la carte est une carte de trésor
     * @return true si c'est une carte de trésor, false sinon
     */
    public boolean estTresor() {
        return this.typeStrategique == TypeStrategique.TRESOR;
    }
    
    /**
     * @brief Récupère les points de popularité gagnés
     * @return Points de popularité gagnés
     */
    public int getPopulariteGagnee() {
        return getValeur();
    }
    
    /**
     * @brief Récupère les points de dégâts subis
     * @return Points de dégâts subis
     */
    public int getDegatsSubis() {
        return getValeurSecondaire();
    }
    
    /**
     * @brief Obtient le type d'effet pour les cartes passives
     * @return Type d'effet pour les cartes passives
     */
    public String getTypeEffet() {
        return this.effetSpecial;
    }
    
    /**
     * @brief Retourne une représentation textuelle de la carte
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNomCarte()).append("\n").append(getDescription());
        
        switch (typeStrategique) {
            case POPULARITE:
                sb.append("\nPopularité gagnée: ").append(getPopulariteGagnee());
                sb.append("\nDégâts subis: ").append(getDegatsSubis());
                break;
            case PASSIVE:
                sb.append("\nValeur: ").append(getValeur());
                sb.append("\nDurée: ").append(getDuree()).append(" tours");
                sb.append("\nType d'effet: ").append(getEffetSpecial());
                break;
            case SPECIALE:
                sb.append("\nValeur: ").append(getValeur());
                sb.append("\nEffet spécial: ").append(getEffetSpecial());
                sb.append("\nRéutilisable: ").append(estReutilisable() ? "Oui" : "Non");
                break;
            case TRESOR:
                if (getOrGagne() > 0) sb.append("\nOr gagné: ").append(getOrGagne());
                if (getOrPerdu() > 0) sb.append("\nOr perdu: ").append(getOrPerdu());
                break;
        }
        
        return sb.toString();
    }
}